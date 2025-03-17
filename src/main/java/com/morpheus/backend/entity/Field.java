package com.morpheus.backend.entity;


import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_field;

    @ManyToOne
    @JoinColumn(name = "id_farm")
    private Farm farm;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @ManyToAny
    @JoinColumn(name = "id_user")
    private User id_user;

    @ManyToMany(mappedBy = "fields")
    private Productivity id_prod;

    @Column(name = "soil")
    private String soil;

    @Column(name = "culture")
    private String culture;

    @Column(name = "image")
    private String image;

}