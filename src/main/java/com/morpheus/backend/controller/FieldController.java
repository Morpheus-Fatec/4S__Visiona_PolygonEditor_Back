package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.entity.Field;
import com.morpheus.backend.service.FieldService;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @GetMapping()
    public List<Field> getAllFields() throws IllegalAccessError {
        return fieldService.getAllFields();
    }
    
    @GetMapping("/{id}")
    public Field getFieldById(@PathVariable Long id) throws IllegalAccessError {
        return fieldService.getFieldById(id);
    }
    
    @PutMapping("/farm/{id}")
    public String updateFarm(@RequestParam Long id, @RequestBody Long newFarm) throws IllegalAccessError {
        return fieldService.updateFarm(id, newFarm);
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
