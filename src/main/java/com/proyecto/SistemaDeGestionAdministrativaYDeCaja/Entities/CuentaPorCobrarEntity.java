package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas_por_cobrar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaPorCobrarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idPuesto;
    private Long idSocio;
    private Long idServicio;
    private String periodo;
    private BigDecimal monto;
    private String estado;
    private Double lecturaInicial;
    private Double lecturaFinal;
    private Boolean esPorConsumo;
}
