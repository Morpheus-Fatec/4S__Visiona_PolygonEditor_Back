package com.morpheus.backend.DTO.Analysis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutomaticAnalysisHealthDTO {
    private String month;
    private int initialArea;
    private int finalArea;

    public AutomaticAnalysisHealthDTO(String month, double initialArea, double finalArea) {
        this.month = month;
        this.initialArea = (int) Math.round(initialArea);
        this.finalArea = (int) Math.round(finalArea);
    }
}
