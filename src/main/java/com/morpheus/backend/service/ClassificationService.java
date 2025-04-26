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

import com.morpheus.backend.entity.classifications.ClassificationAutomatic;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ClassificationManual;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationFeature;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationCollection;
import com.morpheus.backend.entity.ClassEntity;
import com.morpheus.backend.entity.User;
import com.morpheus.backend.repository.ClassEntityRepository;
import com.morpheus.backend.repository.UserRepository;
import com.morpheus.backend.repository.classification.ClassificationAutomaticRepository;
import com.morpheus.backend.repository.classification.ClassificationControlRepository;
import com.morpheus.backend.repository.classification.ClassificationManualRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationAutomaticRepository ClassificationAutomaticRepository;

    @Autowired
    private ClassificationManualRepository classificationManualRepository;

    @Autowired
    private ClassificationControlRepository classificationControlRepository;

    @Autowired
    private ClassEntityRepository classEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createAutomaticClassification(ClassificationControl control, List<ClassificationDTO> automaticClassificationDTO) throws JsonProcessingException {
        List<ClassificationAutomatic> automaticClassifications = new ArrayList<>();
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
            ClassificationAutomatic classificationAutomatic = new ClassificationAutomatic();
            classificationAutomatic.setClassificationControl(control);
            classificationAutomatic.setArea(classificationDTO.getArea());
            classificationAutomatic.setCoordenadas(classificationDTO.convertStringToMultiPolygon());
            classificationAutomatic.setClassEntity(classEntityCache.get(classificationDTO.getClassEntity()));
            automaticClassifications.add(classificationAutomatic);
        }

        ClassificationAutomaticRepository.saveAll(automaticClassifications);
    }

    @Transactional
    public void createManualClassification(ManualClassificationCollection manualDTO) throws Exception {
        
        ClassificationControl control = classificationControlRepository.findByFieldId(manualDTO.getIdField());
        User userResponsable = userRepository.getUserById(manualDTO.getUserResponsable());
        Duration duration = Duration.between(manualDTO.getBegin(), manualDTO.getEnd());

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


        classificationManualRepository.deleteByClassificationControl(control);
        for (ClassificationFeature feature : manualDTO.getFeatures()) {
            ClassificationManual manual = new ClassificationManual();

            manual.setClassificationControl(control);
            manual.setArea(feature.getProperties().getArea());

            Optional<ClassEntity> optionalClassEntity = classEntityRepository.findByName(feature.getProperties().getClassEntity());

            ClassEntity classEntity = optionalClassEntity.orElseThrow(() -> 
                new RuntimeException("Classe não encontrada: " + feature.getProperties().getClassEntity())
            );

            manual.setClassEntity(classEntity);
            manual.setCoordenadas(feature.getGeometry().convertStringToMultiPolygon());

            classificationManualRepository.save(manual);
        }
    }

    public List<ClassificationManual> findByClassificationControl (ClassificationControl control){
        List<ClassificationManual> manual = classificationManualRepository.findByClassificationControl(control);
        return manual;
    }
}
