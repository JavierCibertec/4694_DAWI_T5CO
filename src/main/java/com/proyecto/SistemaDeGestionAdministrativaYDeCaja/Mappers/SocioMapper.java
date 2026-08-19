package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.SocioEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.SocioModel;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class SocioMapper {

    public SocioModel toModel(SocioEntity entity) {
        if (entity == null) return null;
        return SocioModel.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .accion(entity.getAccion())
                .etapa(entity.getEtapa())
                .fechaNacimiento(entity.getFechaNacimiento() != null ? entity.getFechaNacimiento().toString() : null)
                .build();
    }

    public SocioEntity toEntity(SocioModel model) {
        if (model == null) return null;
        return SocioEntity.builder()
                .id(model.getId())
                .codigo(model.getCodigo())
                .nombres(model.getNombres())
                .apellidos(model.getApellidos())
                .accion(model.getAccion())
                .etapa(model.getEtapa())
                .fechaNacimiento(model.getFechaNacimiento() != null && !model.getFechaNacimiento().isEmpty()
                        ? LocalDate.parse(model.getFechaNacimiento()) : null)
                .build();
    }
}