package com.morpheus.backend.DTO.GeoJsonView.classification;

import java.util.List;

import lombok.Data;

@Data
public class ClassificationColletion {
    private String type = "FeatureCollection";
    private List<ClassificationFeature> features;
   

}
