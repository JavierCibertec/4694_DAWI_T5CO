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
import java.nio.charset.StandardCharsets;
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
    public EgresoModel registrar(EgresoModel model) {
        EgresoEntity entity = egresoMapper.toEntity(model);
        EgresoEntity guardado = egresoRepository.save(entity);
        return egresoMapper.toModel(guardado);
    }

    @Override
    public List<EgresoModel> listar() {
        return egresoRepository.findAll()
                .stream()
                .map(egresoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EgresoModel> cargarEgresosMasivos(MultipartFile archivo) {
        List<EgresoEntity> egresos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; // Ignorar líneas vacías

                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    EgresoEntity egreso = EgresoEntity.builder()
                            .proveedor(datos[0].trim())
                            .documento(datos[1].trim())
                            .monto(new BigDecimal(datos[2].trim()))
                            .motivo(datos[3].trim())
                            .fecha(LocalDate.now())
                            .estado("ACTIVO")
                            .build();
                    egresos.add(egreso);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo CSV: " + e.getMessage());
        }

        List<EgresoEntity> guardados = egresoRepository.saveAll(egresos);
        return guardados.stream()
                .map(egresoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<EgresoModel> listarPorMes(int mes, int anio) {
        return egresoRepository.findAll()
                .stream()
                .filter(e -> e.getFecha() != null && e.getFecha().getMonthValue() == mes && e.getFecha().getYear() == anio)
                .map(egresoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public EgresoModel anularEgreso(Long id) {
        EgresoEntity egreso = egresoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Egreso no encontrado"));
        egreso.setEstado("ANULADO");
        return egresoMapper.toModel(egresoRepository.save(egreso));
    }

    @Override
    @Transactional
    public void vaciarTodos() {
        egresoRepository.deleteAll();
    }
}