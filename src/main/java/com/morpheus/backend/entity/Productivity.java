package com.morpheus.backend.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "productivity")
public class Productivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productivityId;

    @Column(name = "productivity_value")
    private Float productivityValue;

    @Column(name = "productivity_year")
    private Integer productivityYear;

    @ManyToMany
    private Set<Field> fields;

}