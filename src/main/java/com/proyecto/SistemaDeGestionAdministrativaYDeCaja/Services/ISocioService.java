package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.SocioModel;
import java.util.List;

public interface ISocioService {
    List<SocioModel> listarSocios();
    SocioModel guardar(SocioModel socioModel);
    SocioModel obtenerPorId(Long id);
    SocioModel obtenerPorCodigo(String codigo);
    void eliminar(Long id);
}