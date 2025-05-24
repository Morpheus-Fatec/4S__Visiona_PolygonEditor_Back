package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.morpheus.backend.entity.classifications.ClassificationControl;


public interface ClassificationControlRepository extends JpaRepository<ClassificationControl, Long>{

    @Query(
    value = "SELECT * FROM controle_classificacao WHERE id_talhao = :fieldId LIMIT 1",
    nativeQuery = true
    )
    ClassificationControl findByFieldId(Long fieldId);

    @Query("SELECT c.analystResponsable.id FROM ClassificationControl c WHERE c.field.id = :fieldId")
    Long getAnalystResponsableByFieldId(@Param("fieldId") Long fieldId);

    @Query("SELECT c.consultantResponsable.id FROM ClassificationControl c WHERE c.field.id = :fieldId")
    Long getConsultationResponsableByFieldId(@Param("fieldId") Long fieldId);

    @Query(value = """
    SELECT 
        a.id_classificacao_automatica AS id,
        a.area AS area,
	    c.nome AS nome,
        ST_AsGeoJSON(a.coordenadas_automatica) AS geojson
    FROM classificacao_automatica a
    JOIN classes c ON a.id_classe = c.id_classe
    WHERE a.id_controle_classificacao = :idControle
    AND NOT EXISTS (
        SELECT 1
        FROM classificacao_manual m
        WHERE m.id_classe = a.id_classe
        AND m.id_controle_classificacao = :idControle
        AND ST_Intersects(a.coordenadas_automatica, m.coordenadas_manual)
    );
    """, nativeQuery = true)
    List<Object[]> getFalsePositivesByControlId(@Param("idControle") Long idControle);


        @Query(value = """
        SELECT 
            m.id_classificacao_manual AS id,
            m.area AS area,
            c.nome AS nome,
            ST_AsGeoJSON(m.coordenadas_manual) AS geojson
        FROM classificacao_manual m
        JOIN classes c ON m.id_classe = c.id_classe
        WHERE m.id_controle_classificacao = :idControle
        AND NOT EXISTS (
            SELECT 1
            FROM classificacao_automatica a
            WHERE a.id_classe = m.id_classe
            AND a.id_controle_classificacao = :idControle
            AND ST_Intersects(m.coordenadas_manual, a.coordenadas_automatica)
        );
        """, nativeQuery = true)
    List<Object[]> getFalseNegativesByControlId(@Param("idControle") Long idControle);





}
