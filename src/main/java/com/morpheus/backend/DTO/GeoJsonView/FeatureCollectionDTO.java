package com.morpheus.backend.DTO.GeoJsonView;

import java.util.List;

import lombok.Data;

@Data
public class FeatureCollectionDTO {
    private String type = "FeatureCollection";
    List<FieldFeatureDTO> features;
}
