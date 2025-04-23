package com.morpheus.backend.DTO.Download.DownloadManual;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "CLASSE",
    "AREA_M2",
    "NM_TL",
})
public class FieldPropertiesManualDto {

    @JsonProperty("CLASSE")
    private String classe;

    @JsonProperty("AREA_M2")
    private BigDecimal area;

    @JsonProperty("NM_TL")
    private String fieldName;


    public FieldPropertiesManualDto(String classe, BigDecimal area, String fieldName) {
        this.classe = classe;
        this.area = area;
        this.fieldName = fieldName;
    }
    
    public String getClasse() {
        return classe;
    }

    public BigDecimal getArea() {
        return area;
    }

    public String getFieldName() {
        return fieldName;
    }
    
}
