package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.Soil;

import java.util.Optional;

public interface SoilRepository extends JpaRepository<Soil, Long> {
    Optional<Soil> findByName(String name);
    Soil getSoilById(Long id);
}
