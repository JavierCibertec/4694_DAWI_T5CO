package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Util;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.EgresoModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.ReciboModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    /**
     * Reporte de Movimientos Diarios y Recibos de Caja (.xlsx)
     */
    public static byte[] generarExcelRecibos(List<ReciboModel> recibos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte Diario");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Correlativo", "Tipo", "Monto Total (S/)", "Fecha y Hora", "Usuario Operador"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
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

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    /**
     * Reporte Mensual de Egresos y Gastos (.xlsx)
     */
    public static byte[] generarExcelEgresos(List<EgresoModel> egresos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte Egresos");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Proveedor", "Documento", "Fecha", "Monto (S/)", "Motivo / Detalle", "Estado"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (EgresoModel e : egresos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(e.getId() != null ? e.getId() : 0);
                row.createCell(1).setCellValue(e.getProveedor() != null ? e.getProveedor() : "");
                row.createCell(2).setCellValue(e.getDocumento() != null ? e.getDocumento() : "");
                row.createCell(3).setCellValue(e.getFecha() != null ? e.getFecha() : "");
                row.createCell(4).setCellValue(e.getMonto() != null ? e.getMonto().doubleValue() : 0.0);
                row.createCell(5).setCellValue(e.getMotivo() != null ? e.getMotivo() : "");
                row.createCell(6).setCellValue(e.getEstado() != null ? e.getEstado() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}