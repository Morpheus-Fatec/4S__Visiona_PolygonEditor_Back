package com.morpheus.backend.DTO.Download;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "MN_TL",
    "AREA_HA_TL",
    "SOLO",
    "CULTURA",
    "SAFRA",
    "FAZENDA"
})
public class FieldPropertiesDto {

    @JsonProperty("MN_TL")
    private String name;

    @JsonProperty("AREA_HA_TL")
    private Double area;

    @JsonProperty("SOLO")
    private String soil;

    @JsonProperty("CULTURA")
    private String culture;

    @JsonProperty("SAFRA")
    private String harvest;

    @JsonProperty("FAZENDA")
    private String farm;

    // Construtor completo (sem Lombok)
    public FieldPropertiesDto(String name, Double area, String soil, String culture, String harvest, String farm) {
        this.name = name;
        this.area = area;
        this.soil = soil;
        this.culture = culture;
        this.harvest = harvest;
        this.farm = farm;
    }

    // Getters (se necessário)
    public String getName() { return name; }
    public Double getArea() { return area; }
    public String getSoil() { return soil; }
    public String getCulture() { return culture; }
    public String getHarvest() { return harvest; }
    public String getFarm() { return farm; }
}
