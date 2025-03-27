package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.Classification;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {

}
