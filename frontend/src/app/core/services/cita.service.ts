import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Cita } from '../../shared/models/cita.model';

@Injectable({
  providedIn: 'root'
})
export class CitaService {
  private endpoint = '/citas';

  constructor(private apiService: ApiService) {}

  getAllCitas(): Observable<Cita[]> {
    return this.apiService.get<Cita[]>(this.endpoint);
  }

  getCitaById(id: number): Observable<Cita> {
    return this.apiService.get<Cita>(`${this.endpoint}/${id}`);
  }

  getCitasByCliente(clienteId: number): Observable<Cita[]> {
    return this.apiService.get<Cita[]>(`${this.endpoint}/cliente/${clienteId}`);
  }

  getCitasByBarbero(barberoId: number): Observable<Cita[]> {
    return this.apiService.get<Cita[]>(`${this.endpoint}/barbero/${barberoId}`);
  }

  getUpcomingCitasByCliente(clienteId: number): Observable<Cita[]> {
    return this.apiService.get<Cita[]>(`${this.endpoint}/cliente/${clienteId}/proximas`);
  }

  getUpcomingCitasByBarbero(barberoId: number): Observable<Cita[]> {
    return this.apiService.get<Cita[]>(`${this.endpoint}/barbero/${barberoId}/proximas`);
  }

  getCitasByBarberoAndDateRange(barberoId: number, fechaInicio: Date, fechaFin: Date): Observable<Cita[]> {
    return this.apiService.get<Cita[]>(`${this.endpoint}/barbero/${barberoId}/rango`, {
      fechaInicio: fechaInicio.toISOString(),
      fechaFin: fechaFin.toISOString()
    });
  }

  createCita(cita: Cita): Observable<Cita> {
    return this.apiService.post<Cita>(this.endpoint, {
      ...cita,
      fechaHora: cita.fechaHora.toISOString()
    });
  }

  updateCita(id: number, cita: Partial<Cita>): Observable<Cita> {
    const updateData = { ...cita };
    if (updateData.fechaHora) {
      updateData.fechaHora = updateData.fechaHora.toISOString() as any;
    }
    return this.apiService.put<Cita>(`${this.endpoint}/${id}`, updateData);
  }

  deleteCita(id: number): Observable<void> {
    return this.apiService.delete<void>(`${this.endpoint}/${id}`);
  }
}