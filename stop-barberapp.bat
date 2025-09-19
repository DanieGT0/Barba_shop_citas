@echo off
REM Script para detener BarberApp y limpiar recursos en Windows

echo 🛑 Deteniendo BarberApp
echo ======================

REM Detectar docker-compose o docker compose
docker-compose --version >nul 2>&1
if %errorlevel% neq 0 (
    set DOCKER_COMPOSE=docker compose
) else (
    set DOCKER_COMPOSE=docker-compose
)

if "%1"=="--help" goto :show_help
if "%1"=="-h" goto :show_help
if "%1"=="stop" goto :stop_services
if "%1"=="down" goto :down_services
if "%1"=="clean" goto :clean_all
if "%1"=="prune" goto :prune_system
if "%1"=="" goto :interactive_menu

echo ❌ Opción desconocida: %1
goto :show_help

:show_help
echo Uso: %0 [opción]
echo.
echo Opciones:
echo   stop      - Solo detener servicios (mantener volúmenes)
echo   down      - Detener servicios y eliminar contenedores
echo   clean     - Detener todo y eliminar volúmenes (¡CUIDADO!)
echo   prune     - Limpiar sistema Docker completo
echo   --help    - Mostrar esta ayuda
echo.
exit /b 0

:stop_services
echo ⏸️ Deteniendo servicios...
%DOCKER_COMPOSE% stop
echo ✅ Servicios detenidos
goto :show_status

:down_services
echo 📦 Deteniendo y eliminando contenedores...
%DOCKER_COMPOSE% down
echo ✅ Contenedores eliminados
goto :show_status

:clean_all
echo ⚠️ ¡CUIDADO! Esto eliminará TODOS los datos de la base de datos
set /p confirm="¿Estás seguro? Escribe 'CONFIRMAR' para continuar: "
if "%confirm%"=="CONFIRMAR" (
    echo 🗑️ Eliminando servicios, contenedores y volúmenes...
    %DOCKER_COMPOSE% down -v
    echo ✅ Todo eliminado incluyendo datos
) else (
    echo ❌ Operación cancelada
    exit /b 0
)
goto :show_status

:prune_system
echo 🧹 Limpiando sistema Docker...
echo Esto eliminará:
echo   - Contenedores detenidos
echo   - Redes no utilizadas
echo   - Imágenes colgantes
echo   - Cache de build
echo.
set /p confirm="¿Continuar? (y/N): "
if /i "%confirm%"=="y" (
    docker system prune -a -f
    echo ✅ Sistema Docker limpiado
) else (
    echo ❌ Operación cancelada
)
exit /b 0

:interactive_menu
echo 🤔 ¿Qué quieres hacer?
echo.
echo 1) Solo detener servicios (mantener datos)
echo 2) Detener y eliminar contenedores (mantener datos)
echo 3) Eliminar todo incluyendo datos (¡CUIDADO!)
echo 4) Ver estado actual
echo 5) Cancelar
echo.
set /p choice="Elige una opción (1-5): "

if "%choice%"=="1" goto :stop_services
if "%choice%"=="2" goto :down_services
if "%choice%"=="3" goto :clean_all
if "%choice%"=="4" goto :show_status
if "%choice%"=="5" (
    echo ❌ Operación cancelada
    exit /b 0
)
echo ❌ Opción inválida
exit /b 1

:show_status
echo.
echo 📊 Estado actual:
echo ================

echo 🐳 Contenedores BarberApp:
docker ps -a --filter "name=barberapp" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

echo.
echo 📦 Volúmenes BarberApp:
docker volume ls --filter "name=barberapp" --format "table {{.Name}}\t{{.Driver}}"

echo.
echo 🌐 Redes BarberApp:
docker network ls --filter "name=barberapp" --format "table {{.Name}}\t{{.Driver}}"

echo.
echo ✅ Operación completada
exit /b 0