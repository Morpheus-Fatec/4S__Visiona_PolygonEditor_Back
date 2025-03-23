package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Culture;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long> {
    Culture getCultureByName(String cultureName);
}
