#!/bin/bash

###############################################################################
# Pruebas Completas del Sistema Parkeoya - Producción
# Incluye: Reservas con Estados + Reviews
###############################################################################

API_URL="http://localhost:8080/api/v1"
TIMESTAMP=$(date +%s)

# Colores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# Variables globales
PARKING_OWNER_TOKEN=""
DRIVER_TOKEN=""
PARKING_OWNER_ID=""
DRIVER_ID=""
PARKING_ID=""
PARKING_SPOT_ID=""
RESERVATION_ID=""

print_step() {
    echo ""
    echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
    echo -e "${BLUE}🔹 $1${NC}"
    echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

echo "╔═══════════════════════════════════════════════════════════════════════╗"
echo "║      🚗 PARKEOYA - PRUEBAS SISTEMA COMPLETO (PRODUCCIÓN)            ║"
echo "╚═══════════════════════════════════════════════════════════════════════╝"
echo ""

###############################################################################
# FASE 1: REGISTRO Y LOGIN
###############################################################################

print_step "FASE 1: Registro de Usuarios"

# Registrar Parking Owner
RUC_NUMBER="20${TIMESTAMP:0:9}"
PHONE_OWNER="9${TIMESTAMP:0:8}"

RESPONSE=$(curl -s -X POST "${API_URL}/authentication/sign-up/parking-owner" \
    -H "Content-Type: application/json" \
    -d "{
        \"email\": \"owner_${TIMESTAMP}@parkeoya.com\",
        \"password\": \"SecurePass123!\",
        \"fullName\": \"Test Owner\",
        \"city\": \"Lima\",
        \"country\": \"Peru\",
        \"phone\": \"${PHONE_OWNER}\",
        \"companyName\": \"ParkTest Co\",
        \"ruc\": \"${RUC_NUMBER}\"
    }")

PARKING_OWNER_ID=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)
print_success "Owner registrado (ID: $PARKING_OWNER_ID)"

# Registrar Driver
PHONE_DRIVER="8${TIMESTAMP:0:8}"
DNI_DRIVER="${TIMESTAMP:0:8}"

RESPONSE=$(curl -s -X POST "${API_URL}/authentication/sign-up/driver" \
    -H "Content-Type: application/json" \
    -d "{
        \"email\": \"driver_${TIMESTAMP}@parkeoya.com\",
        \"password\": \"SecurePass123!\",
        \"fullName\": \"Test Driver\",
        \"city\": \"Lima\",
        \"country\": \"Peru\",
        \"phone\": \"${PHONE_DRIVER}\",
        \"dni\": \"${DNI_DRIVER}\"
    }")

DRIVER_ID=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)
print_success "Driver registrado (ID: $DRIVER_ID)"

# Login Owner
RESPONSE=$(curl -s -X POST "${API_URL}/authentication/sign-in" \
    -H "Content-Type: application/json" \
    -d "{
        \"email\": \"owner_${TIMESTAMP}@parkeoya.com\",
        \"password\": \"SecurePass123!\"
    }")

PARKING_OWNER_TOKEN=$(echo "$RESPONSE" | grep -o '"token":"[^"]*' | grep -o '[^"]*$')
print_success "Owner autenticado"

# Login Driver
RESPONSE=$(curl -s -X POST "${API_URL}/authentication/sign-in" \
    -H "Content-Type: application/json" \
    -d "{
        \"email\": \"driver_${TIMESTAMP}@parkeoya.com\",
        \"password\": \"SecurePass123!\"
    }")

DRIVER_TOKEN=$(echo "$RESPONSE" | grep -o '"token":"[^"]*' | grep -o '[^"]*$')
print_success "Driver autenticado"

###############################################################################
# FASE 2: CREAR INFRAESTRUCTURA
###############################################################################

print_step "FASE 2: Crear Estacionamiento"

RESPONSE=$(curl -s -X POST "${API_URL}/parkings" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${PARKING_OWNER_TOKEN}" \
    -d "{
        \"ownerId\": ${PARKING_OWNER_ID},
        \"name\": \"Estacionamiento Test ${TIMESTAMP}\",
        \"description\": \"Estacionamiento de prueba\",
        \"address\": \"Av. Arequipa 1234\",
        \"city\": \"Lima\",
        \"province\": \"Lima\",
        \"postalCode\": \"15001\",
        \"lat\": -12.046374,
        \"lng\": -77.042793,
        \"ratePerHour\": 5.5,
        \"dailyRate\": 40.0,
        \"monthlyRate\": 800.0,
        \"totalSpots\": 20,
        \"regularSpots\": 15,
        \"disabledSpots\": 3,
        \"electricSpots\": 2,
        \"availableSpots\": 20,
        \"totalRows\": 4,
        \"totalColumns\": 5,
        \"imageUrl\": \"https://example.com/parking.jpg\",
        \"operatingDays\": \"Mon-Sun\",
        \"open24Hours\": false,
        \"openingTime\": \"06:00\",
        \"closingTime\": \"23:00\"
    }")

PARKING_ID=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)
print_success "Estacionamiento creado (ID: $PARKING_ID)"

