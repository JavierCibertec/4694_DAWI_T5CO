package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.GiroModel;
import java.util.List;

public interface IGiroService {
    List<GiroModel> listarGiros();
    GiroModel obtenerPorId(Long id);
    GiroModel guardar(GiroModel model);
    void eliminar(Long id);
}