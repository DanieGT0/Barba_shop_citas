@echo off
REM Script para construir y ejecutar el contenedor Docker del frontend Angular en Windows

echo 🐳 Construyendo imagen Docker para Angular Frontend (Node.js 18+)...

REM Variables
set IMAGE_NAME=barberapp-frontend
set TAG=latest
set CONTAINER_NAME=barberapp-frontend-container

REM Construir la imagen
echo 📦 Construyendo imagen: %IMAGE_NAME%:%TAG%
docker build -t %IMAGE_NAME%:%TAG% .

REM Verificar si la construcción fue exitosa
if %errorlevel% equ 0 (
    echo ✅ Imagen construida exitosamente!

    REM Mostrar información de la imagen
    echo 📋 Información de la imagen:
    docker images | findstr %IMAGE_NAME%

    echo.
    echo 🚀 Para ejecutar el contenedor, usa:
    echo docker run -d --name %CONTAINER_NAME% -p 4200:80 %IMAGE_NAME%:%TAG%
    echo.
    echo 🌐 La aplicación estará disponible en: http://localhost:4200
    echo.
    echo 🔍 Para ver logs:
    echo docker logs -f %CONTAINER_NAME%
    echo.
    echo 🛑 Para detener el contenedor:
    echo docker stop %CONTAINER_NAME%
    echo.
    echo 🗑️ Para eliminar el contenedor:
    echo docker rm %CONTAINER_NAME%

) else (
    echo ❌ Error al construir la imagen Docker
    exit /b 1
)