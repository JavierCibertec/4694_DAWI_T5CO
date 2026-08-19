package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recibos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReciboEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long correlativo;

    private String tipo;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    private LocalDateTime fecha;

    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
}