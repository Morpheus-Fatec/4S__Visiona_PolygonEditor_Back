package com.morpheus.backend.DTO.GeoJsonView;

import java.math.BigDecimal;

import com.morpheus.backend.DTO.CultureDTO;
import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.DTO.SoilDTO;

import lombok.Data;

@Data
public class PropertiesDTO {

    private Long id;
    private String name;
    private CultureDTO culture;
    private BigDecimal area;
    private String harvest;
    private String status;
    private SoilDTO soil;
    private Float productivity;
    private FarmDTO farm;
}

