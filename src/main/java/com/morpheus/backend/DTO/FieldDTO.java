package com.morpheus.backend.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FieldDTO {

    private Long id;
    private Long scanningId;
    private FarmDTO farm;
    private String harvest;
    private String culture;
    private String soil;
    private String name;
    private BigDecimal area;
    private String productivity;
    private String status;
    private String coordinates;

    public FieldDTO(
        Long id,
        Long scanningId,
        String name,
        String farmName,
        String culture,
        String coordinates,
        String status,
        BigDecimal area,
        String harvest,
        String farmCity,
        String farmState,
        String soil
    ) {
        this.id = id;
        this.name = name;
        this.scanningId = scanningId;
        this.harvest = harvest;
        this.area = area;
        this.soil = soil;
        this.status = status;
        this.culture = culture;
        this.farm = new FarmDTO(farmName, farmState, farmCity);
        this.coordinates = coordinates;
    }
}