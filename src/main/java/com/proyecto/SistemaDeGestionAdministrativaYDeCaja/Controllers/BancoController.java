package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.BancoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IBancoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bancos")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BancoController {

    private final IBancoService bancoService;

    @GetMapping
    public ResponseEntity<List<BancoModel>> listar() {
        return ResponseEntity.ok(bancoService.listarBancos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancoModel> obtenerPorId(@PathVariable Long id) {
        BancoModel banco = bancoService.obtenerPorId(id);
        if (banco != null) {
            return ResponseEntity.ok(banco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BancoModel> guardar(@RequestBody BancoModel bancoModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bancoService.guardar(bancoModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        bancoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}