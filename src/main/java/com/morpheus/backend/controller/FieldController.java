package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.PaginatedFieldResponse;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.service.FieldService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @GetMapping("/featureCollectionSimple")
    public ResponseEntity<PaginatedFieldResponse<FeatureSimpleDTO>> getAllFeatureCollectionSimpleDTO(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String soil,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String culture,
        @RequestParam(required = false) String harvest,
        @RequestParam(required = false) String farmName,
        @RequestParam (defaultValue = "1")int page, 
        @RequestParam(defaultValue = "20") int itens
    ) {

        PaginatedFieldResponse<FeatureSimpleDTO> fields = fieldService.getAllFeatureCollectionSimpleDTO(name, soil, status, culture,  harvest, farmName, page, itens);

        return ResponseEntity.ok(fields);
    }

    @GetMapping("/featureCollection/{id}")
    public ResponseEntity<FeatureCollectionDTO> getAllFeatureCollectionDTOByID(@PathVariable Long id) throws IllegalAccessError {
        FeatureCollectionDTO featureCollectionDTO = fieldService.getCompleteFieldById(id);

        return ResponseEntity.ok(featureCollectionDTO);
    }
}
