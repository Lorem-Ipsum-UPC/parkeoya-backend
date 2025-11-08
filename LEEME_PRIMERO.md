# 🎯 PARKEOYA BACKEND - DESPLIEGUE DOCKER

## ✅ YA ESTÁ TODO CONFIGURADO

### 📦 Base de Datos
```
✅ Aiven MySQL Cloud
✅ Host: mysql-parqueoya-parqueoya.k.aivencloud.com
✅ Puerto: 25208
✅ Database: defaultdb
✅ SSL: Habilitado
✅ Credenciales: Configuradas en el Dockerfile
```

### 🐳 Docker
```
✅ Dockerfile optimizado (multi-stage build)
✅ Imagen final: ~250 MB
✅ Java 17 (Eclipse Temurin)
✅ Health check automático
✅ Perfil de producción configurado
✅ Puerto: 8080
```

### 📋 Scripts Creados
```
✅ DEPLOY_FACIL.bat        → Script TODO EN UNO (USA ESTE!)
✅ docker-build-push.bat   → Build y push a Docker Hub
✅ docker-compose.yml      → Para pruebas locales
✅ TODO_LISTO.md          → Guía completa
✅ QUICK_START.md         → Guía rápida
✅ DOCKER_COMMANDS.md     → Comandos útiles
```

---

## 🚀 EJECUTAR AHORA (3 PASOS)

### 1️⃣ Instalar Docker Desktop
```
https://www.docker.com/products/docker-desktop

→ Descargar
→ Instalar
→ Reiniciar PC
→ Abrir Docker Desktop
→ Esperar a que inicie (ícono deja de parpadear)
```

### 2️⃣ Ejecutar el Script
```cmd
cd "C:\Users\Juan\Desktop\Ciclo-8\Desarrollo de Soluciones IOT - Presencial\soft\parkeoya-backend"

DEPLOY_FACIL.bat
```

### 3️⃣ Seguir Instrucciones
```
→ Ingresar usuario Docker Hub
→ Ingresar contraseña
→ Esperar 10 minutos (compilación)
→ ¡LISTO!
```

---

## 🌐 DESPUÉS: DESPLEGAR EN LA NUBE

### 🟢 RENDER.COM (Recomendado - Gratis)
```
1. Ir a: https://render.com
2. Sign up (gratis)
3. New → Web Service
4. Deploy from Docker image
5. Pegar: TU_USUARIO/parkeoya-backend:latest
6. Port: 8080
7. Deploy

→ Te dará URL: https://parkeoya-backend.onrender.com
→ Tarda ~5 minutos en arrancar la primera vez
→ Swagger: https://parkeoya-backend.onrender.com/swagger-ui/index.html
```

### 🔵 RAILWAY.APP (Gratis)
```
1. Ir a: https://railway.app
2. Sign up con GitHub
3. New Project
4. Deploy Docker Image
5. Pegar: TU_USUARIO/parkeoya-backend:latest
6. Deploy

→ Te dará URL: https://parkeoya-backend.up.railway.app
→ Muy rápido (2-3 minutos)
```

### 🟣 FLY.IO (Gratis $5 crédito)
```
1. Instalar Fly CLI: https://fly.io/docs/hands-on/install-flyctl/
2. fly auth login
3. fly launch --image TU_USUARIO/parkeoya-backend:latest
4. Seguir wizard

→ URL: https://parkeoya-backend.fly.dev
→ Muy rápido y confiable
```

---

## 🧪 PROBAR LOCALMENTE (Antes de publicar)

```cmd
REM Ejecutar con Docker Compose
docker-compose up -d

REM Ver logs
docker-compose logs -f

REM Probar endpoints
curl http://localhost:8080/actuator/health
start http://localhost:8080/swagger-ui/index.html

REM Detener
docker-compose down
```

---

## ⏱️ TIEMPOS ESTIMADOS

```
Docker Desktop instalación:     5 minutos
Docker build (primera vez):     10 minutos
Docker push a Hub:              3 minutos
Deploy en Render:               5 minutos
─────────────────────────────────────────
TOTAL:                          23 minutos
```

---

## 📱 ENDPOINTS IMPORTANTES

Una vez desplegado:

```
Health Check:
GET https://TU-URL/actuator/health

Swagger UI:
GET https://TU-URL/swagger-ui/index.html

Registro Parking Owner:
POST https://TU-URL/api/v1/authentication/sign-up/parking-owner

Registro Driver:
POST https://TU-URL/api/v1/authentication/sign-up/driver

Login:
POST https://TU-URL/api/v1/authentication/sign-in
```

---

## 🆘 SOPORTE

### ❌ Error: "docker: command not found"
```
→ Docker Desktop no instalado
→ Instalar desde: https://docker.com
```

### ❌ Error: "Cannot connect to Docker daemon"
```
→ Docker Desktop no está corriendo
→ Abrir Docker Desktop
→ Esperar a que inicie completamente
```

### ❌ Error: "denied: requested access to resource"
```
→ No hiciste login en Docker Hub
→ Ejecutar: docker login
```

### ❌ Build falla
```
→ Verificar Java 17: java -version
→ Limpiar target: mvn clean
→ Intentar sin caché: docker build --no-cache
```

---

## 🎉 RESULTADO FINAL

Cuando termines tendrás:

✅ Backend desplegado en la nube
✅ URL pública del backend
✅ Swagger UI accesible
✅ Base de datos Aiven conectada
✅ API lista para el frontend
✅ Imagen Docker publicada en Docker Hub

---

## 📞 PRÓXIMO PASO

Avísame cuando:
1. ✅ Hayas publicado la imagen en Docker Hub
2. ✅ Tengas la URL del backend desplegado

Para que te ayude con:
- 🎨 Actualizar frontend para usar la nueva URL
- 🚀 Desplegar el frontend en Vercel
- 🧪 Probar flujo completo End-to-End

---

## 💡 TIP PRO

Mientras Docker compila (10 min), aprovecha para:
- Crear cuenta en Render.com
- Crear cuenta en Docker Hub (si no tienes)
- Tomar un café ☕

---

**COMANDO ÚNICO PARA EJECUTAR TODO:**
```cmd
DEPLOY_FACIL.bat
```

¡ASÍ DE SIMPLE! 🚀
