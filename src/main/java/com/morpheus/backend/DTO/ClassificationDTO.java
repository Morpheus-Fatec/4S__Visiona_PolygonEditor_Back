package com.morpheus.backend.DTO;

import java.math.BigDecimal;

import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.utilities.Converter;
import com.morpheus.backend.utilities.GeoJsonToJTSConverter;

import lombok.Data;

@Data
public class ClassificationDTO implements Converter {

    @Autowired
    private GeoJsonToJTSConverter geoJsonToJTSConverter;

    private Long id;
    private BigDecimal area;
    private String coordinates;
    private String classEntity;

    public ClassificationDTO(Long id, BigDecimal area, String coordinates, String classEntity) {
        this.id = id;
        this.area = area;
        this.coordinates = coordinates;
        this.classEntity = classEntity;
    }

    @Override
    public MultiPolygon convertStringToMultiPolygon() throws JsonProcessingException {

        MultiPolygon multiPolygonCooordinates = geoJsonToJTSConverter.convertJsonNode(this.coordinates);
        return multiPolygonCooordinates;
    }
}
