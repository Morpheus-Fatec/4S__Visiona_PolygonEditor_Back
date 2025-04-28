package com.morpheus.backend.DTO.GeoJsonView.classification;

import java.util.List;

import lombok.Data;

@Data
public class ClassificationColletion {
    private List<ClassificationFeature> features;

    public ClassificationColletion(List<ClassificationFeature> features) {
        this.features = features;
    }
   

}
