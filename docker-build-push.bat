@echo off
REM Script para construir y publicar la imagen Docker de Parkeoya Backend
REM Para Windows - Asegúrate de tener Docker Desktop instalado y en ejecución

echo ================================
echo Parkeoya Backend - Docker Build
echo ================================
echo.

REM Variables
set IMAGE_NAME=parkeoya-backend
set VERSION=1.0.0
set LATEST_TAG=latest

REM Solicitar usuario de Docker Hub
set /p DOCKER_USERNAME="Ingresa tu usuario de Docker Hub: "

REM Verificar que Docker está corriendo
docker info >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Docker no esta corriendo. Por favor inicia Docker Desktop.
    pause
    exit /b 1
)

echo [OK] Docker esta corriendo
echo.

REM Login a Docker Hub
echo Iniciando sesion en Docker Hub...
docker login
if errorlevel 1 (
    echo [ERROR] No se pudo iniciar sesion en Docker Hub
    pause
    exit /b 1
)

REM Cambiar al directorio del script
cd /d "%~dp0"

REM Construir la imagen
echo.
echo Construyendo imagen Docker...
echo   Nombre: %IMAGE_NAME%
echo   Version: %VERSION%
echo.

docker build -t %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION% -t %DOCKER_USERNAME%/%IMAGE_NAME%:%LATEST_TAG% .

if errorlevel 1 (
    echo [ERROR] Error al construir la imagen
    pause
    exit /b 1
)

echo [OK] Imagen construida exitosamente
echo.

REM Mostrar tamaño de la imagen
echo Tamano de la imagen:
docker images %DOCKER_USERNAME%/%IMAGE_NAME%:%LATEST_TAG%
echo.

REM Preguntar si desea publicar
set /p PUBLISH="Deseas publicar la imagen en Docker Hub? (s/n): "
if /i "%PUBLISH%"=="s" (
    echo.
    echo Publicando imagen en Docker Hub...
    
    docker push %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%
    docker push %DOCKER_USERNAME%/%IMAGE_NAME%:%LATEST_TAG%
    
    if errorlevel 1 (
        echo [ERROR] Error al publicar la imagen
        pause
        exit /b 1
    )
    
    echo.
    echo ========================================
    echo [OK] Imagen publicada exitosamente!
    echo ========================================
    echo.
    echo Tu imagen esta disponible en:
    echo   docker pull %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%
    echo   docker pull %DOCKER_USERNAME%/%IMAGE_NAME%:%LATEST_TAG%
    echo.
    echo Para ejecutar el contenedor:
    echo   docker run -d -p 8080:8080 %DOCKER_USERNAME%/%IMAGE_NAME%:%LATEST_TAG%
    echo.
) else (
    echo Publicacion cancelada
)

echo.
echo Proceso completado
pause
