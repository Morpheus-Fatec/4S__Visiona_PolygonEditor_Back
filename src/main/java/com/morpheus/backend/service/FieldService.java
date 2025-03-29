package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.FarmDTO;
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
import com.morpheus.backend.entity.Classification;
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
        try {
            if (fieldDTO.getFarmname().isEmpty()){
                throw new DefaultException("Farm não pode ser nulo.");
            }

            Farm farmField = new Farm();
            farmField.setFarmName(fieldDTO.getFarmname());
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
            field.setName(fieldDTO.getName());
            field.setArea(fieldDTO.getArea());
            field.setProductivity(fieldDTO.getProductivity());
            field.setStatus(Status.fromPortuguese("Pendente"));
            field.setCoordinates(fieldDTO.getCoordinates());
            field.setScanning(scan);
            fieldRepository.save(field);
    
            return field;

    
        } catch (Exception e) {
            throw new DefaultException("Erro ao criar o talhão: " + e.getMessage());
        }
    }
    
    public FeatureCollectionSimpleDTO getAllFeatureCollectionSimpleDTO() {
        List<Object[]> results = fieldRepository.getAllFeatureSimpleDTO();
    
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
            properties.setStatus((String) obj[5]);
    
            // Criando o DTO da geometria
            GeometryDTO geometry = new GeometryDTO();
            geometry.setCoordinates((String) obj[4]);
    
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
        Field field = fieldRepository.getFieldById(idField).orElseThrow(() -> new DefaultException("Campo não encontrado."));
        Long scanID = field.getScanning().getId();
        List<Classification> classifications = classificationRepository.getClassificationByFieldId(field.getId());
        List<Image> images = imageRepository.getImagesByScanId(scanID);

        FarmDTO farmDTO = new FarmDTO();
        farmDTO.setFarmName(field.getFarm().getFarmName());
        farmDTO.setFarmCity(field.getFarm().getFarmCity());
        farmDTO.setFarmState(field.getFarm().getFarmState());

        PropertiesDTO properties = new PropertiesDTO();
        properties.setId(field.getId());
        properties.setName(field.getName());
        properties.setArea(field.getArea());
        properties.setCulture(field.getCulture().getName());
        properties.setHarvest(field.getHarvest());
        properties.setStatus(field.getStatus().getPortugueseValue());
        properties.setSoil(field.getSoil().getName());
        properties.setFarm(farmDTO);

        GeometryDTO geometry = new GeometryDTO();
        geometry.setCoordinates(field.getCoordinates());


        List<ClassificationFeature> classificationDTOs = classifications.stream().map(classification -> {
            ClassificationFeature classificationDTO = new ClassificationFeature();
            ClassificationProperties classificationProperties = new ClassificationProperties(classification.getId(), classification.getArea(), classification.getClassEntity().getName());
            GeometryDTO classificationGeometry = new GeometryDTO();
            classificationGeometry.setCoordinates(classification.getOriginalCoordinates());

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
}