package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class CultureService {
    @Autowired
    CultureRepository cultureRepository;
    
    public String createCulture(String cultureName){
        try {   
            if(cultureName.isEmpty()){
                throw new Exception();
            }

            Culture culture = new Culture();
            culture.setName(cultureName);
            cultureRepository.save(culture);

            return "Cultura criada com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível criar a cultura.");
        }
    }

    public List<Culture> getAllCultures(){
        try {
            List<Culture> cultures = cultureRepository.findAll();

            if(cultures.isEmpty()){
                throw new Exception();
            }
            
            return cultures;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar culturas.");
        }
    }

    public Culture getCultureById(Long id){
        try {
            Culture culture = cultureRepository.findById(id).get();

            if(culture == null){
                throw new Exception();
            }

            return culture;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar a cultura.");
        }
    }
}
