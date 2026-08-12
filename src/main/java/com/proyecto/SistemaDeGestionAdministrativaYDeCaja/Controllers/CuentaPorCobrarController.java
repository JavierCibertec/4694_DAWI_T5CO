package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.CuentaPorCobrarModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.ICuentaPorCobrarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas-por-cobrar")
@RequiredArgsConstructor
public class CuentaPorCobrarController {

    private final ICuentaPorCobrarService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaPorCobrarModel> crear(@RequestBody CuentaPorCobrarModel model) {
        return ResponseEntity.ok(cuentaService.crearCuenta(model));
    }

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<CuentaPorCobrarModel>> listarPorSocio(@PathVariable Long idSocio) {
        return ResponseEntity.ok(cuentaService.listarPorSocio(idSocio));
    }

    @GetMapping("/puesto/{idPuesto}")
    public ResponseEntity<List<CuentaPorCobrarModel>> listarPorPuesto(@PathVariable Long idPuesto) {
        return ResponseEntity.ok(cuentaService.listarPorPuesto(idPuesto));
    }

    @PostMapping("/generar-masivo-socios")
    public ResponseEntity<List<CuentaPorCobrarModel>> generarMasivoSocios(
            @RequestParam Long idServicio,
            @RequestParam String periodo,
            @RequestParam List<String> etapas,
            @RequestParam(defaultValue = "true") boolean deduplicar) {
        return ResponseEntity.ok(cuentaService.generarCuentasMasivasSocios(idServicio, periodo, etapas, deduplicar));
    }
}