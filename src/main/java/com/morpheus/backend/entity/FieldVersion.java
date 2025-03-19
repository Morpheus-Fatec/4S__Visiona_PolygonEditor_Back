package com.morpheus.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class FieldVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_field_version")
    private Long idFieldVersion;

    @ManyToOne
    @JoinColumn(name = "id_field")
    private Field field;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "polygon")
    private String polygon;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "timestamp_modified")
    private LocalDateTime timestampModified;

    @Column(name = "observations")
    private String observations;


}
