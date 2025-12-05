#!/bin/bash

echo "=========================================="
echo "Testing feat-parking UPDATE Endpoints"
echo "=========================================="

# 1. Register Parking Owner
echo ""
echo "1. Registering Parking Owner..."
SIGNUP_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/authentication/sign-up/parking-owner \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Juan Test",
    "email": "juantest@example.com",
    "password": "Password123!",
    "city": "Lima",
    "country": "Peru",
    "phone": "987654321",
    "companyName": "Juan Parking Co",
    "ruc": "20987654321"
  }')
echo "$SIGNUP_RESPONSE"
OWNER_ID=$(echo "$SIGNUP_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "Owner ID: $OWNER_ID"

# 2. Login
echo ""
echo "2. Login..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/authentication/sign-in \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juantest@example.com",
    "password": "Password123!"
  }')
echo "$LOGIN_RESPONSE"

TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
PARKING_OWNER_ID=$(echo "$LOGIN_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo ""
echo "Token: ${TOKEN:0:50}..."
echo "Parking Owner ID: $PARKING_OWNER_ID"

# 3. Create Parking
echo ""
echo "3. Creating Parking..."
CREATE_PARKING=$(curl -s -X POST http://localhost:8080/api/v1/parkings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d "{
    \"parkingOwnerId\": $PARKING_OWNER_ID,
    \"name\": \"Test Parking Original\",
    \"description\": \"Original description\",
    \"address\": \"Av. Test 123\",
    \"latitude\": -12.0464,
    \"longitude\": -77.0428,
    \"totalSpots\": 50,
    \"availableSpots\": 50,
    \"ratePerHour\": 5.0,
    \"ratePerDay\": 40.0,
    \"ratePerWeek\": 200.0,
    \"ratePerMonth\": 700.0,
    \"openingTime\": \"07:00:00\",
    \"closingTime\": \"22:00:00\",
    \"coverImageUrl\": \"https://example.com/cover.jpg\",
    \"contactPhone\": \"987654321\",
    \"contactEmail\": \"contact@test.com\",
    \"amenities\": \"Security, Lighting\",
    \"acceptsCash\": true,
    \"acceptsCard\": true,
    \"acceptsYape\": true,
    \"acceptsPlin\": false,
    \"hasSecurityCameras\": true,
    \"hasSecurityPersonnel\": true,
    \"hasDisabledAccess\": true,
    \"isOpen24Hours\": false
  }")
echo "$CREATE_PARKING"

PARKING_ID=$(echo "$CREATE_PARKING" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo ""
echo "Parking ID: $PARKING_ID"

# 4. Test UPDATE Parking (NEW endpoint from feat-parking)
echo ""
echo "4. ✨ Testing PATCH /api/v1/parkings/$PARKING_ID (UPDATE endpoint)..."
UPDATE_PARKING=$(curl -s -X PATCH http://localhost:8080/api/v1/parkings/$PARKING_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Test Parking UPDATED ✅",
    "description": "Updated via PATCH endpoint",
    "ratePerHour": 7.5,
    "availableSpots": 45
  }')
echo "$UPDATE_PARKING"

# 5. Test UPDATE Parking Owner Profile (NEW endpoint from feat-parking)
echo ""
echo "5. ✨ Testing PATCH /api/v1/profiles/parking-owner/$PARKING_OWNER_ID (UPDATE profile)..."
UPDATE_PROFILE=$(curl -s -X PATCH http://localhost:8080/api/v1/profiles/parking-owner/$PARKING_OWNER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "fullName": "Juan Test UPDATED ✅",
    "city": "Arequipa",
    "companyName": "Juan Parking Co UPDATED"
  }')
echo "$UPDATE_PROFILE"

# 6. Register Driver
echo ""
echo "6. Registering Driver..."
DRIVER_SIGNUP=$(curl -s -X POST http://localhost:8080/api/v1/authentication/sign-up/driver \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Driver Test",
    "email": "drivertest@example.com",
    "password": "Password123!",
    "city": "Lima",
    "country": "Peru",
    "phone": "912345678",
    "dni": "12345678"
  }')
echo "$DRIVER_SIGNUP"

# 7. Login as Driver
echo ""
echo "7. Login as Driver..."
DRIVER_LOGIN=$(curl -s -X POST http://localhost:8080/api/v1/authentication/sign-in \
  -H "Content-Type: application/json" \
  -d '{
    "email": "drivertest@example.com",
    "password": "Password123!"
  }')
echo "$DRIVER_LOGIN"

DRIVER_TOKEN=$(echo "$DRIVER_LOGIN" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
DRIVER_ID=$(echo "$DRIVER_LOGIN" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo ""
echo "Driver ID: $DRIVER_ID"

# 8. Test UPDATE Driver Profile (NEW endpoint from feat-parking)
echo ""
echo "8. ✨ Testing PATCH /api/v1/profiles/driver/$DRIVER_ID (UPDATE driver profile)..."
UPDATE_DRIVER=$(curl -s -X PATCH http://localhost:8080/api/v1/profiles/driver/$DRIVER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $DRIVER_TOKEN" \
  -d '{
    "fullName": "Driver Test UPDATED ✅",
    "city": "Cusco",
    "phone": "998877665"
  }')
echo "$UPDATE_DRIVER"

echo ""
echo "=========================================="
echo "✅ All UPDATE endpoints tested successfully!"
echo "=========================================="
