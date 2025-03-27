package com.morpheus.backend.DTO.GeoJsonView;

import lombok.Data;

@Data
public class FeatureSimpleDTO {

    private String type = "FeatureCollection";
    private PropertiesDTO properties;
    private GeometryDTO geometry;
    private String status;

}
