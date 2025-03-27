package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Field;
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.entity.ClassEntity;
import com.morpheus.backend.entity.Classification;
import com.morpheus.backend.repository.ClassEntityRepository;
import com.morpheus.backend.repository.ClassificationRepository;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private ClassEntityRepository classEntityRepository;

    public void createClassification(Field field, List<ClassificationDTO> classificationDTO) {

        for (ClassificationDTO classification : classificationDTO) {
            Classification classificationEntity = new Classification();
            classificationEntity.setField(field);
            classificationEntity.setArea(classification.getArea());
            classificationEntity.setOriginalCoordinates(classification.getCoordinates());
            ClassEntity classEntity = classEntityRepository.findByName(classification.getClassEntity())
            .orElseGet(() -> {
                ClassEntity newClassEntity = new ClassEntity();
                newClassEntity.setName(classification.getClassEntity());
                return classEntityRepository.save(newClassEntity);
            });
            classificationEntity.setClassEntity(classEntity);
            classificationRepository.save(classificationEntity);
            System.out.println("Classification created: " + classificationEntity);
        }
        
    }

}
