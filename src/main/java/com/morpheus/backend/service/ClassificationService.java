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
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.entity.ClassEntity;
import com.morpheus.backend.entity.Classification;
import com.morpheus.backend.repository.ClassEntityRepository;
import com.morpheus.backend.repository.ClassificationRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private ClassEntityRepository classEntityRepository;

    @Transactional
    public void createClassification(Field field, List<ClassificationDTO> classificationDTOs) {
        List<Classification> classifications = new ArrayList<>();
        Map<String, ClassEntity> classEntityCache = new HashMap<>();
        Set<String> classEntityNames = classificationDTOs.stream()
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

        for (ClassificationDTO classificationDTO : classificationDTOs) {
            Classification classificationEntity = new Classification();
            classificationEntity.setField(field);
            classificationEntity.setArea(classificationDTO.getArea());
            classificationEntity.setOriginalCoordinates(classificationDTO.getCoordinates());
            classificationEntity.setClassEntity(classEntityCache.get(classificationDTO.getClassEntity()));
            classifications.add(classificationEntity);
        }

        classificationRepository.saveAll(classifications);
    }

}
