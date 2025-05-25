package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.morpheus.backend.DTO.Analysis.AutomaticAnalysisHealthDTO;
import com.morpheus.backend.DTO.Analysis.TableAnalystDTO;
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
    WITH revisoes_por_classificacao AS (
        SELECT cc.id_controle_classificacao, COUNT(rcm.id_revisao_classificacao_manual) AS total_revisoes
        FROM controle_classificacao cc
        LEFT JOIN revisao_classificacao_manual rcm ON cc.id_controle_classificacao = rcm.id_controle_classificacao
        WHERE (:idAnalista IS NULL OR cc.id_analista = :idAnalista)
        GROUP BY cc.id_controle_classificacao
    )
    SELECT
        CASE
            WHEN total_revisoes = 1 THEN 'Ideais'
            WHEN total_revisoes <= 2 THEN 'Aceitaveis'
            ELSE 'Ruim'
        END AS classificacao_revisao,
        COUNT(*) AS quantidade
    FROM revisoes_por_classificacao
    GROUP BY classificacao_revisao
""", nativeQuery = true)
List<Object[]> getQualityAnalysisByAnalyst(@Param("idAnalista") Long idAnalista);


    @Query(value = """
        WITH revisoes_por_classificacao AS (
            SELECT cc.id_controle_classificacao, COUNT(rcm.id_revisao_classificacao_manual) AS total_revisoes
            FROM controle_classificacao cc
            LEFT JOIN revisao_classificacao_manual rcm ON cc.id_controle_classificacao = rcm.id_controle_classificacao
            GROUP BY cc.id_controle_classificacao
        )
        SELECT
            CASE
                WHEN total_revisoes = 1 THEN 'Ideais'
                WHEN total_revisoes <= 2 THEN 'Aceitaveis'
                ELSE 'Ruim'
            END AS classificacao_revisao,
            COUNT(*) AS quantidade
        FROM revisoes_por_classificacao
        GROUP BY classificacao_revisao
    """, nativeQuery = true)
    List<Object[]> getQualityAnalysisForTeam();

    @Query(value = """
        SELECT 
            u.nome AS name,
            SUM(CASE WHEN t.estado = 'APPROVED' THEN t.area ELSE 0 END) AS approved_area,
            SUM(CASE WHEN t.estado = 'PENDING' THEN t.area ELSE 0 END) AS pending_area,
            SUM(CASE WHEN t.estado = 'REJECTED' THEN t.area ELSE 0 END) AS rejected_area
        FROM 
            controle_classificacao cc
        JOIN 
            usuarios u ON cc.id_analista = u.id_usuario
        JOIN 
            talhoes t ON cc.id_talhao = t.id_talhao
        WHERE 
            u.analista = TRUE
            AND t.estado != 'No Solution'
        GROUP BY 
            u.nome
        ORDER BY 
            u.nome
    """, nativeQuery = true)
    List<Object[]> getAnalystPerformanceData();

    @Query(value = """
        SELECT
          CASE 
            WHEN SUM(cc.time_spent_manual) = 0 THEN 0
            ELSE SUM(t.area) / SUM(cc.time_spent_manual)
          END AS area_por_tempo
        FROM controle_classificacao cc
        JOIN talhoes t ON cc.id_talhao = t.id_talhao
        WHERE t.estado = 'APPROVED'
          AND (:analystId IS NULL OR cc.id_analista = :analystId)
    """, nativeQuery = true)
    Double getAnalystProductivity(@Param("analystId") Long analystId);
    
    @Query(value = """
        WITH analistas_data AS (
          SELECT
            cc.id_analista,
            SUM(t.area) AS total_area_aprovada,
            SUM(cc.time_spent_manual) AS total_tempo_gasto,
            CASE
              WHEN SUM(cc.time_spent_manual) = 0 THEN NULL
              ELSE SUM(t.area) / SUM(cc.time_spent_manual)
            END AS area_por_tempo
          FROM controle_classificacao cc
          JOIN talhoes t ON cc.id_talhao = t.id_talhao
          WHERE t.estado = 'APPROVED'
          GROUP BY cc.id_analista
        )
        SELECT
          COALESCE(AVG(area_por_tempo), 0)
        FROM analistas_data
    """, nativeQuery = true)
    Double getAverageAnalystProductivity();
    
    @Query(value = """
        SELECT 
            t.nome AS talhao,
            t.estado AS situacao,
            TO_CHAR(cc.date_time_created, 'YYYY-MM-DD') AS data_entrada,
            COALESCE(TO_CHAR(cc.date_time_approved, 'YYYY-MM-DD'), '') AS data_aprovacao,
            CASE 
                WHEN cc.date_time_approved IS NOT NULL THEN 
                    CONCAT(EXTRACT(DAY FROM cc.date_time_approved - cc.date_time_created), ' dias')
                ELSE '' 
            END AS tempo_ciclo,
            a.nome AS analista,
            CASE 
                WHEN cc.time_spent_manual IS NOT NULL THEN 
                    CONCAT(ROUND(cc.time_spent_manual / 86400.0), ' dias')
                ELSE '—'
            END AS tempo_analise,
            COALESCE(cc.count_manual_interactions, 0) AS quantidade_analises,
            c.nome AS consultor,
            CASE 
                WHEN cc.time_spent_revision IS NOT NULL AND cc.time_spent_revision > 0 THEN 
                    CONCAT(ROUND(cc.time_spent_revision / 86400.0), ' dias')
                ELSE '—'
            END AS tempo_revisao
        FROM controle_classificacao cc
        JOIN talhoes t ON t.id_talhao = cc.id_talhao
        LEFT JOIN usuarios a ON a.id_usuario = cc.id_analista
        LEFT JOIN usuarios c ON c.id_usuario = cc.id_consultor
        WHERE (:talhao IS NULL OR t.nome ILIKE CONCAT('%', :talhao, '%'))
        AND (:situacao IS NULL OR t.estado ILIKE CONCAT('%', :situacao, '%'))
        AND (:analista IS NULL OR a.nome ILIKE CONCAT('%', :analista, '%'))
        AND (:consultor IS NULL OR c.nome ILIKE CONCAT('%', :consultor, '%'))
        """, nativeQuery = true)
    List<TableAnalystDTO> findCycleFields(
        @Param("talhao") String field,
        @Param("situacao") String status,
        @Param("analista") String analyst,
        @Param("consultor") String consultant
    );

    @Query(value = """
    WITH classificacoes_com_datas AS (
        SELECT 
            cc.id_controle_classificacao,
            DATE_TRUNC('month', cc.date_time_created) AS mes,
            cm.area AS area_inicial,
            rcm.area_metros_quadrados AS area_final
        FROM controle_classificacao cc
        JOIN classificacao_manual cm ON cm.id_controle_classificacao = cc.id_controle_classificacao
        LEFT JOIN LATERAL (
            SELECT 
                ST_Area(ST_Transform(coordenadas_destaque, 5880)) AS area_metros_quadrados
            FROM revisao_classificacao_manual rcm
            WHERE rcm.id_controle_classificacao = cc.id_controle_classificacao
            AND coordenadas_destaque IS NOT NULL
            ORDER BY rcm.id_revisao_classificacao_manual DESC
            LIMIT 1
        ) rcm ON true
        WHERE cc.date_time_created >= DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '3 months'
    )
    SELECT 
        TO_CHAR(mes, 'YYYY-MM') AS month,
        SUM(area_inicial) AS initial_area,
        SUM(COALESCE(area_final, 0)) AS final_area
    FROM classificacoes_com_datas
    GROUP BY mes
    ORDER BY mes
        """, nativeQuery = true)
    List<Object[]> findMonthlyAreaData();

    @Query(value = """
        SELECT 
            EXTRACT(MONTH FROM cc.date_time_created) AS mes,
            SUM(t.area) AS area_revisada
        FROM 
            controle_classificacao cc
        JOIN 
            talhoes t ON t.id_talhao = cc.id_talhao
        WHERE 
            t.estado = 'APPROVED'
            AND EXTRACT(YEAR FROM cc.date_time_created) = EXTRACT(YEAR FROM CURRENT_DATE)
            AND (:consultantId IS NULL OR cc.id_consultor = :consultantId)
        GROUP BY 
            EXTRACT(MONTH FROM cc.date_time_created)
        ORDER BY mes
        """, nativeQuery = true)
    List<Object[]> findMonthlyAreaByConsultant(@Param("consultantId") Long consultantId);

    @Query(value = """
        SELECT 
            EXTRACT(MONTH FROM cc.date_time_created) AS mes,
            SUM(t.area) AS area_revisada
        FROM 
            controle_classificacao cc
        JOIN 
            talhoes t ON t.id_talhao = cc.id_talhao
        WHERE 
            t.estado = 'APPROVED'
            AND EXTRACT(YEAR FROM cc.date_time_created) = EXTRACT(YEAR FROM CURRENT_DATE)
        GROUP BY 
            EXTRACT(MONTH FROM cc.date_time_created)
        ORDER BY mes
        """, nativeQuery = true)
    List<Object[]> findMonthlyAreaByAllConsultants();

    @Query(value = """
        SELECT COUNT(t.id_talhao)
        FROM talhoes AS t
        LEFT JOIN controle_classificacao AS cc ON t.id_talhao = cc.id_talhao
        LEFT JOIN revisao_classificacao_manual AS rcm ON cc.id_controle_classificacao = rcm.id_controle_classificacao
        WHERE rcm.id_revisao_classificacao_manual IS NULL
        """, nativeQuery = true)
    Long countUneditedFields();
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



