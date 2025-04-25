package com.morpheus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationCollection;
import com.morpheus.backend.service.ClassificationService;


@RestController
@RequestMapping("/classification")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    @PostMapping("/manualClassification")
    public ResponseEntity<String> createManualClassification(@RequestBody  ManualClassificationCollection manualClassificationCollection) {
        System.out.println("Manual Classification Collection: " + manualClassificationCollection);
        try {
            classificationService.createManualClassification(manualClassificationCollection);
            return ResponseEntity.status(HttpStatus.CREATED).body("Classificação manual criada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar classificação manual: " + e.getMessage());
        }
    }

}
