# 🚚 PROYECTO TRASLADOS

## 🛠️ BACKEND DE APLICACIONES -- TPI

## 📋 Descripción General

Proyecto backend desarrollado bajo arquitectura de **microservicios**,
orientado a la gestión completa de traslados logísticos. \
Incluye administración de clientes, traslados, rutas, tramos, camiones, transportistas,
contenedores, depósitos, estadias y cálculo de distancias mediante **OSRM**.

El sistema está compuesto por 5 microservicios centrales donde uno de estos
es auxiliar para el manejo de coordenadas y cálculo de rutas alternativas.

------------------------------------------------------------------------

# 🧩 Microservicios del Proyecto

### **1️⃣ ms-camiones**

Gestión de:
- Transportistas
- Camiones
- Tarifas
- Ciudades
- Provincias

### **2️⃣ ms-contenedores**

Gestión de:
- Contenedores
- Depósitos
- Estadías
- Estados de contenedores

### **3️⃣ ms-rutas**

Gestión de:
- Tramos
- Tipos de tramos
- Estados de tramos

### **4️⃣ ms-traslados**

Gestión de:
- Clientes
- Traslados
- Estados de traslados
- Historial de cambios de estados de traslados

### **5️⃣ ms-locations**

Microservicio interno para:
- Integración con **OSRM**
- Cálculo de distancias y ubicaciones
- Determinación de rutas alternativas

------------------------------------------------------------------------

# 🏗️ Estructura Principal del Proyecto

    BACKEND-TPI/
    ├── ms-camiones/
    ├── ms-contenedores/
    ├── ms-locations/
    ├── ms-rutas/
    ├── ms-traslados/
    │
    ├── osrm-data/
    ├── .gitignore
    ├── docker-compose.osrm.yml
    ├── pom.xml
    └── README.md

------------------------------------------------------------------------

# 📦 Estructura Genérica de Cada Microservicio

    ms-microservicio/
    │
    ├── src/main/java/com.backend_tpi.xxx
    │   ├── configs/
    │   ├── constants/
    │   │   └── converters/
    │   ├── controllers/
    │   ├── dtos/
    │   │   ├── requests/
    │   │   └── responses/
    │   ├── exceptions/
    │   ├── external/
    │   │   ├── clients/
    │   │   └── dtos.responses/
    │   ├── mappers/
    │   ├── models/
    │   ├── repositories/
    │   └── services/
    │
    ├── src/main/resources/
    │   ├── application.properties
    │   ├── application-example.properties
    │   ├── application-local.properties
    │   ├── data.sql
    │   └── schema.sql
    │
    └── pom.xml

------------------------------------------------------------------------

# 🚀 Inicio de la Aplicación

### 📁 Archivo `application-local.properties` en `src/main/resources/`

``` properties
# ========================================
# CONFIGURACION DEL SERVIDOR Y EXTERNOS
# ========================================
server.port=808X
api.ms-camiones.base_url=http://localhost:8081
api.ms-contenedores.base_url=http://localhost:8082
api.ms-rutas.base_url=http://localhost:8083
api.ms-traslados.base_url=http://localhost:8084
# De ser necesario
api.ms-locations.base_url=http://localhost:8085

# Para el microservicio: ms-rutas
costoCombustible=1500


# ========================================
# BASE DE DATOS H2 (en memoria)
# ========================================
spring.datasource.url=jdbc:h2:mem:ms-name;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console


# ========================================
# JPA / HIBERNATE
# ========================================
spring.jpa.hibernate.ddl-auto=none
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.jpa.properties.hibernate.show_sql=false
spring.jpa.properties.hibernate.format_sql=false


# ========================================
# LOGGING
# ========================================
logging.level.root=INFO
logging.level.com.backend_tpi.ms_traslados=DEBUG
# logging.level.org.springframework=WARN
logging.level.org.springframework.web=DEBUG
# logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
# logging.level.org.hibernate.orm.jdbc.bind=TRACE

# logging.file.name=logs/ms-traslados.log
# logging.file.max-size=5MB
# logging.file.max-history=7

logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %level - %msg - %logger%n
# logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n


# ========================================
# JSON / SERIALIZACION
# ========================================
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=America/Argentina/Buenos_Aires


# ========================================
# VALIDACION
# ========================================
spring.mvc.throw-exception-if-no-handler-found=true
spring.web.resources.add-mappings=false
```

------------------------------------------------------------------------

# 🗺️ Configuración y Uso de OSRM

### 📉 Descarga

Crear la carpeta `osrm-data` en la raiz del proyecto. \
Descargar archivo desde https://download.geofabrik.de/south-america.html en esa carpeta.

Ejemplo: `argentina-251115.osm.pbf`

### 📑 Archivo "docker-compose.osrm.yml"

Modificar el siguiente apartado para que coincida con la versión correspondiente
``` yml
command: >
    osrm-routed /data/argentina-251115.osrm
```

**Para CMD y PowerShell**

### 1️⃣ Extraer datos

``` bash
docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend   osrm-extract -p /opt/car.lua /data/argentina-251115.osm.pbf
```

### 2️⃣ Contractar datos

``` bash
docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend   osrm-contract /data/argentina-251115.osrm
```

### 3️⃣ Levantar OSRM

``` bash
docker compose -f docker-compose.osrm.yml up
```

### 4️⃣ Detener

