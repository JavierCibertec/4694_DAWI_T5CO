import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-puestos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './puestos.component.html'
})
export class PuestosComponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api/puestos';

  puestos: any[] = [];
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  // Campos del formulario
  id: number | null = null;
  numeroPuesto: string = '';
  inquilino: string = '';
  vigencia: string = '';
  idGiro: number | null = null;
  idSocio: number | null = null;

  ngOnInit(): void {
    this.cargarPuestos();
  }

  cargarPuestos(): void {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.puestos = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mostrarMensaje('Error al cargar los puestos.', 'danger');
      }
    });
  }

  guardar(): void {
    if (!this.numeroPuesto || !this.idGiro) {
      this.mostrarMensaje('Número de puesto e ID Giro son obligatorios.', 'warning');
      return;
    }

    const puesto = {
      id: this.id,
      numeroPuesto: this.numeroPuesto,
      inquilino: this.inquilino,
      vigencia: this.vigencia,
      idGiro: this.idGiro,
      idSocio: this.idSocio
    };

    this.http.post<any>(this.apiUrl, puesto).subscribe({
      next: () => {
        this.mostrarMensaje('Puesto guardado correctamente.', 'success');
        this.limpiarFormulario();
        this.cargarPuestos();
      },
      error: () => {
        this.mostrarMensaje('Error al guardar el puesto.', 'danger');
      }
    });
  }

  editar(puesto: any): void {
    this.id = puesto.id;
    this.numeroPuesto = puesto.numeroPuesto;
    this.inquilino = puesto.inquilino || '';
    this.vigencia = puesto.vigencia || '';
    this.idGiro = puesto.idGiro;
    this.idSocio = puesto.idSocio;
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que desea eliminar este puesto?')) return;

    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: () => {
        this.mostrarMensaje('Puesto eliminado.', 'success');
        this.cargarPuestos();
      },
      error: () => {
        this.mostrarMensaje('Error al eliminar el puesto.', 'danger');
      }
    });
  }

  limpiarFormulario(): void {
    this.id = null;
    this.numeroPuesto = '';
    this.inquilino = '';
    this.vigencia = '';
    this.idGiro = null;
    this.idSocio = null;
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensajeAlert = msg;
    this.tipoAlert = tipo;
    this.cdr.detectChanges();
  }
}