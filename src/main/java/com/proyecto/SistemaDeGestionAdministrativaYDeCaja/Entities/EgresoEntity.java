package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "egresos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EgresoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proveedor;
    private String documento;
    private LocalDate fecha;
    private BigDecimal monto;
    private String motivo;
    private String estado;
}