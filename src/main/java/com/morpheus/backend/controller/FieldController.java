package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FieldUpdatesDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.service.FieldService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/field")
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

    @GetMapping("/featureCollection/{id}")
    public ResponseEntity<FeatureCollectionDTO> getAllFeatureCollectionDTOByID(@PathVariable Long id) throws IllegalAccessError {
        FeatureCollectionDTO featureCollectionDTO = fieldService.getCompleteFieldById(id);

        return ResponseEntity.ok(featureCollectionDTO);
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<Map<String, String>> updateField(
            @PathVariable Long id,
            @RequestBody FieldUpdatesDTO dto) {
        
        fieldService.updateField(id, dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Talhão atualizado com sucesso.");
        
        return ResponseEntity.ok(response);
    }

}
