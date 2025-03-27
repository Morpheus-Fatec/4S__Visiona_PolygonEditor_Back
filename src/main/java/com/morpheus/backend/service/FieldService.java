package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.entity.Status;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class FieldService {
    private Field field;

    @Autowired
    private FieldRepository fieldRepository;
    
    private Farm farm;
    
    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private SoilRepository soilRepository;
    
    private Status status;

    public Field createField(CreateFieldDTO fieldDTO, Scan scan){ 
        try {
            if (fieldDTO.getFarmname().isEmpty()){
                throw new DefaultException("Farm não pode ser nulo.");
            }

            Farm farmField = new Farm();
            farmField.setFarmName(fieldDTO.getFarmname());
            Farm farm = farmRepository.save(farmField);

            Culture farmCulture = new Culture();  
            farmCulture.setName(fieldDTO.getCulture());
            Culture culture = cultureRepository.save(farmCulture);

            Soil farmSoil = new Soil();
            farmSoil.setName(fieldDTO.getSoil());
            Soil soil = soilRepository.save(farmSoil);
    
            Field field = new Field();
            field.setFarm(farm);
            field.setHarvest(fieldDTO.getHarvest());
            field.setCulture(culture);
            field.setSoil(soil);
            field.setName(fieldDTO.getFarmname());
            field.setArea(fieldDTO.getArea());
            field.setProductivity(fieldDTO.getProductivity());
            field.setStatus(Status.fromPortuguese("Pendente"));
            field.setCoordinates(fieldDTO.getCoordinates());
            field.setScanning(scan);
            fieldRepository.save(field);
    
            return field;

    
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