package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;
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
            field.setName(fieldDTO.getNameField());
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
            properties.setHarvest((String) obj[7]);  
            properties.setStatus((String) obj[5]);
            properties.setSoil((String) obj[10]);
    
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

}