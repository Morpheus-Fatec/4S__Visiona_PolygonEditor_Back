package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.entity.Soil;

public interface SoilRepository extends JpaRepository<Soil, Long> {

    Soil getSoilById(Long id);
}
