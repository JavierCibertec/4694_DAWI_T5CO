package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PagoController {

    private final IPagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<ReciboModel> procesarPago(@RequestBody List<Long> idsCuentas,
                                                    @RequestParam(defaultValue = "admin") String usuario) {
        return ResponseEntity.ok(pagoService.procesarPago(idsCuentas, usuario));
    }

    @PostMapping("/canjear-banco")
    public ResponseEntity<ReciboModel> canjear(@RequestParam Long idCuenta,
                                               @RequestParam Long idBanco,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeposito,
                                               @RequestParam(defaultValue = "admin") String usuario) {
        return ResponseEntity.ok(pagoService.canjearPorOperacionBancaria(idCuenta, idBanco, fechaDeposito, usuario));
    }

    @GetMapping("/recibos")
    public ResponseEntity<List<ReciboModel>> listarRecibosPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(pagoService.listarRecibosPorFecha(fecha));
    }

    @GetMapping("/recibos/{correlativo}")
    public ResponseEntity<ReciboModel> obtenerReciboPorCorrelativo(@PathVariable Long correlativo) {
        ReciboModel model = pagoService.obtenerReciboPorCorrelativo(correlativo);
        if (model != null) {
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }
}