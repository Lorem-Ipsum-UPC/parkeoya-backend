#!/bin/bash
# Test completo del flujo: Registro → Login → Crear Parking → Buscar → Reservar
# Flujo: Owner crea parking, Driver busca y reserva un espacio

BASE_URL="http://localhost:8080/api/v1"

# Colores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}"
echo "=========================================="
echo "   FLUJO COMPLETO - PARKEOYA BACKEND"
echo "=========================================="
echo -e "${NC}"
echo ""

# Función para extraer valor JSON
extract_json() {
    echo "$1" | grep -o "\"$2\":\"[^\"]*\"" | cut -d'"' -f4
}

extract_json_number() {
    echo "$1" | grep -o "\"$2\":[0-9]*" | cut -d':' -f2
}

# ====================================
# PASO 1: REGISTRAR PARKING OWNER
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 1: REGISTRAR PARKING OWNER          ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"
TIMESTAMP=$(date +%s)
OWNER_EMAIL="owner${TIMESTAMP}@test.com"
OWNER_PHONE="99911${TIMESTAMP:4:4}"  # Teléfono único
OWNER_RUC="201112223${TIMESTAMP:7:2}"  # RUC único

OWNER_RESPONSE=$(curl -s -X POST "${BASE_URL}/authentication/sign-up/parking-owner" \
  -H "Content-Type: application/json; charset=utf-8" \
  -d "{
    \"email\": \"${OWNER_EMAIL}\",
    \"password\": \"Test1234\",
    \"fullName\": \"Juan Parking Owner\",
    \"city\": \"Lima\",
    \"country\": \"Peru\",
    \"phone\": \"${OWNER_PHONE}\",
    \"companyName\": \"Parking Lima SAC\",
    \"ruc\": \"${OWNER_RUC}\"
  }")

echo "$OWNER_RESPONSE" | jq '.' 2>/dev/null || echo "$OWNER_RESPONSE"
OWNER_ID=$(extract_json_number "$OWNER_RESPONSE" "id")
echo -e "${GREEN}✓ Owner registrado - ID: ${OWNER_ID} - Email: ${OWNER_EMAIL}${NC}"
echo ""
sleep 1

# ====================================
# PASO 2: LOGIN PARKING OWNER
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 2: LOGIN PARKING OWNER              ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

OWNER_LOGIN=$(curl -s -X POST "${BASE_URL}/authentication/sign-in" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"${OWNER_EMAIL}\",
    \"password\": \"Test1234\"
  }")

echo "$OWNER_LOGIN" | jq '.' 2>/dev/null || echo "$OWNER_LOGIN"
OWNER_TOKEN=$(extract_json "$OWNER_LOGIN" "token")
echo -e "${GREEN}✓ Owner autenticado - Token obtenido${NC}"
echo ""
sleep 1

# ====================================
# PASO 3: CREAR PARKING
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 3: CREAR PARKING                    ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

