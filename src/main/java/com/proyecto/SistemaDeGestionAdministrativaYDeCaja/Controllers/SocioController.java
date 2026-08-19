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
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SocioController {

    private final ISocioService socioService;

    @GetMapping
    public ResponseEntity<List<SocioModel>> listar() {
        return ResponseEntity.ok(socioService.listarSocios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioModel> obtenerPorId(@PathVariable Long id) {
        SocioModel socio = socioService.obtenerPorId(id);
        if (socio != null) {
            return ResponseEntity.ok(socio);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SocioModel> guardar(@RequestBody SocioModel socioModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.guardar(socioModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

