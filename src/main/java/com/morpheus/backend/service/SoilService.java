package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class SoilService {

    @Autowired
    private SoilRepository soilRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public List<Soil> getAllSoils() {
        List<Soil> soils = soilRepository.findAll();
        if (soils.isEmpty()) {
            throw new DefaultException("Nenhum tipo de solo encontrado.");
        }
        return soils;
    }

    public Soil getSoilById(Long id) {
        return soilRepository.findById(id)
            .orElseThrow(() -> new DefaultException("Tipo de solo com ID " + id + " não encontrado."));
    }

    public String createSoil(String soilName) {
        if (!StringUtils.hasText(soilName)) {
                throw new DefaultException("O nome do solo é obrigatório.");
        }

        Soil soil = new Soil();
        soil.setName(soilName.trim());
        soilRepository.save(soil);

        return "Tipo de solo '" + soil.getName() + "' criado com sucesso.";
    }

    public String updateSoil(Long id, String soilName) {
            if (!StringUtils.hasText(soilName)) {
                throw new DefaultException("O nome do solo é obrigatório.");
            }

            Soil soil = getSoilById(id);
            if (soil == null) {
                throw new DefaultException("Tipo de solo com ID " + id + " não encontrado.");
            }
            String oldName = soil.getName();
            soil.setName(soilName.trim());
            soilRepository.save(soil);

            return "Tipo de solo atualizado de '" + oldName + "' para '" + soil.getName() + "' com sucesso.";
    }

    public String deleteSoilById(Long id) {
            Soil soil = getSoilById(id);

            if (fieldRepository.existsBySoil_Name(soil.getName())) {
                throw new DefaultException("Não é possível deletar o solo, pois ele está associado a um talhão.");
            }
            soilRepository.delete(soil);
            return "Tipo de solo '" + soil.getName() + "' deletado com sucesso.";
    }
}
