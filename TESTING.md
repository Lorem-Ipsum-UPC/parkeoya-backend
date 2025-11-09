# 🚗 Parkeoya Backend - Pruebas Sistema Completo

## ✅ Estado Actual

**Backend funcionando en PRODUCCIÓN** con base de datos Aiven MySQL Cloud.

## 🎯 Funcionalidades Probadas

### 1. **Sistema de Reservas con Estados**
- ✅ PENDING → Reserva creada
- ✅ CONFIRMED → Confirmada por propietario
- ✅ COMPLETED → Servicio completado
- ✅ CANCELED → Reserva cancelada

**Endpoints clave para el propietario:**
```
GET /api/v1/reservations/parking/{parkingId}/status/PENDING
GET /api/v1/reservations/parking/{parkingId}/status/CONFIRMED
GET /api/v1/reservations/parking/{parkingId}/status/COMPLETED
PATCH /api/v1/reservations/{id}?status=CONFIRMED
PATCH /api/v1/reservations/{id}?status=COMPLETED
```

### 2. **Sistema de Reviews**
- ✅ Conductor puede crear review después de reserva
- ✅ Consulta de reviews por estacionamiento
- ✅ Consulta de reviews por conductor
- ✅ Sistema de calificación (rating)

**Endpoints:**
```
POST /api/v1/reviews
GET /api/v1/reviews/parking/{parkingId}
GET /api/v1/reviews/driver/{driverId}
```

**⚠️ Nota importante:** El campo en el JSON se llama `driverId`, pero debe ser el `userId` del driver autenticado.

## 🧪 Ejecutar Pruebas

```bash
# 1. Asegurarse que el backend está corriendo en producción
java -jar target/parkeoya-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# 2. En otra terminal, ejecutar pruebas
bash test-sistema-completo-prod.sh
```

## 📊 Datos en Producción

**Base de datos:** Aiven MySQL Cloud  
**Host:** mysql-parkeoya-parqueoya.e.aivencloud.com:25208  
**Database:** defaultdb

## 🔑 Para el Dashboard del Propietario

El frontend puede obtener:

1. **Reservas pendientes** (requieren acción):
   ```javascript
   GET /api/v1/reservations/parking/${parkingId}/status/PENDING
   ```

2. **Reservas activas del día**:
   ```javascript
   GET /api/v1/reservations/parking/${parkingId}/status/CONFIRMED
   ```

3. **Historial completado**:
   ```javascript
   GET /api/v1/reservations/parking/${parkingId}/status/COMPLETED
   ```

4. **Reviews del estacionamiento**:
   ```javascript
   GET /api/v1/reviews/parking/${parkingId}
   ```

## 🎨 Vista Dashboard Sugerida

```
┌─────────────────────────────────────────┐
│  📊 Dashboard Propietario               │
├─────────────────────────────────────────┤
│  ⏳ Pendientes: 5     ✅ Hoy: 12       │
│  ✔️ Completadas: 156  ⭐ Rating: 4.5   │
└─────────────────────────────────────────┘
```

## 🐳 Deploy con Docker

### Construir y subir imagen a Docker Hub:
```bash
# Asegúrate que Docker Desktop está corriendo
bash docker-deploy.sh juancali
```

### Ejecutar con Docker:
```bash
docker pull juancali/parkeoya-backend:latest

docker run -d -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  --name parkeoya-backend \
  juancali/parkeoya-backend:latest
```

## �🛠️ Archivos del Proyecto

- `test-sistema-completo-prod.sh` - Script de pruebas automatizado
- `docker-deploy.sh` - Script para build y push a Docker Hub
- `README.md` - Documentación principal
- `application-prod.properties` - Configuración de producción

---

**Última actualización:** 2025-11-09  
**Estado:** ✅ Sistema funcionando en producción  
**Docker Hub:** loremipsumupc/parkeoya-backend
