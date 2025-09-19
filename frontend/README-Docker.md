# 🐳 Docker Setup - Angular Frontend (Node.js 18+)

## ✅ **Componentes Incluidos**

- **Node.js 18** (Alpine Linux)
- **Angular CLI 17+**
- **Nginx** (servidor web optimizado)
- **Multi-stage build** (optimización de tamaño)

## 📦 **Estructura Docker**

```
frontend/
├── Dockerfile                     # Imagen principal
├── .dockerignore                 # Archivos a ignorar
├── nginx.conf                    # Configuración Nginx optimizada
├── docker-entrypoint.sh         # Script de inicio
├── docker-build.sh              # Script Linux/Mac
├── docker-build.bat             # Script Windows
└── src/environments/
    └── environment.docker.ts     # Config específica Docker
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
docker build -t barberapp-frontend:latest .
```

## 🔧 **Ejecución**

### Ejecución Básica:
```bash
docker run -d \
  --name barberapp-frontend \
  -p 4200:80 \
  barberapp-frontend:latest
```

### Con Variables de Entorno:
```bash
docker run -d \
  --name barberapp-frontend \
  -p 4200:80 \
  -e API_URL=http://localhost:8080/api \
  barberapp-frontend:latest
```

### Modo Desarrollo (con hot-reload):
```bash
docker run -d \
  --name barberapp-frontend-dev \
  -p 4200:4200 \
  -v $(pwd)/src:/app/src \
  barberapp-frontend:latest \
  ng serve --host 0.0.0.0
```

## 🌐 **Acceso a la Aplicación**

- **Producción**: http://localhost:4200
- **Health Check**: http://localhost:4200/health
- **Desarrollo**: http://localhost:4200 (con hot-reload)

## 📊 **Health Check**

El contenedor incluye health checks automáticos:

```bash
# Verificar salud del contenedor
docker exec barberapp-frontend curl -f http://localhost:80/health

# Ver logs de health check
docker logs barberapp-frontend
```

## 🔍 **Comandos Útiles**

```bash
# Ver logs en tiempo real
docker logs -f barberapp-frontend

# Acceder al contenedor
docker exec -it barberapp-frontend /bin/sh

# Ver información del contenedor
docker inspect barberapp-frontend

# Detener el contenedor
docker stop barberapp-frontend

# Eliminar el contenedor
docker rm barberapp-frontend

# Eliminar la imagen
docker rmi barberapp-frontend:latest
```

## ⚙️ **Configuración Nginx**

### Características Incluidas:

- ✅ **SPA Routing** - Todas las rutas van a index.html
- ✅ **Gzip Compression** - Reducción de tamaño
- ✅ **Cache Headers** - Optimización de rendimiento
- ✅ **Security Headers** - Protección XSS, CSRF
- ✅ **Proxy API** - Redirige /api/ al backend
- ✅ **Health Endpoint** - /health para monitoreo

### Puertos y Proxy:

- Puerto **80** - Aplicación Angular
- Proxy **/api/** → **backend:8080/api/**

## 🔒 **Seguridad**

- ✅ Ejecuta como usuario **no-root** (`nginx-custom`)
- ✅ Imagen basada en **Alpine Linux** (mínima)
- ✅ Headers de seguridad configurados
- ✅ Health checks automáticos

## 📏 **Tamaño de Imagen**

- **Build Stage**: Node.js 18 Alpine (~150MB)
- **Runtime Stage**: Nginx Alpine (~25MB)
- **Final**: ~50-80MB (incluye app compilada)

## 🚦 **Estados del Contenedor**

| Estado | Descripción |
|--------|-------------|
| `healthy` | Nginx sirviendo correctamente |
| `unhealthy` | Falló el health check HTTP |
| `starting` | Iniciando (período de gracia: 5s) |

## ⚡ **Optimizaciones**

- ✅ **Multi-stage build** (Node.js + Nginx)
- ✅ **Nginx optimizado** para SPA
- ✅ **Gzip compression** automática
- ✅ **Cache headers** configurados
- ✅ **.dockerignore** optimizado
- ✅ **Health checks** configurados
- ✅ **Configuración Docker** específica

## 🎯 **Configuraciones de Build**

| Configuración | Uso | Optimización |
|---------------|-----|-------------|
| `development` | Desarrollo local | No optimizada |
| `production` | Producción general | Optimizada |
| `docker` | Contenedores Docker | Ultra optimizada |

## 🔧 **Variables de Entorno**

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `API_URL` | URL del backend | `http://localhost:8080/api` |
| `NODE_ENV` | Entorno de ejecución | `production` |

---

✅ **Docker para Node.js 18+ y Angular CLI 17+ completado!**

¿Continuamos con PostgreSQL 12+?