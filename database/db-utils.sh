#!/bin/bash

# Utilidades para gestionar la base de datos PostgreSQL en Docker

CONTAINER_NAME="barberapp-postgres-container"
DB_NAME="barberapp"
DB_USER="barberapp_user"
DB_PORT="5433"

# Función para mostrar ayuda
show_help() {
    echo "🐘 Utilidades PostgreSQL BarberApp"
    echo "=================================="
    echo ""
    echo "Uso: $0 [comando]"
    echo ""
    echo "Comandos disponibles:"
    echo "  start         - Iniciar contenedor PostgreSQL"
    echo "  stop          - Detener contenedor"
    echo "  restart       - Reiniciar contenedor"
    echo "  status        - Ver estado del contenedor"
    echo "  logs          - Ver logs en tiempo real"
    echo "  connect       - Conectar a la base de datos"
    echo "  backup        - Crear backup de la base de datos"
    echo "  restore FILE  - Restaurar backup desde archivo"
    echo "  clean         - Limpiar datos (¡CUIDADO!)"
    echo "  psql          - Ejecutar comando psql personalizado"
    echo ""
}

# Función para verificar si el contenedor existe
check_container() {
    if ! docker ps -a --format "table {{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
        echo "❌ El contenedor $CONTAINER_NAME no existe"
        echo "💡 Ejecuta docker-build.sh primero para crear el contenedor"
        exit 1
    fi
}

# Función para iniciar el contenedor
start_db() {
    echo "🚀 Iniciando PostgreSQL..."
    if docker ps --format "table {{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
        echo "✅ PostgreSQL ya está ejecutándose"
    else
        docker start $CONTAINER_NAME
        echo "⏱️ Esperando que PostgreSQL esté listo..."
        sleep 5
        if docker ps --format "table {{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
            echo "✅ PostgreSQL iniciado correctamente"
        else
            echo "❌ Error al iniciar PostgreSQL"
            exit 1
        fi
    fi
}

# Función para detener el contenedor
stop_db() {
    echo "🛑 Deteniendo PostgreSQL..."
    docker stop $CONTAINER_NAME
    echo "✅ PostgreSQL detenido"
}

# Función para reiniciar el contenedor
restart_db() {
    echo "🔄 Reiniciando PostgreSQL..."
    docker restart $CONTAINER_NAME
    echo "✅ PostgreSQL reiniciado"
}

# Función para ver estado
status_db() {
    echo "📊 Estado del contenedor PostgreSQL:"
    echo "=================================="
    docker ps -a --filter "name=$CONTAINER_NAME" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
    echo ""

    if docker ps --format "table {{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
        echo "🔍 Información de la base de datos:"
        docker exec $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -p $DB_PORT -c "SELECT version();" 2>/dev/null || echo "❌ No se pudo conectar a la base de datos"
    fi
}

# Función para ver logs
logs_db() {
    echo "📝 Logs de PostgreSQL (Ctrl+C para salir):"
    docker logs -f $CONTAINER_NAME
}

# Función para conectar a la base de datos
connect_db() {
    echo "🔗 Conectando a la base de datos..."
    docker exec -it $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -p $DB_PORT
}

# Función para hacer backup
backup_db() {
    BACKUP_FILE="barberapp_backup_$(date +%Y%m%d_%H%M%S).sql"
    echo "💾 Creando backup: $BACKUP_FILE"
    docker exec $CONTAINER_NAME pg_dump -U $DB_USER -d $DB_NAME -p $DB_PORT > $BACKUP_FILE
    echo "✅ Backup creado: $BACKUP_FILE"
}

# Función para restaurar backup
restore_db() {
    if [ -z "$1" ]; then
        echo "❌ Especifica el archivo de backup"
        echo "Uso: $0 restore archivo_backup.sql"
        exit 1
    fi

    BACKUP_FILE="$1"
    if [ ! -f "$BACKUP_FILE" ]; then
        echo "❌ El archivo $BACKUP_FILE no existe"
        exit 1
    fi

    echo "🔄 Restaurando backup desde: $BACKUP_FILE"
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -p $DB_PORT < $BACKUP_FILE
    echo "✅ Backup restaurado exitosamente"
}

# Función para limpiar datos
clean_db() {
    echo "⚠️  ¡CUIDADO! Esto eliminará TODOS los datos de la base de datos"
    read -p "¿Estás seguro? Escribe 'CONFIRMAR' para continuar: " confirm
    if [ "$confirm" = "CONFIRMAR" ]; then
        echo "🗑️ Limpiando base de datos..."
        docker exec $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -p $DB_PORT -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"
        echo "✅ Base de datos limpiada"
    else
        echo "❌ Operación cancelada"
    fi
}

# Función para ejecutar comando psql personalizado
psql_custom() {
    echo "💻 Modo psql personalizado (escribe 'exit' para salir):"
    echo "Conectado como: $DB_USER@$DB_NAME:$DB_PORT"
    docker exec -it $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -p $DB_PORT
}

# Main
case "$1" in
    start)
        check_container
        start_db
        ;;
    stop)
        check_container
        stop_db
        ;;
    restart)
        check_container
        restart_db
        ;;
    status)
        check_container
        status_db
        ;;
    logs)
        check_container
        logs_db
        ;;
    connect)
        check_container
        connect_db
        ;;
    backup)
        check_container
        backup_db
        ;;
    restore)
        check_container
        restore_db "$2"
        ;;
    clean)
        check_container
        clean_db
        ;;
    psql)
        check_container
        psql_custom
        ;;
    *)
        show_help
        exit 1
        ;;
esac