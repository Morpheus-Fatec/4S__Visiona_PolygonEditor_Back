package com.morpheus.backend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    public String createFarm(FarmDTO farmDTO){
        
        try {
            Farm farm = new Farm();
            farm.setFarmName(farmDTO.getFarmName());
            farm.setFarmState(farmDTO.getFarmState());
            farm.setFarmCity(farmDTO.getFarmCity());
    
            farmRepository.save(farm);

            return "Fazenda " + farm.getFarmName() + " criado com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível criar");
        }
    }
    

    public List<Farm> getAllFarms(){
        List<Farm> farmList =  farmRepository.findAll();

        try {
            if (farmList == null){
                throw new Exception();
            }

            return farmList;

        } catch (Exception e) {
            throw new DefaultException("Não há nenhuma fazendo cadastrada.");
        }
    }

    public Farm getFarmById(Long id){
        try {
            Farm farm = farmRepository.getFarmById(id);

            if(farm == null){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new DefaultException("Não existe a fazenda com o id " + id);
        }
        return farmRepository.findById(id).orElse(null);
    }

    public String updateFarmName(Long farmId, FarmDTO newName) {
        String oldFarmName = "";

        try {
            Farm farm = farmRepository.getFarmById(farmId);

            if (farm == null){
                throw new Exception();
            }
            oldFarmName = farm.getFarmName();
            farm.setFarmName(newName.getFarmName());
            farmRepository.save(farm);

            return "Nome da Fazenda alterada de " + oldFarmName + " para: "+farm.getFarmName().toString(); 
        } catch (Exception e) {
            throw new DefaultException("Não foi possível alterar o nome da fazenda.");
        }
    }

    public String updateFarmState(Long farmId, FarmDTO newState) {
        String oldFarmState = "";

        try {
            Farm farm = farmRepository.getFarmById(farmId);

            if (farm == null){
                throw new Exception();
            }
            oldFarmState = farm.getFarmState();
            farm.setFarmState(newState.getFarmState());
            farmRepository.save(farm);

            return "Estado da Fazenda alterada de " + oldFarmState + " para: "+farm.getFarmState().toString(); 
        } catch (Exception e) {
            throw new DefaultException("Não foi possível alterar o nome da fazenda.");
        }
    }

    public String updateFarmCity(Long farmId, FarmDTO newCity) {
        String oldFarmCity = "";

        try {
            Farm farm = farmRepository.getFarmById(farmId);

            if (farm == null){
                throw new Exception();
            }
            oldFarmCity = farm.getFarmCity();
            farm.setFarmCity(newCity.getFarmCity());
            farmRepository.save(farm);

            return "Cidade da Fazenda alterada de " + oldFarmCity + " para: "+farm.getFarmCity().toString(); 
        } catch (Exception e) {
            throw new DefaultException("Não foi possível alterar o nome da fazenda.");
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
