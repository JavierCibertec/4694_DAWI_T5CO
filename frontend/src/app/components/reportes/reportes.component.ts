import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './reportes.component.html'
})
export class ReportesComponent {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/reportes';

  fechaDiaria: string = '2026-08-12';
  mesReporte: number = 8;
  anioReporte: number = 2026;

  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  descargarDiario(): void {
    if (!this.fechaDiaria) return;

    this.http.get(`${this.apiUrl}/excel/diario?fecha=${this.fechaDiaria}`, { responseType: 'blob' }).subscribe({
      next: (blob) => {
        this.descargarArchivo(blob, `Reporte_Diario_${this.fechaDiaria}.xlsx`);
        this.mostrarMensaje('Reporte Diario descargado con éxito.', 'success');
      },
      error: () => this.mostrarMensaje('No se pudo obtener el reporte diario.', 'danger')
    });
  }

  descargarEgresos(): void {
    this.http.get(`${this.apiUrl}/excel/egresos-mensual?mes=${this.mesReporte}&anio=${this.anioReporte}`, { responseType: 'blob' }).subscribe({
      next: (blob) => {
        this.descargarArchivo(blob, `Reporte_Egresos_${this.mesReporte}_${this.anioReporte}.xlsx`);
        this.mostrarMensaje('Reporte de Egresos descargado con éxito.', 'success');
      },
      error: () => this.mostrarMensaje('No se pudo obtener el reporte de egresos.', 'danger')
    });
  }

  private descargarArchivo(blob: Blob, nombreArchivo: string): void {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nombreArchivo;
    document.body.appendChild(a);
    a.click();
    a.remove();
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensajeAlert = msg;
    this.tipoAlert = tipo;
  }
}
