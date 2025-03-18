package com.morpheus.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;

@Service
public class FieldService {
    private Field field;
    private FieldRepository fieldRepository;
    private Farm farm;
    private FarmRepository farmRepository;

    public String createField (FieldDTO fieldDTO) {
        try {
            if (field != null){
                Farm farm = farmRepository.getFarmById(fieldDTO.getFarm().getIdFarm());
                field.setFarm(farm);
                field.setCulture(fieldDTO.getCulture());
                field.setIdProductivity(fieldDTO.getProductivity());
                field.setArea(fieldDTO.getArea());
                field.setSoil(fieldDTO.getSoil());
                fieldRepository.save(field);
            }
        } catch (Exception e) {
            throw new IllegalAccessError("Não foi criado o talhão" + e);
        }

        return "Talhão criado com sucesso!";
    }

    public List<Field> getAllFields(){
        List<Field> fieldsList = fieldRepository.findAll();

        try {
            if (fieldsList != null){
                
            }            
        } catch (Exception e) {
            throw new IllegalAccessError("Não existe nenhum talhão cadastrado" + e);
        }

        return fieldsList;
    }

    public Field getFieldById(int fieldId){
        Field field = fieldRepository.getFieldById(fieldId);
        try {
            if (field != null){

            }
        } catch (Exception e){
            throw new IllegalAccessError("Não existe talhão com o id " + fieldId);
        }

        return field;
    }

    public String updateFarm(int idField, Long newFarm){
       String oldFarm = "";

        try{
            Field field = fieldRepository.getFieldById(idField);
            oldFarm = field.getFarm().getFarmName();

            Farm farm = farmRepository.getFarmById(newFarm);
            field.setFarm(farm);
        } catch (Exception e){
            throw new IllegalAccessError("Não foi possível atualizar a fazenda para o talhão " + e);
        }

        return "Fazenda: " + oldFarm + ", atualizada para: " + farm.getFarmName(); 
    }
}