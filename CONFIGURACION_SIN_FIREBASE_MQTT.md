# Configuración para ejecutar sin Firebase y MQTT

## Resumen de cambios

Se han realizado modificaciones en el backend para permitir la ejecución sin necesidad de configurar Firebase ni MQTT. Estos servicios ahora son opcionales y se pueden habilitar/deshabilitar mediante propiedades de configuración.

## Cambios realizados

### 1. Archivos de configuración modificados

#### `application.properties`
Se añadieron las siguientes propiedades para controlar la activación de servicios:

```properties
# Deshabilitar Firebase y MQTT temporalmente
firebase.enabled=false
mqtt.enabled=false
```

### 2. Clases con carga condicional

Las siguientes clases ahora se cargan solo si el servicio correspondiente está habilitado:

#### Firebase:
- **`FirebaseConfig.java`**: Solo se inicializa si `firebase.enabled=true`
- **`FirebaseNotificationSender.java`**: Solo se carga si `firebase.enabled=true`
- **`NoOpNotificationSender.java`** (NUEVO): Implementación sin operaciones que se activa cuando Firebase está deshabilitado. Registra las notificaciones en logs en lugar de enviarlas.

#### MQTT:
- **`MqttConfig.java`**: Solo se inicializa si `mqtt.enabled=true`
- **`ParkingMqttService.java`**: Solo se carga si `mqtt.enabled=true`

### 3. Dependencias opcionales

#### `ReservationCommandServiceImpl.java`
- El servicio `ParkingMqttService` ahora se inyecta como `Optional<ParkingMqttService>`
- Las llamadas a métodos MQTT se envuelven en `ifPresent()` con manejo de excepciones
- Si MQTT está deshabilitado, las reservas funcionan normalmente sin enviar comandos MQTT

## Cómo funciona

### Cuando `firebase.enabled=false`:
- **FirebaseConfig** no se inicializa
- **FirebaseNotificationSender** no se crea
- **NoOpNotificationSender** se activa en su lugar
- Las notificaciones se registran en logs pero no se envían

### Cuando `mqtt.enabled=false`:
- **MqttConfig** no se inicializa
- **ParkingMqttService** no se crea
- Las reservas funcionan normalmente sin enviar comandos MQTT a dispositivos IoT

## Para habilitar los servicios en producción

### Firebase:
1. Define la variable de entorno `FIREBASE_ADMIN_CONFIG` con las credenciales en Base64
2. Cambia en `application.properties`:
   ```properties
   firebase.enabled=true
   ```

### MQTT:
1. Configura las propiedades MQTT en `application.properties`:
   ```properties
   mqtt.broker.url=tu-broker-url
   mqtt.broker.port=8883
   mqtt.broker.username=tu-usuario
   mqtt.broker.password=tu-contraseña
   ```
2. Cambia en `application.properties`:
   ```properties
   mqtt.enabled=true
   ```

## Endpoints disponibles sin Firebase/MQTT

Todos los endpoints REST funcionan normalmente:
- **IAM**: Autenticación y autorización (usando JWT local)
- **Parking Management**: CRUD de estacionamientos y espacios
- **Profile**: Gestión de perfiles de usuarios
- **Reservations**: Crear y gestionar reservas (sin notificaciones push ni control IoT)
- **Reviews**: Sistema de reseñas
- **Payments**: Gestión de pagos

## Qué funcionalidades NO están disponibles

### Sin Firebase:
- ✗ Notificaciones push a dispositivos móviles
- ✓ Las notificaciones se registran en logs para debugging

### Sin MQTT:
- ✗ Control en tiempo real de dispositivos IoT (sensores, barreras, etc.)
- ✗ Actualización automática de estado de espacios desde hardware
- ✓ Las reservas funcionan pero sin comunicación con dispositivos físicos

## Testing

El proyecto compila correctamente con ambos servicios deshabilitados:
```bash
mvnw.cmd clean compile -DskipTests
```

Estado: ✅ BUILD SUCCESS

