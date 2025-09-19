# 🔐 SEGURIDAD - Configuración para GitHub

## ⚠️ **IMPORTANTE: Antes de subir a GitHub**

### 🚨 **NUNCA subas estos archivos:**
- ❌ `.env` (contiene credenciales reales)
- ❌ `application-secrets.properties`
- ❌ Cualquier archivo con passwords/tokens

### ✅ **SÍ puedes subir:**
- ✅ `.env.example` (sin credenciales reales)
- ✅ `application-docker.properties` (solo configuración)
- ✅ Todos los Dockerfiles y scripts

---

## 🔧 **Para tus compañeros de equipo:**

### **Paso 1: Clonar repositorio**
```bash
git clone https://github.com/TU-USUARIO/barber-app-spring-angular.git
cd barber-app-spring-angular
```

### **Paso 2: Configurar credenciales**
```bash
# Copiar archivo de ejemplo
cp .env.example .env

# Editar con credenciales reales
# Cada desarrollador pone sus propias credenciales OAuth2
```

### **Paso 3: Ejecutar**
```bash
# Windows
start-barberapp.bat

# Linux/Mac
./start-barberapp.sh
```

---

## 🔑 **Credenciales OAuth2 por desarrollador**

Cada miembro del equipo debe:

1. **Crear sus propias credenciales** Google/GitHub OAuth2
2. **Configurar su propio .env** (nunca compartir)
3. **Usar las mismas URLs de callback**:
   - Google: `http://localhost:8080/login/oauth2/code/google`
   - GitHub: `http://localhost:8080/login/oauth2/code/github`

---

## 🚀 **Para producción:**

### **Variables de entorno del servidor:**
```bash
# En tu servidor de producción (Render, Heroku, etc.)
GOOGLE_CLIENT_ID=credenciales-de-produccion
GOOGLE_CLIENT_SECRET=secreto-de-produccion
GITHUB_CLIENT_ID=credenciales-de-produccion
GITHUB_CLIENT_SECRET=secreto-de-produccion
```

### **URLs de producción:**
- Callback Google: `https://tu-app.com/login/oauth2/code/google`
- Callback GitHub: `https://tu-app.com/login/oauth2/code/github`

---

## ✅ **Lista de verificación antes de GitHub:**

- [ ] Archivo `.gitignore` creado
- [ ] Archivo `.env` NO está en el repositorio
- [ ] Solo `.env.example` está incluido
- [ ] README principal actualizado
- [ ] Documentación Docker completa
- [ ] Scripts tienen permisos correctos

---

## 🎯 **Estructura final en GitHub:**

```
📁 barber-app-spring-angular/
├── 📋 README.md
├── 📋 DOCKER-README.md
├── 📋 SECURITY-README.md
├── 🔒 .gitignore
├── 🔧 .env.example (SIN credenciales reales)
├── 🐳 docker-compose.yml
├── 🚀 start-barberapp.sh/.bat
├── 🛑 stop-barberapp.sh/.bat
├── 📁 backend/ (con Dockerfile)
├── 📁 frontend/ (con Dockerfile)
└── 📁 database/ (con Dockerfile)
```

**❌ NO incluir:**
- `.env` (credenciales reales)
- `node_modules/`
- `target/`
- `*.log`