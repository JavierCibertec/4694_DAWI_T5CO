import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CuentaPorCobrarService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/cuentas-por-cobrar';

  crearCuenta(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  listarPorSocio(idSocio: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/socio/${idSocio}`);
  }
}
