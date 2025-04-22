package com.morpheus.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.exceptions.DefaultException;

import ch.qos.logback.core.util.StringUtil;

@Service
public class SoilService {

    private final SoilRepository soilRepository;
    private final FieldRepository fieldRepository;

    public SoilService(SoilRepository soilRepository, FieldRepository fieldRepository) {
        this.soilRepository = soilRepository;
        this.fieldRepository = fieldRepository;
    }

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

            Soil soil = findSoilOrThrow(id);
            if (soil == null) {
                throw new DefaultException("Tipo de solo com ID " + id + " não encontrado.");
            }
            String oldName = soil.getName();
            soil.setName(soilName.trim());
            soilRepository.save(soil);

            return "Tipo de solo atualizado de '" + oldName + "' para '" + soil.getName() + "' com sucesso.";
    }

    public String deleteSoilById(Long id) {
            Soil soil = findSoilOrThrow(id);

            if (fieldRepository.existsBySoil_Name(soil.getName())) {
                throw new DefaultException("Não é possível deletar o solo, pois ele está associado a um talhão.");
            }
            soilRepository.delete(soil);
            return "Tipo de solo '" + soil.getName() + "' deletado com sucesso.";
    }

    private Soil findSoilOrThrow(Long id) {
        return soilRepository.findById(id)
            .orElseThrow(() -> new DefaultException("Tipo de solo com ID " + id + " não encontrado."));
        }
    
}
