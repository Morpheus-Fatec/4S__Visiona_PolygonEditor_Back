package com.morpheus.backend.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmDTO {
    @NotNull(message = "Farm não pode ser nulo.")
    private String farmName;
    private String farmCity;
    private String farmState;
}
