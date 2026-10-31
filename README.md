# Veterinaria San Marcos - Backend

API REST en Spring Boot para la Evaluación Parcial 3 de DSY1104 Desarrollo FullStack II.

Frontend: repositorio `veterinaria-san-marcos-frontend`.

## Requisitos

- JDK 21
- MySQL

## Crear la base de datos

Ejecutar una vez:

```sql
CREATE DATABASE veterinaria_san_marcos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

En local se conecta con el usuario `root` sin contraseña.

## Ejecutar en Windows

```powershell
.\mvnw.cmd spring-boot:run
```

Swagger UI: http://localhost:8080/swagger-ui/index.html
