package com.morpheus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.entity.Classification;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    @Query(value = """
        SELECT 
        c.id_classificacao AS id,
        c.area AS area,
        ST_AsGeoJSON(c.coordenadas_originais) AS coordinates,
        cl.nome AS classEntity
        
    FROM 
        classificacoes c
    JOIN 
        classes cl ON cl.id_classe = c.id_classe
    WHERE 
        c.id_talhao = :fieldId
        """, nativeQuery = true)
    List<ClassificationDTO>getClassificationByFieldId(@Param("fieldId") Long fieldId);
}
