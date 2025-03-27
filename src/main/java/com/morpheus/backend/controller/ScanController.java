package com.morpheus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void createScan(@RequestBody ScanTestDTO scan) {
        System.out.println("scan: " + scan);
        Scan scanCreated = scanService.createScan(scan);
        System.out.println("scanCreated: " + scan);
        System.out.println("field: " + scan.getField());
        Field fieldCreated = fieldService.createField(scan.getField(), scanCreated);
        System.out.println("fieldCreated: " + fieldCreated);
        System.out.println("classification: " + scan.getField().getClassification());
        classificationService.createClassification(fieldCreated, scan.getField().getClassification());
        

    }
}
