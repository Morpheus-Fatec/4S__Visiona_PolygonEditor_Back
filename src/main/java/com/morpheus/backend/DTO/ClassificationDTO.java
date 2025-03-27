package com.morpheus.backend.DTO;

import java.math.BigDecimal;

import com.morpheus.backend.entity.ClassEntity;

import lombok.Data;

@Data
public class ClassificationDTO {

    private BigDecimal area;
    private String coordiantes;
    private ClassEntity classEntity;

}
