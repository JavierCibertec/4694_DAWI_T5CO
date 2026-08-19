package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "puestos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuestoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_puesto", nullable = false)
    private String numeroPuesto;

    private String inquilino;
    private String vigencia;

    @Column(name = "id_giro", nullable = false)
    private Long idGiro;

    @Column(name = "id_socio")
    private Long idSocio;
}