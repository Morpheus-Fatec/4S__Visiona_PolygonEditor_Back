package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
    Status getStatusById(Long idStatus);
} 