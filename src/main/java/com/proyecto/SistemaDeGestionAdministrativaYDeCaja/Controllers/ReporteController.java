package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IPagoService;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Util.ExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final IPagoService pagoService;

    @GetMapping("/excel/diario")
    public ResponseEntity<byte[]> descargarReporteDiario(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {

        List<ReciboModel> recibos = pagoService.listarRecibosPorFecha(fecha);
        byte[] excelContent = ExcelGenerator.generarExcelRecibos(recibos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte_Diario_" + fecha + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelContent);
    }
}