package com.morpheus.backend.DTO.GeoJsonView;

import java.util.List;

import com.morpheus.backend.DTO.ClassificationDTO;

import lombok.Data;

@Data
public class FieldFeatureDTO {

    private final String type = "FeatureCollection";
    private PropertiesDTO properties;
    private GeometryDTO geometry;
    private ClassificationDTO classification;
    private List<ImageViewDTO> images;

}
