package com.morpheus.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.entity.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>{
    @Query(value = """
    SELECT 
        t.id_talhao AS id,
        t.id_leitura AS scanningId,
        t.nome AS nome,
        t.produtividade AS productivity,
        fa.nome AS farmName,
        c.id_cultura AS cultureId,
        c.nome AS cultureNome,
        ST_AsGeoJSON(t.coordenadas) AS coordinates,
        t.estado AS status,
        t.area AS area,
        t.safra AS harvest,
        fa.cidade as farmCity,
        fa.estado as farmState,
        fa.id_fazenda as farm_id,
        so.nome as soilName,
        so.id_solo as soilId
    FROM Talhoes t
    JOIN Fazendas fa ON t.id_fazenda = fa.id_fazenda
    LEFT JOIN Culturas c ON t.id_cultura = c.id_cultura
    LEFT JOIN Solos so ON t.id_solo = so.id_solo
    WHERE t.id_talhao = :fieldId
    """, nativeQuery = true)
    Optional<FieldDTO> getFieldById(@Param("fieldId") Long fieldId);


    @Query("SELECT f FROM Field f WHERE f.id = :fieldId")
    Field getFieldEntityById(Long fieldId);

    @Query(value = """
        SELECT 
            t.id_talhao AS id,
            t.nome AS nome,
            fa.nome AS farmName,
            c AS culture,
            ST_AsGeoJSON(t.coordenadas) AS coordinates,
            t.estado AS status,
            t.area AS area,
            t.safra AS harvest,
            fa.cidade as farmCity,
            fa.estado as farmState,
            so as soil,
            t.id_leitura AS scanningId
        FROM Talhoes t
        JOIN Fazendas fa ON t.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON t.id_cultura = c.id_cultura
        LEFT JOIN Solos so ON t.id_solo = so.id_solo
        WHERE t.id_leitura = :scanId
        LIMIT 1
    """, nativeQuery = true)
    Optional<FieldDTO> getFieldByScanningId(@Param("scanId") Long scanId);



    @Query(value = """
        SELECT 
            t.id_talhao AS id,
            t.nome AS nome,
            t.produtividade AS productivity,
            fa.nome AS farmName,
            c.id_cultura AS cultureId,
            c.nome AS cultureNome,
            ST_AsGeoJSON(t.coordenadas) AS coordinates,
            t.estado AS status,
            t.area AS area,
            t.safra AS harvest,
            fa.cidade as farmCity,
            fa.estado as farmState,
            fa.id_fazenda as farm_id,
            so.nome as soilName,
            so.id_solo as soilId
        FROM Talhoes t
        JOIN Fazendas fa ON t.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON t.id_cultura = c.id_cultura
        LEFT JOIN Solos so ON t.id_solo = so.id_solo
        WHERE
            (COALESCE(:nome, '') = '' OR LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND
            (COALESCE(:soil, '') = '' OR LOWER(so.nome) LIKE LOWER(CONCAT('%', :soil, '%'))) AND
            (COALESCE(:status, '') = '' OR LOWER(t.estado) LIKE LOWER(CONCAT('%', :status, '%'))) AND
            (COALESCE(:culture, '') = '' OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :culture, '%'))) AND
            (COALESCE(:harvest, '') = '' OR LOWER(t.safra) LIKE LOWER(CONCAT('%', :harvest, '%'))) AND
            (COALESCE(:farmName, '') = '' OR LOWER(fa.nome) LIKE LOWER(CONCAT('%', :farmName, '%')))
        """, countQuery = """
                 SELECT 
            COUNT(*)
        FROM Talhoes t
        JOIN Fazendas fa ON t.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON t.id_cultura = c.id_cultura
        LEFT JOIN Solos so ON t.id_solo = so.id_solo
        WHERE
            (COALESCE(:nome, '') = '' OR LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND
            (COALESCE(:soil, '') = '' OR LOWER(so.nome) LIKE LOWER(CONCAT('%', :soil, '%'))) AND
            (COALESCE(:status, '') = '' OR LOWER(t.estado) LIKE LOWER(CONCAT('%', :status, '%'))) AND
            (COALESCE(:culture, '') = '' OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :culture, '%'))) AND
            (COALESCE(:harvest, '') = '' OR LOWER(t.safra) LIKE LOWER(CONCAT('%', :harvest, '%'))) AND
            (COALESCE(:farmName, '') = '' OR LOWER(fa.nome) LIKE LOWER(CONCAT('%', :farmName, '%')))
                """, nativeQuery = true)
    Page<FieldDTO> getAllFeatureSimpleDTO(
        @Param("nome") String name,
        @Param("soil") String soil,
        @Param("status") String status,
        @Param("culture") String culture,
        @Param("harvest") String harvest,
        @Param("farmName") String farmNam,
        Pageable pageable
    );

    boolean existsByFarm_FarmName(String farmName);
    boolean existsByCulture_Name(String cultureName);
    boolean existsBySoil_Name(String soilName);
}
