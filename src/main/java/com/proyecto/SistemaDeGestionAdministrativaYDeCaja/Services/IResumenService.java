package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ResumenMovimientosModel;

public interface IResumenService {
    ResumenMovimientosModel obtenerResumenSocio(Long idSocio);
    ResumenMovimientosModel obtenerResumenPuesto(Long idPuesto);
}
