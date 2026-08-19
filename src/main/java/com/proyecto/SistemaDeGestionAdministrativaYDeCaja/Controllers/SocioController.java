package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.SocioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.ISocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SocioController {

    private final ISocioService socioService;

    @GetMapping
    public ResponseEntity<List<SocioModel>> listarTodos() {
        return ResponseEntity.ok(socioService.listarSocios());
    }

    @PostMapping
    public ResponseEntity<SocioModel> guardar(@RequestBody SocioModel socioModel) {
        return ResponseEntity.ok(socioService.guardar(socioModel));
    }

    // ENDPOINT REQUERIDO PARA ELIMINAR EL SOCIO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}