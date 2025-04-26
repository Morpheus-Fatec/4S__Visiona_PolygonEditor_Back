package com.morpheus.backend.repository.classification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.classifications.RevisionManualClassification;


public interface RevisionManualClassificationRepository extends JpaRepository<RevisionManualClassification, Long>{

}
