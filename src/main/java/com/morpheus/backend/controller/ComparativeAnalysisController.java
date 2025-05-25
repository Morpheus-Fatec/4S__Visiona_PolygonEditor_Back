package com.morpheus.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.Analysis.AnalystPerformanceDTO;
import com.morpheus.backend.DTO.Analysis.AnalystQualityDTO;
import com.morpheus.backend.DTO.Analysis.AutomaticAnalysisHealthDTO;
import com.morpheus.backend.DTO.Analysis.MonthlyConsultantAreaDTO;
import com.morpheus.backend.DTO.Analysis.ProductivityComparisonDTO;
import com.morpheus.backend.DTO.Analysis.TableAnalystDTO;
import com.morpheus.backend.service.ComparativeAnalysisService;

@RestController
@RequestMapping("/analise")
public class ComparativeAnalysisController {
    private final ComparativeAnalysisService comparativeAnalysisService;

    public ComparativeAnalysisController(ComparativeAnalysisService comparativeAnalysisService) {
        this.comparativeAnalysisService = comparativeAnalysisService;
    }

    @GetMapping("/qualidadeanalistas/{analystId}")
    public ResponseEntity<AnalystQualityDTO> compare(@PathVariable Long analystId) {
        AnalystQualityDTO dto = comparativeAnalysisService.getQualityGeral(analystId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/desempenhoanalistas")
    public ResponseEntity<List<AnalystPerformanceDTO>> getAnalystPerformance() {
        return ResponseEntity.ok(comparativeAnalysisService.getAnalystPerformance());
    }

    @GetMapping("/metricadeprodutividade/{idAnalyst}")
    public ResponseEntity<ProductivityComparisonDTO> getProductivity(@PathVariable Long idAnalyst) {
        return ResponseEntity.ok(comparativeAnalysisService.compareProductivity(idAnalyst));
    }

    @GetMapping("/consultor-mensal/{consultantId}")
    public ResponseEntity<MonthlyConsultantAreaDTO> getMonthlyReviewedArea(
        @RequestParam(required = false) Long consultantId) {
    MonthlyConsultantAreaDTO dto = comparativeAnalysisService.getMonthlyAreaComparison(consultantId);
    return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/ciclo-talhoes")
    public List<TableAnalystDTO> listCycleFields(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String analyst,
            @RequestParam(required = false) String conultant) {
        
        return comparativeAnalysisService.listCycleFields(field, status, analyst, conultant);
    }

    @GetMapping("/qualidadeanalises")
    public ResponseEntity<Map<String, List<AutomaticAnalysisHealthDTO>>> getMonthlyArea() {
        List<AutomaticAnalysisHealthDTO> monthlyAreas = comparativeAnalysisService.getMonthlyAreaData();

        Map<String, List<AutomaticAnalysisHealthDTO>> response = Map.of("meses", monthlyAreas);

        return ResponseEntity.ok(response);
    }
}
