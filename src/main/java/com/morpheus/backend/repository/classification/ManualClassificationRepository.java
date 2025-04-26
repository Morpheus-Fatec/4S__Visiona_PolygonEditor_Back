package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ManualClassification;

public interface ManualClassificationRepository extends JpaRepository<ManualClassification, Long>{

    @Query(value = """
        SELECT 
            ca.id_classificacao_automatica AS id,
            ca.area AS area,
            ST_AsGeoJSON(ca.coordenadas_automatica) AS coordinates,
            c.nome as classEntity
        FROM 
            controle_classificacao cc
        JOIN classificacao_automatica ca ON cc.id_controle_classificacao = ca.id_controle_classificacao
        JOIN classes c ON ca.id_classe = c.id_classe
        WHERE 
            cc.id_talhao = :fieldId
        """, nativeQuery = true)
    List<ClassificationDTO> getManualClassificationByFieldId(Long fieldId);

    void deleteByClassificationControl(ClassificationControl control);
}
