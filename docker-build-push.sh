#!/bin/bash

# Script para construir y publicar la imagen Docker de Parkeoya Backend
# Asegúrate de tener Docker Desktop instalado y en ejecución

set -e  # Salir si hay algún error

echo "🐳 Parkeoya Backend - Docker Build & Push Script"
echo "================================================"

# Variables
IMAGE_NAME="parkeoya-backend"
DOCKER_USERNAME=""  # Ingresa tu usuario de Docker Hub
VERSION="1.0.0"
LATEST_TAG="latest"

# Verificar que Docker está corriendo
if ! docker info > /dev/null 2>&1; then
    echo "❌ Error: Docker no está corriendo. Por favor inicia Docker Desktop."
    exit 1
fi

echo "✅ Docker está corriendo"

# Solicitar usuario de Docker Hub si no está configurado
if [ -z "$DOCKER_USERNAME" ]; then
    echo ""
    read -p "👤 Ingresa tu usuario de Docker Hub: " DOCKER_USERNAME
fi

# Login a Docker Hub
echo ""
echo "🔐 Iniciando sesión en Docker Hub..."
docker login

# Construir la imagen
echo ""
echo "🏗️  Construyendo imagen Docker..."
echo "   Nombre: $IMAGE_NAME"
echo "   Versión: $VERSION"

cd "$(dirname "$0")"

docker build \
    --platform linux/amd64 \
    -t $DOCKER_USERNAME/$IMAGE_NAME:$VERSION \
    -t $DOCKER_USERNAME/$IMAGE_NAME:$LATEST_TAG \
    .

if [ $? -eq 0 ]; then
    echo "✅ Imagen construida exitosamente"
else
    echo "❌ Error al construir la imagen"
    exit 1
fi

# Mostrar tamaño de la imagen
echo ""
echo "📦 Tamaño de la imagen:"
docker images $DOCKER_USERNAME/$IMAGE_NAME:$LATEST_TAG

# Publicar en Docker Hub
echo ""
read -p "📤 ¿Deseas publicar la imagen en Docker Hub? (y/n): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "📤 Publicando imagen en Docker Hub..."
    
    docker push $DOCKER_USERNAME/$IMAGE_NAME:$VERSION
    docker push $DOCKER_USERNAME/$IMAGE_NAME:$LATEST_TAG
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ ¡Imagen publicada exitosamente!"
        echo ""
        echo "🎉 Tu imagen está disponible en:"
        echo "   docker pull $DOCKER_USERNAME/$IMAGE_NAME:$VERSION"
        echo "   docker pull $DOCKER_USERNAME/$IMAGE_NAME:$LATEST_TAG"
        echo ""
        echo "🚀 Para ejecutar el contenedor:"
        echo "   docker run -d -p 8080:8080 $DOCKER_USERNAME/$IMAGE_NAME:$LATEST_TAG"
    else
        echo "❌ Error al publicar la imagen"
        exit 1
    fi
else
    echo "⏭️  Publicación cancelada"
fi

echo ""
echo "✨ Proceso completado"
