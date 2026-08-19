package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "giros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;
}