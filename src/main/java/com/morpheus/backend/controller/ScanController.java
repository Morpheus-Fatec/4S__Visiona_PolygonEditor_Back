package com.morpheus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.ScanTestDTO;
import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.service.ClassificationService;
import com.morpheus.backend.service.FieldService;
import com.morpheus.backend.service.ScanService;

@RestController
@RequestMapping("/scan")
public class ScanController {
    @Autowired
    private ScanService scanService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ClassificationService classificationService;


    @PostMapping
    public Long createScan(@RequestBody ScanTestDTO scan) throws JsonProcessingException {
        
        Scan scanCreated = scanService.createScan(scan);
        
        for (CreateFieldDTO fieldDTO : scan.getFields()) {
            Field fieldCreated = fieldService.createField(fieldDTO, scanCreated);
            classificationService.createClassification(fieldCreated, fieldDTO.getClassification());
        }

        return scanCreated.getId();
    }

    @GetMapping
    public List<Scan> getAllScans() {
        return scanService.getAllScans();
    }
}
