package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.SocioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.ISocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
@CrossOrigin(origins = "http://localhost:4200") // Permitir peticiones desde el origen de Angular
@RequiredArgsConstructor
public class SocioController {

    // Inyección estricta de la interfaz del servicio
    private final ISocioService socioService;

    /**
     * Endpoint para obtener la lista general de socios registrados (RF-05)
     */
    @GetMapping
    public ResponseEntity<List<SocioModel>> listarTodos() {
        return ResponseEntity.ok(socioService.listarSocios());
    }

    /**
     * Endpoint opcional para registrar o editar socios (RF-06)
     */
    @PostMapping
    public ResponseEntity<SocioModel> guardar(@RequestBody SocioModel socioModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.guardar(socioModel));
    }
}