export interface Corte {
  id?: number;
  nombre: string;
  descripcion?: string;
  precioEstimado?: number;
  duracionMinutos?: number;
  urlImagen?: string;
  tipo: TipoCorte;
}

export enum TipoCorte {
  CLASICO = 'CLASICO',
  MODERNO = 'MODERNO',
  BARBA = 'BARBA',
  BIGOTE = 'BIGOTE',
  COMPLETO = 'COMPLETO'
}