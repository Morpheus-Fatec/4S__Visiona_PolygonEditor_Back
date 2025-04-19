package com.morpheus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.service.FarmService;

@RestController
@RequestMapping("/farm")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @GetMapping
    public List<Farm> getAllFarms() throws Exception{
        return farmService.getAllFarms();
    }

    @GetMapping("/{id}")
    public Farm getFarmById(@PathVariable Long id) throws Exception {
        return farmService.getFarmById(id);
    }

    @PostMapping("/createFarm")
    public String createFarm(@RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.createFarm(farmDTO);
    }

    @PutMapping("updateFarm/{id}")
    public String updateFarm(@PathVariable Long id, @RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.updateFarm(id, farmDTO);
    }

    @PutMapping("updateFarmName/{id}")
    public String updateFarmName(@PathVariable Long id, @RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.updateFarmName(id, farmDTO.getFarmName());
    }


    @PutMapping("updateFarmCity/{id}")
    public String updateFarmCity(@PathVariable Long id, @RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.updateFarmCity(id, farmDTO.getFarmCity());
    }


    @PutMapping("updateFarmState/{id}")
    public String updateFarmState(@PathVariable Long id, @RequestBody FarmDTO farmDTO) throws Exception{
        return farmService.updateFarmState(id, farmDTO.getFarmState());
    }

}
