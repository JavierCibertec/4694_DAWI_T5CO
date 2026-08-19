package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BancoModel {

    private Long id;
    private String nombre;
    private String numeroCuenta;
    private String cci;
    private String moneda;
}