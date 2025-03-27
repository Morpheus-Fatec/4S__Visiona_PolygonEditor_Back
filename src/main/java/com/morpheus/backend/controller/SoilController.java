package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.morpheus.backend.DTO.SoilDTO;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.service.SoilService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/soils")
public class SoilController {

    @Autowired
    private SoilService soilService;
    
    @PostMapping
    public String createSoil(@RequestBody SoilDTO  soilName) {
        return soilService.createSoil(soilName);
    }

    @GetMapping
    public List<Soil> getAllSoils() {
        return soilService.getAllSoils();
    }
}
