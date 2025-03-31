package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.ClassEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {
    Optional<ClassEntity> findByName(String name);
    List<ClassEntity> findByNameIn(Set<String> names);
}
