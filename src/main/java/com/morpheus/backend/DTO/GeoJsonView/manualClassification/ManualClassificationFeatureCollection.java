package com.morpheus.backend.DTO.GeoJsonView.manualClassification;

import java.util.List;

import lombok.Data;

@Data
public class ManualClassificationFeatureCollection {
    private String type = "FeatureCollection";
    private Long idUserResponsable;
    private Long idField;
    private List<ManualClassificationFeature> features;

    public ManualClassificationFeatureCollection(List<ManualClassificationFeature> features) {
        this.features = features;
    }
}
