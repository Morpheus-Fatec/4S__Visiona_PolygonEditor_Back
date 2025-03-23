package com.morpheus.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "culturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Culture {
    @Id
    @Column(name = "id_cultura")
    private Long id;

    @Column(name = "nome")
    private String name;
}
