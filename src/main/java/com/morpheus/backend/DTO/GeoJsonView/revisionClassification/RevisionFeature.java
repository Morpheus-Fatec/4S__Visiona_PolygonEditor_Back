package com.morpheus.backend.DTO.GeoJsonView.revisionClassification;

import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisionFeature {
    private final String type = "Feature";
    private RevisionProperties properties;
    private GeometryDTO geometry;
}
