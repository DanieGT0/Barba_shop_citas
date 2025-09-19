# 🐳 BarberApp - Setup Docker Completo

## 🎯 **Inicio Rápido para tus Compañeros**

### ⚡ **Una sola línea para iniciar todo:**

**Windows:**
```bash
start-barberapp.bat
```

**Linux/Mac:**
```bash
chmod +x start-barberapp.sh && ./start-barberapp.sh
```

### 🌐 **URLs de acceso:**
- **Frontend**: http://localhost:4200
- **Backend**: http://localhost:8080
- **Base de datos**: localhost:5433

---

## 📋 **Requisitos Previos**

### ✅ **Solo necesitas:**
- **Docker** (versión 20.10+)
- **Docker Compose** (versión 2.0+)

### 🔧 **Verificar instalación:**
```bash
docker --version
docker-compose --version  # o docker compose version
```

---

## 🏗️ **Arquitectura del Stack**

```
┌─────────────────────────────────────────────────┐
│                BarberApp Stack                  │
├─────────────────────────────────────────────────┤
│  🌐 Frontend (Angular + Nginx)  :4200 → :80    │
│  ⬇️ API calls                                   │
│  ☕ Backend (Spring Boot + Java 17)  :8080     │
│  ⬇️ Database connection                         │
│  🐘 PostgreSQL 16  :5433                       │
│  📁 Volúmenes persistentes                     │
└─────────────────────────────────────────────────┘
```

## 🚀 **Guía de Uso Completa**

### 1️⃣ **Primer arranque:**
```bash
# Clonar/descargar el proyecto
cd "Spring Boot + Angular"

# Configurar variables de entorno (opcional)
cp .env.example .env
# Editar .env con tus credenciales OAuth2 reales

# Iniciar todo el stack
./start-barberapp.sh    # Linux/Mac
start-barberapp.bat     # Windows
```

### 2️⃣ **Ver estado de servicios:**
```bash
docker-compose ps
# o
docker compose ps
```

### 3️⃣ **Ver logs en tiempo real:**
```bash
# Todos los servicios
docker-compose logs -f

# Servicio específico
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres-db
```

### 4️⃣ **Detener servicios:**
```bash
# Opción 1: Script interactivo
./stop-barberapp.sh     # Linux/Mac
stop-barberapp.bat      # Windows

# Opción 2: Docker Compose directo
docker-compose stop     # Solo detener
docker-compose down     # Detener y eliminar contenedores
docker-compose down -v  # Detener y eliminar TODOS los datos
```

---

## 🔧 **Servicios y Configuración**

### 🌐 **Frontend (Angular + Nginx)**
- **Puerto**: 4200 → 80 (interno)
- **Tecnologías**: Node.js 18, Angular CLI 17, Nginx
- **Health check**: http://localhost:4200/health

### ☕ **Backend (Spring Boot)**
- **Puerto**: 8080
- **Tecnologías**: Java 17, Maven, Spring Boot 3.2
- **Health check**: http://localhost:8080/actuator/health
- **Profile**: `docker` (automático)

### 🐘 **Base de Datos (PostgreSQL 16)**
- **Puerto**: 5433 ⚠️ (NO 5432 para evitar conflictos)
- **Credenciales**:
  - Host: `localhost`
  - Puerto: `5433`
  - DB: `barberapp`
  - Usuario: `barberapp_user`
  - Contraseña: `barberapp_password`

---

## 📦 **Volúmenes Persistentes**

Los datos se mantienen entre reinicios gracias a volúmenes Docker:

- `barberapp-postgres-data` - Datos de PostgreSQL
- `barberapp-postgres-logs` - Logs de PostgreSQL

### 📊 **Gestionar volúmenes:**
```bash
# Ver volúmenes
docker volume ls | grep barberapp

# Backup de datos
docker exec barberapp-postgres pg_dump -U barberapp_user -d barberapp > backup.sql

# Eliminar volúmenes (¡CUIDADO!)
docker volume rm barberapp-postgres-data barberapp-postgres-logs
```

---

## 🔐 **Variables de Entorno**

### ⚙️ **Configuración OAuth2:**
Edita el archivo `.env` para configurar OAuth2:

```bash
# Google OAuth2
GOOGLE_CLIENT_ID=tu-google-client-id
GOOGLE_CLIENT_SECRET=tu-google-client-secret

# GitHub OAuth2
GITHUB_CLIENT_ID=tu-github-client-id
GITHUB_CLIENT_SECRET=tu-github-client-secret
```

### 🔑 **Obtener credenciales OAuth2:**

**Google:**
1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un proyecto o selecciona uno existente
3. Habilita Google+ API
4. Crea credenciales OAuth 2.0
5. Agrega `http://localhost:8080/login/oauth2/code/google` como redirect URI

**GitHub:**
1. Ve a GitHub Settings > Developer settings > OAuth Apps
2. Crea una nueva OAuth App
3. Agrega `http://localhost:8080/login/oauth2/code/github` como redirect URI

---

## 🛠️ **Comandos Útiles**

### 🔍 **Desarrollo y Debug:**
```bash
# Reconstruir servicios
docker-compose up --build -d

# Ejecutar comando en contenedor
docker exec -it barberapp-backend /bin/bash
docker exec -it barberapp-frontend /bin/sh
docker exec -it barberapp-postgres psql -U barberapp_user -d barberapp

# Ver recursos del sistema
docker stats

# Limpiar sistema Docker
docker system prune -a
```

