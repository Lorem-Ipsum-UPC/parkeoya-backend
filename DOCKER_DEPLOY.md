# 🐳 Parkeoya Backend - Docker Deployment Guide

## 📋 Pre-requisitos

1. **Instalar Docker Desktop**
   - Descarga desde: https://www.docker.com/products/docker-desktop
   - Instala y reinicia tu PC
   - Abre Docker Desktop y espera a que inicie

2. **Crear cuenta en Docker Hub** (si no tienes)
   - Ve a: https://hub.docker.com/signup
   - Crea tu cuenta gratis
   - Anota tu username

## 🚀 Pasos para Desplegar

### Opción 1: Script Automático (Recomendado para Windows)

1. Abre Docker Desktop y espera a que inicie completamente
2. Abre PowerShell o CMD en la carpeta `parkeoya-backend`
3. Ejecuta:
   ```cmd
   docker-build-push.bat
   ```
4. Ingresa tu usuario de Docker Hub cuando te lo pida
5. Ingresa tu contraseña de Docker Hub
6. Espera a que compile (puede tomar 5-10 minutos)
7. Cuando pregunte si deseas publicar, escribe `s` y presiona Enter

### Opción 2: Comandos Manuales

```bash
# 1. Login a Docker Hub
docker login

# 2. Construir la imagen (reemplaza TU_USUARIO con tu username de Docker Hub)
docker build -t TU_USUARIO/parkeoya-backend:1.0.0 -t TU_USUARIO/parkeoya-backend:latest .

# 3. Publicar en Docker Hub
docker push TU_USUARIO/parkeoya-backend:1.0.0
docker push TU_USUARIO/parkeoya-backend:latest
```

## ✅ Verificación

Una vez publicada, tu imagen estará disponible en:
```
https://hub.docker.com/r/TU_USUARIO/parkeoya-backend
```

## 🔧 Configuración de Base de Datos

La imagen ya viene configurada con las credenciales de Aiven:

```properties
Database: defaultdb
Host: mysql-parqueoya-parqueoya.k.aivencloud.com
Port: 25208
User: avnadmin
Password: AVNS_cE7yRN4O6rCL1d24Iya
SSL: Required
```

## 🚢 Ejecutar el Contenedor Localmente (Prueba)

```bash
# Ejecutar el contenedor
docker run -d \
  --name parkeoya-api \
  -p 8080:8080 \
  TU_USUARIO/parkeoya-backend:latest

# Ver logs
docker logs -f parkeoya-api

# Detener contenedor
docker stop parkeoya-api

# Eliminar contenedor
docker rm parkeoya-api
```

## 🌐 Desplegar en la Nube

### Render.com (Gratis)
1. Ve a https://render.com
2. Conecta tu Docker Hub
3. Crea un nuevo "Web Service"
4. Selecciona "Deploy from Docker image"
5. Ingresa: `TU_USUARIO/parkeoya-backend:latest`
6. Puerto: `8080`
7. Deploy!

### Railway.app (Gratis)
1. Ve a https://railway.app
2. Crea nuevo proyecto
3. "Deploy from Docker Image"
4. Ingresa: `TU_USUARIO/parkeoya-backend:latest`
5. Configura el puerto: `8080`
6. Deploy!

### Fly.io (Gratis con $5 de crédito)
```bash
# Instalar Fly CLI
curl -L https://fly.io/install.sh | sh

# Login
fly auth login

# Desplegar
fly launch --image TU_USUARIO/parkeoya-backend:latest
```

## 📊 Health Check

La imagen incluye un health check que verifica cada 30 segundos:
```
http://localhost:8080/actuator/health
```

## 🔍 Troubleshooting

### Error: "docker: command not found"
- Instala Docker Desktop
- Reinicia tu terminal

### Error: "Cannot connect to Docker daemon"
- Abre Docker Desktop
- Espera a que el ícono de la ballena deje de parpadear

### Error: "denied: requested access to the resource is denied"
- Verifica que hiciste `docker login`
- Verifica que el nombre de la imagen incluye tu username correcto

## 📝 Notas Importantes

- La imagen usa Java 17 (Eclipse Temurin)
- Tamaño aproximado: ~250 MB
- Multi-stage build para optimizar tamaño
- Usuario no-root para seguridad
- Memoria configurada: 256MB-512MB

## 🎯 Siguiente Paso

Una vez publicada la imagen, comparte el link:
```
docker pull TU_USUARIO/parkeoya-backend:latest
```

Y podrás desplegarla en cualquier servicio de cloud que soporte Docker! 🚀
