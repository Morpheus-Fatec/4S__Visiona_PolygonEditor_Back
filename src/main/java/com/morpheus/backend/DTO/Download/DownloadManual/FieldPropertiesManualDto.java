package com.morpheus.backend.DTO.Download.DownloadManual;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "NM_TL",
    "AREA_M2",
    "CLASSE",
})
public class FieldPropertiesManualDto {

    @JsonProperty("NM_TL")
    private String fieldName;

    @JsonProperty("AREA_M2")
    private BigDecimal area;   

    @JsonProperty("CLASSE")
    private String classe;

    public FieldPropertiesManualDto(String fieldName, BigDecimal area, String classe){
        this.fieldName = fieldName;
        this.area = area;
        this.classe = classe;
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
