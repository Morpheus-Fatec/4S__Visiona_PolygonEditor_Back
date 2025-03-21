package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Status;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class FieldService {
    private Field field;

    @Autowired
    private FieldRepository fieldRepository;
    
    private Farm farm;
    
    @Autowired
    private FarmRepository farmRepository;
    
    private Status status;

    public String createField(FieldDTO fieldDTO) {
        try {
            if (fieldDTO.getFarm() == null || fieldDTO.getFarm().getId() == null) {
                throw new DefaultException("Farm não pode ser nulo.");
            }
    
            Farm farm = farmRepository.getFarmById(fieldDTO.getFarm().getId());
    
            if (farm == null) {
                throw new DefaultException("Fazenda não encontrada.");
            }
    
            Field field = new Field();
            field.setFarm(farm);
            field.setCulture(fieldDTO.getCulture());
            field.setArea(fieldDTO.getArea());
            field.setSoil(fieldDTO.getSoil());
    
            fieldRepository.save(field);
    
            return "Talhão criado com sucesso!";
        } catch (Exception e) {
            throw new DefaultException("Erro ao criar o talhão: " + e.getMessage());
        }
    }
    

    public List<Field> getAllFields(){
        List<Field> fieldsList = fieldRepository.findAll();

        try {
            if (fieldsList.size() == 0){
                throw new Exception();
            }            
        } catch (Exception e) {
            throw new DefaultException("Não existe nenhum talhão cadastrado" + e);
        }

        return fieldsList;
    }

    public Field getFieldById(Long fieldId){
        Field field = fieldRepository.getFieldById(fieldId);
        try {
            if (field == null){
                throw new Exception();
            }
        } catch (Exception e){
            throw new RuntimeException("Não existe talhão com o id " + fieldId);
        }

        return field;
    }

    public String updateFarm(Long idField, Long newFarm){
       String oldFarm = "";

        try{
            Field field = fieldRepository.getFieldById(idField);
            Farm farm = farmRepository.getFarmById(newFarm);
            
            if(field == null){
                throw new IllegalAccessError("Não há talhão com o id " + idField);
            } else if (farm == null){
                throw new IllegalAccessError("Não há fazenda com o id " + newFarm);
            }
            
            oldFarm = field.getFarm().getFarmName();
            field.setFarm(farm);
        } catch (Exception e){
            throw new RuntimeException();
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