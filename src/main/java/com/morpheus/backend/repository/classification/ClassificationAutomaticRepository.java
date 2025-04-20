package com.morpheus.backend.repository.classification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.morpheus.backend.DTO.AutomaticClassificationDTO;
import com.morpheus.backend.entity.classifications.ClassificationAutomatic;

public interface ClassificationAutomaticRepository extends JpaRepository<ClassificationAutomatic, Long>{

    @Query(value = """
        SELECT 
            ca.id_classificacao_automatica AS id,
            ca.area AS area,
            ST_AsGeoJSON(ca.coordenadas_automatica) AS coordinates,
            cl.nome AS classEntity
        FROM 
            controle_classificacao cc
        JOIN classficacao_automatica ca ON cc.id_controle_classificacao = ca.id_controle_classificacao
        JOIN classes cl ON cl.id_classe = ca.id_classe
        WHERE 
            cc.id_talhao = :fieldId
        """, nativeQuery = true)
    List<AutomaticClassificationDTO> getClassificationAutomaticByFieldId(Long fieldId);

}
