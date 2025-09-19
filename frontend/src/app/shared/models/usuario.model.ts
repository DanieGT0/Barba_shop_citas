export interface Usuario {
  id?: number;
  username: string;
  email: string;
  nombre: string;
  apellido: string;
  telefono?: string;
  tipo: TipoUsuario;
  fechaRegistro?: Date;
  activo?: boolean;
}

export enum TipoUsuario {
  CLIENTE = 'CLIENTE',
  BARBERO = 'BARBERO',
  ADMIN = 'ADMIN'
}