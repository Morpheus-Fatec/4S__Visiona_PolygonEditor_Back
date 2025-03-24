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

    public List<Soil> getAllSoil() {
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
    
    public Soil getSoilById(Long id) {
        try {
            Soil soil = soilRepository.findById(id).get();

            if (soil == null) {
                throw new Exception();
            }

            return soil;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar o tipo de solo.");
        }
    }

    public String updateSoilName(Long id, String soilName) {
        try {
            String oldName = "";
            Soil soil = soilRepository.getSoilById(id);

            if (soil == null) {
                throw new Exception();
            }
            if (soilName.isEmpty()) {
                throw new Exception();
            }

            oldName = soil.getName();
            soil.setName(soilName);
            soilRepository.save(soil);

            return "Nome do solo atualizado de " + oldName + " para " + soilName + ".";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível atualizar o tipo de solo.");
        }
    }

    public String deleteSoil(Long id) {
        try {
            Soil soil = soilRepository.getSoilById(id);
            if(soil == null){
                throw new Exception();
            }
            soilRepository.delete(soil);
            return "Tipo de solo " + soil.getName() + " deletado com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível deletar o tipo de solo.");
        }
    }

}
