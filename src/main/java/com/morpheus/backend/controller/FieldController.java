package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.service.FieldService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping
    public String createField(@Valid @RequestBody FieldDTO fieldDTO) throws Exception {
        return fieldService.createField(fieldDTO);
    }
    
    @GetMapping()
    public List<Field> getAllFields() throws IllegalAccessError {
        return fieldService.getAllFields();
    }
    
    @GetMapping("/{id}")
    public Field getFieldById(@RequestParam Long idField) throws IllegalAccessError {
        return fieldService.getFieldById(idField);
    }
    
    @PutMapping("/farm/{id}")
    public String updateFarm(@RequestParam Long id, @RequestBody Long newFarm) throws IllegalAccessError {
        return fieldService.updateFarm(id, newFarm);
    }

    @PutMapping("/culture/{id}")
    public String updateCulture(@RequestParam Long idField, @RequestBody String newCulture) {
        return fieldService.updateCulture(idField, newCulture);
    }

    @PutMapping("/soil/{id}")
    public String updateSoil(@RequestParam Long idField, @RequestBody String newSoil) {
        return fieldService.updateSoil(idField, newSoil);
    }

    @PutMapping("/status/{id}")
    public String updateStatus(@RequestParam Long idField, String status){
        return fieldService.updateStatus(idField, status);
    }

    @DeleteMapping("/{id}")
    public String deleteFieldById(@RequestParam Long idField){
        return fieldService.deleteFieldById(idField);
    }
}
