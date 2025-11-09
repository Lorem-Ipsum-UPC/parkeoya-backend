#!/bin/bash

###############################################################################
# Script para construir y subir imagen Docker al Docker Hub
# Uso: ./docker-deploy.sh [DOCKER_USERNAME]
###############################################################################

# Colores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

# Configuración
DOCKER_USERNAME="${1:-loremipsumupc}"  # Usuario por defecto o primer argumento
IMAGE_NAME="parkeoya-backend"
VERSION="latest"
FULL_IMAGE="${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}"

echo "╔═══════════════════════════════════════════════════════════════════════╗"
echo "║           🐳 DOCKER BUILD & PUSH - PARKEOYA BACKEND                  ║"
echo "╚═══════════════════════════════════════════════════════════════════════╝"
echo ""

# Verificar que Docker está corriendo
if ! docker info > /dev/null 2>&1; then
    echo -e "${RED}✗ Error: Docker no está corriendo${NC}"
    echo "Por favor inicia Docker Desktop y ejecuta este script nuevamente"
    exit 1
fi

echo -e "${BLUE}📦 Configuración:${NC}"
echo "   • Usuario Docker Hub: ${DOCKER_USERNAME}"
echo "   • Imagen: ${IMAGE_NAME}"
echo "   • Tag: ${VERSION}"
echo "   • Imagen completa: ${FULL_IMAGE}"
echo ""

# Paso 1: Construir la imagen
echo -e "${BLUE}🔨 Paso 1/3: Construyendo imagen Docker...${NC}"
if docker build -t ${FULL_IMAGE} .; then
    echo -e "${GREEN}✓ Imagen construida exitosamente${NC}"
else
    echo -e "${RED}✗ Error al construir la imagen${NC}"
    exit 1
fi

# Paso 2: Login a Docker Hub (si es necesario)
echo ""
echo -e "${BLUE}🔐 Paso 2/3: Verificando autenticación en Docker Hub...${NC}"
if docker info | grep -q "Username: ${DOCKER_USERNAME}"; then
    echo -e "${GREEN}✓ Ya autenticado como ${DOCKER_USERNAME}${NC}"
else
    echo "Iniciando login en Docker Hub..."
    if docker login; then
        echo -e "${GREEN}✓ Login exitoso${NC}"
    else
        echo -e "${RED}✗ Error en login${NC}"
        exit 1
    fi
fi

# Paso 3: Push a Docker Hub
echo ""
echo -e "${BLUE}🚀 Paso 3/3: Subiendo imagen a Docker Hub...${NC}"
if docker push ${FULL_IMAGE}; then
    echo -e "${GREEN}✓ Imagen subida exitosamente${NC}"
else
    echo -e "${RED}✗ Error al subir la imagen${NC}"
    exit 1
fi

# Resumen
echo ""
echo "╔═══════════════════════════════════════════════════════════════════════╗"
echo "║                          ✅ DEPLOY EXITOSO                            ║"
echo "╚═══════════════════════════════════════════════════════════════════════╝"
echo ""
echo -e "${GREEN}La imagen está disponible en Docker Hub:${NC}"
echo "   docker pull ${FULL_IMAGE}"
echo ""
echo -e "${BLUE}Para ejecutar el contenedor:${NC}"
echo "   docker run -d -p 8080:8080 \\"
echo "     -e SPRING_PROFILES_ACTIVE=prod \\"
echo "     --name parkeoya-backend \\"
echo "     ${FULL_IMAGE}"
echo ""
echo -e "${BLUE}Para el frontend, usar esta URL base:${NC}"
echo "   http://localhost:8080/api/v1"
echo ""
