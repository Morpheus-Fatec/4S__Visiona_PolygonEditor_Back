package com.morpheus.backend.DTO.GeoJsonView.revisionClassification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisionClassificationCollectionDTO {
    private Long id;
    private String coordinates;
    private String comment;
}
