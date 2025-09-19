import { Usuario } from './usuario.model';
import { Barbero } from './barbero.model';
import { Corte } from './corte.model';

export interface Cita {
  id?: number;
  clienteId: number;
  barberoId: number;
  corteId: number;
  fechaHora: Date;
  estado?: EstadoCita;
  precioFinal?: number;
  notas?: string;
  fechaCreacion?: Date;
  fechaActualizacion?: Date;

  // For detailed responses
  cliente?: Usuario;
  barbero?: Barbero;
  corte?: Corte;
}

export enum EstadoCita {
  PENDIENTE = 'PENDIENTE',
  CONFIRMADA = 'CONFIRMADA',
  EN_PROGRESO = 'EN_PROGRESO',
  COMPLETADA = 'COMPLETADA',
  CANCELADA = 'CANCELADA'
}