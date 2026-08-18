import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CuentasPorCobrarComponent } from './components/cuentas-por-cobrar/cuentas-por-cobrar.component';
import { CobrosComponent } from './components/cobros/cobros.component';
import { EgresosComponent } from './components/egresos/egresos.component';
import { ReportesComponent } from './components/reportes/reportes.component';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'cuentas-por-cobrar', component: CuentasPorCobrarComponent, canActivate: [authGuard] },
  { path: 'cobros', component: CobrosComponent, canActivate: [authGuard] },
  { path: 'egresos', component: EgresosComponent, canActivate: [authGuard] },
  { path: 'reportes', component: ReportesComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: 'login' } // Redirige a login solo si la ruta NO existe
];
