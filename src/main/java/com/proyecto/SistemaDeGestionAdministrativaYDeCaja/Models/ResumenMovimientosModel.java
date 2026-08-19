package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumenMovimientosModel {

    private Long idEntidad;
    private String tipoEntidad;
    private List<CuentaPorCobrarModel> cuentasPendientes;
    private List<ReciboModel> historialPagos;
}
