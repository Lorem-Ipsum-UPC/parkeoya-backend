# 🚗 Parkeoya Backend

Sistema de gestión de estacionamientos inteligente con reservas en tiempo real, integración IoT y notificaciones push.

## 📋 Descripción

**Parkeoya** es una plataforma backend desarrollada con Spring Boot que permite a los usuarios encontrar, reservar y pagar por espacios de estacionamiento en tiempo real. El sistema integra dispositivos IoT para monitoreo de espacios, notificaciones push mediante Firebase, y comunicación MQTT para control de hardware.

### Características Principales

- 🔐 **Autenticación y Autorización**: Sistema JWT con roles (Admin, Conductor, Dueño de Estacionamiento)
- 🅿️ **Gestión de Estacionamientos**: CRUD completo con información de ubicación y disponibilidad
- 📅 **Sistema de Reservas**: Reserva de espacios con control de disponibilidad en tiempo real
- 💳 **Procesamiento de Pagos**: Gestión de pagos por reservas
- ⭐ **Sistema de Reseñas**: Calificación y comentarios de estacionamientos
- 📱 **Notificaciones Push**: Firebase Cloud Messaging (opcional)
- 🔌 **Integración IoT**: Comunicación MQTT con sensores y dispositivos (opcional)
- 📊 **Gestión de Dispositivos**: Control de sensores, cámaras y servidores Edge
- 👤 **Perfiles de Usuario**: Gestión de conductores y dueños de estacionamientos

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura hexagonal (puertos y adaptadores)** con Domain-Driven Design (DDD):

```
src/main/java/upc/edu/pe/parkeoya/backend/v1/
├── deviceManagement/          # Gestión de dispositivos IoT
│   ├── application/           # Casos de uso y servicios de aplicación
│   ├── domain/                # Entidades, agregados y lógica de negocio
│   ├── infrastructure/        # Implementaciones de repositorios y gateways
│   └── interfaces/            # Controladores REST y DTOs
├── iam/                       # Identity and Access Management
│   ├── application/           # Servicios de autenticación y autorización
│   ├── domain/                # Entidades User, Role
│   ├── infrastructure/        # Seguridad Spring, JWT, repositorios
│   └── interfaces/            # Endpoints de autenticación
├── notifications/             # Sistema de notificaciones push
│   ├── application/           # Servicios de notificación
│   ├── domain/                # Entidades FCM Token
│   ├── infrastructure/        # Integración Firebase
│   └── interfaces/            # Endpoints de tokens
├── parkingManagement/         # Gestión de estacionamientos y espacios
│   ├── application/           # CRUD de parkings y spots
│   ├── domain/                # Entidades Parking, ParkingSpot
│   ├── infrastructure/        # Repositorios JPA
│   └── interfaces/            # API REST de parkings
├── payment/                   # Procesamiento de pagos
│   ├── application/           # Servicios de pago
│   ├── domain/                # Entidades Payment
│   ├── infrastructure/        # Persistencia
│   └── interfaces/            # API de pagos
├── profile/                   # Perfiles de usuario
│   ├── application/           # Gestión de conductores y dueños
│   ├── domain/                # Entidades Driver, ParkingOwner
│   ├── infrastructure/        # Repositorios
│   └── interfaces/            # API de perfiles
├── reservations/              # Sistema de reservas
│   ├── application/           # Lógica de reservas
│   ├── domain/                # Entidad Reservation
│   ├── infrastructure/        # Persistencia y servicios externos
│   └── interfaces/            # API de reservas
├── reviews/                   # Sistema de reseñas
│   ├── application/           # Gestión de reseñas
│   ├── domain/                # Entidad Review
│   ├── infrastructure/        # Repositorios
│   └── interfaces/            # API de reseñas
└── shared/                    # Componentes compartidos
    ├── domain/                # Clases base, excepciones
    ├── infrastructure/        # Configuraciones, utilidades
    └── interfaces/            # Filtros, interceptores
```

### Capas de la Arquitectura

1. **Domain (Dominio)**: Lógica de negocio pura, entidades, agregados, value objects
2. **Application (Aplicación)**: Casos de uso, servicios de aplicación, comandos y queries
3. **Infrastructure (Infraestructura)**: Implementaciones técnicas (JPA, Firebase, MQTT)
4. **Interfaces (Presentación)**: API REST, DTOs, transformadores

