#!/bin/bash

# Script para detener BarberApp y limpiar recursos

echo "🛑 Deteniendo BarberApp"
echo "======================"

# Detectar docker-compose o docker compose
if docker compose version &> /dev/null; then
    DOCKER_COMPOSE="docker compose"
else
    DOCKER_COMPOSE="docker-compose"
fi

# Función para mostrar opciones
show_help() {
    echo "Uso: $0 [opción]"
    echo ""
    echo "Opciones:"
    echo "  stop      - Solo detener servicios (mantener volúmenes)"
    echo "  down      - Detener servicios y eliminar contenedores"
    echo "  clean     - Detener todo y eliminar volúmenes (¡CUIDADO!)"
    echo "  prune     - Limpiar sistema Docker completo"
    echo "  --help    - Mostrar esta ayuda"
    echo ""
}

# Función para detener servicios
stop_services() {
    echo "⏸️ Deteniendo servicios..."
    $DOCKER_COMPOSE stop
    echo "✅ Servicios detenidos"
}

# Función para hacer down completo
down_services() {
    echo "📦 Deteniendo y eliminando contenedores..."
    $DOCKER_COMPOSE down
    echo "✅ Contenedores eliminados"
}

# Función para limpiar todo incluyendo volúmenes
clean_all() {
    echo "⚠️  ¡CUIDADO! Esto eliminará TODOS los datos de la base de datos"
    read -p "¿Estás seguro? Escribe 'CONFIRMAR' para continuar: " confirm
    if [ "$confirm" = "CONFIRMAR" ]; then
        echo "🗑️ Eliminando servicios, contenedores y volúmenes..."
        $DOCKER_COMPOSE down -v
        echo "✅ Todo eliminado incluyendo datos"
    else
        echo "❌ Operación cancelada"
        exit 0
    fi
}

# Función para limpiar sistema Docker
prune_system() {
    echo "🧹 Limpiando sistema Docker..."
    echo "Esto eliminará:"
    echo "  - Contenedores detenidos"
    echo "  - Redes no utilizadas"
    echo "  - Imágenes colgantes"
    echo "  - Cache de build"
    echo ""
    read -p "¿Continuar? (y/N): " confirm
    if [ "$confirm" = "y" ] || [ "$confirm" = "Y" ]; then
        docker system prune -a -f
        echo "✅ Sistema Docker limpiado"
    else
        echo "❌ Operación cancelada"
    fi
}

# Función para mostrar estado actual
show_status() {
    echo ""
    echo "📊 Estado actual:"
    echo "================"

    echo "🐳 Contenedores BarberApp:"
    docker ps -a --filter "name=barberapp" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

    echo ""
    echo "📦 Volúmenes BarberApp:"
    docker volume ls --filter "name=barberapp" --format "table {{.Name}}\t{{.Driver}}"

    echo ""
    echo "🌐 Redes BarberApp:"
    docker network ls --filter "name=barberapp" --format "table {{.Name}}\t{{.Driver}}"
}

# Script principal
case "$1" in
    stop)
        stop_services
        show_status
        ;;
    down)
        down_services
        show_status
        ;;
    clean)
        clean_all
        show_status
        ;;
    prune)
        prune_system
        ;;
    --help|-h)
        show_help
        ;;
    "")
        echo "🤔 ¿Qué quieres hacer?"
        echo ""
        echo "1) Solo detener servicios (mantener datos)"
        echo "2) Detener y eliminar contenedores (mantener datos)"
        echo "3) Eliminar todo incluyendo datos (¡CUIDADO!)"
        echo "4) Ver estado actual"
        echo "5) Cancelar"
        echo ""
        read -p "Elige una opción (1-5): " choice

        case $choice in
            1) stop_services ;;
            2) down_services ;;
            3) clean_all ;;
            4) show_status ;;
            5) echo "❌ Operación cancelada"; exit 0 ;;
            *) echo "❌ Opción inválida"; exit 1 ;;
        esac
        show_status
        ;;
    *)
        echo "❌ Opción desconocida: $1"
        show_help
        exit 1
        ;;
esac

echo ""
echo "✅ Operación completada"