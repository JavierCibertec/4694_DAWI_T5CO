package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.SocioEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.SocioMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.SocioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.ISocioRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.ISocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocioService implements ISocioService {

    private final ISocioRepository socioRepository;
    private final SocioMapper socioMapper;

    @Override
    public List<SocioModel> listarSocios() {
        return socioRepository.findAll()
                .stream()
                .map(socioMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public SocioModel guardar(SocioModel socioModel) {
        SocioEntity entity = socioMapper.toEntity(socioModel);
        SocioEntity guardado = socioRepository.save(entity);
        return socioMapper.toModel(guardado);
    }

    @Override
    public SocioModel obtenerPorId(Long id) {
        return socioRepository.findById(id)
                .map(socioMapper::toModel)
                .orElse(null);
    }

    @Override
    public SocioModel obtenerPorCodigo(String codigo) {
        return socioRepository.findByCodigo(codigo)
                .map(socioMapper::toModel)
                .orElse(null);
    }
}
