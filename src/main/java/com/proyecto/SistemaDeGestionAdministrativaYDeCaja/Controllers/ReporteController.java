package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Controllers;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.EgresoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IEgresoService;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReporteController {

    private final IPagoService pagoService;
    private final IEgresoService egresoService;

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

    @GetMapping("/excel/egresos-mensual")
    public ResponseEntity<byte[]> descargarEgresosMensual(
            @RequestParam int mes,
            @RequestParam int anio) throws IOException {

        List<EgresoModel> egresos = egresoService.listarPorMes(mes, anio);
        byte[] excelContent = ExcelGenerator.generarExcelEgresos(egresos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte_Egresos_" + mes + "_" + anio + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelContent);
    }
}