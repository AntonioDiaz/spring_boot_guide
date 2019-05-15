package com.adiaz.ecomdashboard.entities;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public class KeyEntity {

    @Id
    @Column(name="ID", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
