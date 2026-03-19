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


---

# 🧠 FRASE FINAL PARA ENTREVISTA

Dila así tal cual:

> "Implementé el microservicio usando arquitectura hexagonal, contract-first con OpenAPI, WebFlux para entrada reactiva y encapsulé operaciones bloqueantes de JPA con boundedElastic. Además incluí validaciones configurables, manejo global de errores y generación de JWT."

---

# 🎯 RESULTADO FINAL

Con todo esto tienes:

| Nivel | Estado |
|------|--------|
| Arquitectura | ✔ senior |
| Código | ✔ limpio |
| Seguridad | ✔ |
| Validación | ✔ |
| Testing | ✔ |
| Documentación | ✔ |

---

# 🚀 SI QUIERES UN EXTRA FINAL

Te puedo dar:

- Dockerfile
- docker-compose con PostgreSQL
- JWT validation filter
- healthcheck
- metrics

Solo dime:

👉 *“quiero dejarlo nivel empresa real”* 💼🔥