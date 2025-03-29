package com.morpheus.backend.DTO.GeoJsonView;

import lombok.Data;

@Data
public class FeatureCollectionDTO {
    private String type = "FeatureCollection";
    FieldFeatureDTO features;
}
