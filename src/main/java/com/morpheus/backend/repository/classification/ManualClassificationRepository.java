package com.morpheus.backend.repository.classification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ManualClassification;

public interface ManualClassificationRepository extends JpaRepository<ManualClassification, Long>{

    void deleteByClassificationControl(ClassificationControl control);
}
