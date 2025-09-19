import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Usuario, TipoUsuario } from '../../shared/models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private endpoint = '/usuarios';

  constructor(private apiService: ApiService) {}

  getAllUsuarios(): Observable<Usuario[]> {
    return this.apiService.get<Usuario[]>(this.endpoint);
  }

  getUsuarioById(id: number): Observable<Usuario> {
    return this.apiService.get<Usuario>(`${this.endpoint}/${id}`);
  }

  getUsuarioByEmail(email: string): Observable<Usuario> {
    return this.apiService.get<Usuario>(`${this.endpoint}/email/${email}`);
  }

  getUsuariosByTipo(tipo: TipoUsuario): Observable<Usuario[]> {
    return this.apiService.get<Usuario[]>(`${this.endpoint}/tipo/${tipo}`);
  }

  createUsuario(usuario: Usuario): Observable<Usuario> {
    return this.apiService.post<Usuario>(this.endpoint, usuario);
  }

  updateUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    return this.apiService.put<Usuario>(`${this.endpoint}/${id}`, usuario);
  }

  deleteUsuario(id: number): Observable<void> {
    return this.apiService.delete<void>(`${this.endpoint}/${id}`);
  }
}