package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.CultureDTO;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class CultureService {

    @Autowired
    CultureRepository cultureRepository;

    public String createCulture(CultureDTO cultureName){
        try {
            if(cultureName.getName().isEmpty()){
                throw new Exception();
            }
            
            Culture culture = new Culture();
            culture.setName(cultureName.getName());
            cultureRepository.save(culture);

            return "Cultura de " + cultureName.getName() + " criada com sucesso.";
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

    public String updateCultureName(Long id, String cultureName){
        try {
            String oldName = "";
            Culture culture = cultureRepository.getCultureById(id);

            if(culture == null){
                throw new Exception();
            }
            if(cultureName.isEmpty()){
                throw new Exception();
            }

            oldName = culture.getName();
            culture.setName(cultureName);
            cultureRepository.save(culture);

            return "Nome da cultura atualizado de " + oldName + " para " + cultureName + ".";
        }
        catch (Exception e) {
            throw new DefaultException("Não foi possível atualizar o nome da cultura.");
        }
    }

    public String deleteCulture(Long id){
        try {
            Culture culture = cultureRepository.getCultureById(id);
            if(culture == null){
                throw new Exception();
            }
            cultureRepository.delete(culture);
            return "Cultura de " + culture.getName() + " deletada com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível deletar a cultura.");
        }
    }
}
