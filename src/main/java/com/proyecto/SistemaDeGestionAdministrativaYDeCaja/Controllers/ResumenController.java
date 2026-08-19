package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ResumenMovimientosModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumen")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ResumenController {

    private final IResumenService resumenService;

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<ResumenMovimientosModel> obtenerResumenSocio(@PathVariable Long idSocio) {
        return ResponseEntity.ok(resumenService.obtenerResumenSocio(idSocio));
    }

    @GetMapping("/puesto/{idPuesto}")
    public ResponseEntity<ResumenMovimientosModel> obtenerResumenPuesto(@PathVariable Long idPuesto) {
        return ResponseEntity.ok(resumenService.obtenerResumenPuesto(idPuesto));
    }
}