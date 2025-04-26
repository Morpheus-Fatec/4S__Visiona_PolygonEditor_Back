package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationFeature;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ManualClassification;

public interface ManualClassificationRepository extends JpaRepository<ManualClassification, Long>{


    List<ClassificationFeature> getManualClassificationByFieldId(Long fieldId);
    void deleteByClassificationControl(ClassificationControl control);
}
