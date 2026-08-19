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

    @Column(name = "id_puesto")
    private Long idPuesto;

    @Column(name = "id_socio")
    private Long idSocio;

    @Column(name = "id_servicio")
    private Long idServicio;

    private String periodo;
    private BigDecimal monto;
    private String estado;

    @Column(name = "lectura_inicial")
    private Double lecturaInicial;

    @Column(name = "lectura_final")
    private Double lecturaFinal;

    @Column(name = "es_por_consumo")
    private Boolean esPorConsumo;
}