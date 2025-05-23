package com.morpheus.backend.DTO.GeoJsonView.manualClassification;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManualClassificationFeatureCollection {
    private Long idUserResponsable;
    private Long idField;
    private List<ManualClassificationFeature> features = new ArrayList<ManualClassificationFeature>();

}
