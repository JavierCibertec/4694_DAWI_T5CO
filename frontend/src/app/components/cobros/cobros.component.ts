import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-cobros',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cobros.component.html'
})
export class CobrosComponent {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private apiUrl = 'http://localhost:8080/api';

  idSocio: number = 10;
  cuentasPendientes: any[] = [];
  cuentasSeleccionadas: number[] = [];

  mensajeAlert: string = '';
  tipoAlert: string = 'info';
  reciboGenerado: any = null;

  buscarCuentas(): void {
    this.mensajeAlert = '';
    this.reciboGenerado = null;
    this.cuentasSeleccionadas = [];

    if (!this.idSocio) {
      this.mensajeAlert = 'Por favor ingrese un ID de Socio válido para buscar.';
      this.tipoAlert = 'warning';
      return;
    }

    // Petición HTTP al Backend
    this.http.get<any[]>(`${this.apiUrl}/cuentas-por-cobrar/socio/${this.idSocio}`).subscribe({
      next: (cuentas) => {
        this.cuentasPendientes = cuentas.filter(c => c.estado === 'PENDIENTE');

        if (this.cuentasPendientes.length === 0) {
          this.mensajeAlert = `No se encontraron cuentas pendientes para el SOCIO con ID ${this.idSocio}.`;
          this.tipoAlert = 'info';
        }

        // Forzar el refresco de la vista inmediatamente en el primer clic
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensajeAlert = 'Error al consultar deudas en el servidor backend.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }

  toggleSeleccion(idCuenta: number, event: any): void {
    if (event.target.checked) {
      this.cuentasSeleccionadas.push(idCuenta);
    } else {
      this.cuentasSeleccionadas = this.cuentasSeleccionadas.filter(id => id !== idCuenta);
    }
  }

  procesarPago(): void {
    if (this.cuentasSeleccionadas.length === 0) {
      this.mensajeAlert = 'Seleccione al menos una cuenta por cobrar de la tabla.';
      this.tipoAlert = 'warning';
      return;
    }

    const username = localStorage.getItem('username') || 'admin';

    this.http.post<any>(`${this.apiUrl}/pagos/procesar?usuario=${username}`, this.cuentasSeleccionadas).subscribe({
      next: (recibo) => {
        this.reciboGenerado = recibo;
        this.generarPDFRecibo(recibo);
        this.buscarCuentas();
      },
      error: () => {
        this.mensajeAlert = 'Error al procesar el pago.';
        this.tipoAlert = 'danger';
        this.cdr.detectChanges();
      }
    });
  }

  private generarPDFRecibo(recibo: any): void {
    const doc = new jsPDF();
    doc.setFontSize(18);
    doc.text('SISTEMA DE GESTIÓN DE CAJA', 105, 20, { align: 'center' });
    doc.setFontSize(14);
    doc.setTextColor(0, 102, 204);
    doc.text(`RECIBO DE INGRESO N° ${String(recibo.correlativo).padStart(6, '0')}`, 105, 30, { align: 'center' });
    doc.setLineWidth(0.5);
    doc.line(20, 35, 190, 35);

    const bodyData = this.cuentasSeleccionadas.map(id => [`Cuenta N° ${id}`, 'CANCELADO', `ID ${id}`]);
    autoTable(doc, {
      startY: 67,
      head: [['Concepto / ID', 'Estado', 'Detalle']],
      body: bodyData,
      theme: 'striped',
      headStyles: { fillColor: [40, 167, 69] }
    });

    const finalY = (doc as any).lastAutoTable.finalY + 15;
    doc.setFontSize(12);
    doc.setFont('helvetica', 'bold');
    doc.text(`TOTAL RECAUDADO: S/ ${parseFloat(recibo.montoTotal).toFixed(2)}`, 190, finalY, { align: 'right' });

    doc.save(`Recibo_Ingreso_${String(recibo.correlativo).padStart(6, '0')}.pdf`);
  }
}
