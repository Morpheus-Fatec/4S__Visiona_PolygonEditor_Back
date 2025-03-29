package com.morpheus.backend.DTO.GeoJsonView;

import java.math.BigDecimal;

import com.morpheus.backend.DTO.FarmDTO;

import lombok.Data;

@Data
public class PropertiesDTO {

    private Long id;
    private String name;
    private String culture;
    private BigDecimal area;
    private String harvest;
    private String status;
    private String soil;
    private Float productivity;
    private FarmDTO farm;
}

