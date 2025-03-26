package com.morpheus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ImagensApoio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_img")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_leitura", nullable = false)
    private Scan scan;

    @Column(name = "endereco", nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "nome", nullable = false, length = 255)
    private String name;
}