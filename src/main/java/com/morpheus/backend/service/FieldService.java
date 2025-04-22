package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.CultureDTO;
import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.DTO.FieldUpdatesDTO;
import com.morpheus.backend.DTO.SoilDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.FieldFeatureDTO;
import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;
import com.morpheus.backend.DTO.GeoJsonView.ImageViewDTO;
import com.morpheus.backend.DTO.GeoJsonView.PropertiesDTO;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationColletion;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationFeature;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationProperties;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Image;
import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.entity.Status;
import com.morpheus.backend.repository.ClassificationRepository;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.ImageRepository;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.exceptions.DefaultException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FieldService {

    @Autowired
    FarmService farmService;

    @Autowired
    SoilService soilService;
    
    @Autowired
    CultureService cultureService;

    @Autowired
    private FieldRepository fieldRepository;
    
    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private SoilRepository soilRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    public Field createField(CreateFieldDTO fieldDTO, Scan scan){
        try {
            if (fieldDTO.getNameFarm().isEmpty()){
                throw new DefaultException("Farm não pode ser nulo.");
            }

            Farm farmField = new Farm();
            farmField.setFarmName(fieldDTO.getNameFarm());
            Farm farm = farmRepository.save(farmField);

            Culture culture = cultureRepository.findByName(fieldDTO.getCulture())
            .orElseGet(() -> {
                Culture newCulture = new Culture();
                newCulture.setName(fieldDTO.getCulture());
                return cultureRepository.save(newCulture);
            });

            Soil soil = soilRepository.findByName(fieldDTO.getSoil())
            .orElseGet(() -> {
                Soil newSoil = new Soil();
                newSoil.setName(fieldDTO.getSoil());
                return soilRepository.save(newSoil);
            });
    
            Field field = new Field();
            field.setFarm(farm);
            field.setHarvest(fieldDTO.getHarvest());
            field.setCulture(culture);
            field.setSoil(soil);
            field.setName(fieldDTO.getNameField());
            field.setArea(fieldDTO.getArea());
            field.setProductivity(fieldDTO.getProductivity());
            field.setStatus(Status.fromPortuguese("Pendente"));
            field.setCoordinates(fieldDTO.convertStringToMultiPolygon());
            field.setScanning(scan);
            fieldRepository.save(field);
    
            return field;

    
        } catch (Exception e) {
            throw new DefaultException("Erro ao criar o talhão: " + e.getMessage());
        }
    }

    public FeatureCollectionSimpleDTO getAllFeatureCollectionSimpleDTO(
        String name, String soil, String status, String culture, String harvest, String farmName) {
        
        List<Object[]> results = fieldRepository.getAllFeatureSimpleDTO(name, soil, status, culture, harvest, farmName);
    
        List<FeatureSimpleDTO> featureSimpleDTOList = results.stream().map(obj -> {
            // Criando o DTO da Fazenda
            FarmDTO farmDTO = new FarmDTO();
            farmDTO.setFarmName((String) obj[2]);
            farmDTO.setFarmCity((String) obj[8]);
            farmDTO.setFarmState((String) obj[9]);
    
            // Criando o DTO das propriedades
            PropertiesDTO properties = new PropertiesDTO();
            properties.setId(((Number) obj[0]).longValue());
            properties.setName((String) obj[1]);
            properties.setFarm(farmDTO);
            properties.setCulture((String) obj[3]);
            properties.setArea((BigDecimal) obj[6]);
            properties.setSoil((String) obj[10]);
            properties.setHarvest((String) obj[7]);
            properties.setStatus(Status.valueOf((String) obj[5]).getPortugueseValue());
    
            // Criando o DTO da geometria
            GeometryDTO geometry = new GeometryDTO();
            try {
                geometry.convertToGeoJson((String) obj[4]);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            
            // Criando o DTO da Feature
            FeatureSimpleDTO dto = new FeatureSimpleDTO();
            dto.setProperties(properties);
            dto.setGeometry(geometry);
    
            return dto;
            }).collect(Collectors.toList());
    
            FeatureCollectionSimpleDTO featureCollection = new FeatureCollectionSimpleDTO();
            featureCollection.setFeatures(featureSimpleDTOList);
    
            return featureCollection;
        }
    
    public FeatureCollectionDTO getCompleteFieldById(Long idField) {
        FieldDTO field = fieldRepository.getFieldById(idField).orElseThrow(() -> new DefaultException("Talhão não encontrado."));
        Long scanID = field.getScanningId();
        List<ClassificationDTO> classifications = classificationRepository.getClassificationByFieldId(field.getId());
        List<Image> images = imageRepository.getImagesByScanId(scanID);

        FarmDTO farmDTO = field.getFarm();

        PropertiesDTO properties = new PropertiesDTO();
        properties.setId(field.getId());
        properties.setName(field.getName());
        properties.setArea(field.getArea());
        properties.setCulture(field.getCulture());
        properties.setHarvest(field.getHarvest());
        properties.setStatus(field.getStatus());
        properties.setSoil(field.getSoil());
        properties.setFarm(farmDTO);

        GeometryDTO geometry = new GeometryDTO();
        try{
            geometry.convertToGeoJson(field.getCoordinates());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        List<ClassificationFeature> classificationDTOs = classifications.stream().map(classification -> {
            ClassificationFeature classificationDTO = new ClassificationFeature();
            ClassificationProperties classificationProperties = new ClassificationProperties(classification.getId(), classification.getArea(), classification.getClassEntity());
            GeometryDTO classificationGeometry = new GeometryDTO();
            try {
                classificationGeometry.convertToGeoJson(classification.getCoordinates());
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            classificationDTO.setProperties(classificationProperties);
            classificationDTO.setGeometry(classificationGeometry);
            return classificationDTO;
        }).collect(Collectors.toList());


        List<ImageViewDTO> imageDTOs = images.stream().map(image -> {
            ImageViewDTO imageDTO = new ImageViewDTO();
            imageDTO.setName(image.getName());
            imageDTO.setLink(image.getAddress());
            return imageDTO;
        }).collect(Collectors.toList());

        FieldFeatureDTO fieldFeatureDTO = new FieldFeatureDTO();
        fieldFeatureDTO.setProperties(properties);
        fieldFeatureDTO.setGeometry(geometry);
        fieldFeatureDTO.setImages(imageDTOs);

        ClassificationColletion classificationCollection = new ClassificationColletion();
        classificationCollection.setFeatures(classificationDTOs);
        fieldFeatureDTO.setClassification(classificationCollection);
        

        // Criando e retornando o FeatureCollectionDTO corretamente
        FeatureCollectionDTO featureCollection = new FeatureCollectionDTO();
        featureCollection.setFeatures(fieldFeatureDTO);
        
        return featureCollection;
    }

    public FieldUpdatesDTO updateField(Long id, FieldUpdatesDTO dto) {
        Field field = fieldRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado!"));

        validate(dto);
    
        field.setName(dto.getName());
        field.setHarvest(dto.getHarvest());
        field.setProductivity(dto.getProductivity());
    
        if (dto.getFarm() != null) {
            Farm farm = dto.getFarm();
            FarmDTO farmDTO = new FarmDTO(farm.getFarmName(), farm.getFarmCity(), farm.getFarmState());
            farmService.updateFarm(farm.getId(), farmDTO);
        }
        
        if (dto.getSoil() != null) {
            soilService.updateSoil(dto.getSoil().getId(), dto.getSoil().getName());
        }
        
        if (dto.getCulture() != null) {
            cultureService.updateCulture(dto.getCulture().getId(), dto.getCulture().getName());
        }
    
        Field updatedField = fieldRepository.save(field);

        return mapToDto(updatedField);
    }
    

    private void validate(FieldUpdatesDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new DefaultException("O nome do Talhão é obrigatório.");
        }
        if (dto.getHarvest() == null || dto.getHarvest().trim().isEmpty()) {
            throw new DefaultException("A safra é obrigatória.");
        }
        if (dto.getProductivity() == null) {
            throw new DefaultException("A produtividade é obrigatória.");
        }
    }
    
    private FieldUpdatesDTO mapToDto(Field field) {
        FieldUpdatesDTO dto = new FieldUpdatesDTO();
        dto.setId(field.getId());
        dto.setName(field.getName());
        dto.setArea(field.getArea());
        dto.setHarvest(field.getHarvest());
        dto.setStatus(field.getStatus().toString());
        dto.setProductivity(field.getProductivity());
    
        Farm farm = new Farm();
        farm.setId(field.getFarm().getId());
        farm.setFarmName(field.getFarm().getFarmName());
        farm.setFarmCity(field.getFarm().getFarmCity());
        farm.setFarmState(field.getFarm().getFarmState());
        dto.setFarm(farm);

        Culture culture = new Culture();
        culture.setId(field.getCulture().getId());
        culture.setName(field.getCulture().getName());
        dto.setCulture(culture);

        Soil soil = new Soil();
        soil.setId(field.getSoil().getId());
        soil.setName(field.getSoil().getName());
        dto.setSoil(soil);
    
        return dto;
    }
    

}