PARKING_RESPONSE=$(curl -s -X POST "${BASE_URL}/parkings" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${OWNER_TOKEN}" \
  -d "{
    \"ownerId\": ${OWNER_ID},
    \"name\": \"Estacionamiento Centro Lima\",
    \"description\": \"Parking seguro en pleno centro de Lima, vigilancia 24/7\",
    \"address\": \"Av. Arequipa 1234\",
    \"city\": \"Lima\",
    \"province\": \"Lima\",
    \"postalCode\": \"15046\",
    \"lat\": -12.0464,
    \"lng\": -77.0428,
    \"ratePerHour\": 5.0,
    \"dailyRate\": 30.0,
    \"monthlyRate\": 300.0,
    \"totalSpots\": 100,
    \"regularSpots\": 80,
    \"disabledSpots\": 10,
    \"electricSpots\": 10,
    \"availableSpots\": 100,
    \"totalRows\": 10,
    \"totalColumns\": 10,
    \"imageUrl\": \"https://example.com/parking.jpg\",
    \"operatingDays\": \"Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday\",
    \"open24Hours\": true,
    \"openingTime\": \"\",
    \"closingTime\": \"\"
  }")

echo "$PARKING_RESPONSE" | jq '.' 2>/dev/null || echo "$PARKING_RESPONSE"
PARKING_ID=$(extract_json_number "$PARKING_RESPONSE" "id")
echo -e "${GREEN}✓ Parking creado - ID: ${PARKING_ID}${NC}"
echo ""
sleep 1

# ====================================
# PASO 4: VERIFICAR PARKING CREADO
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 4: VERIFICAR PARKING CREADO         ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

GET_PARKING=$(curl -s -X GET "${BASE_URL}/parkings/${PARKING_ID}" \
  -H "Authorization: Bearer ${OWNER_TOKEN}")

echo "$GET_PARKING" | jq '.' 2>/dev/null || echo "$GET_PARKING"
echo -e "${GREEN}✓ Parking verificado correctamente${NC}"
echo ""
sleep 1

# ====================================
# PASO 5: LISTAR TODOS LOS PARKINGS
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 5: LISTAR TODOS LOS PARKINGS        ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

ALL_PARKINGS=$(curl -s -X GET "${BASE_URL}/parkings" \
  -H "Authorization: Bearer ${OWNER_TOKEN}")

echo "$ALL_PARKINGS" | jq '.' 2>/dev/null || echo "$ALL_PARKINGS"
echo -e "${GREEN}✓ Parkings listados${NC}"
echo ""
sleep 1

# ====================================
# PASO 6: REGISTRAR DRIVER
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 6: REGISTRAR DRIVER                 ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

DRIVER_EMAIL="driver${TIMESTAMP}@test.com"
DRIVER_DNI="${TIMESTAMP:0:8}"  # Usar los primeros 8 dígitos del timestamp como DNI único
DRIVER_PHONE="99933${TIMESTAMP:4:4}"  # Teléfono único

DRIVER_RESPONSE=$(curl -s -X POST "${BASE_URL}/authentication/sign-up/driver" \
  -H "Content-Type: application/json; charset=utf-8" \
  -d "{
    \"email\": \"${DRIVER_EMAIL}\",
    \"password\": \"Test1234\",
    \"fullName\": \"Carlos Conductor\",
    \"city\": \"Lima\",
    \"country\": \"Peru\",
    \"phone\": \"${DRIVER_PHONE}\",
    \"dni\": \"${DRIVER_DNI}\"
  }")

echo "$DRIVER_RESPONSE" | jq '.' 2>/dev/null || echo "$DRIVER_RESPONSE"
DRIVER_ID=$(extract_json_number "$DRIVER_RESPONSE" "id")
echo -e "${GREEN}✓ Driver registrado - ID: ${DRIVER_ID} - Email: ${DRIVER_EMAIL}${NC}"
echo ""
sleep 1

# ====================================
# PASO 7: LOGIN DRIVER
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 7: LOGIN DRIVER                     ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

DRIVER_LOGIN=$(curl -s -X POST "${BASE_URL}/authentication/sign-in" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"${DRIVER_EMAIL}\",
    \"password\": \"Test1234\"
  }")

echo "$DRIVER_LOGIN" | jq '.' 2>/dev/null || echo "$DRIVER_LOGIN"
DRIVER_TOKEN=$(extract_json "$DRIVER_LOGIN" "token")
echo -e "${GREEN}✓ Driver autenticado - Token obtenido${NC}"
echo ""
sleep 1

# ====================================
# PASO 8: BUSCAR PARKINGS DISPONIBLES
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 8: BUSCAR PARKINGS DISPONIBLES      ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

SEARCH_PARKINGS=$(curl -s -X GET "${BASE_URL}/parkings" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}")

echo "$SEARCH_PARKINGS" | jq '.' 2>/dev/null || echo "$SEARCH_PARKINGS"
echo -e "${GREEN}✓ Parkings disponibles encontrados${NC}"
echo ""
sleep 1

# ====================================
# PASO 9: CREAR RESERVA
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 9: CREAR RESERVA                    ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

# Usar fechas fijas para evitar problemas con date en Git Bash
START_TIME="2025-11-10T10:00:00"
END_TIME="2025-11-10T12:00:00"

RESERVATION_RESPONSE=$(curl -s -X POST "${BASE_URL}/reservations" \
  -H "Content-Type: application/json; charset=utf-8" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}" \
  -d "{
    \"parkingId\": ${PARKING_ID},
    \"driverId\": ${DRIVER_ID},
    \"startTime\": \"${START_TIME}\",
    \"endTime\": \"${END_TIME}\",
    \"vehiclePlate\": \"ABC-123\",
    \"vehicleType\": \"CAR\"
  }")

echo "$RESERVATION_RESPONSE" | jq '.' 2>/dev/null || echo "$RESERVATION_RESPONSE"
RESERVATION_ID=$(extract_json_number "$RESERVATION_RESPONSE" "id")
echo -e "${GREEN}✓ Reserva creada - ID: ${RESERVATION_ID}${NC}"
echo ""
sleep 1

# ====================================
# PASO 10: VERIFICAR RESERVA
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 10: VERIFICAR RESERVA               ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

GET_RESERVATION=$(curl -s -X GET "${BASE_URL}/reservations/${RESERVATION_ID}" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}")

echo "$GET_RESERVATION" | jq '.' 2>/dev/null || echo "$GET_RESERVATION"
echo -e "${GREEN}✓ Reserva verificada correctamente${NC}"
echo ""
sleep 1

# ====================================
# PASO 11: LISTAR RESERVAS DEL DRIVER
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 11: LISTAR RESERVAS DEL DRIVER      ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

DRIVER_RESERVATIONS=$(curl -s -X GET "${BASE_URL}/reservations/driver/${DRIVER_ID}" \
  -H "Authorization: Bearer ${DRIVER_TOKEN}")

echo "$DRIVER_RESERVATIONS" | jq '.' 2>/dev/null || echo "$DRIVER_RESERVATIONS"
echo -e "${GREEN}✓ Reservas del driver listadas${NC}"
echo ""
sleep 1

# ====================================
# PASO 12: LISTAR RESERVAS DEL PARKING
# ====================================
echo -e "${YELLOW}╔════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  PASO 12: LISTAR RESERVAS DEL PARKING     ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════╝${NC}"

PARKING_RESERVATIONS=$(curl -s -X GET "${BASE_URL}/reservations/parking/${PARKING_ID}" \
  -H "Authorization: Bearer ${OWNER_TOKEN}")

echo "$PARKING_RESERVATIONS" | jq '.' 2>/dev/null || echo "$PARKING_RESERVATIONS"
echo -e "${GREEN}✓ Reservas del parking listadas${NC}"
echo ""

# ====================================
# RESUMEN FINAL
# ====================================
echo -e "${GREEN}"
echo "=========================================="
echo "   ✅ FLUJO COMPLETO EJECUTADO"
echo "=========================================="
echo -e "${NC}"
echo ""
echo "📊 RESUMEN:"
echo "────────────────────────────────────────"
echo "  Owner ID:        ${OWNER_ID}"
echo "  Owner Email:     ${OWNER_EMAIL}"
echo "  Owner Token:     ${OWNER_TOKEN:0:20}..."
echo ""
echo "  Parking ID:      ${PARKING_ID}"
echo "  Parking Name:    Estacionamiento Centro Lima"
echo "  Location:        Lima, Perú"
echo "  Rate/Hour:       S/ 5.00"
echo ""
echo "  Driver ID:       ${DRIVER_ID}"
echo "  Driver Email:    ${DRIVER_EMAIL}"
echo "  Driver Token:    ${DRIVER_TOKEN:0:20}..."
echo ""
echo "  Reservation ID:  ${RESERVATION_ID}"
echo "  Vehicle Plate:   ABC-123"
echo "  Start Time:      ${START_TIME}"
echo "  End Time:        ${END_TIME}"
echo "────────────────────────────────────────"
echo ""
echo "🌐 Swagger UI: http://localhost:8080/swagger-ui/index.html"
echo "📈 Health Check: http://localhost:8080/actuator/health"
echo ""
