import { Usuario } from './usuario.model';
import { Corte } from './corte.model';

export interface Barbero {
  id?: number;
  usuario: Usuario;
  descripcion?: string;
  anosExperiencia?: number;
  precioBase?: number;
  horarioInicio?: string;
  horarioFin?: string;
  urlFotoPerfil?: string;
  disponible?: boolean;
  especialidades?: Corte[];
}