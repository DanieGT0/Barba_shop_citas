import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.scss']
})
export class CallbackComponent implements OnInit {
  isLoading = true;
  message = 'Procesando autenticación...';

  constructor(
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.processAuthCallback();
  }

  private processAuthCallback(): void {
    // Simulate processing OAuth callback
    setTimeout(() => {
      const urlParams = new URLSearchParams(window.location.search);
      const error = urlParams.get('error');

      if (error) {
        this.handleAuthError(error);
      } else {
        this.handleAuthSuccess();
      }
    }, 2000);
  }

  private handleAuthSuccess(): void {
    this.isLoading = false;
    this.message = '¡Autenticación exitosa! Redirigiendo...';

    this.snackBar.open('¡Bienvenido! Has iniciado sesión correctamente', 'Cerrar', {
      duration: 3000,
      panelClass: ['success-snackbar']
    });

    setTimeout(() => {
      this.router.navigate(['/cortes']);
    }, 1500);
  }

  private handleAuthError(error: string): void {
    this.isLoading = false;
    this.message = 'Error en la autenticación. Redirigiendo al inicio de sesión...';

    this.snackBar.open(`Error de autenticación: ${error}`, 'Cerrar', {
      duration: 5000,
      panelClass: ['error-snackbar']
    });

    setTimeout(() => {
      this.router.navigate(['/auth/login']);
    }, 3000);
  }
}