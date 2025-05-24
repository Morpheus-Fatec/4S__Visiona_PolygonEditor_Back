package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationFeatureCollection;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ManualClassification;

import jakarta.transaction.Transactional;

public interface ManualClassificationRepository extends JpaRepository<ManualClassification, Long>{

    @Query(value = """
        SELECT 
            cm.id_classificacao_manual AS id,
            cm.area AS area,
            ST_AsGeoJSON(cm.coordenadas_manual) AS coordinates,
            c.nome as classEntity
        FROM 
            controle_classificacao cc
        JOIN classificacao_manual cm ON cc.id_controle_classificacao = cm.id_controle_classificacao
        JOIN classes c ON cm.id_classe = c.id_classe
        WHERE 
            cc.id_talhao = :fieldId
        """, nativeQuery = true)
    List<ClassificationDTO> getManualClassificationByFieldId(Long fieldId);


    List<ManualClassification> findByClassificationControl(ClassificationControl control);

    void deleteByClassificationControl(ClassificationControl control);

        @Query(value = """
            SELECT m.*
            FROM classificacao_manual m
            LEFT JOIN classificacao_automatica a
            ON ST_Intersects(m.coordenadas_manual, a.coordenadas_automatica)
            AND m.id_controle_classificacao = a.id_controle_classificacao
            WHERE a.id_classificacao_automatica IS NULL
            AND m.id_controle_classificacao = :controlId
            """, nativeQuery = true)
        ManualClassificationFeatureCollection findFalseNegatives(@Param("controlId") Long controlId);

    @Query(value = """
        SELECT m.*
        FROM classificacao_manual m
        RIGHT JOIN classificacao_automatica a
        ON ST_Intersects(a.coordenadas_automatica, m.coordenadas_manual)
        AND a.id_controle_classificacao = m.id_controle_classificacao
        WHERE m.id_classificacao_manual IS NULL
        AND a.id_controle_classificacao = :controlId
        """, nativeQuery = true)
    ManualClassificationFeatureCollection findFalsePositives(@Param("controlId") Long controlId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM classificacao_manual WHERE id_controle_classificacao = :controlId", nativeQuery = true)
    void deleteAllByControlId(@Param("controlId") Long controlId);

}