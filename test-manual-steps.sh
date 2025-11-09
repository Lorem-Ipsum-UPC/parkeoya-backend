# FLUJO COMPLETO DE PRUEBAS - PARKEOYA BACKEND
# Copia y pega cada comando en tu terminal

BASE_URL="http://localhost:8080/api/v1"

# ============================================
# PASO 1: REGISTRAR PARKING OWNER
# ============================================
curl -X POST "${BASE_URL}/authentication/sign-up/parking-owner" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner1@test.com",
    "password": "Test1234",
    "fullName": "Juan Parking Owner",
    "city": "Lima",
    "country": "Perú",
    "phone": "999111222",
    "companyName": "Parking Lima SAC",
    "ruc": "20111222333"
  }'

# ============================================
# PASO 2: LOGIN PARKING OWNER
# ============================================
curl -X POST "${BASE_URL}/authentication/sign-in" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner1@test.com",
    "password": "Test1234"
  }'

# 📝 COPIAR EL TOKEN DEL OWNER Y GUARDARLO AQUÍ:
OWNER_TOKEN="TU_TOKEN_AQUI"

# ============================================
# PASO 3: CREAR PARKING (con los 24 campos)
# ============================================
curl -X POST "${BASE_URL}/parkings" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${OWNER_TOKEN}" \
  -d '{
    "ownerId": 1,
    "name": "Estacionamiento Centro Lima",
    "description": "Parking seguro en pleno centro de Lima, vigilancia 24/7",
    "address": "Av. Arequipa 1234",
    "city": "Lima",
    "province": "Lima",
    "postalCode": "15046",
    "lat": -12.0464,
    "lng": -77.0428,
    "ratePerHour": 5.0,
    "dailyRate": 30.0,
    "monthlyRate": 300.0,
    "totalSpots": 100,
    "regularSpots": 80,
    "disabledSpots": 10,
    "electricSpots": 10,
    "availableSpots": 100,
    "totalRows": 10,
    "totalColumns": 10,
    "imageUrl": "https://example.com/parking.jpg",
    "operatingDays": "Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday",
    "open24Hours": true,
    "openingTime": "",
    "closingTime": ""
  }'

# 📝 COPIAR EL ID DEL PARKING:
PARKING_ID=1

# ============================================
# PASO 4: VERIFICAR PARKING CREADO
# ============================================
curl -X GET "${BASE_URL}/parkings/${PARKING_ID}" \
  -H "Authorization: Bearer ${OWNER_TOKEN}"

# ============================================
# PASO 5: LISTAR TODOS LOS PARKINGS
# ============================================
curl -X GET "${BASE_URL}/parkings" \
  -H "Authorization: Bearer ${OWNER_TOKEN}"

# ============================================
# PASO 6: REGISTRAR DRIVER
# ============================================
curl -X POST "${BASE_URL}/authentication/sign-up/driver" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "driver1@test.com",
    "password": "Test1234",
    "fullName": "Carlos Conductor",
    "city": "Lima",
    "country": "Perú",
    "phone": "999333444",
    "dni": "12345678"
  }'

# ============================================
# PASO 7: LOGIN DRIVER
# ============================================
curl -X POST "${BASE_URL}/authentication/sign-in" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "driver1@test.com",
    "password": "Test1234"
  }'

# 📝 COPIAR EL TOKEN DEL DRIVER:
DRIVER_TOKEN="TU_TOKEN_AQUI"

# ============================================
# PASO 8: BUSCAR PARKINGS DISPONIBLES
# ============================================
curl -X GET "${BASE_URL}/parkings" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}"

# ============================================
# PASO 9: CREAR RESERVA
# ============================================
curl -X POST "${BASE_URL}/reservations" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}" \
  -d '{
    "parkingId": 1,
    "driverId": 2,
    "startTime": "2025-11-10T10:00:00",
    "endTime": "2025-11-10T12:00:00",
    "vehiclePlate": "ABC-123",
    "vehicleType": "CAR"
  }'

# 📝 COPIAR EL ID DE LA RESERVA:
RESERVATION_ID=1

# ============================================
# PASO 10: VERIFICAR RESERVA
# ============================================
curl -X GET "${BASE_URL}/reservations/${RESERVATION_ID}" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}"

# ============================================
# PASO 11: LISTAR RESERVAS DEL DRIVER
# ============================================
curl -X GET "${BASE_URL}/reservations/driver/2" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}"

# ============================================
# PASO 12: LISTAR RESERVAS DEL PARKING (como Owner)
# ============================================
curl -X GET "${BASE_URL}/reservations/parking/${PARKING_ID}" \
  -H "Authorization: Bearer ${OWNER_TOKEN}"

# ============================================
# PASO 13: CANCELAR RESERVA (opcional)
# ============================================
curl -X DELETE "${BASE_URL}/reservations/${RESERVATION_ID}" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}"

# ============================================
# ENDPOINTS ADICIONALES ÚTILES
# ============================================

# Actualizar parking
curl -X PUT "${BASE_URL}/parkings/${PARKING_ID}" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${OWNER_TOKEN}" \
  -d '{
    "name": "Estacionamiento Centro Lima - Actualizado",
    "ratePerHour": 6.0,
    "availableSpots": 95
  }'

# Eliminar parking
curl -X DELETE "${BASE_URL}/parkings/${PARKING_ID}" \
  -H "Authorization: Bearer ${OWNER_TOKEN}"

# Health check
curl http://localhost:8080/actuator/health

# Swagger UI
# Abrir en navegador: http://localhost:8080/swagger-ui/index.html
