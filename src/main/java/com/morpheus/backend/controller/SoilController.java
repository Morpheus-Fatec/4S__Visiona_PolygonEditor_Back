package com.morpheus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.SoilDTO;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.service.SoilService;

@RestController
@RequestMapping("/soil")
public class SoilController {

    @Autowired
    private SoilService soilService;

    @GetMapping
    public List<Soil> getAllSoils() {
        return soilService.getAllSoils();
    }

    @GetMapping("/{id}")
    public Soil getSoilById(@PathVariable Long id) {
        return soilService.getSoilById(id);
    }

    @PostMapping("/createSoil")
    public String createSoil(@RequestBody SoilDTO soilName) {
        return soilService.createSoil(soilName.getNome());
    }

    @PutMapping("/updateSoil/{id}")
    public String updateSoil(@PathVariable Long id, @RequestBody SoilDTO soilName) {
        return soilService.updateSoil(id, soilName.getNome());
    }

    @DeleteMapping("/{id}")
    public String deleteSoil(@PathVariable Long id) {
        return soilService.deleteSoilById(id);
    }
}
