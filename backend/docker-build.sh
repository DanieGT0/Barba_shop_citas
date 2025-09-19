#!/bin/bash

# Script para construir y ejecutar el contenedor Docker del backend Spring Boot

echo "🐳 Construyendo imagen Docker para Spring Boot Backend (Java 17)..."

# Variables
IMAGE_NAME="barberapp-backend"
TAG="latest"
CONTAINER_NAME="barberapp-backend-container"

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
    echo "docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME:$TAG"
    echo ""
    echo "🔍 Para ver logs:"
    echo "docker logs -f $CONTAINER_NAME"
    echo ""
    echo "🛑 Para detener el contenedor:"
    echo "docker stop $CONTAINER_NAME"
    echo ""
    echo "🗑️ Para eliminar el contenedor:"
    echo "docker rm $CONTAINER_NAME"

else
    echo "❌ Error al construir la imagen Docker"
    exit 1
fi