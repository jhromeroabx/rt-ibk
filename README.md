# Users Microservice

Microservicio desarrollado con Spring Boot 3 + WebFlux + JPA bajo arquitectura hexagonal.

## 🚀 Tecnologías
- Java 17
- Spring Boot 3
- WebFlux
- JPA / Hibernate
- H2 Database
- OpenAPI (Contract First)
- JWT

## 📌 Arquitectura
- Domain
- Application (Use Cases)
- Infrastructure (Adapters)
- Config

## 🔐 Seguridad
- Generación de JWT en registro
- Password encriptado con BCrypt
- Validación configurable por regex

## 📦 Endpoint principal

POST /api/v1/users

## 🧪 Base de datos
- H2 en memoria

## ▶️ Ejecución

```bash
mvn clean install
mvn spring-boot:run
```

## 🌐 Swagger en Codespaces

Si ejecutas la app detrás del proxy de GitHub Codespaces, Spring debe procesar los headers reenviados para que OpenAPI no publique `http://localhost:8080` como servidor.

La configuración ya queda habilitada con:

```yaml
server:
	forward-headers-strategy: framework
```

Con eso, Swagger/OpenAPI toma automáticamente la URL pública del Codespace cuando accedes desde el navegador.