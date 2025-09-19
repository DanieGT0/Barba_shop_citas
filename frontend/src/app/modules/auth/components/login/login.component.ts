import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  isLoading = false;

  constructor(
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  loginWithGoogle(): void {
    this.isLoading = true;

    // Redirect to backend OAuth2 endpoint for Google
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }

  loginWithGitHub(): void {
    this.isLoading = true;

    // Redirect to backend OAuth2 endpoint for GitHub
    window.location.href = 'http://localhost:8080/oauth2/authorization/github';
  }

  continueAsGuest(): void {
    this.snackBar.open('Continuando como invitado', 'Cerrar', {
      duration: 3000
    });
    this.router.navigate(['/cortes']);
  }
}