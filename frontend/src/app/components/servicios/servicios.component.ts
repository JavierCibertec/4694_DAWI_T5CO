import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-servicios',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './servicios.component.html'
})
export class ServiciosComponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api/servicios';

  servicios: any[] = [];
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  // Campos del formulario
  id: number | null = null;
  nombre: string = '';
  recurrencia: string = 'MENSUAL';
  costo: number | null = null;
  moneda: string = 'PEN';
  destinoCargo: string = 'PUESTO';
  esPorConsumo: boolean = false;

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.servicios = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mostrarMensaje('Error al cargar los servicios.', 'danger');
      }
    });
  }

  guardar(): void {
    if (!this.nombre || this.costo === null) {
      this.mostrarMensaje('Nombre y Costo son obligatorios.', 'warning');
      return;
    }

    const servicio = {
      id: this.id,
      nombre: this.nombre,
      recurrencia: this.recurrencia,
      costo: this.costo,
      moneda: this.moneda,
      destinoCargo: this.destinoCargo,
      esPorConsumo: this.esPorConsumo
    };

    this.http.post<any>(this.apiUrl, servicio).subscribe({
      next: () => {
        this.mostrarMensaje('Servicio guardado correctamente.', 'success');
        this.limpiarFormulario();
        this.cargarServicios();
      },
      error: () => {
        this.mostrarMensaje('Error al guardar el servicio.', 'danger');
      }
    });
  }

  editar(servicio: any): void {
    this.id = servicio.id;
    this.nombre = servicio.nombre;
    this.recurrencia = servicio.recurrencia || 'MENSUAL';
    this.costo = servicio.costo;
    this.moneda = servicio.moneda || 'PEN';
    this.destinoCargo = servicio.destinoCargo || 'PUESTO';
    this.esPorConsumo = servicio.esPorConsumo || false;
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que desea eliminar este servicio?')) return;

    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: () => {
        this.mostrarMensaje('Servicio eliminado.', 'success');
        this.cargarServicios();
      },
      error: () => {
        this.mostrarMensaje('Error al eliminar el servicio.', 'danger');
      }
    });
  }

  limpiarFormulario(): void {
    this.id = null;
    this.nombre = '';
    this.recurrencia = 'MENSUAL';
    this.costo = null;
    this.moneda = 'PEN';
    this.destinoCargo = 'PUESTO';
    this.esPorConsumo = false;
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensajeAlert = msg;
    this.tipoAlert = tipo;
    this.cdr.detectChanges();
  }
}