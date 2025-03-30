package com.morpheus.backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Image;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long id);[

    @Query("SELECT i FROM Image i WHERE i.scan.id = :scanID")
    List<Image> getImagesByScanId(Long scanID);
}
