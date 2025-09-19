# Dockerfile para PostgreSQL 16 con configuración personalizada
FROM postgres:16-alpine

# Información del mantenedor
LABEL maintainer="BarberApp Team"
LABEL description="PostgreSQL 16 Database for BarberApp with persistent volumes"

# Variables de entorno por defecto
ENV POSTGRES_DB=barberapp
ENV POSTGRES_USER=barberapp_user
ENV POSTGRES_PASSWORD=barberapp_password
ENV PGDATA=/var/lib/postgresql/data/pgdata

# Instalar herramientas adicionales
RUN apk add --no-cache \
    curl \
    bash \
    && rm -rf /var/cache/apk/*

# Crear directorios necesarios
RUN mkdir -p /var/lib/postgresql/data/pgdata && \
    mkdir -p /docker-entrypoint-initdb.d && \
    mkdir -p /var/log/postgresql

# Copiar scripts de inicialización
COPY init-scripts/ /docker-entrypoint-initdb.d/
COPY postgresql.conf /etc/postgresql/postgresql.conf
COPY pg_hba.conf /etc/postgresql/pg_hba.conf

# Copiar script personalizado de inicio
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# Cambiar propietarios
RUN chown -R postgres:postgres /var/lib/postgresql/data && \
    chown -R postgres:postgres /var/log/postgresql && \
    chown postgres:postgres /etc/postgresql/postgresql.conf && \
    chown postgres:postgres /etc/postgresql/pg_hba.conf

# Cambiar a usuario postgres
USER postgres

# Exponer puerto personalizado 5433
EXPOSE 5433

# Configurar volúmenes
VOLUME ["/var/lib/postgresql/data", "/var/log/postgresql"]

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD pg_isready -p 5433 -h localhost -U $POSTGRES_USER || exit 1

# Comando de inicio
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]