package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.EgresoEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.EgresoModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EgresoMapper {

    public EgresoModel toModel(EgresoEntity entity) {
        if (entity == null) return null;
        return EgresoModel.builder()
                .id(entity.getId())
                .proveedor(entity.getProveedor())
                .documento(entity.getDocumento())
                .fecha(entity.getFecha() != null ? entity.getFecha().toString() : null)
                .monto(entity.getMonto())
                .motivo(entity.getMotivo())
                .estado(entity.getEstado())
                .build();
    }

    public EgresoEntity toEntity(EgresoModel model) {
        if (model == null) return null;
        return EgresoEntity.builder()
                .id(model.getId())
                .proveedor(model.getProveedor())
                .documento(model.getDocumento())
                .fecha(model.getFecha() != null && !model.getFecha().isBlank() ? LocalDate.parse(model.getFecha()) : null)
                .monto(model.getMonto())
                .motivo(model.getMotivo())
                .estado(model.getEstado())
                .build();
    }
}