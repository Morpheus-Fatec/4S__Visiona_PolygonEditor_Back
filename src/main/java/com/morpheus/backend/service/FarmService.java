package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public List<Farm> getAllFarms() {
        List<Farm> farmList = farmRepository.findAll();
        if (farmList.isEmpty()) {
            throw new DefaultException("Não há nenhuma fazenda cadastrada.");
        }
        return farmList;
    }

    public Farm getFarmById(Long id) {
        return findFarmOrThrow(id);
    }

    public String createFarm(FarmDTO farmDTO) {
        validateFarmDTO(farmDTO);

        Farm farm = new Farm();
        farm.setFarmName(farmDTO.getFarmName());
        farm.setFarmState(farmDTO.getFarmState());
        farm.setFarmCity(farmDTO.getFarmCity());
        farmRepository.save(farm);

        return "Fazenda " + farm.getFarmName() + " criada com sucesso.";
    }

    public String updateFarm(Long farmId, FarmDTO farmDTO) {
        validateFarmDTO(farmDTO);

        Farm farm = findFarmOrThrow(farmId);
        String oldFarmName = farm.getFarmName();
        String oldFarmState = farm.getFarmState();
        String oldFarmCity = farm.getFarmCity();

        farm.setFarmName(farmDTO.getFarmName());
        farm.setFarmState(farmDTO.getFarmState());
        farm.setFarmCity(farmDTO.getFarmCity());
        farmRepository.save(farm);

        return String.format("Fazenda alterada de Nome: %s, Estado: %s, Cidade: %s para Nome: %s, Estado: %s, Cidade: %s",
                oldFarmName, oldFarmState, oldFarmCity, farm.getFarmName(), farm.getFarmState(), farm.getFarmCity());
    }

    public String updateFarmName(Long id, String newName) {
        return updateFarmField(id, newName, "name");
    }

    public String updateFarmCity(Long farmId, String newCity) {
        return updateFarmField(farmId, newCity, "city");
    }

    public String updateFarmState(Long farmId, String newState) {
        return updateFarmField(farmId, newState, "state");
    }

    private String updateFarmField(Long farmId, String newFieldValue, String fieldType) {
        Farm farm = findFarmOrThrow(farmId);

        if (!StringUtils.hasText(newFieldValue)) {
            throw new DefaultException("O " + fieldType + " da fazenda não pode estar vazio.");
        }

        String oldValue = "";
        switch (fieldType) {
            case "name":
                oldValue = farm.getFarmName();
                farm.setFarmName(newFieldValue);
                break;
            case "city":
                oldValue = farm.getFarmCity();
                farm.setFarmCity(newFieldValue);
                break;
            case "state":
                oldValue = farm.getFarmState();
                farm.setFarmState(newFieldValue);
                break;
        }

        farmRepository.save(farm);

        return String.format("Fazenda '%s' %s alterado com sucesso: De '%s' para '%s'.", farm.getFarmName(), fieldType, oldValue, newFieldValue);
    }

    public String deleteFarmById(Long id) {
        Farm farm = findFarmOrThrow(id);

        if (fieldRepository.existsByFarm_FarmName(farm.getFarmName())) {
            throw new DefaultException("A fazenda está relacionada a um ou mais talhões e não pode ser excluída.");
        }

        String deletedFarm = farm.getFarmName();
        farmRepository.deleteById(id);

        return deletedFarm + " foi deletada com sucesso!";
    }

    private void validateFarmDTO(FarmDTO farmDTO) {
        if (farmDTO == null || !StringUtils.hasText(farmDTO.getFarmName()) || !StringUtils.hasText(farmDTO.getFarmState()) || !StringUtils.hasText(farmDTO.getFarmCity())) {
            throw new DefaultException("Todos os campos de fazenda são obrigatórios.");
        }
    }

    private Farm findFarmOrThrow(Long id) {
        return farmRepository.findById(id).orElseThrow(() -> new DefaultException("Fazenda com o ID " + id + " não encontrada."));
    }
}
