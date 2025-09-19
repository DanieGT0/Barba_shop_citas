import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Barbero } from '../../shared/models/barbero.model';

@Injectable({
  providedIn: 'root'
})
export class BarberoService {
  private endpoint = '/barberos';

  constructor(private apiService: ApiService) {}

  getAllBarberos(): Observable<Barbero[]> {
    return this.apiService.get<Barbero[]>(`${this.endpoint}/public`);
  }

  getBarberosDisponibles(): Observable<Barbero[]> {
    return this.apiService.get<Barbero[]>(`${this.endpoint}/public/disponibles`);
  }

  getBarberoById(id: number): Observable<Barbero> {
    return this.apiService.get<Barbero>(`${this.endpoint}/public/${id}`);
  }

  getBarberoByUsuarioId(usuarioId: number): Observable<Barbero> {
    return this.apiService.get<Barbero>(`${this.endpoint}/usuario/${usuarioId}`);
  }

  getBarberosByHorario(horarioInicio: string, horarioFin: string): Observable<Barbero[]> {
    return this.apiService.get<Barbero[]>(`${this.endpoint}/public/horario`, {
      horarioInicio,
      horarioFin
    });
  }

  getBarberosByEspecialidad(corteId: number): Observable<Barbero[]> {
    return this.apiService.get<Barbero[]>(`${this.endpoint}/public/especialidad/${corteId}`);
  }

  createBarbero(barbero: Barbero, usuarioId: number): Observable<Barbero> {
    return this.apiService.post<Barbero>(`${this.endpoint}/manage?usuarioId=${usuarioId}`, barbero);
  }

  updateBarbero(id: number, barbero: Barbero): Observable<Barbero> {
    return this.apiService.put<Barbero>(`${this.endpoint}/manage/${id}`, barbero);
  }

  deleteBarbero(id: number): Observable<void> {
    return this.apiService.delete<void>(`${this.endpoint}/manage/${id}`);
  }
}