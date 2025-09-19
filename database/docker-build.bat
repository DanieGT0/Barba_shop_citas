@echo off
REM Script para construir y ejecutar el contenedor Docker de PostgreSQL 16 en Windows

echo 🐳 Construyendo imagen Docker para PostgreSQL 16 (Puerto 5433)...

REM Variables
set IMAGE_NAME=barberapp-postgres
set TAG=16
set CONTAINER_NAME=barberapp-postgres-container
set POSTGRES_PORT=5433
set VOLUME_NAME=barberapp-postgres-data
set LOGS_VOLUME=barberapp-postgres-logs

REM Crear volúmenes si no existen
echo 📦 Creando volúmenes persistentes...
docker volume create %VOLUME_NAME% 2>nul || echo    Volumen %VOLUME_NAME% ya existe
docker volume create %LOGS_VOLUME% 2>nul || echo    Volumen %LOGS_VOLUME% ya existe

REM Construir la imagen
echo 🔨 Construyendo imagen: %IMAGE_NAME%:%TAG%
docker build -t %IMAGE_NAME%:%TAG% .

REM Verificar si la construcción fue exitosa
if %errorlevel% equ 0 (
    echo ✅ Imagen construida exitosamente!

    REM Mostrar información de la imagen
    echo 📋 Información de la imagen:
    docker images | findstr %IMAGE_NAME%

    echo.
    echo 🚀 Para ejecutar el contenedor con volúmenes persistentes:
    echo docker run -d ^
    echo   --name %CONTAINER_NAME% ^
    echo   -p %POSTGRES_PORT%:5433 ^
    echo   -v %VOLUME_NAME%:/var/lib/postgresql/data ^
    echo   -v %LOGS_VOLUME%:/var/log/postgresql ^
    echo   -e POSTGRES_DB=barberapp ^
    echo   -e POSTGRES_USER=barberapp_user ^
    echo   -e POSTGRES_PASSWORD=barberapp_password ^
    echo   %IMAGE_NAME%:%TAG%

    echo.
    echo 🔗 Conexión a la base de datos:
    echo    Host: localhost
    echo    Puerto: %POSTGRES_PORT%
    echo    Base de datos: barberapp
    echo    Usuario: barberapp_user
    echo    Contraseña: barberapp_password

    echo.
    echo 🔍 Comandos útiles:
    echo    Ver logs: docker logs -f %CONTAINER_NAME%
    echo    Conectar: docker exec -it %CONTAINER_NAME% psql -U barberapp_user -d barberapp -p 5433
    echo    Detener: docker stop %CONTAINER_NAME%
    echo    Eliminar: docker rm %CONTAINER_NAME%

    echo.
    echo 📊 Volúmenes creados:
    echo    Datos: %VOLUME_NAME%
    echo    Logs: %LOGS_VOLUME%
    docker volume ls | findstr barberapp-postgres

) else (
    echo ❌ Error al construir la imagen Docker
    exit /b 1
)