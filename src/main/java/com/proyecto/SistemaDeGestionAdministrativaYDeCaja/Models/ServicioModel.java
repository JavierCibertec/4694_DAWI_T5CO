package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioModel {
    private Long id;
    private String nombre;
    private String recurrencia;
    private BigDecimal costo;
    private String moneda;
    private String destinoCargo;
    private Boolean esPorConsumo;
}