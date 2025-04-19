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

import com.morpheus.backend.DTO.CultureDTO;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.service.CultureService;

@RestController
@RequestMapping("/cultures")
public class CultureController {

    @Autowired
    private CultureService cultureService;

    @GetMapping
    public List<Culture> getAllCultures() {
        return cultureService.getAllCultures();
    }

    @GetMapping("/{id}")
    public Culture getCultureById(@PathVariable Long id) {
        return cultureService.getCultureById(id);
    }

    @PostMapping
    public String createCulture(@RequestBody CultureDTO culture) {
        return cultureService.createCulture(culture.getName());
    }

    @PutMapping("/updateCulture/{id}")
    public String updateCulture(@PathVariable Long id, @RequestBody CultureDTO culture) {
        return cultureService.updateCulture(id, culture.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteCulture(@PathVariable Long id) {
        return cultureService.deleteCulture(id);
    }
}
