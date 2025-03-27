package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.morpheus.backend.entity.Culture;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long> {
    Optional<Culture> findByName(String name);

    Culture getCultureById(Long id);

    
}

