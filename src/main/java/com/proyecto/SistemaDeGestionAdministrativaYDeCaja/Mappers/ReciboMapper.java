package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.ReciboEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import org.springframework.stereotype.Component;

@Component
public class ReciboMapper {

    public ReciboModel toModel(ReciboEntity entity) {
        if (entity == null) return null;
        return ReciboModel.builder()
                .id(entity.getId())
                .correlativo(entity.getCorrelativo())
                .tipo(entity.getTipo())
                .montoTotal(entity.getMontoTotal())
                .fecha(entity.getFecha())
                .usuarioCreacion(entity.getUsuarioCreacion())
                .build();
    }

    public ReciboEntity toEntity(ReciboModel model) {
        if (model == null) return null;
        return ReciboEntity.builder()
                .id(model.getId())
                .correlativo(model.getCorrelativo())
                .tipo(model.getTipo())
                .montoTotal(model.getMontoTotal())
                .fecha(model.getFecha())
                .usuarioCreacion(model.getUsuarioCreacion())
                .build();
    }
}