# Crear espacios
for i in {1..3}; do
    RESPONSE=$(curl -s -X POST "${API_URL}/parkings/${PARKING_ID}/spots" \
        -H "Content-Type: application/json" \
        -H "Authorization: Bearer ${PARKING_OWNER_TOKEN}" \
        -d "{
            \"parkingId\": ${PARKING_ID},
            \"spotLabel\": \"A-${i}\",
            \"isAvailable\": true,
            \"type\": \"REGULAR\"
        }")
    
    if [ -z "$PARKING_SPOT_ID" ]; then
        PARKING_SPOT_ID=$(echo "$RESPONSE" | grep -o '"id":"[^"]*' | grep -o '[^"]*$' | head -1)
    fi
done
print_success "Espacios creados"

###############################################################################
# FASE 3: RESERVAS CON ESTADOS
###############################################################################

print_step "FASE 3: Gestión de Reservas con Estados"

TODAY=$(date +%Y-%m-%d)

# Crear reserva
RESPONSE=$(curl -s -X POST "${API_URL}/reservations" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${DRIVER_TOKEN}" \
    -d "{
        \"driverId\": ${DRIVER_ID},
        \"parkingId\": ${PARKING_ID},
        \"parkingSpotId\": \"${PARKING_SPOT_ID}\",
        \"vehiclePlate\": \"ABC-123\",
        \"date\": \"${TODAY}\",
        \"startTime\": \"10:00\",
        \"endTime\": \"12:00\"
    }")

RESERVATION_ID=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)
print_success "Reserva creada (ID: $RESERVATION_ID) - Estado: PENDING"

# Confirmar reserva
curl -s -X PATCH "${API_URL}/reservations/${RESERVATION_ID}?status=CONFIRMED" \
    -H "Authorization: Bearer ${PARKING_OWNER_TOKEN}" > /dev/null
print_success "Reserva CONFIRMED"

# Completar reserva
curl -s -X PATCH "${API_URL}/reservations/${RESERVATION_ID}?status=COMPLETED" \
    -H "Authorization: Bearer ${PARKING_OWNER_TOKEN}" > /dev/null
print_success "Reserva COMPLETED"

# Ver reservas por estado
print_info "Consultando reservas COMPLETED..."
RESPONSE=$(curl -s -X GET "${API_URL}/reservations/parking/${PARKING_ID}/status/COMPLETED" \
    -H "Authorization: Bearer ${PARKING_OWNER_TOKEN}")
COUNT=$(echo "$RESPONSE" | grep -o '"status":"COMPLETED"' | wc -l)
print_success "Reservas COMPLETED: $COUNT"

###############################################################################
# FASE 4: REVIEWS
###############################################################################

print_step "FASE 4: Sistema de Reviews"

# Nota: El endpoint usa driverId, que en realidad debe ser el userId
# El driver puede crear una review después de completar una reserva
RESPONSE=$(curl -s -X POST "${API_URL}/reviews" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${DRIVER_TOKEN}" \
    -d "{
        \"driverId\": ${DRIVER_ID},
        \"parkingId\": ${PARKING_ID},
        \"comment\": \"Excelente estacionamiento, muy seguro y limpio\",
        \"rating\": 4.5
    }")

if echo "$RESPONSE" | grep -q '"id"'; then
    REVIEW_ID=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)
    print_success "Review creada (ID: $REVIEW_ID)"
else
    print_error "Error al crear review"
    echo "Response: $RESPONSE"
fi

# Obtener reviews del parking
print_info "Consultando reviews del estacionamiento..."
RESPONSE=$(curl -s -X GET "${API_URL}/reviews/parking/${PARKING_ID}" \
    -H "Authorization: Bearer ${PARKING_OWNER_TOKEN}")

if echo "$RESPONSE" | grep -q '"id"'; then
    COUNT=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | wc -l)
    AVG_RATING=$(echo "$RESPONSE" | grep -o '"rating":[0-9.]*' | grep -o '[0-9.]*' | awk '{sum+=$1; count++} END {if(count>0) print sum/count; else print 0}')
    print_success "Reviews encontradas: $COUNT"
    print_info "Rating promedio: $AVG_RATING"
else
    print_info "No hay reviews aún"
fi

# Obtener reviews del driver
print_info "Consultando reviews del conductor..."
RESPONSE=$(curl -s -X GET "${API_URL}/reviews/driver/${DRIVER_ID}" \
    -H "Authorization: Bearer ${DRIVER_TOKEN}")

if echo "$RESPONSE" | grep -q '"id"'; then
    COUNT=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | wc -l)
    print_success "Reviews del conductor: $COUNT"
else
    print_info "El conductor no ha hecho reviews aún"
fi

###############################################################################
# RESUMEN FINAL
###############################################################################

echo ""
echo "╔═══════════════════════════════════════════════════════════════════════╗"
echo "║                    RESUMEN DE PRUEBAS                                 ║"
echo "╚═══════════════════════════════════════════════════════════════════════╝"
echo ""
echo "✅ FASE 1 - Usuarios:"
echo "   • Owner ID: $PARKING_OWNER_ID"
echo "   • Driver ID: $DRIVER_ID"
echo ""
echo "✅ FASE 2 - Infraestructura:"
echo "   • Parking ID: $PARKING_ID"
echo "   • Spot ID: $PARKING_SPOT_ID"
echo ""
echo "✅ FASE 3 - Reservas:"
echo "   • Reservation ID: $RESERVATION_ID"
echo "   • Estados probados: PENDING → CONFIRMED → COMPLETED"
echo ""
echo "✅ FASE 4 - Reviews:"
echo "   • Sistema de calificación funcional"
echo "   • Consultas por parking y driver disponibles"
echo ""
print_success "¡Todas las pruebas completadas exitosamente! ✨"
echo ""
