package com.morpheus.backend.repository.classification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ClassificationManual;

public interface ClassificationManualRepository extends JpaRepository<ClassificationManual, Long>{

    void deleteByClassificationControl(ClassificationControl control);
}
