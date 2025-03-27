package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.service.FieldService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @GetMapping("/featureCollectionSimple")
    public ResponseEntity<FeatureCollectionSimpleDTO> getAllFeatureCollectionSimpleDTO() throws IllegalAccessError {
        FeatureCollectionSimpleDTO featureCollectionSimpleDTO = fieldService.getAllFeatureCollectionSimpleDTO();

        return ResponseEntity.ok(featureCollectionSimpleDTO);
    }
    
   
}
