# 🚀 GUÍA RÁPIDA - Desplegar Backend en Docker

## ✅ PASOS INMEDIATOS

### 1️⃣ Instalar Docker Desktop (si no lo tienes)
```
https://www.docker.com/products/docker-desktop
```
- Descarga e instala
- Reinicia tu PC
- Abre Docker Desktop y espera a que el ícono deje de parpadear

### 2️⃣ Ejecutar el Script
Abre PowerShell o CMD en la carpeta `parkeoya-backend` y ejecuta:

```cmd
docker-build-push.bat
```

### 3️⃣ Seguir las Instrucciones
1. Te pedirá tu **usuario de Docker Hub** (créalo gratis en hub.docker.com si no tienes)
2. Te pedirá tu **contraseña**
3. Empezará a compilar (tomará 5-10 minutos)
4. Cuando pregunte si quieres publicar, escribe: **s**

### 4️⃣ Obtener el Link
Al finalizar verás algo como:
```
docker pull TU_USUARIO/parkeoya-backend:latest
```

**¡ESE ES EL LINK QUE NECESITAS COMPARTIR!** 🎉

---

## 📋 Información de la Imagen

- **Base de datos**: Ya configurada con Aiven MySQL
- **Puerto**: 8080
- **Health Check**: http://localhost:8080/actuator/health
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html

## 🔧 Si hay algún error

### Error: "docker: command not found"
→ Instala Docker Desktop primero

### Error: "Cannot connect to Docker daemon"
→ Abre Docker Desktop y espera a que inicie completamente

### Error: "denied: requested access to the resource is denied"
→ Verifica que ejecutaste `docker login` correctamente

---

## ✨ DESPUÉS DE PUBLICAR

Podrás desplegar en cualquier plataforma:

### Render.com (GRATIS)
1. Ve a https://render.com
2. Crea "New Web Service"
3. Selecciona "Deploy from Docker image"
4. Pega: `TU_USUARIO/parkeoya-backend:latest`
5. Puerto: 8080
6. ¡Deploy!

### Railway.app (GRATIS)
1. Ve a https://railway.app
2. "New Project" → "Deploy Docker Image"
3. Pega: `TU_USUARIO/parkeoya-backend:latest`
4. ¡Deploy!

---

## 🎯 RESUMEN ULTRA RÁPIDO

```bash
# 1. Instalar Docker Desktop
# 2. Ejecutar:
docker-build-push.bat

# 3. Ingresar credenciales Docker Hub
# 4. Esperar 5-10 minutos
# 5. Confirmar publicación (s)
# 6. Copiar el link que te da
# 7. ¡Listo para desplegar! 🚀
```

---

**💡 TIP**: Mientras compila, puedes ir creando tu cuenta en Render.com o Railway.app
