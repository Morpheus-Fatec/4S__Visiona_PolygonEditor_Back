package com.morpheus.backend.DTO;

import java.math.BigDecimal;


import lombok.Data;

@Data
public class ClassificationDTO {

    private BigDecimal area;
    private String coordinates;
    private String classEntity;

}
