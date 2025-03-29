package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.FieldFeatureDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;
import com.morpheus.backend.DTO.GeoJsonView.ImageViewDTO;
import com.morpheus.backend.DTO.GeoJsonView.PropertiesDTO;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.entity.Status;
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
    
    public FeatureCollectionDTO getCompleteField(){
        List<Object[]> results = fieldRepository.getAllCompleteField();
        List<Object[]> imageResults = imageRepository.getAllImageView();

        List<FieldFeatureDTO> completeField = results.stream().map(obj -> {
            FarmDTO farm = new FarmDTO();
            farm.setFarmName((String)obj[0]);
            farm.setFarmCity((String)obj[1]);
            farm.setFarmState((String)obj[2]);

            PropertiesDTO properties = new PropertiesDTO();
            properties.setId((Long)obj[3]);
            properties.setName((String)obj[4]);
            properties.setCulture((String)obj[5]);
            properties.setArea((BigDecimal)obj[6]);
            properties.setHarvest((String)obj[7]);
            properties.setStatus((String)obj[8]);
            properties.setFarm(farm);
            properties.setSoil((String)obj[9]);
            properties.setProductivity((Float)obj[10]);

            ClassificationDTO classification = new ClassificationDTO();
            classification.setArea((BigDecimal)obj[11]);
            classification.setCoordinates((String)obj[12]);
            classification.setClassEntity((String)obj[13]);

            GeometryDTO geometry = new GeometryDTO();
            geometry.setCoordinates((String)obj[14]);

            List<ImageViewDTO> images = imageResults.stream().map(img->{
                ImageViewDTO image = new ImageViewDTO();
                image.setLink((String)obj[0]);
                image.setName((String)obj[1]);
                
                return image;
            }).collect(Collectors.toList());

            FieldFeatureDTO fieldFeatureDTO = new FieldFeatureDTO();
            fieldFeatureDTO.setProperties(properties);
            fieldFeatureDTO.setGeometry(geometry);
            fieldFeatureDTO.setClassification(classification);
            fieldFeatureDTO.setImages(images);

            return fieldFeatureDTO;
        }).collect(Collectors.toList());
    
        FeatureCollectionDTO featureCollection = new FeatureCollectionDTO();
        featureCollection.setFeatures(completeField);

        return featureCollection;
    }
}