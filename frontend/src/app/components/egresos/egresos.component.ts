import { Component, inject, ChangeDetectorRef, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-egresos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './egresos.component.html'
})
export class EgresosComponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api';


  egresosList: any[] = [];
  selectedFile: File | null = null;
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  ngOnInit(): void {
    this.cargarListaEgresos();
  }

  cargarListaEgresos(): void {
    this.http.get<any[]>(`${this.apiUrl}/egresos`).subscribe({
      next: (data) => {
        this.egresosList = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensajeAlert = 'Error al cargar la lista de egresos.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  cargarMasivo(): void {
    if (!this.selectedFile) {
      this.mensajeAlert = 'Seleccione un archivo CSV primero.';
      this.tipoAlert = 'warning';
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.http.post<any[]>(`${this.apiUrl}/egresos/cargar-masivo`, formData).subscribe({
      next: (res) => {
        this.mensajeAlert = `Carga exitosa. Se procesaron ${res.length} registros.`;
        this.tipoAlert = 'success';
        this.cargarListaEgresos();
      },
      error: () => {
        this.mensajeAlert = 'Error al procesar la carga masiva CSV.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }

  vaciarEgresos(): void {
    if (!confirm('¿Estás seguro de que deseas eliminar TODOS los egresos de la base de datos?')) {
      return;
    }

    this.http.delete(`${this.apiUrl}/egresos/vaciar`).subscribe({
      next: () => {
        this.mensajeAlert = 'Se han eliminado todos los registros de egresos.';
        this.tipoAlert = 'warning';
        this.egresosList = [];
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensajeAlert = 'Error al intentar vaciar los registros.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }
}
