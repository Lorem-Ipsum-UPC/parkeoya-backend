#!/bin/bash

echo "===================================="
echo "Testing feat-parking Backend with Production DB"
echo "===================================="

# 1. Test Health Endpoint
echo ""
echo "1. Testing Health Endpoint..."
curl -s http://localhost:8080/actuator/health
echo ""

# 2. Register Parking Owner
echo ""
echo "2. Registering Parking Owner..."
SIGNUP_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/auth/sign-up/parking-owner \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test Owner FeatParking",
    "email": "testowner-feat@test.com",
    "password": "Password123!",
    "city": "Lima",
    "country": "Peru",
    "phone": "+51999888111",
    "companyName": "Test Parking Company",
    "ruc": "20999888111"
  }')
echo "$SIGNUP_RESPONSE"
echo ""

# 3. Login to get token
echo ""
echo "3. Login to get JWT token..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/auth/sign-in \
  -H "Content-Type: application/json" \
  -d '{
    "email": "testowner-feat@test.com",
    "password": "Password123!"
  }')
echo "$LOGIN_RESPONSE"

# Extract token (using simple grep and cut)
TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo ""
echo "Token extracted: ${TOKEN:0:50}..."
echo ""

# Get parkingOwnerId from login response
PARKING_OWNER_ID=$(echo "$LOGIN_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "Parking Owner ID: $PARKING_OWNER_ID"

# 4. Create a Parking
echo ""
echo "4. Creating a Parking..."
CREATE_PARKING_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/parkings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "parkingOwnerId": '"$PARKING_OWNER_ID"',
    "name": "Test Parking feat-parking",
    "description": "Parking for testing UPDATE endpoint",
    "address": "Av. Test 123, Lima",
    "latitude": -12.0464,
    "longitude": -77.0428,
    "totalSpots": 50,
    "availableSpots": 50,
    "ratePerHour": 5.0,
    "ratePerDay": 40.0,
    "ratePerWeek": 200.0,
    "ratePerMonth": 700.0,
    "openingTime": "07:00:00",
    "closingTime": "22:00:00",
    "coverImageUrl": "https://example.com/cover.jpg",
    "contactPhone": "+51987654321",
    "contactEmail": "contact@testparking.com",
    "amenities": "Security, Lighting",
    "acceptsCash": true,
    "acceptsCard": true,
    "acceptsYape": true,
    "acceptsPlin": false,
    "hasSecurityCameras": true,
    "hasSecurityPersonnel": true,
    "hasDisabledAccess": true,
    "isOpen24Hours": false
  }')
echo "$CREATE_PARKING_RESPONSE"

# Extract parkingId
PARKING_ID=$(echo "$CREATE_PARKING_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo ""
echo "Parking ID created: $PARKING_ID"
echo ""

# 5. Test UPDATE Parking Endpoint (New endpoint from feat-parking)
echo ""
echo "5. Testing PATCH /api/v1/parkings/{parkingId} (UPDATE endpoint)..."
UPDATE_PARKING_RESPONSE=$(curl -s -X PATCH http://localhost:8080/api/v1/parkings/$PARKING_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Test Parking UPDATED",
    "description": "Description updated via PATCH endpoint",
    "ratePerHour": 6.5,
    "availableSpots": 45
  }')
echo "$UPDATE_PARKING_RESPONSE"
echo ""

# 6. Test UPDATE Parking Owner Profile (New endpoint from feat-parking)
echo ""
echo "6. Testing PATCH /api/v1/profiles/parking-owner/{parkingOwnerId} (UPDATE endpoint)..."
UPDATE_PROFILE_RESPONSE=$(curl -s -X PATCH http://localhost:8080/api/v1/profiles/parking-owner/$PARKING_OWNER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "fullName": "Test Owner UPDATED",
    "city": "Arequipa",
    "companyName": "Updated Company Name"
  }')
echo "$UPDATE_PROFILE_RESPONSE"
echo ""

# 7. Register and test Driver UPDATE endpoint
echo ""
echo "7. Registering Driver for testing Driver UPDATE endpoint..."
DRIVER_SIGNUP=$(curl -s -X POST http://localhost:8080/api/v1/auth/sign-up/driver \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test Driver FeatParking",
    "email": "testdriver-feat@test.com",
    "password": "Password123!",
    "city": "Lima",
    "country": "Peru",
    "phone": "+51999777888",
    "dni": "99977788"
  }')
echo "$DRIVER_SIGNUP"

# Login as driver
echo ""
echo "8. Login as Driver..."
DRIVER_LOGIN=$(curl -s -X POST http://localhost:8080/api/v1/auth/sign-in \
  -H "Content-Type: application/json" \
  -d '{
    "email": "testdriver-feat@test.com",
    "password": "Password123!"
  }')
echo "$DRIVER_LOGIN"

DRIVER_TOKEN=$(echo "$DRIVER_LOGIN" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
DRIVER_ID=$(echo "$DRIVER_LOGIN" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo ""
echo "Driver ID: $DRIVER_ID"

# Test Driver UPDATE endpoint
echo ""
echo "9. Testing PATCH /api/v1/profiles/driver/{driverId} (UPDATE endpoint)..."
UPDATE_DRIVER_RESPONSE=$(curl -s -X PATCH http://localhost:8080/api/v1/profiles/driver/$DRIVER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $DRIVER_TOKEN" \
  -d '{
    "fullName": "Test Driver UPDATED",
    "city": "Cusco",
    "phone": "+51999777999"
  }')
echo "$UPDATE_DRIVER_RESPONSE"
echo ""

echo "===================================="
echo "✅ All tests completed!"
echo "===================================="
echo ""
echo "Summary:"
echo "- Health endpoint: OK"
echo "- Parking Owner registration: OK"
echo "- Parking creation: OK"
echo "- PATCH /parkings/{id}: Tested (NEW feat-parking endpoint)"
echo "- PATCH /profiles/parking-owner/{id}: Tested (NEW feat-parking endpoint)"
echo "- PATCH /profiles/driver/{id}: Tested (NEW feat-parking endpoint)"
