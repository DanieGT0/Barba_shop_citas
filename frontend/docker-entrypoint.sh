#!/bin/sh
set -e

# Script de inicio para el contenedor Angular Frontend

echo "🚀 Iniciando BarberApp Frontend..."

# Verificar que los archivos existen
if [ ! -f "/usr/share/nginx/html/index.html" ]; then
    echo "❌ Error: No se encontraron los archivos de la aplicación Angular"
    exit 1
fi

echo "✅ Archivos de la aplicación encontrados"

# Mostrar información del contenedor
echo "📋 Información del contenedor:"
echo "   - Node.js: $(node --version 2>/dev/null || echo 'N/A (solo en build)')"
echo "   - Nginx: $(nginx -v 2>&1 | cut -d' ' -f3)"
echo "   - Usuario: $(whoami)"
echo "   - Directorio: $(pwd)"

# Verificar permisos
echo "🔒 Verificando permisos..."
if [ ! -r "/usr/share/nginx/html/index.html" ]; then
    echo "❌ Error: Sin permisos de lectura en archivos de la aplicación"
    exit 1
fi

# Verificar configuración de Nginx
echo "🔧 Validando configuración de Nginx..."
nginx -t

if [ $? -eq 0 ]; then
    echo "✅ Configuración de Nginx válida"
else
    echo "❌ Error en la configuración de Nginx"
    exit 1
fi

# Crear directorios necesarios si no existen
mkdir -p /var/log/nginx
mkdir -p /var/run/nginx

echo "🌐 Iniciando servidor Nginx en puerto 80..."
echo "📱 Aplicación Angular lista en: http://localhost"

# Ejecutar el comando pasado como parámetros
exec "$@"