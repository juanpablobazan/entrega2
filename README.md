# Clientes

API REST con Spring Boot para usuarios y cuentas.

## Requisitos

- Java 21
- Maven o el wrapper del proyecto
- MySQL

## Base de datos

Crear la base de datos `clientes` en MySQL y revisar las credenciales en `src/main/resources/application.properties`.

## Ejecución

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI: http://localhost:8080/v3/api-docs

## Endpoints

### Usuarios

- `GET /usuarios`
- `GET /usuarios/{id}`
- `POST /usuarios/guardar`
- `PUT /usuarios/editar/{id}`
- `DELETE /usuarios/eliminar/{id}`

### Cuentas

- `GET /api/cuenta/cuentas`
- `GET /api/cuenta/{id}`
- `POST /api/cuenta`
- `PUT /api/cuenta/{id}`
- `DELETE /api/cuenta/{id}`

### Cuentas standard

- `GET /api/cuenta-standard`
- `GET /api/cuenta-standard/{id}`
- `POST /api/cuenta-standard`
- `PUT /api/cuenta-standard/{id}`
- `DELETE /api/cuenta-standard/{id}`

### Cuentas premium

- `GET /api/cuenta-premium`
- `GET /api/cuenta-premium/{id}`
- `POST /api/cuenta-premium`
- `PUT /api/cuenta-premium/{id}`
- `DELETE /api/cuenta-premium/{id}`

## Relaciones entre entidades

`Usuario` tiene una colección de cuentas con `@OneToMany(mappedBy = "cliente")`. `Cuenta` tiene un campo `cliente` con `@ManyToOne` y la columna de unión `idCliente`.

`CuentaStandard` y `CuentaPremium` heredan de `Cuenta`. La entidad base usa la estrategia JPA `JOINED`.

## Pruebas

```powershell
.\mvnw.cmd test
```
