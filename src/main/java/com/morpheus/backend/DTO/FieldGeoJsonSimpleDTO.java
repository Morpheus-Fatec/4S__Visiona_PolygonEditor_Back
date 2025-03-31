package com.morpheus.backend.DTO;

import java.util.List;

import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;

import lombok.Data;

@Data
public class FieldGeoJsonSimpleDTO {

    private final String type = "FeatureCollection";
    private List<FeatureSimpleDTO> features;

}
