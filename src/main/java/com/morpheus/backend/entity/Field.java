package com.morpheus.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "talhao")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talhao")
    private Long id;

    @ManyToMany
    @JoinColumn(name = "id_leitura")
    private Scan scan;

    @ManyToOne
    @JoinColumn(name = "id_fazenda")
    private Farm farm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User idUser;

    @Column(name = "solo")
    private String soil;

    @Column(name = "cultura")
    private String culture;

    @Column(name = "area")
    private Float area;

    @Column(name = "imagem")
    private String image;

}