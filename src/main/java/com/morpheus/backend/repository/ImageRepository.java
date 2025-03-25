package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Image;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findById(Long id);
}
