package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.CultureDTO;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.service.CultureService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    public Culture getCultureById(@RequestBody Long id) {
        return cultureService.getCultureById(id);
    }

    @PostMapping
    public String createCulture(@RequestBody CultureDTO cultureName) {
        return cultureService.createCulture(cultureName);
    }

}
