package com.morpheus.backend.DTO.GeoJsonView.classification;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassificationProperties {

    private Long id;
    private BigDecimal area;
    private String classEntity;

}
