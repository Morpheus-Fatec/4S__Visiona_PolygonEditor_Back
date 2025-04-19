package com.morpheus.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class FarmService {
    private FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository){
        this.farmRepository = farmRepository;
    }

    public List<Farm> getAllFarms(){
        try {
            List<Farm> farmList =  farmRepository.findAll();
        
            if (farmList.size() == 0){
                throw new Exception();
            }
            return farmList;
        
        } catch (Exception e) {
            throw new DefaultException("Não há nenhuma fazenda cadastrada.");
        }
    }

    public Farm getFarmById(Long id){
        try {
            Farm farm = farmRepository.getFarmById(id);

            if(farm == null){
                throw new Exception();
            }

            return farmRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new DefaultException("Não existe a fazenda com o id " + id);
        }
    }

    public String createFarm(FarmDTO farmDTO){
        try {
            if(farmDTO.getFarmName() == null || farmDTO.getFarmState() == null || farmDTO.getFarmCity() == null){
                throw new Exception();
            }

            Farm farm = new Farm();
            farm.setFarmName(farmDTO.getFarmName());
            farm.setFarmState(farmDTO.getFarmState());
            farm.setFarmCity(farmDTO.getFarmCity());
            farmRepository.save(farm);
            return "Fazenda " + farm.getFarmName() + " criado com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível criar a fazenda");
        }
    }

    public String updateFarm(Long farmId, FarmDTO farmDTO) {
        try {
            Farm farm = farmRepository.getFarmById(farmId);
            if (farm == null) {
                throw new DefaultException("Fazenda com o ID " + farmId + " não encontrada.");
            }
            if (farmDTO.getFarmName() == null || farmDTO.getFarmState() == null || farmDTO.getFarmCity() == null) {
                throw new DefaultException("Fazenda não pode ser atualizada com valores nulos.");
            }
            String oldFarmName = farm.getFarmName();
            String oldFarmState = farm.getFarmState();
            String oldFarmCity = farm.getFarmCity();

            farm.setFarmName(farmDTO.getFarmName());
            farm.setFarmState(farmDTO.getFarmState());
            farm.setFarmCity(farmDTO.getFarmCity());
            farmRepository.save(farm);

            return String.format(
                "Fazenda alterada de Nome: %s, Estado: %s, Cidade: %s para Nome: %s, Estado: %s, Cidade: %s",
                oldFarmName, oldFarmState, oldFarmCity,
                farm.getFarmName(), farm.getFarmState(), farm.getFarmCity()
            );
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Erro ao atualizar a fazenda: " + e.getMessage());
        }
    }

    public String updateFarmName(Long id, String newName) {
        try {
            Farm farm = farmRepository.getFarmById(id);
            if (farm == null) {
                throw new DefaultException("Fazenda com o ID " + id + " não encontrada.");
            }
            if (newName == null || newName.isEmpty()) {
                throw new DefaultException("O novo nome da fazenda não pode estar vazio.");
            }
    
            String oldFarmName = farm.getFarmName();
    
            farm.setFarmName(newName);
            farmRepository.save(farm);
    
            return String.format(
                "Nome da fazenda alterado com sucesso: De '" + oldFarmName+ "' para '"  + farm.getFarmName() + "'.",
                oldFarmName, farm.getFarmName()
            );
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Erro ao alterar o nome da fazenda: " + e.getMessage());
        }
    }


    public String updateFarmCity(Long farmId, String newCity) {
        try {
            Farm farm = farmRepository.getFarmById(farmId);
    
            if (farm == null) {
                throw new DefaultException("Fazenda com o ID " + farmId + " não encontrada.");
            }
    
            if (newCity == null || newCity.isEmpty()) {
                throw new DefaultException("A nova cidade da fazenda não pode estar vazia.");
            }
    
            String oldFarmCity = farm.getFarmCity();
    
            farm.setFarmCity(newCity);
            farmRepository.save(farm);
    
            return String.format(
                "Cidade da fazenda '" + farm.getFarmName() + "' alterada com sucesso: De '" + oldFarmCity + "' para '" + farm.getFarmCity() + "'.",
                oldFarmCity, farm.getFarmCity()
            );
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Erro ao alterar a cidade da fazenda: " + e.getMessage());
        }
    }


    public String updateFarmState(Long farmId, String newState) {
        try {
            Farm farm = farmRepository.getFarmById(farmId);
    
            if (farm == null) {
                throw new DefaultException("Fazenda com o ID " + farmId + " não encontrada.");
            }
    
            if (newState == null || newState.isEmpty()) {
                throw new DefaultException("O novo estado da fazenda não pode estar vazio.");
            }
    
            String oldFarmState = farm.getFarmState();
    
            farm.setFarmState(newState);
            farmRepository.save(farm);
    
            return String.format(
                "Estado da fazenda '" + farm.getFarmName() + "' alterado com sucesso: De '" + oldFarmState + "' para '" + farm.getFarmState() + "'."
            );
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Erro ao alterar o estado da fazenda: " + e.getMessage());
        }
    }


    public String deleteFarmById(Long id) {
        String deletedFarm = "";

        try {
            Farm farm = farmRepository.getFarmById(id);

            if(farm == null){
                throw new Exception();
            }

            deletedFarm = farm.getFarmName();
            farmRepository.deleteById(id);
            
            return deletedFarm+" foi deletada com sucesso!";
        } catch (Exception e) {
            throw new DefaultException("Não existe fazenda com o id "+id);
        }
    }
}
