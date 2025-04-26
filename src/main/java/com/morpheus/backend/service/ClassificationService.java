package com.morpheus.backend.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.classifications.AutomaticClassification;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ManualClassification;
import com.morpheus.backend.entity.classifications.RevisionManualClassification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationFeature;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationProperties;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationCollection;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationFeature;
import com.morpheus.backend.DTO.GeoJsonView.revisionClassification.RevisionClassificationCollection;
import com.morpheus.backend.DTO.GeoJsonView.revisionClassification.RevisionFeature;
import com.morpheus.backend.entity.ClassEntity;
import com.morpheus.backend.entity.User;
import com.morpheus.backend.repository.ClassEntityRepository;
import com.morpheus.backend.repository.UserRepository;
import com.morpheus.backend.repository.classification.AutomaticClassificationRepository;
import com.morpheus.backend.repository.classification.ClassificationControlRepository;
import com.morpheus.backend.repository.classification.ManualClassificationRepository;
import com.morpheus.backend.repository.classification.RevisionManualClassificationRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassificationService {

    @Autowired
    private AutomaticClassificationRepository automaticClassificationRepository;

    @Autowired
    private ManualClassificationRepository manualClassificationRepository;

    @Autowired
    private RevisionManualClassificationRepository revisionManualClassificationRepository;

    @Autowired
    private ClassificationControlRepository classificationControlRepository;

    @Autowired
    private ClassEntityRepository classEntityRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ClassificationFeature> getAutomaticClassificationsByFieldId(Long fieldId) {
        List<ClassificationDTO> classifications = automaticClassificationRepository.getAutomaticClassificationByFieldId(fieldId);
            List<ClassificationFeature> classificationDTOs = classifications.stream().map(classification -> {
            ClassificationFeature classificationDTO = new ClassificationFeature();
            ClassificationProperties classificationProperties = new ClassificationProperties(classification.getId(), classification.getArea(), classification.getClassEntity());
            GeometryDTO classificationGeometry = new GeometryDTO();
            try {
                classificationGeometry.convertToGeoJson(classification.getCoordinates());
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            classificationDTO.setProperties(classificationProperties);
            classificationDTO.setGeometry(classificationGeometry);
            return classificationDTO;
        }).collect(Collectors.toList());

        return classificationDTOs;
    }

    @Transactional
    public void saveAutomaticClassification(ClassificationControl control, List<ClassificationDTO> automaticClassificationDTO) throws JsonProcessingException {
        List<AutomaticClassification> automaticClassifications = new ArrayList<>();
        Map<String, ClassEntity> classEntityCache = new HashMap<>();
        Set<String> classEntityNames = automaticClassificationDTO.stream()
                .map(ClassificationDTO::getClassEntity)
                .collect(Collectors.toSet());

        List<ClassEntity> existingClassEntities = classEntityRepository.findByNameIn(classEntityNames);
        for (ClassEntity classEntity : existingClassEntities) {
            classEntityCache.put(classEntity.getName(), classEntity);
        }

        List<ClassEntity> newClassEntities = new ArrayList<>();
        for (String className : classEntityNames) {
            if (!classEntityCache.containsKey(className)) {
                ClassEntity newClassEntity = new ClassEntity();
                newClassEntity.setName(className);
                newClassEntities.add(newClassEntity);
                classEntityCache.put(className, newClassEntity);
            }
        }

        if (!newClassEntities.isEmpty()) {
            classEntityRepository.saveAll(newClassEntities);
        }

        for (ClassificationDTO classificationDTO : automaticClassificationDTO) {
            AutomaticClassification classificationAutomatic = new AutomaticClassification();
            classificationAutomatic.setClassificationControl(control);
            classificationAutomatic.setArea(classificationDTO.getArea());
            classificationAutomatic.setCoordenadas(classificationDTO.convertStringToMultiPolygon());
            classificationAutomatic.setClassEntity(classEntityCache.get(classificationDTO.getClassEntity()));
            automaticClassifications.add(classificationAutomatic);
        }

        automaticClassificationRepository.saveAll(automaticClassifications);
    }

    public List<ManualClassificationFeature> getManualClassificationByFieldId(Long fieldId) {
        List<ClassificationDTO> manual = manualClassificationRepository.getManualClassificationByFieldId(fieldId);

        List<ManualClassificationFeature> manualDTOs = manual.stream().map(manualClassification -> {
            ManualClassificationFeature manualDTO = new ManualClassificationFeature();
            ClassificationProperties classificationProperties = new ClassificationProperties(manualClassification.getId(), manualClassification.getArea(), manualClassification.getClassEntity());
            GeometryDTO classificationGeometry = new GeometryDTO();
            try {
                classificationGeometry.convertToGeoJson(manualClassification.getCoordinates());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            manualDTO.setProperties(classificationProperties);
            manualDTO.setGeometry(classificationGeometry);
            return manualDTO;
        }).collect(Collectors.toList());
        return manualDTOs;
        
    }   

    @Transactional
    public void saveManualClassification(ManualClassificationCollection manualDTO) throws Exception {
        
        ClassificationControl control = classificationControlRepository.findByFieldId(manualDTO.getIdField());
        User userResponsable = userRepository.getUserById(manualDTO.getUserResponsable());
        Duration duration = null;
        try {
            duration = Duration.between(manualDTO.getBegin(), manualDTO.getEnd());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular a duração: " + e.getMessage());
        }
        

        if (control.getAnalystResponsable() == null) {
            control.setAnalystResponsable(userResponsable);
        } else if (!Objects.equals(control.getAnalystResponsable().getId(), userResponsable.getId())) {
            throw new RuntimeException("Este controle de classificação já está sendo editado por outro usuário.");
        }

        if (control.getTimeSpentManual() == null) {
            control.setTimeSpentManual(duration);
        } else {
            control.setTimeSpentManual(control.getTimeSpentManual().plus(duration));
        }

        control.setCountManualInteractions(control.getCountManualInteractions() + 1);
        classificationControlRepository.save(control);


        manualClassificationRepository.deleteByClassificationControl(control);
        try {
            for (ClassificationFeature feature : manualDTO.getFeatures()) {
                ManualClassification manual = new ManualClassification();
    
                manual.setClassificationControl(control);
                manual.setArea(feature.getProperties().getArea());
    
                Optional<ClassEntity> optionalClassEntity = classEntityRepository.findByName(feature.getProperties().getClassEntity());
    
                ClassEntity classEntity = optionalClassEntity.orElseThrow(() -> 
                    new RuntimeException("Classe não encontrada: " + feature.getProperties().getClassEntity())
                );
    
                manual.setClassEntity(classEntity);
                manual.setCoordenadas(feature.getGeometry().convertStringToMultiPolygon());
    
                manualClassificationRepository.save(manual);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a classificação manual: " + e.getMessage());
        }
        
    }

    @Transactional
    public void saveRevisionClassification(RevisionClassificationCollection revisionClassificationCollection) throws Exception {
        ClassificationControl control = classificationControlRepository.findByFieldId(revisionClassificationCollection.getIdField());
        User userResponsable = userRepository.getUserById(revisionClassificationCollection.getUserResponsable());
        Duration duration = null;
        try {
            duration = Duration.between(revisionClassificationCollection.getBegin(), revisionClassificationCollection.getEnd());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular a duração: " + e.getMessage());
        }

        if (control.getAnalystResponsable() == null) {
            control.setAnalystResponsable(userResponsable);
        } else {
            throw new RuntimeException("é necessário um consultor vinculado.");
        }

        if (control.getTimeSpentRevision() == null) {
            control.setTimeSpentRevision(duration);
        } else {
            control.setTimeSpentRevision(control.getTimeSpentRevision().plus(duration));
        }

        classificationControlRepository.save(control);

        revisionManualClassificationRepository.deleteByClassificationControl(control);

        try {
            for (RevisionFeature revision : revisionClassificationCollection.getFeatures()) {
                RevisionManualClassification revisionEntity = new RevisionManualClassification();
                revisionEntity.setClassificationControl(control);
                revisionEntity.setCoordenatiesHighLight(revision.getGeometry().convertStringToMultiPolygon());
                revisionEntity.setComment(revision.getProperties().getDescription());
                revisionManualClassificationRepository.save(revisionEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a classificação manual: " + e.getMessage());
        }

    }
}
