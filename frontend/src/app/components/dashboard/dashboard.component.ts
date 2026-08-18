import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  // Obtiene el identificador del usuario autenticado para el encabezado (RF-03)
  username: string = localStorage.getItem('username') || 'Usuario';

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
