# 🐳 Docker Setup - PostgreSQL 16 con Puerto 5433 y Volúmenes Persistentes

## ✅ **Componentes Incluidos**

- **PostgreSQL 16** (Alpine Linux)
- **Puerto personalizado 5433** (evita conflicto con PostgreSQL local)
- **Volúmenes persistentes** para datos y logs
- **Configuración optimizada** para desarrollo
- **Scripts de inicialización** automáticos
- **Utilidades de gestión** completas

## 📦 **Estructura Docker**

```
database/
├── Dockerfile                     # Imagen PostgreSQL 16
├── .dockerignore                 # Archivos a ignorar
├── postgresql.conf               # Configuración optimizada
├── pg_hba.conf                   # Configuración de autenticación
├── docker-entrypoint.sh          # Script de inicio personalizado
├── docker-build.sh               # Script construcción Linux/Mac
├── docker-build.bat              # Script construcción Windows
├── db-utils.sh                   # Utilidades de gestión
└── init-scripts/
    ├── 01-create-database.sql    # Inicialización DB
    └── 02-insert-sample-data.sql # Datos de ejemplo
```

## 🚀 **Construcción Rápida**

### Windows:
```bash
docker-build.bat
```

### Linux/Mac:
```bash
chmod +x docker-build.sh
./docker-build.sh
```

### Manual:
```bash
docker build -t barberapp-postgres:16 .
```

## 🔧 **Ejecución con Volúmenes Persistentes**

### Ejecución Completa:
```bash
# Crear volúmenes persistentes
docker volume create barberapp-postgres-data
docker volume create barberapp-postgres-logs

# Ejecutar contenedor
docker run -d \
  --name barberapp-postgres-container \
  -p 5433:5433 \
  -v barberapp-postgres-data:/var/lib/postgresql/data \
  -v barberapp-postgres-logs:/var/log/postgresql \
  -e POSTGRES_DB=barberapp \
  -e POSTGRES_USER=barberapp_user \
  -e POSTGRES_PASSWORD=barberapp_password \
  barberapp-postgres:16
```

### Variables de Entorno:
```bash
docker run -d \
  --name barberapp-postgres-container \
  -p 5433:5433 \
  -v barberapp-postgres-data:/var/lib/postgresql/data \
  -e POSTGRES_DB=mi_base_datos \
  -e POSTGRES_USER=mi_usuario \
  -e POSTGRES_PASSWORD=mi_password_seguro \
  barberapp-postgres:16
```

## 🔗 **Conexión a la Base de Datos**

### Datos de Conexión:
- **Host**: `localhost`
- **Puerto**: `5433` ⚠️ (NO 5432)
- **Base de datos**: `barberapp`
- **Usuario**: `barberapp_user`
- **Contraseña**: `barberapp_password`

### Conexión desde aplicaciones:
```properties
# Spring Boot
spring.datasource.url=jdbc:postgresql://localhost:5433/barberapp
spring.datasource.username=barberapp_user
spring.datasource.password=barberapp_password
```

### Conexión directa:
```bash
# Desde el host
psql -h localhost -p 5433 -U barberapp_user -d barberapp

# Desde el contenedor
docker exec -it barberapp-postgres-container psql -U barberapp_user -d barberapp
```

## 🛠️ **Utilidades de Gestión**

El script `db-utils.sh` proporciona comandos útiles:

```bash
# Hacer ejecutable (Linux/Mac)
chmod +x db-utils.sh

# Comandos disponibles
./db-utils.sh start      # Iniciar PostgreSQL
./db-utils.sh stop       # Detener PostgreSQL
./db-utils.sh restart    # Reiniciar PostgreSQL
./db-utils.sh status     # Ver estado
./db-utils.sh logs       # Ver logs en tiempo real
./db-utils.sh connect    # Conectar a la DB
./db-utils.sh backup     # Crear backup
./db-utils.sh restore archivo.sql  # Restaurar backup
./db-utils.sh clean      # Limpiar datos (¡CUIDADO!)
./db-utils.sh psql       # Modo psql interactivo
```

## 📊 **Health Check**

El contenedor incluye health checks automáticos:

```bash
# Verificar salud del contenedor
docker exec barberapp-postgres-container pg_isready -p 5433 -h localhost -U barberapp_user

# Ver logs de health check
docker logs barberapp-postgres-container
```

## 💾 **Gestión de Volúmenes Persistentes**

### Volúmenes Creados:
- `barberapp-postgres-data` - Datos de la base de datos
- `barberapp-postgres-logs` - Logs de PostgreSQL

