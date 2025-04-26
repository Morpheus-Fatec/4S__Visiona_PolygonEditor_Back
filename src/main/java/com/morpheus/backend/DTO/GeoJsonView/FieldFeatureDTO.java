package com.morpheus.backend.DTO.GeoJsonView;

import java.util.List;

import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationColletion;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationFeatureCollection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldFeatureDTO {

    private final String type = "Feature";
    private PropertiesDTO properties;
    private GeometryDTO geometry;
    private ClassificationColletion automatic;
    private ManualClassificationFeatureCollection manual;
    private List<ImageViewDTO> images;


    public FieldFeatureDTO(PropertiesDTO properties, GeometryDTO geometry, List<ImageViewDTO> images, ClassificationColletion automatic) {
        this.properties = properties;
        this.geometry = geometry;
        this.images = images;
        this.automatic = automatic;
    }
}
