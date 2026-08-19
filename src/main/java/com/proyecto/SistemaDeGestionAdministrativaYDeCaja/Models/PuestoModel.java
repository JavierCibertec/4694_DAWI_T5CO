package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuestoModel {

    private Long id;
    private String numeroPuesto;
    private String inquilino;
    private String vigencia;
    private Long idGiro;
    private Long idSocio;
}
