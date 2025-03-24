package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.SoilDTO;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.service.SoilService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public List<Soil> getAllSoil(){
        return soilService.getAllSoil();
    }

    @GetMapping("/{id}")
    public Soil getSoilById(@PathVariable Long id) {
        return soilService.getSoilById(id);
    }

    @PutMapping("/name/{id}")
    public String updateSoilName(@PathVariable Long id, @RequestBody SoilDTO soil) {
        return soilService.updateSoilName(id, soil.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteSoil(@PathVariable Long id) {
        return soilService.deleteSoil(id);
    }

}
