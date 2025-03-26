package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Scan;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long>{
    
}
