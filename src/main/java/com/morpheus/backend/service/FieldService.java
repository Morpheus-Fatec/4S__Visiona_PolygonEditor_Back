package com.morpheus.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Status;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;

@Service
public class FieldService {
    private Field field;
    private FieldRepository fieldRepository;
    private Farm farm;
    private FarmRepository farmRepository;
    private Status status;

    public String createField (FieldDTO fieldDTO) {
        try {
            Farm farm = farmRepository.getFarmById(fieldDTO.getFarm().getId());

            if (farm != null){
                field.setFarm(farm);
                field.setCulture(fieldDTO.getCulture());
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

    public Field getFieldById(Long fieldId){
        Field field = fieldRepository.getFieldById(fieldId);
        try {
            if (field != null){

            }
        } catch (Exception e){
            throw new IllegalAccessError("Não existe talhão com o id " + fieldId);
        }

        return field;
    }

    public String updateFarm(Long idField, Long newFarm){
       String oldFarm = "";

        try{
            Field field = fieldRepository.getFieldById(idField);
            Farm farm = farmRepository.getFarmById(newFarm);
            
            if(field != null && farm != null){
                oldFarm = field.getFarm().getFarmName();
                field.setFarm(farm);
            }
        } catch (Exception e){
            throw new IllegalAccessError("Não foi possível atualizar a fazenda para o talhão " + e);
        }

        return "Fazenda: " + oldFarm + ", atualizada para: " + farm.getFarmName(); 
    }

    public String updateCulture(Long idField, String newCulture){
        String oldCulture = "";
        try {
            Field field = fieldRepository.getFieldById(idField);
            
            if(field != null){
                oldCulture = field.getCulture();
                field.setCulture(newCulture);
            }    
        } catch (Exception e) {
            throw new IllegalAccessError("Não foi possível alterar a cultura do talhão" + e);
        }

        return "Cultura alterada de: " + oldCulture + " para: " + field.getCulture();
    }


    public String updateStatus(Long idField, String statusParam){
        String oldStatus = "";

        try {
            Field field = fieldRepository.getFieldById(idField);

                if(field != null && status != null){
                    oldStatus = field.getStatus().toString();
                    field.setStatus(Status.valueOf(statusParam));
                }
            } catch (Exception e) {
                throw new IllegalAccessError("Não foi possível alterar o status");
            }

            return "Status alterado de: " + oldStatus + " para: " + field.getStatus();
    }

    public String updateSoil(Long idField, String newSoil){
        String oldSoil = "";

        try {
            Field field = fieldRepository.getFieldById(idField);

            if (field != null) {
                oldSoil = field.getSoil();
                field.setSoil(newSoil);
            }
        } catch (Exception e) {
            throw new IllegalAccessError("Não foi possível alterar o tipo do solo");
        }

        return "Solo alterado de: " + oldSoil + " para: " + field.getSoil();
    }

    public String deleteFieldById(Long idField){
        try{
            Field field = fieldRepository.getFieldById(idField);

            if(field != null){
                fieldRepository.delete(field);
            }
        } catch(Exception e){
            throw new IllegalAccessError("Não foi possível deletar o talhão");
        }

        return "Talhão deletado";
    }
}