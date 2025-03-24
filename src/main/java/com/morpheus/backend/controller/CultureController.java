package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.CultureDTO;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.service.CultureService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/cultures")
public class CultureController {
    
    @Autowired
    private CultureService cultureService;
    
    @PostMapping
    public String createCulture(@RequestBody CultureDTO cultureName) {
        return cultureService.createCulture(cultureName);
    }

    @GetMapping
    public List<Culture> getAllCuture() throws Exception {
        return cultureService.getAllCultures();
    }

    @GetMapping("/{id}")
    public Culture getCultureById(@PathVariable Long id) {
        return cultureService.getCultureById(id);
    }

    @PutMapping("/name/{id}")
    public String updateCultureName(@PathVariable Long id, @RequestBody CultureDTO culture) {
        return cultureService.updateCultureName(id, culture.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteCulture(@PathVariable Long id) {
        return cultureService.deleteCulture(id);
    }

}
