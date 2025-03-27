package com.morpheus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.CreateFieldDTO;
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

            Culture farmCulture = new Culture();  
            farmCulture.setName(fieldDTO.getCulture());
            Culture culture = cultureRepository.save(farmCulture);

            Soil farmSoil = new Soil();
            farmSoil.setName(fieldDTO.getSoil());
            Soil soil = soilRepository.save(farmSoil);
    
            Field field = new Field();
            field.setFarm(farm);
            field.setHarvest(fieldDTO.getHarvest());
            field.setCulture(culture);
            field.setSoil(soil);
            field.setName(fieldDTO.getFarmname());
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
    
     public List<FeatureSimpleDTO> getAllFeatureSimpleDTO() {
        List<Object[]> results = fieldRepository.getAllFeatureSimpleDTO();

        return results.stream().map(obj -> {
            PropertiesDTO properties = new PropertiesDTO();
            properties.setId(((Number) obj[0]).longValue());
            properties.setNome((String) obj[1]);
            properties.setFazenda((String) obj[2]);
            properties.setCultura((String) obj[3]);

            GeometryDTO geometry = new GeometryDTO();
            geometry.setCoordinates((String) obj[4]);  // Lembre-se que `coordinates` é uma **string**

            FeatureSimpleDTO dto = new FeatureSimpleDTO();
            dto.setProperties(properties);
            dto.setGeometry(geometry);
            dto.setStatus((String) obj[5]);

            return dto;
        }).collect(Collectors.toList());
    }

   
}