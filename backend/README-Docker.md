# 🐳 Docker Setup - Spring Boot Backend (Java 17)

## ✅ **Componentes Incluidos**

- **Java 17** (OpenJDK)
- **Maven 3.9+** (para construcción)
- **Spring Boot 3.2.0**
- **Spring Boot Actuator** (health checks)

## 📦 **Estructura Docker**

```
backend/
├── Dockerfile                  # Imagen principal
├── .dockerignore              # Archivos a ignorar
├── docker-build.sh           # Script Linux/Mac
├── docker-build.bat          # Script Windows
└── src/main/resources/
    └── application-docker.properties  # Config Docker
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
docker build -t barberapp-backend:latest .
```

## 🔧 **Ejecución**

### Ejecución Básica:
```bash
docker run -d \
  --name barberapp-backend \
  -p 8080:8080 \
  barberapp-backend:latest
```

### Con Variables de Entorno:
```bash
docker run -d \
  --name barberapp-backend \
  -p 8080:8080 \
  -e GOOGLE_CLIENT_ID=tu_google_client_id \
  -e GOOGLE_CLIENT_SECRET=tu_google_client_secret \
  -e GITHUB_CLIENT_ID=tu_github_client_id \
  -e GITHUB_CLIENT_SECRET=tu_github_client_secret \
  barberapp-backend:latest
```

## 📊 **Health Check**

El contenedor incluye health checks automáticos:

```bash
# Verificar salud del contenedor
docker exec barberapp-backend curl -f http://localhost:8080/actuator/health

# Ver logs de health check
docker logs barberapp-backend
```

## 🔍 **Comandos Útiles**

```bash
# Ver logs en tiempo real
docker logs -f barberapp-backend

# Acceder al contenedor
docker exec -it barberapp-backend /bin/bash

# Ver información del contenedor
docker inspect barberapp-backend

# Detener el contenedor
docker stop barberapp-backend

# Eliminar el contenedor
docker rm barberapp-backend

# Eliminar la imagen
docker rmi barberapp-backend:latest
```

## ⚙️ **Configuración**

### Variables de Entorno Disponibles:

- `SPRING_PROFILES_ACTIVE=docker` (por defecto)
- `JAVA_OPTS="-Xmx512m -Xms256m"` (configuración JVM)
- `GOOGLE_CLIENT_ID` (OAuth2 Google)
- `GOOGLE_CLIENT_SECRET` (OAuth2 Google)
- `GITHUB_CLIENT_ID` (OAuth2 GitHub)
- `GITHUB_CLIENT_SECRET` (OAuth2 GitHub)

### Puertos Expuestos:

- `8080` - API REST Spring Boot

## 🔒 **Seguridad**

- ✅ Ejecuta como usuario **no-root** (`spring`)
- ✅ Imagen basada en **OpenJDK Slim**
- ✅ Health checks automáticos
- ✅ Variables de entorno para secretos

## 📏 **Tamaño de Imagen**

- **Base**: OpenJDK 17 Slim (~200MB)
- **Final**: ~300-400MB (incluye app compilada)

## 🚦 **Estados del Contenedor**

| Estado | Descripción |
|--------|-------------|
| `healthy` | Aplicación funcionando correctamente |
| `unhealthy` | Falló el health check |
| `starting` | Iniciando (período de gracia: 5s) |

## ⚡ **Optimizaciones**

- ✅ **Multi-stage build** (reduce tamaño final)
- ✅ **Docker layer caching** (builds más rápidos)
- ✅ **.dockerignore** optimizado
- ✅ **Health checks** configurados
- ✅ **JVM tuning** para contenedores

---

✅ **Docker para Java 17 completado!**

¿Continuamos con Node.js 18+?