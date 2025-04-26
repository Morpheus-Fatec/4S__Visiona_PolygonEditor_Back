package com.morpheus.backend.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FieldDTO {

    private Long id;
    private Long scanningId;
    private FarmDTO farm;
    private String harvest;
    private CultureDTO culture;
    private SoilDTO soil;
    private String name;
    private BigDecimal area;
    private String productivity;
    private String status;
    private String coordinates;

    public FieldDTO(
        Long id,
        Long scanningId,
        String name,
        String productivity,
        String farmName,
        Long cultureId,
        String cultureNome,
        String coordinates,
        String status,
        BigDecimal area,
        String harvest,
        String farmCity,
        String farmState,
        Long farm_id,
        String soilNome,
        Long soilId
    ) {
        this.id = id;
        this.name = name;
        this.scanningId = scanningId;
        this.harvest = harvest;
        this.area = area;
        this.soil = new SoilDTO(soilId, soilNome);
        this.status = status;
        this.culture = new CultureDTO(cultureId, cultureNome);
        this.farm = new FarmDTO(farmName, farmCity, farmState, farm_id);
        this.coordinates = coordinates;
        this.productivity = productivity;
    }

    public FieldDTO(
        Long id,
        String name,
        String productivity,
        String farmName,
        Long cultureId,
        String cultureNome,
        String coordinates,
        String status,
        BigDecimal area,
        String harvest,
        String farmCity,
        String farmState,
        Long farm_id,
        String soilNome,
        Long soilId
    ) {
        this.id = id;
        this.name = name;
        this.harvest = harvest;
        this.area = area;
        this.soil = new SoilDTO(soilId, soilNome);
        this.status = status;
        this.culture = new CultureDTO(cultureId, cultureNome);
        this.farm = new FarmDTO(farmName, farmCity, farmState, farm_id);
        this.coordinates = coordinates;
        this.productivity = productivity;
    }
}