package com.morpheus.backend.DTO.GeoJsonView.revisionClassification;

import java.util.List;

import com.morpheus.backend.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisionClassificationCollectionOut {
    private final String type = "FeatureCollection";
    private Long idField;
    private Long userResponsable;
    private Status status;
    private List<RevisionFeature> features;

}
