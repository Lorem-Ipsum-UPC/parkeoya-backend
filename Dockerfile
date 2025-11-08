# Multi-stage build para optimizar tamaño de imagen
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

# Directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración Maven
COPY pom.xml .
COPY src ./src

# Compilar el proyecto (sin ejecutar tests para acelerar)
RUN mvn clean package -DskipTests

# Etapa final - imagen ligera con solo JRE
FROM eclipse-temurin:17-jre-alpine

# Instalar curl para healthchecks
RUN apk add --no-cache curl

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto 8080
EXPOSE 8080

# Variables de entorno para Spring Profile y configuración
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080
ENV JAVA_OPTS="-Xmx512m -Xms256m -Djava.security.egd=file:/dev/./urandom"

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
