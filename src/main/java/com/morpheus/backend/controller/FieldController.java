package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.service.FieldService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @GetMapping("/simplesfeatures")
    public ResponseEntity<List<FeatureSimpleDTO>> getAllFeatureSimpleDTO() throws IllegalAccessError {
        List<FeatureSimpleDTO> list = fieldService.getAllFeatureSimpleDTO();

        return ResponseEntity.ok(list);
    }
    
   
}
