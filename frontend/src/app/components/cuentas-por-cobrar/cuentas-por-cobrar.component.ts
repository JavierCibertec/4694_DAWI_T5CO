import { Component, inject, ChangeDetectorRef, OnInit } from '@angular/core';
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
export class CuentasPorCobrarComponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api';

  // Campos del formulario de Cuenta por Cobrar
  idPuesto: number = 2;
  idSocio: number = 13;
  idServicio: number = 3;
  periodo: string = '2026-11';
  monto: number = 50;
  estado: string = 'PENDIENTE';
  lecturaInicial: number = 100;
  lecturaFinal: number = 150;
  esPorConsumo: boolean = true;

  // Campos opcionales del socio (RF-06)
  nombresSocio: string = 'Javier';
  apellidosSocio: string = 'Herrera';
  accionSocio: string = 'ACC-002';
  etapaSocio: string = 'Etapa 1';
  fechaNacimientoSocio: string = '2026-08-14';

  // Lista de Socios y Alertas Dinámicas
  sociosList: any[] = [];
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  ngOnInit(): void {
    this.obtenerTotalSocios();
  }

  // Método invocado al presionar "Mostrar Total de Socios"
  obtenerTotalSocios(): void {
    this.http.get<any[]>(`${this.apiUrl}/socios`).subscribe({
      next: (data) => {
        this.sociosList = data;
        this.mensajeAlert = `Se obtuvieron ${data.length} socios registrados en el sistema.`;
        this.tipoAlert = 'info';
        this.cdr.detectChanges(); // Forzar renderizado inmediato
      },
      error: () => {
        // Respaldo en caso de respuesta por controlador secundario
        this.http.get<any[]>(`${this.apiUrl}/cuentas-por-cobrar/socios`).subscribe({
          next: (data) => {
            this.sociosList = data;
            this.mensajeAlert = `Se obtuvieron ${data.length} socios registrados.`;
            this.tipoAlert = 'info';
            this.cdr.detectChanges();
          },
          error: () => {
            this.mensajeAlert = 'Error al consultar el listado total de socios en el servidor.';
            this.tipoAlert = 'danger';
            this.cdr.detectChanges();
          }
        });
      }
    });
  }

  generarCargo(): void {
    this.mensajeAlert = '';

    let montoCalculado = this.monto;
    if (this.esPorConsumo) {
      montoCalculado = Math.max(0, this.lecturaFinal - this.lecturaInicial);
    }

    // 1. Payload para guardar/actualizar la información del Socio en MySQL
    const socioPayload = {
      id: this.idSocio,
      codigo: `SOC-0${this.idSocio}`,
      nombres: this.nombresSocio,
      apellidos: this.apellidosSocio,
      accion: this.accionSocio,
      etapa: this.etapaSocio,
      fechaNacimiento: this.fechaNacimientoSocio
    };

    // 2. Persistir primero al socio y luego registrar la cuenta por cobrar
    this.http.post<any>(`${this.apiUrl}/socios`, socioPayload).subscribe({
      next: () => {
        const cuentaPayload = {
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

        this.http.post<any>(`${this.apiUrl}/cuentas-por-cobrar`, cuentaPayload).subscribe({
          next: (res) => {
            this.mensajeAlert = `¡Cuenta y Socio Registrados Exitosamente! ID Cuenta: ${res.id} | Socio ID: ${this.idSocio} | Monto: S/ ${res.monto}`;
            this.tipoAlert = 'success';

            // 3. Actualizar dinámicamente la lista de socios en pantalla
            this.obtenerTotalSocios();
          },
          error: () => {
            this.mensajeAlert = 'Socio guardado, pero ocurrió un error al registrar la cuenta por cobrar.';
            this.tipoAlert = 'warning';
            this.obtenerTotalSocios();
          }
        });
      },
      error: () => {
        this.mensajeAlert = 'Error al registrar la información del socio en la base de datos.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }
}
