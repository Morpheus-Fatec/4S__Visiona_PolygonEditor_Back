package com.morpheus.backend.DTO;


import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFieldDTO {

    private BigDecimal area;
    private String coordinates;
    @NotNull(message = "Cultura não pode ser nula.")
    private String culture;
    @NotNull(message = "Safra não pode ser nula.")
    private String harvest;
    private String nameField;
    @NotNull(message = "Farm não pode ser nulo.")
    private String Farmname;
    private String productivity;
    private String soil;
    private List<ClassificationDTO> classification;

}