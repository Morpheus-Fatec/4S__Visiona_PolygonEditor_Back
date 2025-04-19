package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class SoilService {

    @Autowired
    public SoilRepository soilRepository;

    @Autowired
    public FieldRepository fieldRepository;

    public List<Soil> getAllSoils() {
        try {
            List<Soil> soils = soilRepository.findAll();

            if (soils.isEmpty()) {
                throw new Exception();
            }

            return soils;
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar tipos de solo.");
        }
    }

    public Soil getSoilById(Long id) {
        try {
            Soil soil = soilRepository.findById(id).orElseThrow(Exception::new);
            return soil;
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar o tipo de solo.");
        }
    }

    public String createSoil(String soilName) {
        try {
            if (soilName == null || soilName.isEmpty()) {
                throw new DefaultException("O nome do solo não pode ser vazio ou nulo.");
            }

            Soil soil = new Soil();
            soil.setName(soilName);
            soilRepository.save(soil);

            return "Tipo de solo " + soil.getName() +  " criado com sucesso.";
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível criar o tipo de solo.");
        }
    }

    public String updateSoil(Long id, String soilName) {
        try {
            Soil soil = soilRepository.getSoilById(id);

            if(soil == null) {
                throw new Exception();
            }

            if (soilName == null || soilName.isEmpty()) {
                throw new Exception();
            }

            String oldSoild = soil.getName();
            soil.setName(soilName);
            soilRepository.save(soil);

            return "Tipo de solo atualizado de " + oldSoild + " para " + soilName + " com sucesso.";
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível atualizar o tipo de solo.");
        }
    }

    public String deleteSoilById(Long id) {
        try {
            if (id == null) {
                throw new Exception();
            }
            Soil soil = soilRepository.getSoilById(id);
            
            if(soil == null) {
                throw new Exception();
            }
            if(fieldRepository.existsBySoil_Name(soil.getName())) {
                throw new DefaultException("Não é possível deletar o tipo de solo, pois ele está vinculado a um campo.");
            }
            
            soilRepository.delete(soil);
            return "Tipo de solo " + soil.getName() + " deletado com sucesso.";
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível deletar o tipo de solo.");
        }
    }
}
