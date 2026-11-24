# Veterinaria San Marcos - Backend

API REST para la Evaluación Parcial 3 de DSY1104 Desarrollo FullStack II.
Son dos microservicios Spring Boot, cada uno con su propia base de datos MySQL, protegidos con JWT y roles.

- Aplicación publicada (AWS): http://52.21.218.157
- Frontend: [Veterinaria-frontend](https://github.com/fssepul-prog/Veterinaria-frontend)
- Integrantes: Felipe Echeverría, Fabián Mondaca, Enrique Ignacio Gutiérrez Benítez

| Microservicio | Carpeta | Puerto | Base de datos | Responsabilidad |
|---|---|---|---|---|
| Autenticación | `veterinaria-auth/` | 8081 | `veterinaria_auth` | Usuarios, login, registro y emisión/renovación del JWT |
| Clínica | raíz del repositorio | 8082 | `veterinaria_san_marcos` | Servicios, citas, pacientes, fichas clínicas y reportes |

Tecnologías: Java 21, Spring Boot 4.1.2, Spring Security, Spring Data JPA, JJWT, springdoc-openapi (Swagger), MySQL 8, Docker.

## Arquitectura

```
Navegador (React)
   │  Authorization: Bearer <token>
   ├──► veterinaria-auth (8081) ──► MySQL veterinaria_auth
   │       emite el JWT firmado con JWT_SECRET
   └──► clínica (8082) ──────────► MySQL veterinaria_san_marcos
           verifica el JWT con el mismo JWT_SECRET
```

- Contraseñas guardadas con BCrypt.
- El JWT incluye correo (`sub`), `rol`, `nombre`, emisor (`veterinaria-auth`) y vence en 1 hora; se renueva con `POST /api/v1/auth/renovar`.
- Roles: `ADMIN`, `RECEPCIONISTA`, `CLIENTE`. Sin token válido la API responde 401; con un rol sin permiso, 403. Las rutas no declaradas quedan bloqueadas.
- Errores en JSON: `{"error": "mensaje"}` en la clínica y `{"mensaje": "..."}` en autenticación.

## Ejecutar en local (Windows)

Requisitos: JDK 21 o superior y MySQL 8 (por ejemplo, XAMPP).

1. Crear las dos bases de datos (las tablas se crean solas al iniciar):

   ```sql
   CREATE DATABASE veterinaria_auth;
   CREATE DATABASE veterinaria_san_marcos;
   ```

2. Definir el secreto compartido una vez (Base64 de al menos 32 bytes) y volver a abrir PowerShell:

   ```powershell
   [Environment]::SetEnvironmentVariable("JWT_SECRET", "SECRETO_EN_BASE64", "User")
   ```

   Por defecto se usa el usuario `root` de MySQL sin contraseña. Si tiene contraseña, definir también `AUTH_DB_PASSWORD` y `DB_PASSWORD`.

3. Iniciar cada microservicio en su propia ventana:

   ```powershell
   # Ventana 1: autenticación
   cd veterinaria-auth
   .\mvnw.cmd spring-boot:run

   # Ventana 2: clínica (raíz del repositorio)
   .\mvnw.cmd spring-boot:run
   ```

Al primer inicio se crean el administrador `admin@sanmarcos.cl` y los cinco servicios del catálogo. La clave del administrador se toma de `ADMIN_PASSWORD`; si no está definida se usa la clave por defecto de `application.properties`, que solo sirve para pruebas locales.

Swagger:

- Autenticación: http://localhost:8081/swagger-ui.html
- Clínica: http://localhost:8082/swagger-ui.html

Para probar endpoints protegidos: `POST /api/v1/auth/login` en el Swagger de autenticación, copiar el `token` y pegarlo en **Authorize** del Swagger de la clínica.

## Endpoints

Todas las rutas comienzan con `/api/v1`. El detalle completo (datos de entrada y respuestas) está en Swagger y en el Anexo 3.

| Método | Ruta | Roles |
|---|---|---|
| POST | `/auth/login`, `/auth/registro` | Público |
| POST | `/auth/renovar` | Con token |
| GET · POST · PUT | `/usuarios`, `/usuarios/{id}` | ADMIN |
| GET | `/servicios`, `/servicios/{id}` | Público |
| POST · PUT · DELETE | `/servicios`, `/servicios/{id}` | ADMIN |
| GET | `/citas` | ADMIN, RECEPCIONISTA |
| GET | `/citas/mias` | CLIENTE |
| POST | `/citas` | CLIENTE |
| PUT | `/citas/{id}/estado` | ADMIN, RECEPCIONISTA (cancelar: solo ADMIN) |
| PUT | `/citas/{id}/reagendar` | ADMIN, RECEPCIONISTA |
| GET · POST | `/pacientes`, `/pacientes/{id}` | ADMIN, RECEPCIONISTA |
| GET | `/fichas` (filtro opcional `?pacienteId=`) | ADMIN, RECEPCIONISTA |
| GET | `/fichas/mias` | CLIENTE |
| POST · PUT | `/fichas`, `/fichas/{id}` | ADMIN, RECEPCIONISTA |
| GET | `/reportes/atenciones?desde=AAAA-MM-DD&hasta=AAAA-MM-DD` | ADMIN |

## Docker

`docker-compose.yml` levanta los dos microservicios y sus dos bases MySQL, con volúmenes para los datos y reinicio automático.

1. Copiar `.env.docker.example` como `.env` y reemplazar cada valor. El `.env` no se sube a GitHub.
2. Compilar y levantar:

   ```bash
   cd veterinaria-auth && ./mvnw clean package -DskipTests && cd ..
   ./mvnw clean package -DskipTests
   docker compose up -d --build
   docker compose ps
   ```

Los puertos 8081 y 8082 solo se abren en `127.0.0.1`.

## Despliegue en AWS

- Instancia EC2 con Ubuntu Server 24.04 e IP elástica `52.21.218.157`.
- Docker Compose ejecuta los cuatro contenedores; los secretos se generan en el servidor (`.env`).
- Nginx en el puerto 80 entrega el frontend compilado y reenvía `/api/auth-service/` al puerto 8081 y `/api/clinica-service/` al 8082.
- Swagger no se publica en internet; en el servidor se revisa con un túnel SSH a los puertos 8081 y 8082.

Actualizar el servidor después de un cambio en `main`:

```bash
cd ~/Veterinaria-backend && git pull
cd veterinaria-auth && ./mvnw -q clean package -DskipTests && cd ..
./mvnw -q clean package -DskipTests
docker compose up -d --build
```
