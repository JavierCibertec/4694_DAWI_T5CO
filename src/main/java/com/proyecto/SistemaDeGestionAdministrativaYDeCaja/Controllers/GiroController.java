package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.GiroModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IGiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giros")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class GiroController {

    private final IGiroService giroService;

    @GetMapping
    public ResponseEntity<List<GiroModel>> listar() {
        return ResponseEntity.ok(giroService.listarGiros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiroModel> obtenerPorId(@PathVariable Long id) {
        GiroModel giro = giroService.obtenerPorId(id);
        if (giro != null) {
            return ResponseEntity.ok(giro);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GiroModel> guardar(@RequestBody GiroModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giroService.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        giroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}