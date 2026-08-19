package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.ServicioEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ServicioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IServicioRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioService implements IServicioService {

    private final IServicioRepository servicioRepository;

    @Override
    public List<ServicioModel> listarServicios() {
        return servicioRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ServicioModel obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .map(this::toModel)
                .orElse(null);
    }

    @Override
    public ServicioModel guardar(ServicioModel model) {
        ServicioEntity entity = toEntity(model);
        ServicioEntity guardado = servicioRepository.save(entity);
        return toModel(guardado);
    }

    @Override
    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }

    private ServicioModel toModel(ServicioEntity entity) {
        return ServicioModel.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .recurrencia(entity.getRecurrencia())
                .costo(entity.getCosto())
                .moneda(entity.getMoneda())
                .destinoCargo(entity.getDestinoCargo())
                .esPorConsumo(entity.getEsPorConsumo())
                .build();
    }

    private ServicioEntity toEntity(ServicioModel model) {
        return ServicioEntity.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .recurrencia(model.getRecurrencia())
                .costo(model.getCosto())
                .moneda(model.getMoneda())
                .destinoCargo(model.getDestinoCargo())
                .esPorConsumo(model.getEsPorConsumo())
                .build();
    }
}