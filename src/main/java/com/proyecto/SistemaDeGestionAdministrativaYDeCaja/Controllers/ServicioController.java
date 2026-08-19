package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ServicioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ServicioController {

    private final IServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioModel>> listar() {
        return ResponseEntity.ok(servicioService.listarServicios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioModel> obtenerPorId(@PathVariable Long id) {
        ServicioModel servicio = servicioService.obtenerPorId(id);
        if (servicio != null) {
            return ResponseEntity.ok(servicio);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ServicioModel> guardar(@RequestBody ServicioModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}