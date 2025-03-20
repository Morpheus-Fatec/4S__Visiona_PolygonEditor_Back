package com.morpheus.backend.DTO;

import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Productivity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDTO {
    @NotNull(message = "Farm não pode ser nulo.")
    private Farm farm;
    @NotNull(message = "Cultura não pode ser nula.")
    private String culture;
    private Productivity productivity;
    private Float area;
    private String soil;
    private String city;
    private String state; 
}
