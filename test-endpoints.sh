#!/bin/bash
# Test rápido de endpoints principales de Parkeoya Backend

BASE_URL="http://localhost:8080/api/v1"

echo "========================================"
echo "  PRUEBA RÁPIDA - PARKEOYA BACKEND"
echo "========================================"
echo ""

# Colores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}=== 1. HEALTH CHECK ===${NC}"
curl -s http://localhost:8080/actuator/health
echo -e "\n"

echo -e "${BLUE}=== 2. REGISTRAR PARKING OWNER ===${NC}"
OWNER_RESPONSE=$(curl -s -X POST "${BASE_URL}/authentication/sign-up/parking-owner" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner'$(date +%s)'@test.com",
    "password": "Test1234",
    "fullName": "Propietario Test",
    "city": "Lima",
    "country": "Perú",
    "phone": "999111222",
    "companyName": "Parking Test SAC",
    "ruc": "20111222333"
  }')
echo "$OWNER_RESPONSE"
echo -e "\n"

echo -e "${BLUE}=== 3. REGISTRAR DRIVER ===${NC}"
DRIVER_RESPONSE=$(curl -s -X POST "${BASE_URL}/authentication/sign-up/driver" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "driver'$(date +%s)'@test.com",
    "password": "Test1234",
    "fullName": "Conductor Test",
    "city": "Lima",
    "country": "Perú",
    "phone": "999333444",
    "dni": "12345678"
  }')
echo "$DRIVER_RESPONSE"
echo -e "\n"

# Extraer email del owner para login
OWNER_EMAIL=$(echo "$OWNER_RESPONSE" | grep -o '"email":"[^"]*"' | cut -d'"' -f4)
DRIVER_EMAIL=$(echo "$DRIVER_RESPONSE" | grep -o '"email":"[^"]*"' | cut -d'"' -f4)

echo -e "${BLUE}=== 4. LOGIN PARKING OWNER ===${NC}"
OWNER_LOGIN=$(curl -s -X POST "${BASE_URL}/authentication/sign-in" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$OWNER_EMAIL\",
    \"password\": \"Test1234\"
  }")
echo "$OWNER_LOGIN"
echo -e "\n"

echo -e "${BLUE}=== 5. LOGIN DRIVER ===${NC}"
DRIVER_LOGIN=$(curl -s -X POST "${BASE_URL}/authentication/sign-in" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$DRIVER_EMAIL\",
    \"password\": \"Test1234\"
  }")
echo "$DRIVER_LOGIN"
echo -e "\n"

echo -e "${GREEN}========================================"
echo -e "  ✅ PRUEBAS COMPLETADAS"
echo -e "========================================${NC}"
echo ""
echo "Para probar más endpoints, abre Swagger UI:"
echo "http://localhost:8080/swagger-ui/index.html"
echo ""
