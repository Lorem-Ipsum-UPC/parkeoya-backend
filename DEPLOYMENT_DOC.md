# Documentación de Despliegue - Parkeoya Backend

Este documento contiene una plantilla y checklist para documentar el despliegue del API (imagen Docker) hacia producción. Úsalo para capturar evidencias (capturas de pantalla / outputs) y pasos reproducibles.

---
## Resumen rápido (rellenar)
- Fecha y hora del despliegue: 
- Imagen Docker: `registry/org/parkeoya-backend:tag`
- Digest: 
- Commit Git asociado: 
- Branch: 
- Proveedor de despliegue: (Render / AWS / DigitalOcean / VPS / Kubernetes)
- URL pública: 

---
## Archivos / capturas recomendadas
Para cada elemento, toma una captura de pantalla mostrando la ruta y las líneas relevantes (o copia el contenido en el documento). Añade el timestamp y el commit hash.

1) `Dockerfile` (raíz del repo)
   - Qué mostrar: etapas (build/runtime), base images, COPY, ENTRYPOINT, puerto expuesto
   - Ruta: `./Dockerfile`

2) `pom.xml`
   - Qué mostrar: versión de Java, plugins (maven-jar-plugin/ spring-boot-maven-plugin), versión del artefacto
   - Ruta: `pom.xml`
   - Comando de build usado: `mvn clean package -DskipTests` (copiar salida de build)

3) `application-prod.properties` / `application.properties`
   - Qué mostrar: `spring.datasource.*`, `authorization.jwt.secret` (no mostrar secretos completos en capturas públicas), perfil activo
   - Ruta: `src/main/resources/application-prod.properties`

4) `WebSecurityConfiguration.java` (CORS / auth)
   - Qué mostrar: configuración CORS, filtros, rutas permitidas
   - Ruta: `src/main/java/.../WebSecurityConfiguration.java`

5) Artefacto JAR
   - Qué mostrar: `target/parkeoya-*.jar` y su checksum (sha256) y el commit hash del build
   - Comando sugerido para checksum: `sha256sum target/parkeoya-0.0.1-SNAPSHOT.jar`

6) Docker Hub / Registry
   - Qué mostrar: página del repo (nombre), tag, digest, fecha de push, logs de build (si se usó build automático)
   - Si usaste GitHub Actions / CI: capturar la run que generó la imagen

7) Configuración del servicio en la plataforma (ej. Render)
   - Qué mostrar: tipo de servicio (Docker/imagen), command/start, puerto, escalado, health check path

8) Variables de entorno y secretos
   - Qué mostrar: listado de variables (no valores sensibles), indicar qué valores están ocultos
   - Ej: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `authorization.jwt.secret`

9) Health checks & logs
   - Qué mostrar: `/actuator/health` response, logs recientes del container (últimos 200 lines)

10) Firewall / DB access
   - Qué mostrar: si la DB requiere whitelist de IPs, la IP del host donde corre el contenedor

---
## Comandos útiles para reproducir y verificar

### Construir localmente
```bash
# Desde la raíz del backend
mvn clean package -DskipTests
# o usar el Dockerfile multistage
docker build -t youruser/parkeoya-backend:latest .
```

### Ejecutar la imagen apuntando a la DB de producción (solo lectura si posible)
> **Atención**: solo haz esto si entiendes el riesgo de tocar datos de producción.
```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://<PROD_HOST>:<PORT>/<DB>?useSSL=true" \
  -e SPRING_DATASOURCE_USERNAME="<USER>" \
  -e SPRING_DATASOURCE_PASSWORD="<PASS>" \
  -e authorization.jwt.secret="<JWT_SECRET>" \
  youruser/parkeoya-backend:latest
```

### Verificar healthcheck
```bash
curl -v http://localhost:8080/actuator/health
```

### Logs del container
```bash
docker ps
docker logs -f <container-id>
```

### Inspeccionar imagen
```bash
docker images youruser/parkeoya-backend:latest
docker inspect youruser/parkeoya-backend:latest
```

