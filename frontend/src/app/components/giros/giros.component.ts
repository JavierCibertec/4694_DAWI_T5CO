import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-giros',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './giros.component.html'
})
export class GirosComponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api/giros';

  giros: any[] = [];
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  // Campos del formulario
  id: number | null = null;
  nombre: string = '';
  descripcion: string = '';

  ngOnInit(): void {
    this.cargarGiros();
  }

  cargarGiros(): void {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.giros = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mostrarMensaje('Error al cargar los giros.', 'danger');
      }
    });
  }

  guardar(): void {
    if (!this.nombre) {
      this.mostrarMensaje('El nombre del giro es obligatorio.', 'warning');
      return;
    }

    const giro = {
      id: this.id,
      nombre: this.nombre,
      descripcion: this.descripcion
    };

    this.http.post<any>(this.apiUrl, giro).subscribe({
      next: () => {
        this.mostrarMensaje('Giro guardado correctamente.', 'success');
        this.limpiarFormulario();
        this.cargarGiros();
      },
      error: () => {
        this.mostrarMensaje('Error al guardar el giro.', 'danger');
      }
    });
  }

  editar(giro: any): void {
    this.id = giro.id;
    this.nombre = giro.nombre;
    this.descripcion = giro.descripcion || '';
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que desea eliminar este giro?')) return;

    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: () => {
        this.mostrarMensaje('Giro eliminado.', 'success');
        this.cargarGiros();
      },
      error: () => {
        this.mostrarMensaje('Error al eliminar el giro.', 'danger');
      }
    });
  }

  limpiarFormulario(): void {
    this.id = null;
    this.nombre = '';
    this.descripcion = '';
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensajeAlert = msg;
    this.tipoAlert = tipo;
    this.cdr.detectChanges();
  }
}