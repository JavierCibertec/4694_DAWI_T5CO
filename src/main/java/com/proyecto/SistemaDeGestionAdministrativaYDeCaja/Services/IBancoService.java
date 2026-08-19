package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.BancoModel;
import java.util.List;

public interface IBancoService {
    List<BancoModel> listarBancos();
    BancoModel guardar(BancoModel bancoModel);
    BancoModel obtenerPorId(Long id);
    void eliminar(Long id);
}