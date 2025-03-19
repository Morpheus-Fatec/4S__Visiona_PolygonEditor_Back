package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.service.FieldService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/field")
public class FieldController {
    private FieldService fieldService;

    @PostMapping
    public String createField(@RequestBody FieldDTO fieldDTO) {
        return fieldService.createField(fieldDTO);
    }
    
    @GetMapping()
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }
    
    @GetMapping("/{id}")
    public Field getFieldById(@PathVariable Long idField) {
        return fieldService.getFieldById(idField);
    }
    
    @PutMapping("/farm/{id}")
    public String updateFarm(@PathVariable Long id, @RequestBody Long newFarm) {
        return fieldService.updateFarm(id, newFarm);
    }

    @PutMapping("/culture/{id}")
    public String updateCulture(@PathVariable Long idField, @RequestBody String newCulture) {
        return fieldService.updateCulture(idField, newCulture);
    }

    @PutMapping("/soil/{id}")
    public String updateSoil(@PathVariable Long idField, @RequestBody String newSoil) {
        return fieldService.updateSoil(idField, newSoil);
    }

    @PutMapping("/status/{id}")
    public String updateStatus(@PathVariable Long idField, String status){
        return fieldService.updateStatus(idField, status);
    }

    @DeleteMapping("/{id}")
    public String deleteFieldById(@PathVariable Long idField){
        return fieldService.deleteFieldById(idField);
    }
}
