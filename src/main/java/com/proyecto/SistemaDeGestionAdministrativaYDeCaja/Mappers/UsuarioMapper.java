package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.UsuarioEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioModel toModel(UsuarioEntity entity) {
        if (entity == null) return null;
        return UsuarioModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .rol(entity.getRol())
                .build();
    }

    public UsuarioEntity toEntity(UsuarioModel model) {
        if (model == null) return null;
        return UsuarioEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .password(model.getPassword())
                .nombre(model.getNombre())
                .apellido(model.getApellido())
                .rol(model.getRol())
                .build();
    }
}
