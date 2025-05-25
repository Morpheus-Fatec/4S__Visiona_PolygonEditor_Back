package com.morpheus.backend.DTO.Analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableAnalystDTO {
    private String fieldName;
    private String status;
    private String entryDate;
    private String approvalDate;
    private String cycleTime;
    private String analyst;
    private String analysisTime;
    private int numberOfAnalyses;
    private String consultant;
    private String reviewTime;
}
