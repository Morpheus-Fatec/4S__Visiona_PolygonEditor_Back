package com.morpheus.backend.DTO.GeoJsonView;

import org.locationtech.jts.geom.MultiPolygon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morpheus.backend.utilities.GeoJsonToJTSConverter;

import lombok.Data;

@Data
public class GeometryDTO {

    private String type = "MultiPolygon";
    private String coordinates;

    public void convertToGeoJson(String geoJson) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode geoJsonNode = objectMapper.readTree(geoJson);

        JsonNode coordinatesNode = geoJsonNode.get("coordinates");

        String coordinatesJson = coordinatesNode.toString();
        this.coordinates = coordinatesJson;
    }

    public MultiPolygon convertStringToMultiPolygon() throws JsonProcessingException {

        GeoJsonToJTSConverter geoJsonToJTSConverter = new GeoJsonToJTSConverter();

        MultiPolygon multiPolygonCooordinates = geoJsonToJTSConverter.convertJsonNode(this.coordinates);
        return multiPolygonCooordinates;
    }
    
}
