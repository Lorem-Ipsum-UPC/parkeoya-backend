# 🐳 Comandos Docker Útiles para Parkeoya Backend

## 📦 Construcción

```bash
# Construir imagen localmente
docker build -t parkeoya-backend:local .

# Construir con tu usuario de Docker Hub
docker build -t TU_USUARIO/parkeoya-backend:1.0.0 .

# Construir sin caché (si hay problemas)
docker build --no-cache -t TU_USUARIO/parkeoya-backend:1.0.0 .

# Ver imágenes disponibles
docker images | grep parkeoya
```

## 🚀 Ejecución Local

```bash
# Ejecutar contenedor en modo detached (background)
docker run -d --name parkeoya-api -p 8080:8080 TU_USUARIO/parkeoya-backend:latest

# Ejecutar en modo interactivo (ver logs en tiempo real)
docker run -it --name parkeoya-api -p 8080:8080 TU_USUARIO/parkeoya-backend:latest

# Ejecutar con variables de entorno personalizadas
docker run -d \
  --name parkeoya-api \
  -p 8080:8080 \
  -e SPRING_JPA_SHOW_SQL=true \
  -e LOGGING_LEVEL_ROOT=DEBUG \
  TU_USUARIO/parkeoya-backend:latest
```

## 📋 Gestión de Contenedores

```bash
# Ver contenedores en ejecución
docker ps

# Ver todos los contenedores (incluyendo detenidos)
docker ps -a

# Ver logs del contenedor
docker logs parkeoya-api

# Ver logs en tiempo real (seguimiento)
docker logs -f parkeoya-api

# Ver últimas 100 líneas de logs
docker logs --tail 100 parkeoya-api

# Detener contenedor
docker stop parkeoya-api

# Iniciar contenedor detenido
docker start parkeoya-api

# Reiniciar contenedor
docker restart parkeoya-api

# Eliminar contenedor
docker rm parkeoya-api

# Eliminar contenedor forzadamente (si está en ejecución)
docker rm -f parkeoya-api
```

## 🔍 Inspección y Debug

```bash
# Inspeccionar contenedor
docker inspect parkeoya-api

# Ver uso de recursos (CPU, memoria)
docker stats parkeoya-api

# Ver procesos dentro del contenedor
docker top parkeoya-api

# Ejecutar comando dentro del contenedor
docker exec parkeoya-api ls -la

# Abrir shell interactivo dentro del contenedor
docker exec -it parkeoya-api sh

# Ver health check status
docker inspect --format='{{json .State.Health}}' parkeoya-api | python -m json.tool
```

## 🌐 Docker Hub

```bash
# Login a Docker Hub
docker login

# Etiquetar imagen
docker tag parkeoya-backend:local TU_USUARIO/parkeoya-backend:1.0.0
docker tag parkeoya-backend:local TU_USUARIO/parkeoya-backend:latest

# Subir imagen a Docker Hub
docker push TU_USUARIO/parkeoya-backend:1.0.0
docker push TU_USUARIO/parkeoya-backend:latest

# Descargar imagen desde Docker Hub
docker pull TU_USUARIO/parkeoya-backend:latest

# Ver información de imagen remota
docker manifest inspect TU_USUARIO/parkeoya-backend:latest
```

## 🧹 Limpieza

```bash
# Eliminar imagen
docker rmi parkeoya-backend:local

# Eliminar imagen forzadamente
docker rmi -f TU_USUARIO/parkeoya-backend:1.0.0

# Limpiar contenedores detenidos
docker container prune

# Limpiar imágenes sin usar
docker image prune

# Limpiar todo (contenedores, imágenes, redes, volúmenes no usados)
docker system prune -a

# Ver espacio usado por Docker
docker system df
```

## 🐳 Docker Compose

```bash
# Iniciar servicios en background
docker-compose up -d

# Iniciar y ver logs en tiempo real
docker-compose up

# Detener servicios
docker-compose down

# Ver logs
docker-compose logs -f

# Reconstruir imágenes
docker-compose build

# Reiniciar un servicio específico
docker-compose restart parkeoya-backend
```

## 🧪 Testing

```bash
# Verificar que el contenedor responde
curl http://localhost:8080/actuator/health

# Probar con formato bonito
curl http://localhost:8080/actuator/health | python -m json.tool

# Verificar Swagger
curl http://localhost:8080/swagger-ui/index.html

# Probar endpoint de autenticación
curl -X POST http://localhost:8080/api/v1/authentication/sign-in \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Test1234"}'
```

## ⚡ Comandos de Producción

```bash
# Descargar última versión y ejecutar
docker pull TU_USUARIO/parkeoya-backend:latest && \
docker stop parkeoya-api || true && \
docker rm parkeoya-api || true && \
docker run -d \
  --name parkeoya-api \
  --restart unless-stopped \
  -p 8080:8080 \
  TU_USUARIO/parkeoya-backend:latest

# Ver si el contenedor está healthy
docker ps --filter name=parkeoya-api --format "table {{.Names}}\t{{.Status}}"

# Backup de logs antes de reiniciar
docker logs parkeoya-api > parkeoya-$(date +%Y%m%d-%H%M%S).log
```

## 📊 Monitoreo

```bash
# Ver estadísticas en tiempo real de todos los contenedores
docker stats

# Ver solo el contenedor de Parkeoya
docker stats parkeoya-api

# Health check manual
while true; do 
  docker inspect --format='{{.State.Health.Status}}' parkeoya-api
  sleep 5
done
```

## 🔧 Troubleshooting

```bash
# Ver por qué falló un contenedor
docker logs parkeoya-api --tail 50

# Ver eventos del contenedor
docker events --filter container=parkeoya-api

# Copiar archivo desde el contenedor
docker cp parkeoya-api:/app/logs/application.log ./

# Copiar archivo al contenedor
docker cp ./nuevo-config.properties parkeoya-api:/app/config/
```

## 💡 Tips Útiles

```bash
# Crear alias para comandos frecuentes (agregar a ~/.bashrc o ~/.zshrc)
alias dps='docker ps'
alias dlogs='docker logs -f parkeoya-api'
alias dstop='docker stop parkeoya-api'
alias dstart='docker start parkeoya-api'
alias drestart='docker restart parkeoya-api'

# Ver solo IDs de contenedores
docker ps -q

# Detener todos los contenedores
docker stop $(docker ps -q)

# Eliminar todos los contenedores detenidos
docker rm $(docker ps -a -q)
```

---

**🎯 Comando más usado en desarrollo:**
```bash
docker-compose up -d && docker-compose logs -f
```

**🎯 Comando más usado en producción:**
```bash
docker pull TU_USUARIO/parkeoya-backend:latest && docker-compose up -d
```
