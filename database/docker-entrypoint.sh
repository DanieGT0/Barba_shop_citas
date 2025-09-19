#!/bin/bash
set -e

# Script de inicio personalizado para PostgreSQL 16 BarberApp

echo "🐘 Iniciando PostgreSQL 16 para BarberApp..."

# Variables de entorno por defecto
export POSTGRES_DB=${POSTGRES_DB:-barberapp}
export POSTGRES_USER=${POSTGRES_USER:-barberapp_user}
export POSTGRES_PASSWORD=${POSTGRES_PASSWORD:-barberapp_password}
export PGDATA=${PGDATA:-/var/lib/postgresql/data/pgdata}

# Mostrar información del contenedor
echo "📋 Configuración PostgreSQL:"
echo "   - Versión: $(postgres --version)"
echo "   - Puerto: 5433"
echo "   - Base de datos: $POSTGRES_DB"
echo "   - Usuario: $POSTGRES_USER"
echo "   - Directorio datos: $PGDATA"
echo "   - Usuario del proceso: $(whoami)"

# Verificar y crear directorio de datos si no existe
if [ ! -d "$PGDATA" ]; then
    echo "📂 Creando directorio de datos: $PGDATA"
    mkdir -p "$PGDATA"
    chown postgres:postgres "$PGDATA"
    chmod 700 "$PGDATA"
fi

# Verificar si la base de datos ya está inicializada
if [ ! -s "$PGDATA/PG_VERSION" ]; then
    echo "🔧 Inicializando base de datos PostgreSQL..."

    # Inicializar la base de datos
    initdb -D "$PGDATA" \
           --auth-host=md5 \
           --auth-local=trust \
           --encoding=UTF8 \
           --locale=C \
           --data-checksums

    echo "✅ Base de datos inicializada"

    # Configurar PostgreSQL temporal para scripts de inicialización
    pg_ctl -D "$PGDATA" \
           -o "-c listen_addresses='' -c port=5433" \
           -w start

    echo "🗄️ Ejecutando scripts de inicialización..."

    # Ejecutar scripts de inicialización
    for f in /docker-entrypoint-initdb.d/*; do
        case "$f" in
            *.sh)
                echo "⚡ Ejecutando script: $f"
                bash "$f"
                ;;
            *.sql)
                echo "📊 Ejecutando SQL: $f"
                psql -v ON_ERROR_STOP=1 --username postgres --port 5433 < "$f"
                ;;
            *.sql.gz)
                echo "📦 Ejecutando SQL comprimido: $f"
                gunzip -c "$f" | psql -v ON_ERROR_STOP=1 --username postgres --port 5433
                ;;
            *)
                echo "⚠️ Ignorando archivo: $f"
                ;;
        esac
    done

    # Detener PostgreSQL temporal
    pg_ctl -D "$PGDATA" -m fast -w stop

    echo "✅ Scripts de inicialización completados"
else
    echo "✅ Base de datos ya está inicializada"
fi

# Verificar permisos del directorio de logs
mkdir -p /var/log/postgresql
chown postgres:postgres /var/log/postgresql

echo "🚀 Iniciando PostgreSQL en puerto 5433..."

# Ejecutar PostgreSQL con la configuración personalizada
exec "$@"