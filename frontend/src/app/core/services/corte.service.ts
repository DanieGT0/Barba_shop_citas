import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Corte, TipoCorte } from '../../shared/models/corte.model';

@Injectable({
  providedIn: 'root'
})
export class CorteService {
  private endpoint = '/cortes';

  constructor(private apiService: ApiService) {}

  getAllCortes(): Observable<Corte[]> {
    return this.apiService.get<Corte[]>(this.endpoint);
  }

  getCorteById(id: number): Observable<Corte> {
    return this.apiService.get<Corte>(`${this.endpoint}/${id}`);
  }

  getCortesByTipo(tipo: TipoCorte): Observable<Corte[]> {
    return this.apiService.get<Corte[]>(`${this.endpoint}/tipo/${tipo}`);
  }

  getCortesByPriceRange(precioMin?: number, precioMax?: number): Observable<Corte[]> {
    return this.apiService.get<Corte[]>(`${this.endpoint}/precio`, {
      precioMin,
      precioMax
    });
  }

  getCortesByDurationRange(duracionMin?: number, duracionMax?: number): Observable<Corte[]> {
    return this.apiService.get<Corte[]>(`${this.endpoint}/duracion`, {
      duracionMin,
      duracionMax
    });
  }

  getCortesByBarbero(barberoId: number): Observable<Corte[]> {
    return this.apiService.get<Corte[]>(`${this.endpoint}/barbero/${barberoId}`);
  }

  createCorte(corte: Corte): Observable<Corte> {
    return this.apiService.post<Corte>(this.endpoint, corte);
  }

  updateCorte(id: number, corte: Corte): Observable<Corte> {
    return this.apiService.put<Corte>(`${this.endpoint}/${id}`, corte);
  }

  deleteCorte(id: number): Observable<void> {
    return this.apiService.delete<void>(`${this.endpoint}/${id}`);
  }
}