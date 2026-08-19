package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EgresoModel {

    private Long id;
    private String proveedor;
    private String documento;
    private String fecha;
    private BigDecimal monto;
    private String motivo;
    private String estado;
}