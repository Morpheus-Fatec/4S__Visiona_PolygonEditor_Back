package com.morpheus.backend.DTO.Analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalystQualityDTO {

    private CategoryDTO analyst;
    private CategoryDTO team;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        private int ideal;
        private int acceptable;
        private int critical;
    }
}