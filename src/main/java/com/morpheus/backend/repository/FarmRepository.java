package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Farm;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    Farm getFarmById(Long idFarm);    
    Farm getByFarmName(String farmName);
}