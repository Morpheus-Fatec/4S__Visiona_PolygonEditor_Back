package com.morpheus.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.classifications.ClassificationAutomatic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.AutomaticClassificationDTO;
import com.morpheus.backend.entity.ClassEntity;
import com.morpheus.backend.repository.ClassEntityRepository;
import com.morpheus.backend.repository.classification.ClassificationAutomaticRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationAutomaticRepository ClassificationAutomaticRepository;

    @Autowired
    private ClassEntityRepository classEntityRepository;

    @Transactional
    public void createAutomaticClassification(Field field, List<AutomaticClassificationDTO> automaticClassificationDTO) throws JsonProcessingException {
        List<ClassificationAutomatic> automaticClassifications = new ArrayList<>();
        Map<String, ClassEntity> classEntityCache = new HashMap<>();
        Set<String> classEntityNames = automaticClassificationDTO.stream()
                .map(AutomaticClassificationDTO::getClassEntity)
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

        for (AutomaticClassificationDTO classificationDTO : automaticClassificationDTO) {
            ClassificationAutomatic classificationAutomatic = new ClassificationAutomatic();
            classificationAutomatic.setArea(classificationDTO.getArea());
            classificationAutomatic.setCoordenadas(classificationDTO.convertStringToMultiPolygon());
            classificationAutomatic.setClassEntity(classEntityCache.get(classificationDTO.getClassEntity()));
            automaticClassifications.add(classificationAutomatic);
        }

        ClassificationAutomaticRepository.saveAll(automaticClassifications);
    }
}
