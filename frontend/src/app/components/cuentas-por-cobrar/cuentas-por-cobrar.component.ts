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

  // Campos del formulario
  idPuesto: number = 2;
  idSocio: number = 13;
  idServicio: number = 3;
  periodo: string = '2026-11';
  monto: number = 50;
  estado: string = 'PENDIENTE';
  lecturaInicial: number = 100;
  lecturaFinal: number = 150;
  esPorConsumo: boolean = true;

  // Campos opcionales del socio
  nombresSocio: string = 'Javier';
  apellidosSocio: string = 'Herrera';
  accionSocio: string = 'ACC-002';
  etapaSocio: string = 'Etapa 1';
  fechaNacimientoSocio: string = '2026-08-14';

  // Lista de Socios y Alertas
  sociosList: any[] = [];
  mensajeAlert: string = '';
  tipoAlert: string = 'info';

  ngOnInit(): void {
    this.obtenerTotalSocios();
  }

  obtenerTotalSocios(): void {
    this.http.get<any[]>(`${this.apiUrl}/socios`).subscribe({
      next: (data) => {
        this.sociosList = data;
        this.mensajeAlert = `Se obtuvieron ${data.length} socios registrados en el sistema.`;
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

  // Confirmación de Edición para el botón del encabezado
  confirmarEditarSocioActual(): void {
    if (confirm('Seguro que desea editar este usuario?')) {
      const socioActual = this.sociosList.find(s => s.id === this.idSocio) || {
        id: this.idSocio,
        nombres: this.nombresSocio,
        apellidos: this.apellidosSocio,
        accion: this.accionSocio,
        etapa: this.etapaSocio,
        fechaNacimiento: this.fechaNacimientoSocio
      };
      this.cargarSocioEnFormulario(socioActual);
    }
  }

  // Confirmación de Edición desde la fila de la tabla
  confirmarEditarSocio(socio: any): void {
    if (confirm('Seguro que desea editar este usuario?')) {
      this.cargarSocioEnFormulario(socio);
    }
  }

  cargarSocioEnFormulario(socio: any): void {
    this.idSocio = socio.id;
    this.nombresSocio = socio.nombres;
    this.apellidosSocio = socio.apellidos;
    this.accionSocio = socio.accion || '';
    this.etapaSocio = socio.etapa || '';
    this.fechaNacimientoSocio = socio.fechaNacimiento || '';
    this.mensajeAlert = `Socio ID ${socio.id} cargado en el formulario para edición.`;
    this.tipoAlert = 'warning';
    this.cdr.detectChanges();
  }

  // Confirmación de Eliminación para el botón del encabezado
  confirmarEliminarSocioActual(): void {
    if (confirm('Seguro que desea eliminar este usuario?')) {
      this.eliminarSocio(this.idSocio);
    }
  }

  // Confirmación de Eliminación desde la fila de la tabla
  confirmarEliminarSocio(id: number): void {
    if (confirm('Seguro que desea eliminar este usuario?')) {
      this.eliminarSocio(id);
    }
  }

  eliminarSocio(id: number): void {
    this.http.delete(`${this.apiUrl}/socios/${id}`).subscribe({
      next: () => {
        this.mensajeAlert = `Socio ID ${id} eliminado exitosamente.`;
        this.tipoAlert = 'warning';
        this.obtenerTotalSocios();
      },
      error: () => {
        this.mensajeAlert = `Error al intentar eliminar el socio ID ${id}.`;
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }

  generarCargo(): void {
    this.mensajeAlert = '';

    let montoCalculado = this.monto;
    if (this.esPorConsumo) {
      montoCalculado = Math.max(0, this.lecturaFinal - this.lecturaInicial);
    }

    const socioPayload = {
      id: this.idSocio,
      codigo: `SOC-0${this.idSocio}`,
      nombres: this.nombresSocio,
      apellidos: this.apellidosSocio,
      accion: this.accionSocio,
      etapa: this.etapaSocio,
      fechaNacimiento: this.fechaNacimientoSocio
    };

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
            this.mensajeAlert = `¡Cuenta y Socio Registrados/Actualizados Exitosamente! ID Cuenta: ${res.id} | Socio ID: ${this.idSocio}`;
            this.tipoAlert = 'success';
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
