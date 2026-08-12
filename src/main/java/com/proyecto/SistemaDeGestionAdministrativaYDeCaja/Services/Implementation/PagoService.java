package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.CuentaPorCobrarEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.ReciboEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.ReciboMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.ICuentaPorCobrarRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IReciboRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService implements IPagoService {

    private final IReciboRepository reciboRepository;
    private final ICuentaPorCobrarRepository cuentaPorCobrarRepository;
    private final ReciboMapper reciboMapper;

    @Override
    @Transactional
    public ReciboModel procesarPago(List<Long> idsCuentas, String usuario) {
        if (idsCuentas == null || idsCuentas.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos una cuenta por cobrar.");
        }

        List<CuentaPorCobrarEntity> cuentas = cuentaPorCobrarRepository.findAllById(idsCuentas);
        BigDecimal montoTotal = BigDecimal.ZERO;

        for (CuentaPorCobrarEntity cuenta : cuentas) {
            cuenta.setEstado("ABONADO");
            if (cuenta.getMonto() != null) {
                montoTotal = montoTotal.add(cuenta.getMonto());
            }
        }
        cuentaPorCobrarRepository.saveAll(cuentas);

        Long ultimo = reciboRepository.findUltimoCorrelativo().orElse(0L);
        Long nuevoCorrelativo = ultimo + 1;

        ReciboEntity recibo = ReciboEntity.builder()
                .correlativo(nuevoCorrelativo)
                .tipo("INGRESO")
                .montoTotal(montoTotal)
                .fecha(LocalDateTime.now())
                .usuarioCreacion(usuario)
                .build();

        ReciboEntity guardado = reciboRepository.save(recibo);
        return reciboMapper.toModel(guardado);
    }

    @Override
    @Transactional
    public ReciboModel canjearPorOperacionBancaria(Long idCuenta, Long idBanco, LocalDate fechaDeposito, String usuario) {
        CuentaPorCobrarEntity cuenta = cuentaPorCobrarRepository.findById(idCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        cuenta.setEstado("ABONADO");
        cuentaPorCobrarRepository.save(cuenta);

        Long ultimo = reciboRepository.findUltimoCorrelativo().orElse(0L);

        ReciboEntity reciboBancario = ReciboEntity.builder()
                .correlativo(ultimo + 1)
                .tipo("BANCO")
                .montoTotal(cuenta.getMonto() != null ? cuenta.getMonto() : BigDecimal.ZERO)
                .fecha(fechaDeposito != null ? fechaDeposito.atStartOfDay() : LocalDateTime.now())
                .usuarioCreacion(usuario)
                .build();

        ReciboEntity guardado = reciboRepository.save(reciboBancario);
        return reciboMapper.toModel(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReciboModel> listarRecibosPorFecha(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59);

        return reciboRepository.findAll()
                .stream()
                .filter(r -> r.getFecha() != null && !r.getFecha().isBefore(inicio) && !r.getFecha().isAfter(fin))
                .map(reciboMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReciboModel obtenerReciboPorCorrelativo(Long correlativo) {
        return reciboRepository.findAll()
                .stream()
                .filter(r -> correlativo.equals(r.getCorrelativo()))
                .findFirst()
                .map(reciboMapper::toModel)
                .orElse(null);
    }
}
