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

        public FieldDTO(Long id, Long scanningId, String name, String harvest, BigDecimal area, String productivity, String status, String culture, String farmName, String farmState, String FarmCity, String coordinates) {
        this.id = id;
        this.scanningId = scanningId;
        this.name = name;
        this.harvest = harvest;
        this.area = area;
        this.productivity = productivity;
        this.status = status;
        this.culture = culture;
        this.farm = new FarmDTO(farmName, farmState, FarmCity);
        this.coordinates= coordinates;
    }
}
