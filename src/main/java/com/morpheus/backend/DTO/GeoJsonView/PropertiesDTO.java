package com.morpheus.backend.DTO.GeoJsonView;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PropertiesDTO {

    private Long id;
    private String nome;
    private String fazenda;
    private String cultura;
    private BigDecimal area;
    private String harvest;
}

