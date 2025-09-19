import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/cortes',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'cortes',
    loadChildren: () => import('./modules/cortes/cortes.module').then(m => m.CortesModule)
  },
  {
    path: 'barberos',
    loadChildren: () => import('./modules/barberos/barberos.module').then(m => m.BarberosModule)
  },
  {
    path: 'citas',
    loadChildren: () => import('./modules/citas/citas.module').then(m => m.CitasModule)
  },
  {
    path: 'usuarios',
    loadChildren: () => import('./modules/usuarios/usuarios.module').then(m => m.UsuariosModule)
  },
  {
    path: '**',
    redirectTo: '/cortes'
  }
];