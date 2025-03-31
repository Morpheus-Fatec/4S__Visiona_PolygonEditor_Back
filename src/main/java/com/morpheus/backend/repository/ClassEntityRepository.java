package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.ClassEntity;

import java.util.Optional;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {
    Optional<ClassEntity> findByName(String name);
}