---
## Checklist para la documentación (marcar cuando esté listo)
- [ ] Captura `Dockerfile`
- [ ] Captura `pom.xml` y salida del `mvn package`
- [ ] Captura archivos `application*.properties`
- [ ] Captura `WebSecurityConfiguration.java`
- [ ] Subir screenshots de Docker Hub (repo + tag + digest)
- [ ] Subir screenshots de la configuración del proveedor (env vars, healthcheck)
- [ ] Ejecutar `docker run` y capturar `curl /actuator/health` y logs
- [ ] Agregar rollback steps (tag anterior y comando)
- [ ] Anotar riesgos (DB acceso, backups, read-only creds)

---
## Ejemplo de sección "Despliegue realizado"
- Fecha: 
- Commit: 
- Imagen: 
- Comando de despliegue usado: 
- Notas de validación: `curl /actuator/health` -> `UP`, logs ok

---
## Rollback (ejemplo)
1. `docker pull youruser/parkeoya-backend:<previous-tag>`
2. Actualizar servicio para usar `<previous-tag>` y reiniciar
3. Validar healthcheck

---
## Notas de seguridad y operacionales
- No incluir secretos en las capturas públicas. En las capturas internas, borra o pixeliza valores sensibles.
- Preferir credenciales de solo-lectura cuando necesites consultar la DB de producción.
- Hacer snapshot/backups antes de operaciones destructivas.

---

##### 6.2.1.8. Software Deployment Evidence for Sprint Review (Backend - Docker)

Se adjuntan links y procedimiento del despliegue del API (imagen Docker en Docker Hub y servicio desplegado en la plataforma elegida):

**Docker Hub (imagen)**

https://hub.docker.com/r/juancali/parkeoya-backend

Para configurar el despliegue del API seguimos los pasos detallados a continuación utilizando Docker Hub para el hosting de la imagen y Render / VPS / AWS para ejecutar la imagen:

1) Subir la imagen al registry

- Iniciar sesión y pushear la imagen:

```bash
docker login
docker push youruser/parkeoya-backend:2025-11-14
```

![Docker Hub tag view](assets/chapter-6/backend-dockhub-1.png)

2) Configurar el servicio en la plataforma

- En el panel del proveedor (ej. Render) creamos un nuevo servicio y apuntamos a la imagen `youruser/parkeoya-backend:2025-11-14`.
- Configuramos las variables de entorno necesarias (ocultar valores en las capturas):
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `authorization.jwt.secret`
- Configuramos el health check a `/actuator/health`.

![Proveedor - configuración del servicio](assets/chapter-6/backend-provider-1.png)

3) Verificar despliegue

- Comprobar el health endpoint:

```bash
curl -v https://<your-backend-host>/actuator/health
# espera: {"status":"UP"}
```

- Capturar logs de inicio para confirmar que la app arrancó correctamente:

```bash
docker logs -f <container-id> --tail 200
```

![Healthcheck y logs](assets/chapter-6/backend-health-1.png)

4) Evidencias recomendadas (añadir a la memoria del sprint)

- Captura del `Dockerfile` (etapas y ENTRYPOINT) — `assets/chapter-6/backend-dockerfile.png`
- Captura de `pom.xml` y salida de `mvn clean package` (versiones y plugin) — `assets/chapter-6/backend-pom.png`
- Captura del Docker Hub mostrando tag y digest — `assets/chapter-6/backend-dockhub-1.png`
- Captura del panel del proveedor con env vars y health check — `assets/chapter-6/backend-provider-1.png`
- Captura de la respuesta de `/actuator/health` — `assets/chapter-6/backend-health-1.png`
- Captura de logs del servicio (últimas ~200 líneas) — `assets/chapter-6/backend-logs-1.png`

5) Texto ejemplo para pegar en la memoria del Sprint Review

Se adjuntan links y procedimiento del despliegue del API (imagen Docker en Docker Hub y servicio desplegado en Render/AWS/DigitalOcean):

**Docker Hub (imagen)**

[https://hub.docker.com/r/youruser/parkeoya-backend](https://hub.docker.com/r/youruser/parkeoya-backend)

Para el despliegue subimos la imagen al registry, luego en el proveedor creamos un servicio que consuma la imagen. Se configuraron las variables de entorno necesarias para conexión a la base de datos y JWT. Se configuró el health check en `/actuator/health` y se validó que la respuesta sea `UP`.

Capturas incluidas: `backend-dockhub-1.png`, `backend-provider-1.png`, `backend-health-1.png`, `backend-logs-1.png`.

---



