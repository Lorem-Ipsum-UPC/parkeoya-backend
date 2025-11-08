@echo off
REM ============================================
REM PARKEOYA BACKEND - DOCKER BUILD & DEPLOY
REM Configurado para Aiven MySQL Database
REM ============================================

echo.
echo ========================================
echo   PARKEOYA BACKEND - DOCKER BUILDER
echo ========================================
echo.
echo Este script va a:
echo 1. Compilar el backend Java con Maven
echo 2. Crear una imagen Docker optimizada
echo 3. Subir la imagen a Docker Hub
echo.
echo Base de datos: Aiven MySQL (ya configurada)
echo Puerto: 8080
echo.
pause

REM Verificar que Docker esta corriendo
echo.
echo [1/6] Verificando Docker...
docker info >nul 2>&1
if errorlevel 1 (
    echo.
    echo [ERROR] Docker no esta corriendo!
    echo.
    echo Por favor:
    echo 1. Instala Docker Desktop desde: https://www.docker.com/products/docker-desktop
    echo 2. Abre Docker Desktop
    echo 3. Espera a que el icono deje de parpadear
    echo 4. Vuelve a ejecutar este script
    echo.
    pause
    exit /b 1
)
echo [OK] Docker esta corriendo correctamente
timeout /t 2 >nul

REM Solicitar credenciales de Docker Hub
echo.
echo [2/6] Configuracion de Docker Hub
echo.
echo Si no tienes cuenta, creala GRATIS en: https://hub.docker.com/signup
echo.
set /p DOCKER_USER="Ingresa tu usuario de Docker Hub: "

if "%DOCKER_USER%"=="" (
    echo [ERROR] Debes ingresar un usuario
    pause
    exit /b 1
)

REM Login a Docker Hub
echo.
echo [3/6] Iniciando sesion en Docker Hub...
echo.
docker login -u %DOCKER_USER%
if errorlevel 1 (
    echo.
    echo [ERROR] No se pudo iniciar sesion
    echo Verifica tu usuario y contraseña
    pause
    exit /b 1
)
echo [OK] Sesion iniciada correctamente
timeout /t 2 >nul

REM Construir la imagen
echo.
echo [4/6] Construyendo imagen Docker...
echo.
echo Esto puede tomar 5-10 minutos...
echo - Descargando dependencias Maven
echo - Compilando codigo Java
echo - Creando imagen Docker
echo.

set IMAGE_NAME=%DOCKER_USER%/parkeoya-backend
set VERSION=1.0.0

docker build ^
  -t %IMAGE_NAME%:%VERSION% ^
  -t %IMAGE_NAME%:latest ^
  --build-arg BUILDKIT_PROGRESS=plain ^
  .

if errorlevel 1 (
    echo.
    echo [ERROR] Fallo la construccion de la imagen
    echo.
    echo Posibles causas:
    echo - Java 17 no esta instalado
    echo - Problemas de red al descargar dependencias
    echo - Codigo con errores de compilacion
    echo.
    pause
    exit /b 1
)

echo.
echo [OK] Imagen construida exitosamente!
timeout /t 2 >nul

REM Mostrar info de la imagen
echo.
echo [5/6] Informacion de la imagen:
docker images %IMAGE_NAME%:latest
timeout /t 3 >nul

REM Publicar en Docker Hub
echo.
echo [6/6] Publicando imagen en Docker Hub...
echo.

docker push %IMAGE_NAME%:%VERSION%
if errorlevel 1 goto push_error

docker push %IMAGE_NAME%:latest
if errorlevel 1 goto push_error

echo.
echo ============================================
echo    IMAGEN PUBLICADA EXITOSAMENTE!
echo ============================================
echo.
echo Tu imagen esta disponible en Docker Hub:
echo.
echo   https://hub.docker.com/r/%IMAGE_NAME%
echo.
echo Para descargar la imagen usa:
echo   docker pull %IMAGE_NAME%:latest
echo.
echo Para ejecutar localmente:
echo   docker run -d -p 8080:8080 %IMAGE_NAME%:latest
echo.
echo ============================================
echo    SIGUIENTE PASO: DESPLEGAR EN LA NUBE
echo ============================================
echo.
echo OPCION 1: Render.com (RECOMENDADO - GRATIS)
echo   1. Ve a: https://render.com
echo   2. Crea "New Web Service"
echo   3. Selecciona "Deploy from Docker image"
echo   4. Pega: %IMAGE_NAME%:latest
echo   5. Puerto: 8080
echo   6. Deploy!
echo.
echo OPCION 2: Railway.app (GRATIS)
echo   1. Ve a: https://railway.app
echo   2. "New Project" - "Deploy Docker Image"
echo   3. Pega: %IMAGE_NAME%:latest
echo   4. Deploy!
echo.
echo OPCION 3: Fly.io (GRATIS con $5 credito)
echo   1. Instala Fly CLI
echo   2. Ejecuta: fly auth login
echo   3. Ejecuta: fly launch --image %IMAGE_NAME%:latest
echo.
echo ============================================

REM Preguntar si quiere probar localmente
echo.
set /p TEST_LOCAL="Deseas probar la imagen localmente? (s/n): "
if /i "%TEST_LOCAL%"=="s" (
    echo.
    echo Ejecutando contenedor localmente...
    echo.
    
    REM Detener contenedor anterior si existe
    docker stop parkeoya-api >nul 2>&1
    docker rm parkeoya-api >nul 2>&1
    
    REM Ejecutar nuevo contenedor
    docker run -d ^
      --name parkeoya-api ^
      -p 8080:8080 ^
      %IMAGE_NAME%:latest
    
    if errorlevel 1 (
        echo [ERROR] No se pudo ejecutar el contenedor
        pause
        exit /b 1
    )
    
    echo.
    echo [OK] Contenedor ejecutandose!
    echo.
    echo Endpoints disponibles:
    echo   Health Check: http://localhost:8080/actuator/health
    echo   Swagger UI:   http://localhost:8080/swagger-ui/index.html
    echo.
    echo Para ver logs: docker logs -f parkeoya-api
    echo Para detener:  docker stop parkeoya-api
    echo.
)

echo.
echo ============================================
echo   PROCESO COMPLETADO CON EXITO!
echo ============================================
echo.
echo Imagen Docker: %IMAGE_NAME%:latest
echo Estado: PUBLICADA en Docker Hub
echo.
echo Ya puedes desplegar en cualquier plataforma cloud!
echo.
pause
exit /b 0

:push_error
echo.
echo [ERROR] No se pudo publicar la imagen en Docker Hub
echo.
echo Posibles causas:
echo - Problemas de conexion a internet
echo - Limite de almacenamiento en Docker Hub alcanzado
echo - Sesion expirada
echo.
echo Intenta ejecutar manualmente:
echo   docker push %IMAGE_NAME%:%VERSION%
echo   docker push %IMAGE_NAME%:latest
echo.
pause
exit /b 1
