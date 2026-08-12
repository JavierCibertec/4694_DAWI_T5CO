package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioModel {
    private Long id;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String rol;
    private String token;
}