package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaPorCobrarModel {
    private Long id;
    private Long idPuesto;
    private Long idSocio;
    private Long idServicio;
    private String periodo;
    private BigDecimal monto;
    private String estado;
    private Double lecturaInicial;
    private Double lecturaFinal;
    private Boolean esPorConsumo;
}