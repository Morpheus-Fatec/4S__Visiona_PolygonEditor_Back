package com.morpheus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationCollection;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationFeatureCollection;
import com.morpheus.backend.DTO.GeoJsonView.revisionClassification.RevisionClassificationCollection;
import com.morpheus.backend.service.ClassificationService;


@RestController
@RequestMapping("/classification")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    @PostMapping("/manualClassification")
    public ResponseEntity<String> createManualClassification(@RequestBody  ManualClassificationCollection manualClassificationCollection) {
        try {
            classificationService.saveManualClassification(manualClassificationCollection);
            return ResponseEntity.status(HttpStatus.CREATED).body("Classificação manual criada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar classificação manual: " + e.getMessage());
        }
    }

    @PostMapping("/revisonClassification")
    public ResponseEntity<String> createRevisionClassification(@RequestBody RevisionClassificationCollection revisionClassificationCollection) {
        try {
            classificationService.saveRevisionClassification(revisionClassificationCollection);
            return ResponseEntity.status(HttpStatus.CREATED).body("Classificação de revisão criada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar classificação de revisão: " + e.getMessage());
        }
    }

    @GetMapping("/falsePositive")
    public ResponseEntity<ManualClassificationFeatureCollection> getFalsePositive(@RequestParam Long id) {
        ManualClassificationFeatureCollection falsePositive = classificationService.getFalsePositiveByFieldId(id);
        return ResponseEntity.ok(falsePositive);
    }
    
    @GetMapping("/falseNegative")
    public ResponseEntity<ManualClassificationFeatureCollection> getFalseNegative(@RequestParam Long id) {
        ManualClassificationFeatureCollection falseNegative = classificationService.getFalseNegativeByFieldId(id);
        return ResponseEntity.ok(falseNegative);
    }
}
