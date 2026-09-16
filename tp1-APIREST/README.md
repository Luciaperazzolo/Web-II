# TP1 - API REST

API REST desarrollada con Spring Boot para gestionar productos y favoritos.

## Tecnologías utilizadas

* Java 25
* Spring Boot 4.1.x
* Maven
* Spring WebMVC
* Bean Validation
* Springdoc OpenAPI / Swagger UI
* RestClient
* DummyJSON

## Descripción

La aplicación expone una API REST que permite:

* Consultar productos desde una API externa.
* Consultar un producto por su ID.
* Crear favoritos.
* Obtener todos los favoritos.
* Obtener un favorito por su ID.
* Actualizar favoritos.
* Eliminar favoritos.
* Validar los datos recibidos.
* Manejar errores de forma uniforme.
* Documentar y probar la API mediante Swagger UI.

Los favoritos se almacenan en memoria, por lo que se pierden al reiniciar la aplicación.

## Arquitectura

El proyecto está organizado en capas:

Controller
    ↓
Service
    ↓
Repository
    ↓
RepositoryMemoria

### Controller

Recibe las solicitudes HTTP y devuelve las respuestas correspondientes.

### Service

Contiene la lógica de negocio de la aplicación.

### Repository

Define las operaciones necesarias para almacenar y consultar favoritos.

### RepositoryMemoria

Implementa el repositorio utilizando una lista en memoria.

### DTO

Se utilizan DTOs para definir los datos que recibe y devuelve la API.

### Exception

Contiene las excepciones personalizadas y el manejo global de errores.

## Productos

Los productos se obtienen desde la API externa:

https://dummyjson.com

### Obtener todos los productos

GET /api/productos

Respuesta exitosa:
200 OK

### Obtener un producto por ID

GET /api/productos/{id}

Respuesta exitosa:
200 OK

Si ocurre un problema al comunicarse con la API externa, la aplicación devuelve un error controlado.

## Favoritos

### Crear un favorito

POST /api/favoritos

Body:

{
  "productoId": 1,
  "nota": "Mi producto favorito"
}

Respuesta:
201 Created

### Obtener todos los favoritos

GET /api/favoritos

Respuesta:
200 OK

### Obtener un favorito por ID

GET /api/favoritos/{id}

Respuesta exitosa:
200 OK

Si el favorito no existe:
404 Not Found

### Actualizar un favorito

PUT /api/favoritos/{id}

Body:
{
  "productoId": 2,
  "nota": "Nota actualizada"
}

Respuesta exitosa:
200 OK

Si el favorito no existe:
404 Not Found

### Eliminar un favorito

DELETE /api/favoritos/{id}

Respuesta exitosa:
204 No Content

Si el favorito no existe:
404 Not Found

## Validaciones

Los datos de entrada de los favoritos se validan mediante Bean Validation.

Por ejemplo:

* `productoId` no puede ser `null`.
* `productoId` debe ser mayor que `0`.
* `nota` no puede estar vacía.
* `nota` no puede superar los 500 caracteres.

Ejemplo de solicitud inválida:
{
  "productoId": 0,
  "nota": ""
}

Respuesta:
400 Bad Request

La respuesta incluye el campo que produjo el error y el motivo de la validación.

## Manejo de errores

La aplicación utiliza `@RestControllerAdvice` para centralizar el manejo de errores.

Se contemplan:

### Error de validación

400 Bad Request

### Recurso no encontrado

404 Not Found

### Error de comunicación con la API externa

502 Bad Gateway

Los errores se devuelven utilizando una estructura JSON uniforme.

## Swagger

La API está documentada mediante Springdoc OpenAPI.

Swagger UI está disponible en:

http://localhost:8080/swagger-ui/index.html

Desde Swagger se pueden consultar y probar todos los endpoints de productos y favoritos.

## Ejecución del proyecto

Para ejecutar el proyecto se puede utilizar Maven Wrapper.
.\mvnw.cmd spring-boot:run

Para compilar y verificar el proyecto:
.\mvnw.cmd clean package

El proyecto debe finalizar con:
BUILD SUCCESS

Una vez iniciada la aplicación, la API queda disponible en:

http://localhost:8080

## Endpoints principales

| Método | Endpoint              | Descripción                 |
| ------ | --------------------- | --------------------------- |
| GET    | `/api/productos`      | Obtener todos los productos |
| GET    | `/api/productos/{id}` | Obtener producto por ID     |
| POST   | `/api/favoritos`      | Crear favorito              |
| GET    | `/api/favoritos`      | Obtener todos los favoritos |
| GET    | `/api/favoritos/{id}` | Obtener favorito por ID     |
| PUT    | `/api/favoritos/{id}` | Actualizar favorito         |
| DELETE | `/api/favoritos/{id}` | Eliminar favorito           |

## Estado del proyecto

El proyecto cuenta con:

* CRUD de favoritos funcionando.
* Consumo de API externa funcionando.
* DTOs implementados.
* Validaciones implementadas.
* Manejo global de excepciones.
* Documentación OpenAPI/Swagger.
* Pruebas manuales de casos exitosos y errores.
* Compilación exitosa con Maven.
