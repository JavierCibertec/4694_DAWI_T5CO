package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Regla 3 (>3 atributos)
public class ResumenMovimientosModel {
    private Long idEntidad;
    private String tipoEntidad; // "SOCIO" o "PUESTO"
    private List<CuentaPorCobrarModel> cuentasPendientes;
    private List<ReciboModel> historialPagos;
}
