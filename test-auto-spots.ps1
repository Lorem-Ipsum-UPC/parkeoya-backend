# Script de Testing para Auto-Generacion de Parking Spots
# Autor: Testing Script
# Fecha: 2025-11-11

$baseUrl = "http://localhost:8080/api/v1"

Write-Host "=== Iniciando pruebas de auto-generacion de parking spots ===" -ForegroundColor Cyan
Write-Host ""

# Paso 0: Crear usuario Parking Owner y obtener token
Write-Host "[SETUP] Creando Parking Owner y obteniendo token de autenticacion..." -ForegroundColor Yellow

$timestamp = Get-Date -Format "yyyyMMddHHmmss"
$randomPhone = "9" + (Get-Random -Minimum 10000000 -Maximum 99999999)
$randomRuc = "20" + (Get-Random -Minimum 100000000 -Maximum 999999999)

$signUpBody = @{
    email = "testowner$timestamp@test.com"
    password = "Test1234!"
    fullName = "Test Owner"
    city = "Lima"
    country = "Peru"
    phone = $randomPhone
    companyName = "Test Company"
    ruc = $randomRuc
} | ConvertTo-Json

try {
    # Crear parking owner
    $signUpResponse = Invoke-RestMethod -Uri "$baseUrl/authentication/sign-up/parking-owner" -Method Post -Body $signUpBody -ContentType "application/json"
    Write-Host "[OK] Parking Owner creado con ID: $($signUpResponse.id)" -ForegroundColor Green
    
    # Hacer login para obtener token
    $signInBody = @{
        email = "testowner$timestamp@test.com"
        password = "Test1234!"
    } | ConvertTo-Json
    
    $authResponse = Invoke-RestMethod -Uri "$baseUrl/authentication/sign-in" -Method Post -Body $signInBody -ContentType "application/json"
    $token = $authResponse.token
    $ownerId = $signUpResponse.id
    
    Write-Host "[OK] Token de autenticacion obtenido" -ForegroundColor Green
    Write-Host ""
    
    # Test 1: Crear parking con auto-generacion (3x4 = 12 spots)
    Write-Host "[TEST 1] Creando parking con 3 filas x 4 columnas (12 spots)..." -ForegroundColor Yellow

    $parkingBody = @{
        ownerId = $ownerId
        name = "Test Auto-Spots 3x4"
        description = "Parking de prueba con generacion automatica"
        address = "Av. Test 123"
        city = "Lima"
        province = "Lima"
        postalCode = "15001"
        lat = -12.0464
        lng = -77.0428
        ratePerHour = 5.0
        dailyRate = 30.0
        monthlyRate = 500.0
        totalSpots = 12
        regularSpots = 12
        disabledSpots = 0
        electricSpots = 0
        availableSpots = 12
        totalRows = 3
        totalColumns = 4
        imageUrl = "https://example.com/parking.jpg"
        open24Hours = $false
        openingTime = "08:00"
        closingTime = "22:00"
        operatingDays = "Lunes a Domingo"
    } | ConvertTo-Json

    # Crear headers con el token
    $headers = @{
        "Authorization" = "Bearer $token"
        "Content-Type" = "application/json"
    }
    
    $response = Invoke-RestMethod -Uri "$baseUrl/parkings" -Method Post -Body $parkingBody -Headers $headers
    $parkingId = $response.id
    
    Write-Host "[OK] Parking creado exitosamente!" -ForegroundColor Green
    Write-Host "   ID del Parking: $parkingId" -ForegroundColor White
    Write-Host "   Nombre: $($response.name)" -ForegroundColor White
    Write-Host ""
    
    # Test 2: Obtener spots del parking
    Write-Host "[TEST 2] Obteniendo spots generados del parking..." -ForegroundColor Yellow
    Start-Sleep -Seconds 1
    
    $spots = Invoke-RestMethod -Uri "$baseUrl/parkings/$parkingId/spots" -Method Get -Headers @{"Authorization" = "Bearer $token"}
    $spotsCount = $spots.Count
    
    Write-Host "[OK] Spots obtenidos exitosamente!" -ForegroundColor Green
    Write-Host "   Total de spots generados: $spotsCount" -ForegroundColor White
    Write-Host "   Esperado: 12 spots (3 filas x 4 columnas)" -ForegroundColor Gray
    
    if ($spotsCount -eq 12) {
        Write-Host "   [PASS] Cantidad correcta!" -ForegroundColor Green
    } else {
        Write-Host "   [FAIL] ERROR: Se esperaban 12 spots pero se generaron $spotsCount" -ForegroundColor Red
    }
    Write-Host ""
    
    # Test 3: Verificar estructura de spots
    Write-Host "[TEST 3] Verificando estructura de spots..." -ForegroundColor Yellow
    
    Write-Host "   Primeros 5 spots:" -ForegroundColor White
    $spots | Select-Object -First 5 | ForEach-Object {
        Write-Host "   - Spot #$($_.label): Fila $($_.row), Columna $($_.col) - Estado: $($_.status)" -ForegroundColor Cyan
    }
    
    # Verificar que los labels sean secuenciales
    $labels = $spots | Sort-Object { [int]$_.label } | Select-Object -ExpandProperty label
    $isSequential = $true
    for ($i = 0; $i -lt $labels.Count; $i++) {
        if ([int]$labels[$i] -ne ($i + 1)) {
            $isSequential = $false
            break
        }
    }
    
    Write-Host ""
    if ($isSequential) {
        Write-Host "   [PASS] Labels son secuenciales (1, 2, 3 ... $spotsCount)" -ForegroundColor Green
    } else {
        Write-Host "   [FAIL] ERROR: Labels no son secuenciales" -ForegroundColor Red
    }
    
    # Verificar que todos esten disponibles
    $availableSpots = ($spots | Where-Object { $_.status -eq "AVAILABLE" }).Count
    Write-Host "   Spots disponibles: $availableSpots de $spotsCount" -ForegroundColor White
    
    if ($availableSpots -eq $spotsCount) {
        Write-Host "   [PASS] Todos los spots estan disponibles" -ForegroundColor Green
    }
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Gray
    Write-Host "RESUMEN DE PRUEBAS" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Gray
    Write-Host "   Parking ID: $parkingId" -ForegroundColor White
    Write-Host "   Total Spots Generados: $spotsCount / 12 esperados" -ForegroundColor White
    Write-Host "   Labels Secuenciales: $(if($isSequential){'[PASS]'}else{'[FAIL]'})" -ForegroundColor White
    Write-Host "   Todos Disponibles: $(if($availableSpots -eq $spotsCount){'[PASS]'}else{'[FAIL]'})" -ForegroundColor White
    Write-Host ""
    
    if ($spotsCount -eq 12 -and $isSequential -and $availableSpots -eq $spotsCount) {
        Write-Host "*** TODAS LAS PRUEBAS PASARON EXITOSAMENTE! ***" -ForegroundColor Green
    } else {
        Write-Host "*** Algunas pruebas fallaron. Revisar arriba. ***" -ForegroundColor Yellow
    }
    
} catch {
    Write-Host "[ERROR] Error durante las pruebas:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    
    # Intentar obtener más detalles del error
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.BaseStream.Position = 0
        $reader.DiscardBufferedData()
        $responseBody = $reader.ReadToEnd()
        Write-Host "Detalles del error:" -ForegroundColor Yellow
        Write-Host $responseBody -ForegroundColor White
    }
    
    Write-Host ""
    Write-Host "Posibles causas:" -ForegroundColor Yellow
    Write-Host "  - La aplicacion no esta corriendo en $baseUrl" -ForegroundColor White
    Write-Host "  - Faltan campos requeridos en el request" -ForegroundColor White
    Write-Host "  - Error en la creacion de spots" -ForegroundColor White
}

Write-Host ""
Write-Host "Para mas pruebas manuales, consulta: test-auto-spots.md" -ForegroundColor Gray
