package com.morpheus.backend.DTO.GeoJsonView;

import java.math.BigDecimal;

import com.morpheus.backend.DTO.FarmDTO;

import lombok.Data;

@Data
public class PropertiesDTO {

    private Long id;
    private String name;
    private FarmDTO farm;
    private String culture;
    private BigDecimal area;
    private String harvest;
    private String status;
}

