package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.EgresoEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.EgresoMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.EgresoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IEgresoRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IEgresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EgresoService implements IEgresoService {

    private final IEgresoRepository egresoRepository;
    private final EgresoMapper egresoMapper;

    @Override
    @Transactional
    public EgresoModel registrar(EgresoModel model) {
        EgresoEntity entity = egresoMapper.toEntity(model);
        EgresoEntity guardado = egresoRepository.save(entity);
        return egresoMapper.toModel(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EgresoModel> listar() {
        return egresoRepository.findAll().stream()
                .map(egresoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EgresoModel> cargarEgresosMasivos(MultipartFile archivo) {
        List<EgresoEntity> egresos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    egresos.add(EgresoEntity.builder()
                            .proveedor(datos[0].trim())
                            .documento(datos[1].trim())
                            .monto(new BigDecimal(datos[2].trim()))
                            .motivo(datos[3].trim())
                            .fecha(LocalDate.now())
                            .estado("ACTIVO")
                            .build());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar archivo CSV de egresos: " + e.getMessage());
        }
        return egresoRepository.saveAll(egresos).stream()
                .map(egresoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EgresoModel> listarPorMes(int mes, int anio) {
        return egresoRepository.findAll().stream()
                .filter(e -> e.getFecha() != null && e.getFecha().getMonthValue() == mes && e.getFecha().getYear() == anio)
                .map(egresoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EgresoModel anularEgreso(Long id) {
        EgresoEntity egreso = egresoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Egreso no encontrado con ID: " + id));
        egreso.setEstado("ANULADO");
        return egresoMapper.toModel(egresoRepository.save(egreso));
    }

    @Override
    @Transactional
    public void vaciarTodos() {
        egresoRepository.deleteAll();
    }
}