import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cuentas-por-cobrar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cuentas-por-cobrar.component.html'
})
export class CuentasPorCobrarComponent {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api';

  // Propiedades del formulario
  idPuesto: number = 1;
  idSocio: number = 10;
  idServicio: number = 2;
  periodo: string = '2026-10';
  monto: number = 50;
  estado: string = 'PENDIENTE';
  lecturaInicial: number = 100;
  lecturaFinal: number = 150;
  esPorConsumo: boolean = true;

  // Variables para la notificación
  mensajeAlert: string = '';
  tipoAlert: string = 'success';

  generarCargo(): void {
    this.mensajeAlert = '';

    // Cálculo dinámico según el switch
    let montoCalculado = this.monto;
    if (this.esPorConsumo) {
      montoCalculado = Math.max(0, this.lecturaFinal - this.lecturaInicial);
    }

    const payload = {
      idPuesto: this.idPuesto,
      idSocio: this.idSocio,
      idServicio: this.idServicio,
      periodo: this.periodo,
      monto: montoCalculado,
      estado: this.estado,
      lecturaInicial: this.lecturaInicial,
      lecturaFinal: this.lecturaFinal,
      esPorConsumo: this.esPorConsumo
    };

    this.http.post<any>(`${this.apiUrl}/cuentas-por-cobrar`, payload).subscribe({
      next: (res) => {
        // Asignación de la notificación dinámica
        this.mensajeAlert = `¡Cuenta Registrada Exitosamente! ID Cuenta: ${res.id} | Socio: ${res.idSocio} | Monto: S/ ${res.monto}`;
        this.tipoAlert = 'success';
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.mensajeAlert = 'Error al registrar la cuenta por cobrar en el servidor.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }
}
