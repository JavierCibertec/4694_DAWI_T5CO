package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiroModel {
    private Long id;
    private String nombre;
    private String descripcion;
}