### 📊 **Monitoreo:**
```bash
# Estado de contenedores
docker ps -a --filter "name=barberapp"

# Uso de recursos
docker stats barberapp-backend barberapp-frontend barberapp-postgres

# Logs con timestamp
docker-compose logs -f -t
```

---

## 🚨 **Solución de Problemas**

### ❌ **Errores comunes:**

**Puerto ya en uso:**
```bash
# Verificar qué está usando el puerto
netstat -tulpn | grep :4200
netstat -tulpn | grep :8080
netstat -tulpn | grep :5433

# Detener servicios conflictivos
sudo systemctl stop postgresql  # PostgreSQL local
```

**Problemas de construcción:**
```bash
# Limpiar cache de Docker
docker builder prune -a

# Reconstruir desde cero
docker-compose down -v
docker-compose up --build --force-recreate
```

**Base de datos no conecta:**
```bash
# Verificar logs de PostgreSQL
docker logs barberapp-postgres

# Conectar manualmente
docker exec -it barberapp-postgres psql -U barberapp_user -d barberapp
```

**Frontend no carga:**
```bash
# Verificar logs de Nginx
docker logs barberapp-frontend

# Verificar configuración
docker exec barberapp-frontend cat /etc/nginx/nginx.conf
```

---

## 📈 **Rendimiento y Optimización**

### ⚡ **Configuración recomendada:**
- **RAM**: 4GB mínimo (8GB recomendado)
- **CPU**: 2 cores mínimo
- **Disco**: 5GB libres

### 🎯 **Configuraciones por entorno:**

**Desarrollo:**
```bash
# Usar configuración actual (optimizada para desarrollo)
docker-compose up -d
```

**Producción:**
```bash
# Variables de entorno para producción
export JAVA_OPTS="-Xmx1g -Xms512m"
export POSTGRES_PASSWORD="contraseña_super_segura"
docker-compose up -d
```

---

## 📚 **Estructura de Archivos Docker**

```
📁 Spring Boot + Angular/
├── 🐳 docker-compose.yml          # Orquestación completa
├── 🔧 .env.example                # Variables de entorno
├── 🚀 start-barberapp.sh/.bat     # Scripts de inicio
├── 🛑 stop-barberapp.sh/.bat      # Scripts de parada
│
├── 📁 backend/
│   ├── 🐳 Dockerfile
│   ├── 🔧 .dockerignore
│   ├── 🚀 docker-build.sh/.bat
│   └── 📋 README-Docker.md
│
├── 📁 frontend/
│   ├── 🐳 Dockerfile
│   ├── 🌐 nginx.conf
│   ├── 🔧 .dockerignore
│   ├── 🚀 docker-build.sh/.bat
│   └── 📋 README-Docker.md
│
└── 📁 database/
    ├── 🐳 Dockerfile
    ├── ⚙️ postgresql.conf
    ├── 🔐 pg_hba.conf
    ├── 🛠️ db-utils.sh
    ├── 🚀 docker-build.sh/.bat
    ├── 📁 init-scripts/
    └── 📋 README-Docker.md
```

---

## 🎯 **Casos de Uso**

### 👥 **Para compañeros de equipo:**
```bash
# 1. Clonar repositorio
git clone <repo-url>
cd "Spring Boot + Angular"

# 2. Iniciar stack completo
./start-barberapp.sh

# 3. Abrir navegador en http://localhost:4200
# ¡Listo para desarrollar!
```

### 🔄 **Para desarrollo continuo:**
```bash
# Desarrollo del día a día
docker-compose logs -f backend    # Ver logs del backend
docker-compose restart backend    # Reiniciar solo backend
docker-compose up --build -d      # Reconstruir con cambios
```

### 🚀 **Para demos y presentaciones:**
```bash
# Inicio limpio
./start-barberapp.sh --clean

# URLs para demo:
# - Frontend: http://localhost:4200
# - API Health: http://localhost:8080/actuator/health
```

---

## ✅ **Lista de Verificación**

### 🎯 **Antes de compartir con compañeros:**
- [ ] Docker y Docker Compose instalados
- [ ] Puerto 4200, 8080 y 5433 disponibles
- [ ] Archivo `.env` configurado (opcional para desarrollo)
- [ ] Scripts tienen permisos de ejecución (Linux/Mac)

### 🚀 **Para producción:**
- [ ] Variables de entorno seguras configuradas
- [ ] OAuth2 credenciales de producción
- [ ] Backup de datos configurado
- [ ] Monitoreo de logs activado
- [ ] Recursos del servidor adecuados

---

## 🎉 **¡Listo!**

Tu equipo ahora puede ejecutar **BarberApp completo** con un solo comando:

```bash
./start-barberapp.sh    # Linux/Mac
start-barberapp.bat     # Windows
```

### 🌟 **Beneficios del setup Docker:**
- ✅ **Sin conflictos** de versiones
- ✅ **Mismo entorno** para todo el equipo
- ✅ **Inicio en menos de 2 minutos**
- ✅ **Datos persistentes** entre reinicios
- ✅ **Fácil limpieza** y reset
- ✅ **Lista para producción**

---

### 🤝 **¿Necesitas ayuda?**
- 📖 Ver documentación específica en cada carpeta (README-Docker.md)
- 🐛 Revisar logs con `docker-compose logs -f`
- 🔧 Usar scripts de utilidades incluidos