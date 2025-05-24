package com.morpheus.backend.DTO.GeoJsonView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class GeometryDTO {

    private String type = "MultiPolygon";
    private String coordinates;

    public void convertToGeoJson(String geoJson) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode geoJsonNode = objectMapper.readTree(geoJson);

        // Acessando a chave "coordinates"
        JsonNode coordinatesNode = geoJsonNode.get("coordinates");

        // Se quiser como String formatada
        String coordinatesJson = coordinatesNode.toString();
        this.coordinates = coordinatesJson;
    }
}
