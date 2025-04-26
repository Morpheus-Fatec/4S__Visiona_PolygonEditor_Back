package com.morpheus.backend.DTO;

import java.math.BigDecimal;

import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Soil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldUpdatesDTO{

    private Long id;
    private String name;
    private Culture culture;
    private BigDecimal area;
    private String harvest;
    private String status;
    private Soil soil;
    private String productivity;
    private Farm farm;
}