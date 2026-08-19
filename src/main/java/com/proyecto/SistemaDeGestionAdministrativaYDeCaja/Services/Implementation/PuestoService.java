package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.PuestoEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.PuestoMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.PuestoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IPuestoRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IPuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PuestoService implements IPuestoService {

    private final IPuestoRepository puestoRepository;
    private final PuestoMapper puestoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PuestoModel> listarPuestos() {
        return puestoRepository.findAll().stream()
                .map(puestoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PuestoModel guardar(PuestoModel puestoModel) {
        PuestoEntity entity = puestoMapper.toEntity(puestoModel);
        PuestoEntity guardado = puestoRepository.save(entity);
        return puestoMapper.toModel(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PuestoModel obtenerPorId(Long id) {
        return puestoRepository.findById(id)
                .map(puestoMapper::toModel)
                .orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        puestoRepository.deleteById(id);
    }
}
