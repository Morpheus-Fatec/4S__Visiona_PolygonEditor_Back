package com.morpheus.backend.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {
    @NotNull(message = "Farm não pode ser nulo.")
    private String farmName;
    private String farmCity;
    private String farmState;
}
