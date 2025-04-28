package com.morpheus.backend.DTO.GeoJsonView.manualClassification;

import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualClassificationFeature {
    private final String type = "Feature";
    private ClassificationProperties properties;
    private GeometryDTO geometry;
}
