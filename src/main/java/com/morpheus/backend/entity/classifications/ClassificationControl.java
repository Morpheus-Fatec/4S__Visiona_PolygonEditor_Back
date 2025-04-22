package com.morpheus.backend.entity.classifications;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.User;
import com.morpheus.backend.utilities.DurationToLongConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
    private LocalDateTime dateTimeCreated;

    @Column(name = "time_spent_manual")
    @Convert(converter = DurationToLongConverter.class)
    private Duration timeSpentManual;

    @Column(name = "count_manual_interactions")
    private int countManualInteractions;

    @ManyToOne
    @JoinColumn(name = "id_analista", referencedColumnName = "id_usuario")
    private User analystResponsable;

    @Column(name = "time_Spent_Revision")
    @Convert(converter = DurationToLongConverter.class)
    private Duration timeSpentRevision;

    @ManyToOne
    @JoinColumn(name = "id_consultor", referencedColumnName = "id_usuario")
    private User consultantResponsable;

    @Column(columnDefinition = "timestamp")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateTimeApproved;

}
