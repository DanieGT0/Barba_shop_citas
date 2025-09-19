import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '@auth0/auth0-angular';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule
  ],
  template: `
    <mat-toolbar class="nav-toolbar">
      <span>BarberApp</span>
      <span class="nav-spacer"></span>

      <nav>
        <a routerLink="/cortes" class="nav-link">Cortes</a>
        <a routerLink="/barberos" class="nav-link">Barberos</a>
        <a routerLink="/citas" class="nav-link">Mis Citas</a>
      </nav>

      <!-- Usuario no autenticado -->
      <ng-container *ngIf="auth.isAuthenticated$ | async; else notAuthenticated">
        <button mat-icon-button [matMenuTriggerFor]="userMenu">
          <mat-icon>account_circle</mat-icon>
        </button>
        <mat-menu #userMenu="matMenu">
          <div mat-menu-item disabled>
            <span>{{ (auth.user$ | async)?.name }}</span>
          </div>
          <mat-divider></mat-divider>
          <button mat-menu-item routerLink="/usuarios/perfil">
            <mat-icon>person</mat-icon>
            <span>Mi Perfil</span>
          </button>
          <button mat-menu-item (click)="logout()">
            <mat-icon>logout</mat-icon>
            <span>Cerrar Sesión</span>
          </button>
        </mat-menu>
      </ng-container>

      <ng-template #notAuthenticated>
        <button mat-raised-button color="primary" (click)="loginWithGitHub()">
          <mat-icon>login</mat-icon>
          Iniciar con GitHub
        </button>
      </ng-template>
    </mat-toolbar>

    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'BarberApp';

  constructor(
    @Inject(DOCUMENT) public document: Document,
    public auth: AuthService
  ) {}

  loginWithGitHub() {
    this.auth.loginWithRedirect({
      authorizationParams: {
        connection: 'github'
      }
    });
  }

  logout() {
    this.auth.logout({
      logoutParams: {
        returnTo: this.document.location.origin
      }
    });
  }
}