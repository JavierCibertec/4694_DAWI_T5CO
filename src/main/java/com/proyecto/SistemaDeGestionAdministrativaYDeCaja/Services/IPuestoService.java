package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.PuestoModel;
import java.util.List;

public interface IPuestoService {
    List<PuestoModel> listarPuestos();
    PuestoModel guardar(PuestoModel puestoModel);
    PuestoModel obtenerPorId(Long id);
    void eliminar(Long id);
}