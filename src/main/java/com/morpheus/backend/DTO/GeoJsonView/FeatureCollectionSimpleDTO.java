package com.morpheus.backend.DTO.GeoJsonView;

import java.util.List;

import lombok.Data;

@Data
public class FeatureCollectionSimpleDTO {
    private String type = "FeatureCollection";
    List<FeatureSimpleDTO> features;
}
