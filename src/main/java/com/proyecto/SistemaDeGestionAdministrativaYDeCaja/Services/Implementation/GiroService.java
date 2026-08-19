package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.GiroEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.GiroModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IGiroRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IGiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiroService implements IGiroService {

    private final IGiroRepository giroRepository;

    @Override
    public List<GiroModel> listarGiros() {
        return giroRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public GiroModel obtenerPorId(Long id) {
        return giroRepository.findById(id)
                .map(this::toModel)
                .orElse(null);
    }

    @Override
    public GiroModel guardar(GiroModel model) {
        GiroEntity entity = toEntity(model);
        GiroEntity guardado = giroRepository.save(entity);
        return toModel(guardado);
    }

    @Override
    public void eliminar(Long id) {
        giroRepository.deleteById(id);
    }

    private GiroModel toModel(GiroEntity entity) {
        return GiroModel.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .build();
    }

    private GiroEntity toEntity(GiroModel model) {
        return GiroEntity.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .descripcion(model.getDescripcion())
                .build();
    }
}