``` bash
docker compose -f docker-compose.osrm.yml down
```

------------------------------------------------------------------------

# 🔄 Orden Correcto de Inicio

1.  OSRM
2.  ms-camiones
3.  ms-contenedores
4.  ms-rutas
5.  ms-traslados

------------------------------------------------------------------------

# 🌿 Workflow de Git

## 📋 Estructura de Ramas

```
main (protegida)
  └── develop (protegida)
        ├── feat/nueva-funcionalidad
        ├── fix/correccion-bug
        ├── doc/actualizarcion-readme
        └── style/mejora-interfaz
```

- **`main`**: Rama de producción (protegida, no se trabaja directamente)
- **`develop`**: Rama de desarrollo (protegida, no se trabaja directamente)
- **Ramas de trabajo**: Se crean desde `develop` con prefijos específicos

## 🏷️ Nomenclatura de Ramas

```
feat/nombre-descriptivo      # Nueva funcionalidad
fix/nombre-del-bug          # Corrección de errores
hotfix/nombre-urgente       # Corrección urgente
doc/nombre-documentacion    # Documentación
style/nombre-estilo         # Cambios de estilo/UI
```

## 🔄 Flujo de Trabajo Completo

### 1. Crear nueva rama de trabajo

``` bash
# Asegurate de estar en develop actualizado
git switch develop
git pull origin develop

# Crear y cambiar a tu nueva rama
git switch -c feat/microservicio-x
```

### 2. Realizar cambios y commits

``` bash
# Hacer tus cambios en el código...

# Agregar archivos modificados
git add .

# Commit con formato: "prefijo: descripción"
git commit -m "feat: implementacion de jpa"
git commit -m "feat: se agrego validacion en post clientes"
git commit -m "fix: correccion de error de mappeo"
```

### 3. Subir cambios a GitHub

``` bash
# Primer push (crear la rama en remoto)
git push -u origin feat/microservicio-x

# Pushes siguientes (la rama ya existe)
git push
```

### 4. Crear Pull Request en GitHub

1. Ve a GitHub → pestaña **Pull Requests**
2. Click en **New Pull Request**
3. Seleccionar:
    - **Base**: `develop`
    - **Compare**: tu rama (ej: `feat/microservicio-x`)
4. **Título**: Nombre de la rama (ej: `feat/microservicio-x`)
5. **Descripción**: Lista de cambios realizados
``` markdown
   ## Cambios realizados
   
   - Implementacion de repositories de clientes y traslados
   - Se agrego validacion de de posts
   - Se corrigio el mapeo de clientes
```
6. Click en **Create Pull Request**
7. Esperar revisión y aprobación del equipo

## 📝 Formato de Commits

```
# Formato general
prefijo: descripción breve en minúsculas

# Ejemplos correctos
feat: agregar pantalla de listado de eventos
fix: corregir error en cálculo de magnitud
doc: actualizar README con instrucciones de instalación
style: mejorar diseño de botones principales
hotfix: resolver error crítico en guardado de datos

# ❌ Ejemplos incorrectos
Agregué una nueva función          # Sin prefijo
feat: Agregar Pantalla             # Mayúsculas innecesarias
arreglé un bug                     # Sin prefijo adecuado
```

## 🔍 Comandos Útiles

``` bash
# Ver en qué rama estás
git branch

# Ver estado de cambios
git status

# Ver historial de commits
git log --oneline

# Actualizar tu rama con los últimos cambios de develop
git switch develop
git pull origin develop
git switch tu-rama
git merge develop
# O más facil
git switch tu-rama
git pull origin develop

# Descartar cambios locales (¡cuidado!)
git checkout -- archivo.py
git reset --hard  # Descarta TODOS los cambios
```

## ⚠️ Reglas Importantes

- ✅ **SIEMPRE** crear ramas desde `develop` actualizado
- ✅ **NUNCA** hacer commit directamente en `main` o `develop`
- ✅ **SIEMPRE** usar el formato de commits con prefijo
- ✅ Mantener commits atómicos (un cambio = un commit)
- ✅ Escribir descripciones claras y concisas
- ✅ Probar el código antes de hacer push
- ✅ Revisar Pull Requests de compañeros

------------------------------------------------------------------------

# 📱 Tecnologías

-   Java 21
-   Spring Boot 3.5.6
-   Maven
-   H2
-   JPA / Hibernate
-   OSRM
-   Lombok
-   Git
-   Docker

------------------------------------------------------------------------

# 👥 Equipo de Desarrollo

*A completar*

------------------------------------------------------------------------

# 🔗 Enlaces de Documentación por Tecnología
🧡 Java 21
https://docs.oracle.com/en/java/javase/21/ \
🌱 Spring Boot 3.5.6
https://docs.spring.io/spring-boot/docs/current/reference/html/ \
⚙️ Maven
https://maven.apache.org/guides/index.html \
🛢️H2 Database
https://www.h2database.com/html/main.html \
JPA / Hibernate \
JPA (Jakarta Persistence):
https://jakarta.ee/specifications/persistence/ \
Hibernate ORM:
https://hibernate.org/orm/documentation/ \
🗺️ OSRM (Open Source Routing Machine)
http://project-osrm.org/docs/v5.24.0/api/ \
🧡 Lombok
https://projectlombok.org/features/ \
🐙 Git
https://git-scm.com/doc \
🐳 Docker
https://docs.docker.com/
