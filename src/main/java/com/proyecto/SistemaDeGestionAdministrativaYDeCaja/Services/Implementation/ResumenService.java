package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.CuentaPorCobrarMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.ReciboMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.CuentaPorCobrarModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ResumenMovimientosModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.ICuentaPorCobrarRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IReciboRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumenService implements IResumenService {


    private final ICuentaPorCobrarRepository cuentaRepository;
    private final IReciboRepository reciboRepository;
    private final CuentaPorCobrarMapper cuentaMapper;
    private final ReciboMapper reciboMapper;

    @Override
    public ResumenMovimientosModel obtenerResumenSocio(Long idSocio) {
        List<CuentaPorCobrarModel> cuentas = cuentaRepository.findByIdSocio(idSocio)
                .stream().map(cuentaMapper::toModel).collect(Collectors.toList());

        List<ReciboModel> recibos = reciboRepository.findAll()
                .stream().map(reciboMapper::toModel).collect(Collectors.toList());

        return ResumenMovimientosModel.builder()
                .idEntidad(idSocio)
                .tipoEntidad("SOCIO")
                .cuentasPendientes(cuentas)
                .historialPagos(recibos)
                .build();
    }

    @Override
    public ResumenMovimientosModel obtenerResumenPuesto(Long idPuesto) {
        List<CuentaPorCobrarModel> cuentas = cuentaRepository.findByIdPuesto(idPuesto)
                .stream().map(cuentaMapper::toModel).collect(Collectors.toList());

        return ResumenMovimientosModel.builder()
                .idEntidad(idPuesto)
                .tipoEntidad("PUESTO")
                .cuentasPendientes(cuentas)
                .historialPagos(List.of())
                .build();
    }
}