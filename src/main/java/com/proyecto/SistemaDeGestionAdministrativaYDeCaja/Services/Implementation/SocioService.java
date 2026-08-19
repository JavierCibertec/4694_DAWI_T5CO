package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.SocioEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.SocioMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.SocioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.ICuentaPorCobrarRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.ISocioRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.ISocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocioService implements ISocioService {

    private final ISocioRepository socioRepository;
    private final ICuentaPorCobrarRepository cuentaPorCobrarRepository;
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

    @Override
    @Transactional
    public void eliminar(Long id) {
        // 1. Elimina primero las cuentas por cobrar vinculadas al socio
        cuentaPorCobrarRepository.deleteByIdSocio(id);

        // 2. Procede a borrar la entidad del socio
        socioRepository.deleteById(id);
    }
}