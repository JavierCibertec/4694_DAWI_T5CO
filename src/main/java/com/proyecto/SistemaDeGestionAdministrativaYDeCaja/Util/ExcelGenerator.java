package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Util;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    public static byte[] generarExcelRecibos(List<ReciboModel> recibos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte Movimientos");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Correlativo", "Tipo", "Monto Total", "Fecha", "Usuario"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (ReciboModel r : recibos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getId() != null ? r.getId() : 0);
                row.createCell(1).setCellValue(r.getCorrelativo() != null ? r.getCorrelativo() : 0);
                row.createCell(2).setCellValue(r.getTipo() != null ? r.getTipo() : "");
                row.createCell(3).setCellValue(r.getMontoTotal() != null ? r.getMontoTotal().doubleValue() : 0.0);
                row.createCell(4).setCellValue(r.getFecha() != null ? r.getFecha().toString() : "");
                row.createCell(5).setCellValue(r.getUsuarioCreacion() != null ? r.getUsuarioCreacion() : "");
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
