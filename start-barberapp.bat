@echo off
REM Script para iniciar BarberApp completo con Docker Compose en Windows

echo 🚀 Iniciando BarberApp - Stack Completo
echo ======================================

REM Verificar dependencias
echo 🔍 Verificando dependencias...

docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker no está instalado. Instala Docker primero.
    exit /b 1
)

docker-compose --version >nul 2>&1
if %errorlevel% neq 0 (
    docker compose version >nul 2>&1
    if %errorlevel% neq 0 (
        echo ❌ Docker Compose no está instalado. Instala Docker Compose primero.
        exit /b 1
    )
    set DOCKER_COMPOSE=docker compose
) else (
    set DOCKER_COMPOSE=docker-compose
)

echo ✅ Docker y Docker Compose están disponibles

REM Verificar archivo .env
if not exist ".env" (
    echo ⚠️ Archivo .env no encontrado
    if exist ".env.example" (
        echo 💡 Creando .env desde .env.example...
        copy .env.example .env >nul
        echo ✅ Archivo .env creado desde .env.example
        echo 🔧 IMPORTANTE: Edita el archivo .env con tus credenciales OAuth2 reales
    ) else (
        echo ❌ No se encontró .env.example
        exit /b 1
    )
) else (
    echo ✅ Archivo .env encontrado
)

REM Limpiar contenedores anteriores si se especifica
if "%1"=="--clean" (
    echo 🧹 Limpiando contenedores anteriores...
    %DOCKER_COMPOSE% down -v 2>nul
    docker system prune -f 2>nul
    echo ✅ Limpieza completada
)

REM Construir e iniciar servicios
echo 🔨 Construyendo y iniciando servicios...
echo 📦 Usando: %DOCKER_COMPOSE%

%DOCKER_COMPOSE% up --build -d

if %errorlevel% equ 0 (
    echo ✅ Servicios iniciados exitosamente

    echo.
    echo 📊 Estado de los servicios:
    echo ==========================
    %DOCKER_COMPOSE% ps

    echo.
    echo 🌐 URLs de acceso:
    echo ==================
    echo    Frontend:  http://localhost:4200
    echo    Backend:   http://localhost:8080
    echo    API Docs:  http://localhost:8080/actuator/health
    echo    Database:  localhost:5433
    echo.
    echo 🔗 Credenciales de base de datos:
    echo =================================
    echo    Host: localhost
    echo    Puerto: 5433
    echo    Base de datos: barberapp
    echo    Usuario: barberapp_user
    echo    Contraseña: barberapp_password
    echo.

    echo 📝 Para ver logs en tiempo real:
    echo ===============================
    echo    Todos los servicios: %DOCKER_COMPOSE% logs -f
    echo    Solo backend:        %DOCKER_COMPOSE% logs -f backend
    echo    Solo frontend:       %DOCKER_COMPOSE% logs -f frontend
    echo    Solo database:       %DOCKER_COMPOSE% logs -f postgres-db
    echo.

    echo 🛠️ Comandos útiles:
    echo ==================
    echo    Detener servicios:     %DOCKER_COMPOSE% stop
    echo    Reiniciar servicios:   %DOCKER_COMPOSE% restart
    echo    Detener y limpiar:     %DOCKER_COMPOSE% down
    echo    Ver logs:              %DOCKER_COMPOSE% logs -f
    echo    Reconstruir:           %DOCKER_COMPOSE% up --build -d
    echo.

    echo 🎉 ¡BarberApp está ejecutándose!
    echo 🌐 Abre http://localhost:4200 en tu navegador
    echo.
    echo ⏹️ Para detener: Ctrl+C o ejecuta '%DOCKER_COMPOSE% down'

) else (
    echo ❌ Error al iniciar servicios
    exit /b 1
)