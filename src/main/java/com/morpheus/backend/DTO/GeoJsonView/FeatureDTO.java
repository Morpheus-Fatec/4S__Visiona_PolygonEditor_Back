package com.morpheus.backend.DTO.GeoJsonView;

import java.util.List;

import com.morpheus.backend.DTO.AutomaticClassificationDTO;

import lombok.Data;

@Data
public class FeatureDTO {

    private final String type = "FeatureCollection";
    private PropertiesDTO properties;
    private GeometryDTO geometry;
    private AutomaticClassificationDTO classification;
    private List<ImageViewDTO> images;

}
