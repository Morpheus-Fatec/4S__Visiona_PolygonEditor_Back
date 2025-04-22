package com.morpheus.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class CultureService {

    private final CultureRepository cultureRepository;
    private final FieldRepository fieldRepository;

    public CultureService(CultureRepository cultureRepository, FieldRepository fieldRepository) {
        this.cultureRepository = cultureRepository;
        this.fieldRepository = fieldRepository;
    }

    public List<Culture> getAllCultures() {
        List<Culture> cultures = cultureRepository.findAll();
        if (cultures.isEmpty()) {
            throw new DefaultException("Não foi possível encontrar culturas.");
        }
        return cultures;
    }

    public Culture getCultureById(Long id) {
        return findCultureOrThrow(id);
    }

    public String createCulture(String cultureName) {
        if (!StringUtils.hasText(cultureName)) {
            throw new DefaultException("O nome da cultura não pode ser vazio ou nulo.");
        }

        Culture culture = new Culture();
        culture.setName(cultureName);
        cultureRepository.save(culture);

        return "Cultura de " + culture.getName() + " criada com sucesso.";
    }

    public String updateCulture(Long id, String cultureName) {
        if (!StringUtils.hasText(cultureName)) {
            throw new DefaultException("O novo nome da cultura não pode ser vazio ou nulo.");
        }

        Culture culture = findCultureOrThrow(id);
        if (fieldRepository.existsByCulture_Name(culture.getName())) {
            throw new DefaultException("Não é possível atualizar o nome da cultura, pois ela está associada a um talhão.");
        }
        String oldName = culture.getName();
        culture.setName(cultureName);
        cultureRepository.save(culture);

        return "Nome da cultura atualizado de " + oldName + " para " + cultureName + ".";
    }

    public String deleteCulture(Long id) {
        Culture culture = findCultureOrThrow(id);

        if (fieldRepository.existsByCulture_Name(culture.getName())) {
            throw new DefaultException("Não é possível deletar a cultura, pois ela está associada a um talhão.");
        }

        cultureRepository.delete(culture);
        return "Cultura de " + culture.getName() + " deletada com sucesso.";
    }

    private Culture findCultureOrThrow(Long id) {
        return Optional.ofNullable(cultureRepository.getCultureById(id))
                .orElseThrow(() -> new DefaultException("Cultura com ID " + id + " não encontrada."));
    }
}
