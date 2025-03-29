package com.morpheus.backend.DTO;

import java.util.List;

import com.morpheus.backend.DTO.GeoJsonView.FieldFeatureDTO;

import lombok.Data;

@Data
public class FieldGeoJsonDTO {

    private final String type = "FeatureCollection";
    private List<FieldFeatureDTO> features;

}
