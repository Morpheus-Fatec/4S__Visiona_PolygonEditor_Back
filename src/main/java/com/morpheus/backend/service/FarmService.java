package com.morpheus.backend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.repository.FarmRepository;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    public Farm createFarmOnly(@Validated Farm farm){
        formatFarmFields(farm);
        return farmRepository.save(farm);
    }
    

    public List<Farm> getAllFarms(){
        return farmRepository.findAll();
    }

    public Farm getFarmById(Long id){
        return farmRepository.findById(id).orElse(null);
    }

    public Farm updateFarm(Long id, @Validated Farm farm) {
        return farmRepository.findById(id).map(existingFarm -> {
            existingFarm.setFarmName(capitalizeWords(farm.getFarmName()));
            existingFarm.setFarmCity(capitalizeWords(farm.getFarmCity()));
            existingFarm.setFarmState(capitalizeWords(farm.getFarmState()));
            return farmRepository.save(existingFarm);
        }).orElse(null);
    }

    public void deleteFarm(Long id) {
        farmRepository.deleteById(id);
    }

    public void deleteAllFarms() {
        farmRepository.deleteAll();
    }
    
    private void formatFarmFields(Farm farm) {
        farm.setFarmName(capitalizeWords(farm.getFarmName()));
        farm.setFarmCity(capitalizeWords(farm.getFarmCity()));
        farm.setFarmState(capitalizeWords(farm.getFarmState()));
    }
    
    private String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split("\\s+");
        StringBuilder capitalizedWords = new StringBuilder();
        String[] lowercaseWords = {"da", "de", "do", "das", "dos", "e"};
        for (int i = 0; i <words.length; i++) {
            String word = words[i].toLowerCase();

            if(i== 0 || !Arrays.asList(lowercaseWords).contains(word)){
                word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            }
            capitalizedWords.append(word).append(" ");
        }
        return capitalizedWords.toString().trim();
    }
}
