package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class CultureService {

    @Autowired
    CultureRepository cultureRepository;

    @Autowired
    FieldRepository fieldRepository;


    public List<Culture> getAllCultures(){
        try {
            List<Culture> cultures = cultureRepository.findAll();
            
            if(cultures.isEmpty()){
                throw new Exception();
            }
            
            return cultures;
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar culturas.");
        }
    }

    public Culture getCultureById(Long id){
        try {
            Culture culture = cultureRepository.getCultureById(id);
            
            if(culture == null){
                throw new Exception();
            }
            
            return culture;
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar a cultura.");
        }
    }

    public String createCulture(String cultureName) {
        try {
            if (cultureName == null || cultureName.isEmpty()) {
                throw new DefaultException("O nome da cultura não pode ser vazio ou nulo.");
            }
            
            Culture culture = new Culture();
            culture.setName(cultureName);
            cultureRepository.save(culture);
            
            return "Cultura de " + culture.getName() + " criada com sucesso.";
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Erro inesperado ao criar a cultura.");
        }
    }

    public String updateCulture(Long id, String cultureName){
        try {
            Culture culture = cultureRepository.getCultureById(id);

            if(culture == null){
                throw new Exception();
            }
            if(cultureName == null || cultureName.isEmpty()){
                throw new Exception();
            }

            String oldName = culture.getName();
            culture.setName(cultureName);
            cultureRepository.save(culture);

            return "Nome da cultura atualizado de " + oldName + " para " + cultureName + ".";
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível atualizar o nome da cultura.");
        }
    }

    public String deleteCulture(Long id){
        try {
            if(id == null){
                throw new Exception();
            }
            
            Culture culture = cultureRepository.getCultureById(id);
            
            if(culture == null){
                throw new Exception();
            }
            if (fieldRepository.existsByCulture_Name(culture.getName())) {
                throw new DefaultException("Não é possível deletar a cultura, pois ela está associada a um talhão.");
            }
            
            cultureRepository.delete(culture);
            return "Cultura de " + culture.getName() + " deletada com sucesso.";
        } catch (DefaultException e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível deletar a cultura.");
        }
    }
}
