package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.service.FieldService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/field")
@CrossOrigin(origins = "http://localhost:5173")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @GetMapping("/featureCollectionSimple")
    public ResponseEntity<FeatureCollectionSimpleDTO> getAllFeatureCollectionSimpleDTO(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String soil,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String culture,
        @RequestParam(required = false) String harvest,
        @RequestParam(required = false) String farmName
    ) {
        FeatureCollectionSimpleDTO featureCollection = fieldService.getAllFeatureCollectionSimpleDTO(
            name, soil, status, culture, harvest, farmName
        );
        return ResponseEntity.ok(featureCollection);
    }
   
}
