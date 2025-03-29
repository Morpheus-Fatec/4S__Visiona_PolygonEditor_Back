package com.morpheus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>{
    Field getFieldById(Long fieldId);
    @Query(value = """
        SELECT 
            f.id_talhao AS id, 
            f.nome AS nome, 
            fa.nome AS fazenda, 
            c.nome AS cultura,
            f.coordenadas AS coordinates,
            f.estado AS status
        FROM Talhoes f
        JOIN Fazendas fa ON f.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON f.id_cultura = c.id_cultura
    """, nativeQuery = true)
    List<Object[]> getAllFeatureSimpleDTO();

    @Query(value = """
        SELECT
        -- fazenda
        f.nome as fazenda_nome,
        f.cidade as fazenda_cidade,
        f.estado as fazenda_estado,
        -- talhao
        t.id_talhao as id_talhao,
        t.nome as talhao_nome,
        c.nome as cultura_nome,
        t.area,
        t.safra,
        t.estado as status,
        s.nome as solo_nome,
        t.produtividade,
        -- classificacao
        cl.area,
        cl.coordenadas_atuais,
        cla.nome as classe_nome,
        -- geometry
        t.coordenadas
        FROM talhoes t
        LEFT JOIN fazendas f
        ON t.id_fazenda = f.id_fazenda
        LEFT JOIN leituras l
        ON t.id_leitura = l.id_leitura
        LEFT JOIN imagens_apoio ia
        ON ia.id_leitura = l.id_leitura
        LEFT JOIN solos s
        ON s.id_solo = t.id_solo
        LEFT JOIN culturas c
        ON t.id_cultura = c.id_cultura
        LEFT JOIN classificacoes cl
        ON cl.id_talhao = t.id_talhao
        LEFT JOIN classes cla
        ON cla.id_classe = cl.id_classe;
    """, nativeQuery = true)
    List<Object[]> getAllCompleteField();
}
