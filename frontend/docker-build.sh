#!/bin/bash

# Script para construir y ejecutar el contenedor Docker del frontend Angular

echo "🐳 Construyendo imagen Docker para Angular Frontend (Node.js 18+)..."

# Variables
IMAGE_NAME="barberapp-frontend"
TAG="latest"
CONTAINER_NAME="barberapp-frontend-container"

# Construir la imagen
echo "📦 Construyendo imagen: $IMAGE_NAME:$TAG"
docker build -t $IMAGE_NAME:$TAG .

# Verificar si la construcción fue exitosa
if [ $? -eq 0 ]; then
    echo "✅ Imagen construida exitosamente!"

    # Mostrar información de la imagen
    echo "📋 Información de la imagen:"
    docker images | grep $IMAGE_NAME

    echo ""
    echo "🚀 Para ejecutar el contenedor, usa:"
    echo "docker run -d --name $CONTAINER_NAME -p 4200:80 $IMAGE_NAME:$TAG"
    echo ""
    echo "🌐 La aplicación estará disponible en: http://localhost:4200"
    echo ""
    echo "🔍 Para ver logs:"
    echo "docker logs -f $CONTAINER_NAME"
    echo ""
    echo "🛑 Para detener el contenedor:"
    echo "docker stop $CONTAINER_NAME"
    echo ""
    echo "🗑️ Para eliminar el contenedor:"
    echo "docker rm $CONTAINER_NAME"
    echo ""
    echo "⚡ Para desarrollo con hot-reload:"
    echo "docker run -d --name $CONTAINER_NAME-dev -p 4200:4200 -v \$(pwd)/src:/app/src $IMAGE_NAME:$TAG npm start"

else
    echo "❌ Error al construir la imagen Docker"
    exit 1
fi