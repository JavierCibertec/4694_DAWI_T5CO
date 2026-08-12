package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.EgresoModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEgresoService {
    EgresoModel registrar(EgresoModel model);
    List<EgresoModel> listar();
    List<EgresoModel> cargarEgresosMasivos(MultipartFile archivo);
    List<EgresoModel> listarPorMes(int mes, int anio);
    EgresoModel anularEgreso(Long id);
}