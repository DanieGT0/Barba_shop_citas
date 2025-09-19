# 🪒 Barba Shop - Sistema de Citas para Barbería

Una aplicación web completa desarrollada con **Spring Boot** y **Angular** que permite a los usuarios explorar estilos de cortes de cabello, ver perfiles de barberos, y agendar citas con autenticación OAuth2.

## 🚀 Inicio Rápido (1 comando)

### **Windows:**
```bash
start-barberapp.bat
```

### **Linux/Mac:**
```bash
chmod +x start-barberapp.sh && ./start-barberapp.sh
```

### **URLs de acceso:**
- 🌐 **Frontend**: http://localhost:4200
- ⚙️ **Backend**: http://localhost:8080
- 🗄️ **Database**: localhost:5433

---

## 📋 Requisitos

Solo necesitas tener instalado:
- **Docker** (versión 20.10+)
- **Docker Compose** (versión 2.0+)

```bash
# Verificar instalación
docker --version
docker-compose --version
```

---

## ⚙️ Configuración Inicial

### 1. Clonar repositorio
```bash
git clone https://github.com/Developer545/Barba_Shop-.git
cd Barba_Shop-
```

### 2. Configurar variables de entorno
```bash
# Copiar archivo de ejemplo
cp .env.example .env

# Editar .env con tus credenciales OAuth2 (opcional para desarrollo)
```

### 3. Iniciar aplicación
```bash
# Windows
start-barberapp.bat

# Linux/Mac
./start-barberapp.sh
```

**¡Listo!** 🎉 La aplicación estará disponible en http://localhost:4200

---

## 🏗️ Arquitectura

```
🐳 Barba Shop Stack
├── 🌐 Frontend (Angular 17 + Nginx) → Puerto 4200
├── ☕ Backend (Spring Boot + Java 17) → Puerto 8080
├── 🐘 PostgreSQL 16 → Puerto 5433
└── 📁 Volúmenes persistentes
```

### Tecnologías:
- **Frontend**: Angular 17, Node.js 18, Material Design
- **Backend**: Spring Boot 3.2, Java 17, Maven
- **Database**: PostgreSQL 16
- **Infraestructura**: Docker, Docker Compose

---

## 📚 Documentación

- 📖 [Guía completa de Docker](DOCKER-README.md)
- 🔐 [Configuración de seguridad](SECURITY-README.md)
- 🏗️ [Documentación técnica](README-original.md)

---

## 🔐 Configuración OAuth2 (Opcional)

Para autenticación completa, configura credenciales OAuth2:

### Google OAuth2:
1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea proyecto y habilita Google+ API
3. Crea credenciales OAuth 2.0
4. Callback URL: `http://localhost:8080/login/oauth2/code/google`

### GitHub OAuth2:
1. Ve a GitHub Settings → Developer settings → OAuth Apps
2. Crea nueva OAuth App
3. Callback URL: `http://localhost:8080/login/oauth2/code/github`

Agrega las credenciales al archivo `.env`

---

## 🛠️ Comandos Útiles

```bash
# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose stop

# Limpiar todo
./stop-barberapp.sh clean

# Reconstruir
docker-compose up --build -d
```

---

## 🤝 Contribuir

1. Fork el proyecto
2. Crea tu rama: `git checkout -b feature/nueva-funcionalidad`
3. Commit: `git commit -m 'Agregar nueva funcionalidad'`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Abre un Pull Request

---

## 👥 Equipo

- **Desarrollo**: Arquitectura completa Spring Boot + Angular
- **Infraestructura**: Docker multi-container con volúmenes persistentes
- **Frontend**: Angular Material Design responsivo
- **Backend**: REST API con Spring Security OAuth2

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.

---

## 🎯 Características

### ✨ Funcionalidades
- 🔐 Autenticación OAuth2 (Google, GitHub)
- 💇‍♂️ Galería de cortes con filtros
- 👨‍💼 Perfiles de barberos
- 📅 Sistema de citas
- 📱 Diseño responsive

### 🚀 DevOps
- 🐳 Dockerización completa
- 📦 Volúmenes persistentes
- 🔄 Health checks automáticos
- 📊 Logs centralizados
- ⚡ Inicio en 1 comando

---

**¡Desarrollado con ❤️ para revolucionar las barberías!** 🪒✨