### Comandos de Volúmenes:
```bash
# Listar volúmenes
docker volume ls | grep barberapp

# Inspeccionar volumen
docker volume inspect barberapp-postgres-data

# Backup de volumen completo
docker run --rm -v barberapp-postgres-data:/data -v $(pwd):/backup alpine tar czf /backup/postgres-data-backup.tar.gz -C /data .

# Restaurar volumen completo
docker run --rm -v barberapp-postgres-data:/data -v $(pwd):/backup alpine tar xzf /backup/postgres-data-backup.tar.gz -C /data

# Limpiar volúmenes (¡CUIDADO!)
docker volume rm barberapp-postgres-data barberapp-postgres-logs
```

## 🔍 **Comandos Útiles**

```bash
# Ver logs en tiempo real
docker logs -f barberapp-postgres-container

# Acceder al contenedor
docker exec -it barberapp-postgres-container /bin/bash

# Ver información del contenedor
docker inspect barberapp-postgres-container

# Ver estadísticas de uso
docker stats barberapp-postgres-container

# Detener el contenedor
docker stop barberapp-postgres-container

# Eliminar el contenedor (los datos persisten)
docker rm barberapp-postgres-container

# Eliminar la imagen
docker rmi barberapp-postgres:16
```

## 📋 **Operaciones de Base de Datos**

### Backup y Restore:
```bash
# Crear backup
docker exec barberapp-postgres-container pg_dump -U barberapp_user -d barberapp > backup_$(date +%Y%m%d).sql

# Restaurar backup
docker exec -i barberapp-postgres-container psql -U barberapp_user -d barberapp < backup_20241201.sql

# Backup comprimido
docker exec barberapp-postgres-container pg_dump -U barberapp_user -d barberapp | gzip > backup_$(date +%Y%m%d).sql.gz

# Restaurar backup comprimido
gunzip -c backup_20241201.sql.gz | docker exec -i barberapp-postgres-container psql -U barberapp_user -d barberapp
```

### Consultas útiles:
```sql
-- Verificar conexiones activas
SELECT * FROM pg_stat_activity WHERE datname = 'barberapp';

-- Ver tamaño de la base de datos
SELECT pg_size_pretty(pg_database_size('barberapp'));

-- Ver tablas y sus tamaños
SELECT tablename, pg_size_pretty(pg_total_relation_size(tablename::text)) as size
FROM pg_tables WHERE schemaname = 'public';
```

## ⚙️ **Configuración Optimizada**

### Características incluidas:
- ✅ **Puerto personalizado 5433** (sin conflictos)
- ✅ **Configuración optimizada** para desarrollo
- ✅ **Logs estructurados** con rotación automática
- ✅ **Autovacuum configurado** para rendimiento
- ✅ **Encoding UTF-8** por defecto
- ✅ **Timezone UTC** configurado
- ✅ **Extensiones útiles** (uuid-ossp, pg_trgm)

## 🔒 **Seguridad**

- ✅ Usuario **no-root** para ejecución
- ✅ Configuración **pg_hba.conf** restrictiva
- ✅ **Contraseñas por variables** de entorno
- ✅ **Conexiones limitadas** por IP
- ✅ **Health checks** automáticos

## 📏 **Tamaño y Rendimiento**

- **Imagen base**: PostgreSQL 16 Alpine (~200MB)
- **Final**: ~250MB (con configuraciones)
- **RAM recomendada**: 512MB mínimo
- **Disco**: Según datos + logs

## 🚦 **Estados del Contenedor**

| Estado | Descripción |
|--------|-------------|
| `healthy` | PostgreSQL respondiendo en puerto 5433 |
| `unhealthy` | Falló pg_isready |
| `starting` | Iniciando (período gracia: 10s) |

---

## 🎯 **Ventajas de esta Configuración**

### ✅ **Sin Conflictos**:
- Puerto 5433 evita conflicto con PostgreSQL local
- Volúmenes persistentes mantienen datos entre reinicios

### ✅ **Fácil Gestión**:
- Scripts automatizados para construcción
- Utilidades completas para operaciones diarias
- Health checks automáticos

### ✅ **Optimizado**:
- Configuración PostgreSQL tuneada
- Logs estructurados con rotación
- Extensiones útiles preinstaladas

---

✅ **Docker para PostgreSQL 16 con puerto 5433 y volúmenes persistentes completado!**

¿Continuamos con Maven 3.6+?