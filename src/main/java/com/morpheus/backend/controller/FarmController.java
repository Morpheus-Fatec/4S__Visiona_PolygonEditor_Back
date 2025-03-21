package com.morpheus.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.service.FarmService;

@RestController
@RequestMapping("/farm")
public class FarmController {
    private FarmService farmService;

    public FarmController(FarmService farmService){
        this.farmService = farmService;
    }

    @PostMapping
    public String createFarm(@RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.createFarm(farmDTO);
    }

    @GetMapping
    public List<Farm> getAllFarms() throws Exception{
        return farmService.getAllFarms();
    }

    @GetMapping("/{id}")
    public Farm getFarmById(@PathVariable Long id) throws Exception{
        return farmService.getFarmById(id);
    }

    @PostMapping("/name/{id}")
    public String updatefarmName(@PathVariable Long id, @RequestBody FarmDTO newName) throws Exception{
        return farmService.updateFarmName(id, newName);
    }

    @PostMapping("/state/{id}")
    public String updatefarmState(@PathVariable Long id, @RequestBody FarmDTO newState) throws Exception{
        return farmService.updateFarmState(id, newState);
    }

    @PostMapping("/city/{id}")
    public String updateFarmCity(@PathVariable Long id, @RequestBody FarmDTO newCity) throws Exception{
        return farmService.updateFarmCity(id, newCity);
    }

    @DeleteMapping("{id}")
    public String deleteFarm(@PathVariable Long id) throws Exception{
        return farmService.deleteFarmById(id);
    }
}
