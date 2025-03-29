package com.morpheus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.service.FarmService;

@RestController
@RequestMapping("/farm")
@CrossOrigin(origins = "http://localhost:5173")
public class FarmController {
    @Autowired
    private FarmService farmService;


    @PostMapping
    public String createFarm(@RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.createFarm(farmDTO);
    }

    @GetMapping
    public List<Farm> getAllFarms() throws Exception{
        return farmService.getAllFarms();
    }

}
