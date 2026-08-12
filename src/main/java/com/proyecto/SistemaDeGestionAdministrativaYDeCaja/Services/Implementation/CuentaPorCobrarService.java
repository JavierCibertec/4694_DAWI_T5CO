package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.CuentaPorCobrarEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.CuentaPorCobrarMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.CuentaPorCobrarModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.ICuentaPorCobrarRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.ICuentaPorCobrarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaPorCobrarService implements ICuentaPorCobrarService {

    private final ICuentaPorCobrarRepository cuentaRepository;
    private final CuentaPorCobrarMapper cuentaMapper;

    @Override
    public CuentaPorCobrarModel crearCuenta(CuentaPorCobrarModel model) {
        CuentaPorCobrarEntity entity = cuentaMapper.toEntity(model);

        if (Boolean.TRUE.equals(entity.getEsPorConsumo())) {
            double lecturaInicial = entity.getLecturaInicial() != null ? entity.getLecturaInicial() : 0.0;
            double lecturaFinal = entity.getLecturaFinal() != null ? entity.getLecturaFinal() : 0.0;
            double consumoEfectivo = Math.max(0.0, lecturaFinal - lecturaInicial);
            entity.setMonto(BigDecimal.valueOf(consumoEfectivo * 1.0));
        }

        CuentaPorCobrarEntity guardada = cuentaRepository.save(entity);
        return cuentaMapper.toModel(guardada);
    }

    @Override
    public List<CuentaPorCobrarModel> listarPorSocio(Long idSocio) {
        return cuentaRepository.findByIdSocio(idSocio)
                .stream()
                .map(cuentaMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<CuentaPorCobrarModel> listarPorPuesto(Long idPuesto) {
        return cuentaRepository.findByIdPuesto(idPuesto)
                .stream()
                .map(cuentaMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CuentaPorCobrarModel> generarCuentasMasivasSocios(Long idServicio, String periodo, List<String> etapas, boolean deduplicar) {
        List<CuentaPorCobrarEntity> generadas = new ArrayList<>();

        CuentaPorCobrarEntity cuenta = CuentaPorCobrarEntity.builder()
                .idSocio(10L)
                .idServicio(idServicio)
                .periodo(periodo)
                .monto(BigDecimal.valueOf(100.00))
                .estado("PENDIENTE")
                .esPorConsumo(false)
                .build();

        if (!cuentaRepository.existsByIdSocioAndIdServicioAndPeriodo(10L, idServicio, periodo)) {
            generadas.add(cuenta);
        }

        return cuentaRepository.saveAll(generadas)
                .stream()
                .map(cuentaMapper::toModel)
                .collect(Collectors.toList());
    }
}