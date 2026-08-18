import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  username: string = '';
  password: string = '';
  errorMessage: string = '';

  onLogin(): void {
    // Resetea el mensaje antes de realizar la petición
    this.errorMessage = '';

    if (!this.username || !this.password) {
      this.errorMessage = 'Ingrese usuario y contraseña.';
      return;
    }

    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: (res) => {
        if (res && res.token) {
          localStorage.setItem('jwtToken', res.token);
          localStorage.setItem('username', res.username || this.username);
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = 'Credenciales inválidas';
          this.cdr.detectChanges();
        }
      },
      error: () => {
        // Muestra el error e inmediatamente detecta el cambio en la vista al primer clic
        this.errorMessage = 'Credenciales inválidas';
        this.cdr.detectChanges();
      }
    });
  }
}
