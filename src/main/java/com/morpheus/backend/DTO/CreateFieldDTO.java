package com.morpheus.backend.DTO;


import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFieldDTO {

    private BigDecimal area;
    private String coordinates;
    private String culture;
    private String harvest;
    private Long idField;
    private String nameFarm;
    private String productivity;
    private String soil;
    private List<ClassificationDTO> classification;

}