### Patrones Utilizados

- **Repository Pattern**: Abstracción de acceso a datos
- **CQRS Light**: Separación de comandos y queries
- **Aggregate Pattern**: Manejo de consistencia de datos
- **Facade Pattern**: Comunicación entre bounded contexts
- **Strategy Pattern**: Diferentes implementaciones de notificaciones

## 🛠️ Tecnologías

### Core
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.5.6** - Framework principal
- **Maven** - Gestión de dependencias

### Base de Datos
- **MySQL 8.0** - Base de datos relacional
- **Hibernate/JPA** - ORM
- **HikariCP** - Connection pooling

### Seguridad
- **Spring Security** - Framework de seguridad
- **JWT (jjwt 0.12.3)** - Tokens de autenticación
- **BCrypt** - Encriptación de contraseñas

### Documentación API
- **SpringDoc OpenAPI 2.7.0** - Generación de documentación
- **Swagger UI** - Interfaz interactiva de API

### Integración IoT (Opcional)
- **Eclipse Paho MQTT 1.2.5** - Cliente MQTT
- **Spring Integration MQTT 6.5.0** - Integración MQTT

### Notificaciones (Opcional)
- **Firebase Admin SDK 9.2.0** - Notificaciones push

### Desarrollo
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Hot reload en desarrollo

## 📦 Prerequisitos

- **JDK 17 o superior** (el proyecto usa Java 17, pero funciona con JDK 24)
- **MySQL 8.0** instalado y ejecutándose
- **Maven** (o usar el Maven Wrapper incluido)
- **Git** para clonar el repositorio

## 🚀 Configuración y Despliegue

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Lorem-Ipsum-UPC/parkeoya-backend.git
cd parkeoya-backend
```

### 2. Configurar Base de Datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS parkeoya;
```

O desde la terminal:

```bash
# Windows
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS parkeoya;"

# Linux/Mac
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS parkeoya;"
```

### 3. Configurar Variables de Entorno

Edita el archivo `src/main/resources/application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/parkeoya?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI

# JWT Secret (CAMBIAR EN PRODUCCIÓN)
authorization.jwt.secret=8Zz5tw0Ionm3XPZZfN0NOml3z9FMfmpgXwovR9fp6ryDIoGRM8EPHAB6iHsc0fb
authorization.jwt.expiration.days=7

# Servicios opcionales (por defecto deshabilitados)
firebase.enabled=false
mqtt.enabled=false
```

### 4. Ejecutar la Aplicación

#### Opción A: Con Maven Wrapper (Recomendado)

**Windows:**
```powershell
# Configurar JAVA_HOME
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"

# Ejecutar aplicación
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
# Configurar JAVA_HOME
export JAVA_HOME=/path/to/jdk-17

# Dar permisos de ejecución
chmod +x mvnw

# Ejecutar aplicación
./mvnw spring-boot:run
```

#### Opción B: Compilar y ejecutar JAR

```bash
# Compilar
./mvnw clean package -DskipTests

# Ejecutar
java -jar target/parkeoya-0.0.1-SNAPSHOT.jar
```

### 5. Verificar la Aplicación

Una vez iniciada, la aplicación estará disponible en:

- **API Base**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔧 Configuración Avanzada

### Habilitar Firebase (Notificaciones Push)

1. Obtén las credenciales de Firebase (`serviceAccountKey.json`)

2. Convierte el archivo a Base64:

**Windows (PowerShell):**
```powershell
[Convert]::ToBase64String([System.IO.File]::ReadAllBytes("serviceAccountKey.json"))
```

**Linux/Mac:**
```bash
base64 -w 0 serviceAccountKey.json
```

3. Configura la variable de entorno:

```bash
export FIREBASE_ADMIN_CONFIG="tu_base64_aqui"
```

4. En `application.properties`:
```properties
firebase.enabled=true
```

### Habilitar MQTT (IoT)

En `application.properties`:

```properties
mqtt.enabled=true
mqtt.broker.url=ssl://your-broker.com
mqtt.broker.port=8883
mqtt.broker.username=tu_usuario
mqtt.broker.password=tu_password
```

