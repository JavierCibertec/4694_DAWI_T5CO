package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.CuentaPorCobrarEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.CuentaPorCobrarModel;
import org.springframework.stereotype.Component;

@Component
public class CuentaPorCobrarMapper {

    public CuentaPorCobrarModel toModel(CuentaPorCobrarEntity entity) {
        if (entity == null) return null;
        return CuentaPorCobrarModel.builder()
                .id(entity.getId())
                .idPuesto(entity.getIdPuesto())
                .idSocio(entity.getIdSocio())
                .idServicio(entity.getIdServicio())
                .periodo(entity.getPeriodo())
                .monto(entity.getMonto())
                .estado(entity.getEstado())
                .lecturaInicial(entity.getLecturaInicial())
                .lecturaFinal(entity.getLecturaFinal())
                .esPorConsumo(entity.getEsPorConsumo())
                .build();
    }

    public CuentaPorCobrarEntity toEntity(CuentaPorCobrarModel model) {
        if (model == null) return null;
        return CuentaPorCobrarEntity.builder()
                .id(model.getId())
                .idPuesto(model.getIdPuesto())
                .idSocio(model.getIdSocio())
                .idServicio(model.getIdServicio())
                .periodo(model.getPeriodo())
                .monto(model.getMonto())
                .estado(model.getEstado())
                .lecturaInicial(model.getLecturaInicial())
                .lecturaFinal(model.getLecturaFinal())
                .esPorConsumo(model.getEsPorConsumo())
                .build();
    }
}
