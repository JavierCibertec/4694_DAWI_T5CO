package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.PuestoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IPuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puestos")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PuestoController {

    private final IPuestoService puestoService;

    @GetMapping
    public ResponseEntity<List<PuestoModel>> listar() {
        return ResponseEntity.ok(puestoService.listarPuestos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuestoModel> obtenerPorId(@PathVariable Long id) {
        PuestoModel puesto = puestoService.obtenerPorId(id);
        if (puesto != null) {
            return ResponseEntity.ok(puesto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PuestoModel> guardar(@RequestBody PuestoModel puestoModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(puestoService.guardar(puestoModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        puestoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
