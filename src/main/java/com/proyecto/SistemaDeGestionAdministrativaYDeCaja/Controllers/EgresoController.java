package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.EgresoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IEgresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/egresos")
@RequiredArgsConstructor
public class EgresoController {

    private final IEgresoService egresoService;


     // Registro individual de un egreso
    @PostMapping
    public ResponseEntity<EgresoModel> registrar(@RequestBody EgresoModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(egresoService.registrar(model));
    }


     // Listado general de todos los egresos
    @GetMapping
    public ResponseEntity<List<EgresoModel>> listar() {
        return ResponseEntity.ok(egresoService.listar());
    }


     // Carga masiva mediante archivo CSV (RF-28)
    @PostMapping(value = "/cargar-masivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<EgresoModel>> cargarMasivo(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(egresoService.cargarEgresosMasivos(file));
    }


     // Filtrado de egresos por mes y año (RF-30)
    @GetMapping("/mes")
    public ResponseEntity<List<EgresoModel>> listarPorMes(@RequestParam int mes, @RequestParam int anio) {
        return ResponseEntity.ok(egresoService.listarPorMes(mes, anio));
    }


    // Anulación de un egreso por su ID (RF-30)
    @PutMapping("/{id}/anular")
    public ResponseEntity<EgresoModel> anular(@PathVariable Long id) {
        return ResponseEntity.ok(egresoService.anularEgreso(id));
    }

    // Vaciar/Eliminar todos los egresos registrados de la base de datos
    @DeleteMapping("/vaciar")
    public ResponseEntity<Void> vaciarEgresos() {
        egresoService.vaciarTodos();
        return ResponseEntity.noContent().build();
    }
}