package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.PuestoEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.PuestoModel;
import org.springframework.stereotype.Component;

@Component
public class PuestoMapper {

    public PuestoModel toModel(PuestoEntity entity) {
        if (entity == null) return null;
        return PuestoModel.builder()
                .id(entity.getId())
                .numeroPuesto(entity.getNumeroPuesto())
                .inquilino(entity.getInquilino())
                .vigencia(entity.getVigencia())
                .idGiro(entity.getIdGiro())
                .idSocio(entity.getIdSocio())
                .build();
    }

    public PuestoEntity toEntity(PuestoModel model) {
        if (model == null) return null;
        return PuestoEntity.builder()
                .id(model.getId())
                .numeroPuesto(model.getNumeroPuesto())
                .inquilino(model.getInquilino())
                .vigencia(model.getVigencia())
                .idGiro(model.getIdGiro())
                .idSocio(model.getIdSocio())
                .build();
    }
}