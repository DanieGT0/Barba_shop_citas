import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

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
    MatMenuModule
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

      <button mat-icon-button [matMenuTriggerFor]="userMenu">
        <mat-icon>account_circle</mat-icon>
      </button>
      <mat-menu #userMenu="matMenu">
        <button mat-menu-item routerLink="/auth/login">
          <mat-icon>login</mat-icon>
          <span>Iniciar Sesión</span>
        </button>
        <button mat-menu-item routerLink="/usuarios/perfil">
          <mat-icon>person</mat-icon>
          <span>Mi Perfil</span>
        </button>
        <mat-divider></mat-divider>
        <button mat-menu-item>
          <mat-icon>logout</mat-icon>
          <span>Cerrar Sesión</span>
        </button>
      </mat-menu>
    </mat-toolbar>

    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'BarberApp';
}