package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReciboModel {
    private Long id;
    private Long correlativo;
    private String tipo;
    private BigDecimal montoTotal;
    private LocalDateTime fecha;
    private String usuarioCreacion;
}
