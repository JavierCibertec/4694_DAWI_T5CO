package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String recurrencia;          // MENSUAL, ANUAL, UNICO
    private BigDecimal costo;
    private String moneda;               // PEN, USD
    private String destinoCargo;         // PUESTO, SOCIO
    private Boolean esPorConsumo;        // true = depende de lecturas
}