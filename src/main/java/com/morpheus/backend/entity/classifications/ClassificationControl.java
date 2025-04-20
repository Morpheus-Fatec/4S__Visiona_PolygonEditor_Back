package com.morpheus.backend.entity.classifications;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.morpheus.backend.entity.Field;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "controle_classificacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_controle_classificacao")
    private Long idControle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_talhao", referencedColumnName = "id_talhao", nullable = false)
    private Field field;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraCadastroAutomatica;

    @Column(columnDefinition = "timestamp")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime tempoGastoManual;

    @Column(columnDefinition = "timestamp")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime tempoGastoRevisao;

    @Column(columnDefinition = "timestamp")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraAprovacao;

    @Column(name = "esta_aprovado")
    private Boolean isApproved;

}
