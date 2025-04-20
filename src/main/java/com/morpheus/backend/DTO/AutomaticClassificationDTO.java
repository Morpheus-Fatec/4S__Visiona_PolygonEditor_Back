package com.morpheus.backend.DTO;

import java.math.BigDecimal;

import org.locationtech.jts.geom.MultiPolygon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.utilities.Converter;
import com.morpheus.backend.utilities.GeoJsonToJTSConverter;

import lombok.Data;

@Data
public class AutomaticClassificationDTO implements Converter {

    private static GeoJsonToJTSConverter geoJsonToJTSConverter = new GeoJsonToJTSConverter();;

    private Long id;
    private BigDecimal area;
    private String coordinates;
    private String classEntity;

    public AutomaticClassificationDTO(Long id, BigDecimal area, String coordinates, String classEntity) {
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
