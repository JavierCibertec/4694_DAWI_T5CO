package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ServicioModel;
import java.util.List;

public interface IServicioService {
    List<ServicioModel> listarServicios();
    ServicioModel obtenerPorId(Long id);
    ServicioModel guardar(ServicioModel model);
    void eliminar(Long id);
}