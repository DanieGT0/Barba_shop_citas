# Dockerfile para Angular Frontend con Node.js 18+
FROM node:18-alpine as build

# Información del mantenedor
LABEL maintainer="BarberApp Team"
LABEL description="Angular Frontend with Node.js 18+ for BarberApp"

# Instalar herramientas necesarias
RUN apk add --no-cache git curl

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración
COPY package*.json ./
COPY angular.json ./
COPY tsconfig*.json ./

# Instalar dependencias
RUN npm ci --only=production

# Instalar Angular CLI globalmente
RUN npm install -g @angular/cli@17

# Copiar código fuente
COPY src ./src

# Construir aplicación para Docker
RUN ng build --configuration docker

# Etapa de runtime con Nginx
FROM nginx:alpine

# Instalar curl para health checks
RUN apk add --no-cache curl

# Crear usuario nginx personalizado
RUN addgroup -g 1001 -S nginx-custom && \
    adduser -S -D -H -u 1001 -h /var/cache/nginx -s /sbin/nologin -G nginx-custom -g nginx-custom nginx-custom

# Copiar configuración personalizada de Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Copiar archivos construidos desde la etapa de build
COPY --from=build /app/dist/barber-app-frontend /usr/share/nginx/html

# Copiar script de inicio
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

# Cambiar propietarios de archivos
RUN chown -R nginx-custom:nginx-custom /usr/share/nginx/html && \
    chown -R nginx-custom:nginx-custom /var/cache/nginx && \
    chown -R nginx-custom:nginx-custom /var/log/nginx && \
    chown -R nginx-custom:nginx-custom /etc/nginx/conf.d

# Crear directorio para PID
RUN mkdir -p /var/run/nginx && \
    chown -R nginx-custom:nginx-custom /var/run/nginx

# Cambiar a usuario no-root
USER nginx-custom

# Exponer puerto
EXPOSE 80

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:80/ || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]