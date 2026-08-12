package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.UsuarioModel;
import java.util.List;

public interface IUsuarioService {
    UsuarioModel login(String username, String password);
    List<UsuarioModel> listarTodos();
    UsuarioModel obtenerPorId(Long id);
    UsuarioModel guardar(UsuarioModel usuarioModel);
    void eliminar(Long id);
}
