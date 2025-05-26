package com.morpheus.backend.DTO.Analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyConsultantAreaDTO {
    private MonthlyAreaDTO consultant;
    private MonthlyAreaDTO consultants;
}
