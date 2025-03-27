package com.morpheus.backend.DTO.GeoJsonView;

import lombok.Data;

@Data
public class FeatureSimpleDTO {
    private final String type = "Feature";
    private PropertiesDTO properties;
    private GeometryDTO geometry;
    

}
