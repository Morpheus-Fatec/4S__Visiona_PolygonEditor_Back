package com.morpheus.backend.DTO.GeoJsonView.classification;

import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;

import lombok.Data;

@Data
public class ClassificationFeature {
    private final String type = "Feature";
    private ClassificationProperties properties;
    private GeometryDTO geometry;

}
