package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.DTO.FieldDTO;
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

@Service
public class FieldService {

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
        validateFieldDTO(fieldDTO);
        try {

            Farm farm = farmRepository.findByFarmName(fieldDTO.getNameFarm())
            .orElseGet(() -> {
                Farm newFarm = new Farm();
                newFarm.setFarmName(fieldDTO.getNameFarm());
                return farmRepository.save(newFarm);
            });

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

        } catch (DefaultException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new DefaultException("Erro ao processar coordenadas GeoJSON: " + e.getMessage());
        } catch (Exception e) {
            throw new DefaultException("Erro inesperado ao criar o talhão: " + e.getMessage());
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
        
        FeatureCollectionDTO featureCollection = new FeatureCollectionDTO();
        featureCollection.setFeatures(fieldFeatureDTO);
        
        return featureCollection;
    }

    private void validateFieldDTO(CreateFieldDTO fieldDTO) {
        if (fieldDTO.getNameFarm() == null || fieldDTO.getNameFarm().isEmpty()) {
            throw new DefaultException("O nome da fazenda não pode ser nulo ou vazio.");
        }
        if (fieldDTO.getNameField() == null || fieldDTO.getNameField().isEmpty()) {
            throw new DefaultException("O nome do talhão não pode ser nulo ou vazio.");
        }
        if (fieldDTO.getCulture() == null || fieldDTO.getCulture().isEmpty()) {
            throw new DefaultException("A cultura não pode ser nula ou vazia.");
        }
        if (fieldDTO.getArea() == null || fieldDTO.getArea().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DefaultException("A área deve ser maior que zero.");
        }
    }
}