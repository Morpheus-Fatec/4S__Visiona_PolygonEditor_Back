package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.Analysis.AnalystPerformanceDTO;
import com.morpheus.backend.DTO.Analysis.AnalystQualityDTO;
import com.morpheus.backend.DTO.Analysis.AutomaticAnalysisHealthDTO;
import com.morpheus.backend.DTO.Analysis.MonthlyAreaDTO;
import com.morpheus.backend.DTO.Analysis.MonthlyConsultantAreaDTO;
import com.morpheus.backend.DTO.Analysis.ProductivityComparisonDTO;
import com.morpheus.backend.DTO.Analysis.TableAnalystDTO;
import com.morpheus.backend.repository.classification.ClassificationControlRepository;

@Service
public class ComparativeAnalysisService {

    private final ClassificationControlRepository classificationControlRepository;

    public ComparativeAnalysisService(ClassificationControlRepository classificationControlRepository) {
        this.classificationControlRepository = classificationControlRepository;
    }

    public AnalystQualityDTO getQualityGeral(Long analystId) {
        List<Object[]> analystData = classificationControlRepository.getQualityAnalysisByAnalyst(analystId);
        List<Object[]> teamData = classificationControlRepository.getQualityAnalysisForTeam();

        return new AnalystQualityDTO(
            resultMap(analystData),
            resultMap(teamData)
        );
    }

    private AnalystQualityDTO.CategoryDTO resultMap(List<Object[]> data) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Ideais", 0);
        map.put("Aceitaveis", 0);
        map.put("Ruim", 0);

        for (Object[] row : data) {
            String classification = (String) row[0];
            Long quantity = (Long) row[1];
            map.put(classification, quantity.intValue());
        }

        return new AnalystQualityDTO.CategoryDTO(
            map.get("Ideais"),
            map.get("Aceitaveis"),
            map.get("Ruim")
        );
    }

    public List<AnalystPerformanceDTO> getAnalystPerformance() {
        List<Object[]> rows = classificationControlRepository.getAnalystPerformanceData();
        List<AnalystPerformanceDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            String name = (String) row[0];
            Double approved = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
            Double pending = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
            Double rejected = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;

            result.add(new AnalystPerformanceDTO(name, rejected, pending, approved));
        }

        return result;      
    }

    
    public ProductivityComparisonDTO compareProductivity(Long analystId) {
        Double analystValue = classificationControlRepository.getAnalystProductivity(analystId);
        Double averageValue = classificationControlRepository.getAverageAnalystProductivity();

        return new ProductivityComparisonDTO(
            round(analystValue),
            round(averageValue)
        );
    }

    private double round(Double value) {
        if (value == null) return 0.0;
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public List<TableAnalystDTO> listCycleFields(String field, String status, String analyst, String consultant) {
        return classificationControlRepository.findCycleFields(field, status, analyst, consultant);
    }

    public List<AutomaticAnalysisHealthDTO> getMonthlyAreaData() {
        List<Object[]> results = classificationControlRepository.findMonthlyAreaData();

        return results.stream()
            .map(r -> new AutomaticAnalysisHealthDTO(
                (String) r[0],
                ((Number) r[1]).doubleValue(),
                ((Number) r[2]).doubleValue()
            ))
            .toList();
    }

    public MonthlyConsultantAreaDTO getMonthlyAreaComparison(Long consultantId) {
        List<Object[]> consultantResults = classificationControlRepository.findMonthlyAreaByConsultant(consultantId);
        Map<Integer, Double> consultantMap = convertToMonthMap(consultantResults);
        MonthlyAreaDTO consultantDTO = buildMonthlyAreaDTO(consultantMap);

        List<Object[]> allResults = classificationControlRepository.findMonthlyAreaByAllConsultants();
        Map<Integer, Double> allMap = convertToMonthMap(allResults);
        MonthlyAreaDTO allConsultantsDTO = buildMonthlyAreaDTO(allMap);

        MonthlyConsultantAreaDTO dto = new MonthlyConsultantAreaDTO();
        dto.setConsultant(consultantDTO);
        dto.setConsultants(allConsultantsDTO);
        return dto;
    }

    private Map<Integer, Double> convertToMonthMap(List<Object[]> results) {
        Map<Integer, Double> map = new HashMap<>();
        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue();
            Double area = ((Number) row[1]).doubleValue();
            map.put(month, area);
        }
        return map;
    }

    private MonthlyAreaDTO buildMonthlyAreaDTO(Map<Integer, Double> monthToAreaMap) {
        MonthlyAreaDTO dto = new MonthlyAreaDTO();
        dto.setJaneiro(monthToAreaMap.getOrDefault(1, 0.0).intValue());
        dto.setFevereiro(monthToAreaMap.getOrDefault(2, 0.0).intValue());
        dto.setMarco(monthToAreaMap.getOrDefault(3, 0.0).intValue());
        dto.setAbril(monthToAreaMap.getOrDefault(4, 0.0).intValue());
        dto.setMaio(monthToAreaMap.getOrDefault(5, 0.0).intValue());
        dto.setJunho(monthToAreaMap.getOrDefault(6, 0.0).intValue());
        dto.setJulho(monthToAreaMap.getOrDefault(7, 0.0).intValue());
        dto.setAgosto(monthToAreaMap.getOrDefault(8, 0.0).intValue());
        dto.setSetembro(monthToAreaMap.getOrDefault(9, 0.0).intValue());
        dto.setOutubro(monthToAreaMap.getOrDefault(10, 0.0).intValue());
        dto.setNovembro(monthToAreaMap.getOrDefault(11, 0.0).intValue());
        dto.setDezembro(monthToAreaMap.getOrDefault(12, 0.0).intValue());
        return dto;
    }
}
