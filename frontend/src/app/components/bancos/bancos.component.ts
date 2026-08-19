import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-bancos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './bancos.component.html'
})
export class BancosComponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api/bancos';

  bancos: any[] = [];
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  // Campos del formulario
  id: number | null = null;
  nombre: string = '';
  numeroCuenta: string = '';
  cci: string = '';
  moneda: string = 'PEN';

  ngOnInit(): void {
    this.cargarBancos();
  }

  cargarBancos(): void {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.bancos = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mostrarMensaje('Error al cargar los bancos.', 'danger');
      }
    });
  }

  guardar(): void {
    if (!this.nombre || !this.numeroCuenta) {
      this.mostrarMensaje('Nombre y Número de Cuenta son obligatorios.', 'warning');
      return;
    }

    const banco = {
      id: this.id,
      nombre: this.nombre,
      numeroCuenta: this.numeroCuenta,
      cci: this.cci,
      moneda: this.moneda
    };

    this.http.post<any>(this.apiUrl, banco).subscribe({
      next: () => {
        this.mostrarMensaje('Banco guardado correctamente.', 'success');
        this.limpiarFormulario();
        this.cargarBancos();
      },
      error: () => {
        this.mostrarMensaje('Error al guardar el banco.', 'danger');
      }
    });
  }

  editar(banco: any): void {
    this.id = banco.id;
    this.nombre = banco.nombre;
    this.numeroCuenta = banco.numeroCuenta;
    this.cci = banco.cci || '';
    this.moneda = banco.moneda || 'PEN';
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que desea eliminar este banco?')) return;

    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: () => {
        this.mostrarMensaje('Banco eliminado.', 'success');
        this.cargarBancos();
      },
      error: () => {
        this.mostrarMensaje('Error al eliminar el banco.', 'danger');
      }
    });
  }

  limpiarFormulario(): void {
    this.id = null;
    this.nombre = '';
    this.numeroCuenta = '';
    this.cci = '';
    this.moneda = 'PEN';
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensajeAlert = msg;
    this.tipoAlert = tipo;
    this.cdr.detectChanges();
  }
}