#!/bin/bash

# Script para iniciar BarberApp completo con Docker Compose

echo "🚀 Iniciando BarberApp - Stack Completo"
echo "======================================"

# Función para verificar dependencias
check_dependencies() {
    echo "🔍 Verificando dependencias..."

    if ! command -v docker &> /dev/null; then
        echo "❌ Docker no está instalado. Instala Docker primero."
        exit 1
    fi

    if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
        echo "❌ Docker Compose no está instalado. Instala Docker Compose primero."
        exit 1
    fi

    echo "✅ Docker y Docker Compose están disponibles"
}

# Función para verificar archivo .env
check_env_file() {
    if [ ! -f ".env" ]; then
        echo "⚠️  Archivo .env no encontrado"
        echo "💡 Creando .env desde .env.example..."

        if [ -f ".env.example" ]; then
            cp .env.example .env
            echo "✅ Archivo .env creado desde .env.example"
            echo "🔧 IMPORTANTE: Edita el archivo .env con tus credenciales OAuth2 reales"
        else
            echo "❌ No se encontró .env.example"
            exit 1
        fi
    else
        echo "✅ Archivo .env encontrado"
    fi
}

# Función para limpiar contenedores anteriores (opcional)
cleanup_containers() {
    if [ "$1" = "--clean" ]; then
        echo "🧹 Limpiando contenedores anteriores..."
        docker-compose down -v 2>/dev/null || true
        docker system prune -f 2>/dev/null || true
        echo "✅ Limpieza completada"
    fi
}

# Función para construir e iniciar servicios
start_services() {
    echo "🔨 Construyendo y iniciando servicios..."

    # Usar docker compose o docker-compose según disponibilidad
    if docker compose version &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        DOCKER_COMPOSE="docker-compose"
    fi

    echo "📦 Usando: $DOCKER_COMPOSE"

    # Construir e iniciar servicios en segundo plano
    $DOCKER_COMPOSE up --build -d

    if [ $? -eq 0 ]; then
        echo "✅ Servicios iniciados exitosamente"
        show_status
    else
        echo "❌ Error al iniciar servicios"
        exit 1
    fi
}

# Función para mostrar estado de servicios
show_status() {
    echo ""
    echo "📊 Estado de los servicios:"
    echo "=========================="

    if docker compose version &> /dev/null; then
        docker compose ps
    else
        docker-compose ps
    fi

    echo ""
    echo "🌐 URLs de acceso:"
    echo "=================="
    echo "   Frontend:  http://localhost:4200"
    echo "   Backend:   http://localhost:8080"
    echo "   API Docs:  http://localhost:8080/actuator/health"
    echo "   Database:  localhost:5433"
    echo ""
    echo "🔗 Credenciales de base de datos:"
    echo "================================="
    echo "   Host: localhost"
    echo "   Puerto: 5433"
    echo "   Base de datos: barberapp"
    echo "   Usuario: barberapp_user"
    echo "   Contraseña: barberapp_password"
    echo ""
}

# Función para mostrar logs
show_logs() {
    echo "📝 Para ver logs en tiempo real:"
    echo "==============================="

    if docker compose version &> /dev/null; then
        echo "   Todos los servicios: docker compose logs -f"
        echo "   Solo backend:        docker compose logs -f backend"
        echo "   Solo frontend:       docker compose logs -f frontend"
        echo "   Solo database:       docker compose logs -f postgres-db"
    else
        echo "   Todos los servicios: docker-compose logs -f"
        echo "   Solo backend:        docker-compose logs -f backend"
        echo "   Solo frontend:       docker-compose logs -f frontend"
        echo "   Solo database:       docker-compose logs -f postgres-db"
    fi
    echo ""
}

# Función para mostrar comandos útiles
show_helpful_commands() {
    echo "🛠️ Comandos útiles:"
    echo "=================="

    if docker compose version &> /dev/null; then
        echo "   Detener servicios:     docker compose stop"
        echo "   Reiniciar servicios:   docker compose restart"
        echo "   Detener y limpiar:     docker compose down"
        echo "   Ver logs:              docker compose logs -f"
        echo "   Reconstruir:           docker compose up --build -d"
    else
        echo "   Detener servicios:     docker-compose stop"
        echo "   Reiniciar servicios:   docker-compose restart"
        echo "   Detener y limpiar:     docker-compose down"
        echo "   Ver logs:              docker-compose logs -f"
        echo "   Reconstruir:           docker-compose up --build -d"
    fi
    echo ""
}

# Script principal
main() {
    check_dependencies
    check_env_file
    cleanup_containers "$1"
    start_services
    show_logs
    show_helpful_commands

    echo "🎉 ¡BarberApp está ejecutándose!"
    echo "🌐 Abre http://localhost:4200 en tu navegador"
    echo ""
    echo "⏹️  Para detener: Ctrl+C o ejecuta 'docker compose down'"
}

# Verificar argumentos
case "$1" in
    --clean)
        main --clean
        ;;
    --help|-h)
        echo "Uso: $0 [--clean] [--help]"
        echo ""
        echo "Opciones:"
        echo "  --clean    Limpiar contenedores anteriores antes de iniciar"
        echo "  --help     Mostrar esta ayuda"
        exit 0
        ;;
    "")
        main
        ;;
    *)
        echo "❌ Opción desconocida: $1"
        echo "Usa $0 --help para ver opciones disponibles"
        exit 1
        ;;
esac