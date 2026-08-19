package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocioModel {

    private Long id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String accion;
    private String etapa;
    private String fechaNacimiento;
}