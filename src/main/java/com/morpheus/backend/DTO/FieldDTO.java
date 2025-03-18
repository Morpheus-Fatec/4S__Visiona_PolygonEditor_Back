package com.morpheus.backend.DTO;

import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Productivity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDTO {
    private Farm farm;
    private String culture;
    private Productivity productivity;
    private Float area;
    private String soil;
    private String city;
    private String state; 
}