### Cambiar Puerto del Servidor

```properties
server.port=8081
```

## 📚 API Endpoints Principales

### Autenticación

```http
POST /api/v1/authentication/sign-up
POST /api/v1/authentication/sign-in
```

### Estacionamientos

```http
GET    /api/v1/parkings
POST   /api/v1/parkings
GET    /api/v1/parkings/{id}
PUT    /api/v1/parkings/{id}
DELETE /api/v1/parkings/{id}
GET    /api/v1/parkings/search?lat=&lng=&radius=
```

### Reservas

```http
GET    /api/v1/reservations
POST   /api/v1/reservations
GET    /api/v1/reservations/{id}
PUT    /api/v1/reservations/{id}/cancel
GET    /api/v1/reservations/driver/{driverId}
GET    /api/v1/reservations/parking/{parkingId}
```

### Pagos

```http
POST   /api/v1/payments
GET    /api/v1/payments/{reservationId}
```

### Reseñas

```http
POST   /api/v1/reviews
GET    /api/v1/reviews/parking/{parkingId}
GET    /api/v1/reviews/driver/{driverId}
```

Ver documentación completa en Swagger UI.

## 🧪 Testing

Ejecutar tests:

```bash
./mvnw test
```

Ejecutar tests con cobertura:

```bash
./mvnw verify
```

## 🐳 Despliegue con Docker

### Usando Docker Compose (Próximamente)

```bash
docker-compose up -d
```

### Construir imagen Docker

```bash
docker build -t parkeoya-backend:latest .
```

### Ejecutar contenedor

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/parkeoya \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  parkeoya-backend:latest
```

## 📝 Modelo de Datos

### Entidades Principales

- **User**: Usuarios del sistema
- **Role**: Roles (ADMIN, DRIVER, PARKING_OWNER)
- **Driver**: Perfil de conductor
- **ParkingOwner**: Perfil de dueño de estacionamiento
- **Parking**: Estacionamiento
- **ParkingSpot**: Espacio de estacionamiento
- **Reservation**: Reserva
- **Payment**: Pago
- **Review**: Reseña
- **Device**: Dispositivo IoT (sensor, cámara)
- **EdgeServer**: Servidor Edge para gestión de dispositivos
- **FcmToken**: Token de notificación Firebase

## 🔒 Seguridad

- Autenticación basada en JWT
- Contraseñas encriptadas con BCrypt
- Autorización por roles (RBAC)
- CORS configurado
- Protección contra CSRF
- Validación de entrada en todos los endpoints

## 🌍 Variables de Entorno

| Variable | Descripción | Requerida | Default |
|----------|-------------|-----------|---------|
| `SPRING_DATASOURCE_URL` | URL de conexión MySQL | ✅ | - |
| `SPRING_DATASOURCE_USERNAME` | Usuario de MySQL | ✅ | - |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de MySQL | ✅ | - |
| `AUTHORIZATION_JWT_SECRET` | Secreto para JWT | ✅ | - |
| `AUTHORIZATION_JWT_EXPIRATION_DAYS` | Días de expiración del token | ❌ | 7 |
| `FIREBASE_ENABLED` | Habilitar Firebase | ❌ | false |
| `FIREBASE_ADMIN_CONFIG` | Config Firebase en Base64 | ⚠️ | - |
| `MQTT_ENABLED` | Habilitar MQTT | ❌ | false |
| `MQTT_BROKER_URL` | URL del broker MQTT | ⚠️ | - |

## 🐛 Troubleshooting

### Error: Cannot connect to database

Verifica que MySQL esté corriendo:

```bash
# Windows
Get-Service -Name "*mysql*"

# Linux
sudo systemctl status mysql
```

### Error: Port 8080 already in use

Cambia el puerto en `application.properties`:

```properties
server.port=8081
```

### Error: Lombok getters/setters not working

Instala el plugin de Lombok en tu IDE:
- **IntelliJ**: Settings → Plugins → Buscar "Lombok"
- **Eclipse**: Descargar `lombok.jar` y ejecutar instalador
- **VS Code**: Instalar extensión "Lombok Annotations Support"

## 📄 Documentación Adicional

- [Configuración sin Firebase/MQTT](./CONFIGURACION_SIN_FIREBASE_MQTT.md)
