package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import java.time.LocalDate;
import java.util.List;

public interface IPagoService {
    ReciboModel procesarPago(List<Long> idsCuentas, String usuario);
    ReciboModel canjearPorOperacionBancaria(Long idCuenta, Long idBanco, LocalDate fechaDeposito, String usuario);
    List<ReciboModel> listarRecibosPorFecha(LocalDate fecha);
    ReciboModel obtenerReciboPorCorrelativo(Long correlativo);
}
