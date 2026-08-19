package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.BancoEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.BancoMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.BancoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IBancoRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IBancoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BancoService implements IBancoService {

    private final IBancoRepository bancoRepository;
    private final BancoMapper bancoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BancoModel> listarBancos() {
        return bancoRepository.findAll().stream()
                .map(bancoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BancoModel guardar(BancoModel bancoModel) {
        BancoEntity entity = bancoMapper.toEntity(bancoModel);
        BancoEntity guardado = bancoRepository.save(entity);
        return bancoMapper.toModel(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public BancoModel obtenerPorId(Long id) {
        return bancoRepository.findById(id)
                .map(bancoMapper::toModel)
                .orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        bancoRepository.deleteById(id);
    }
}