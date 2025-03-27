package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.morpheus.backend.DTO.SoilDTO;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class SoilService {

    @Autowired
    private SoilRepository soilRepository;

    public String createSoil(SoilDTO soilName) {
        try {
            if (soilName.getName().isEmpty()) {
                throw new Exception();
            }

            Soil soil = new Soil();
            soil.setName(soilName.getName());
            soilRepository.save(soil);

            return "Tipo de solo " + soilName.getName() +  " criado com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível criar o tipo de solo.");
        }
    }

    public List<Soil> getAllSoils() {
        try {
            List<Soil> soils = soilRepository.findAll();

            if (soils.isEmpty()) {
                throw new Exception();
            }

            return soils;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar tipos de solo.");
        }
    }
}
