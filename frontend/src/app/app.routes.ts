import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CuentasPorCobrarComponent } from './components/cuentas-por-cobrar/cuentas-por-cobrar.component';
import { CobrosComponent } from './components/cobros/cobros.component';
import { EgresosComponent } from './components/egresos/egresos.component';
import { ReportesComponent } from './components/reportes/reportes.component';
import { PuestosComponent } from './components/puestos/puestos.component';
import { BancosComponent } from './components/bancos/bancos.component';
import { GirosComponent } from './components/giros/giros.component';
import { ServiciosComponent } from './components/servicios/servicios.component';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'cuentas-por-cobrar', component: CuentasPorCobrarComponent, canActivate: [authGuard] },
  { path: 'cobros', component: CobrosComponent, canActivate: [authGuard] },
  { path: 'egresos', component: EgresosComponent, canActivate: [authGuard] },
  { path: 'reportes', component: ReportesComponent, canActivate: [authGuard] },
  { path: 'puestos', component: PuestosComponent, canActivate: [authGuard] },
  { path: 'bancos', component: BancosComponent, canActivate: [authGuard] },
  { path: 'giros', component: GirosComponent, canActivate: [authGuard] },
  { path: 'servicios', component: ServiciosComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: 'login' }
];