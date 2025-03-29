package com.morpheus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.morpheus.backend.entity.Classification;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {

    @Query("SELECT c FROM Classification c WHERE c.field.id = :fieldId")
    List<Classification> getClassificationByFieldId(Long fieldId);

}
