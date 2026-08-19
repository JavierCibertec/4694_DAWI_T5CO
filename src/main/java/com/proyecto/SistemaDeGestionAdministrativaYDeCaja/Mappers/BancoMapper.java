package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.BancoEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.BancoModel;
import org.springframework.stereotype.Component;

@Component
public class BancoMapper {

    public BancoModel toModel(BancoEntity entity) {
        if (entity == null) return null;
        return BancoModel.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .numeroCuenta(entity.getNumeroCuenta())
                .cci(entity.getCci())
                .moneda(entity.getMoneda())
                .build();
    }

    public BancoEntity toEntity(BancoModel model) {
        if (model == null) return null;
        return BancoEntity.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .numeroCuenta(model.getNumeroCuenta())
                .cci(model.getCci())
                .moneda(model.getMoneda())
                .build();
    }
}
