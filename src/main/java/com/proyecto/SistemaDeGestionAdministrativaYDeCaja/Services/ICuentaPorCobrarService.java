package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.CuentaPorCobrarModel;
import java.util.List;

public interface ICuentaPorCobrarService {
    CuentaPorCobrarModel crearCuenta(CuentaPorCobrarModel model);
    List<CuentaPorCobrarModel> listarPorSocio(Long idSocio);
    List<CuentaPorCobrarModel> listarPorPuesto(Long idPuesto);
    List<CuentaPorCobrarModel> generarCuentasMasivasSocios(Long idServicio, String periodo, List<String> etapas, boolean deduplicar);
}