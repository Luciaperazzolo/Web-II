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

TP2 — Persistencia y migraciones
Este trabajo práctico continúa el proyecto del TP1, incorporando persistencia en PostgreSQL mediante Spring Data JPA e Hibernate, migraciones versionadas con Flyway y una relación entre favoritos y listas.

El catálogo de productos continúa funcionando como un servicio de solo lectura que obtiene información de una API externa. Los favoritos y las listas, en cambio, son recursos propios de la aplicación y se almacenan en PostgreSQL.

 ## Tecnologías utilizadas
Java y Spring Boot.
Spring Data JPA e Hibernate.
PostgreSQL.
Docker para ejecutar la base de datos.
Flyway para gestionar las migraciones.
Swagger / OpenAPI para documentar y probar los endpoints.

## Cómo ejecutar el proyecto
1. Iniciar PostgreSQL
Es necesario tener Docker Desktop iniciado y el contenedor PostgreSQL creado.
Si el contenedor ya existe y está detenido, se puede iniciar con:
##docker start tp1-postgres

La configuración de conexión utilizada en application.properties es:

spring.datasource.url=jdbc:postgresql://localhost:5432/tp1
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.open-in-view=false

Si se utiliza otra configuración de base de datos, deben ajustarse estos valores.

2. Iniciar la aplicación
Desde la raíz del proyecto, ejecutar:

$env:JAVA_TOOL_OPTIONS="-Duser.timezone=UTC"
.\mvnw.cmd spring-boot:run

La aplicación utiliza Flyway para aplicar las migraciones pendientes y Hibernate para validar que el esquema de la base de datos coincida con las entidades. Hibernate no crea ni modifica el esquema automáticamente porque se utiliza ddl-auto=validate.

3. Verificar las migraciones
Las migraciones se encuentran en:

src/main/resources/db/migration/

V1__create_favoritos.sql: crea la tabla de favoritos.
V2__create_listas.sql: crea la tabla de listas.
V3__add_lista_id_a_favoritos.sql: incorpora la relación entre favoritos y listas.
V4__lista_id_obligatorio.sql: asigna una lista a los favoritos existentes y establece lista_id como obligatorio.

Flyway registra las migraciones aplicadas en la tabla flyway_schema_history. Se puede consultar esa tabla en PostgreSQL para verificar su estado.

## Persistencia y arquitectura hexagonal

En el TP1, los favoritos se almacenaban en memoria. En este práctico, esa persistencia se reemplazó por PostgreSQL mediante Spring Data JPA.

Se incorporaron las entidades FavoritoEntity y ListaEntity, los repositorios JPA y los adapters FavoritoRepositoryAdapter y ListaRepositoryAdapter, que convierten los modelos del dominio en entidades JPA y delegan las operaciones en la base de datos.

FavoritoRepository funciona como un puerto porque define un contrato con las operaciones que necesita el servicio, sin depender de una implementación concreta. El adapter JPA implementa ese contrato y se encarga de la persistencia.

Esta separación permite cambiar la implementación de almacenamiento sin tener que modificar necesariamente el servicio ni el controlador. En la migración se modificaron las clases relacionadas con la infraestructura y la persistencia; las clases que mantuvieron el mismo contrato pudieron seguir funcionando sin depender directamente de JPA.

## Relación entre favoritos y listas

Cada favorito pertenece a una lista mediante el campo listaId. En la base de datos, esta relación se representa mediante la columna lista_id de la tabla favoritos, que referencia la clave primaria de listas.

La relación se implementa en JPA mediante @ManyToOne. Para consultar los favoritos de una lista se utiliza una consulta derivada del repositorio, sin necesidad de agregar una relación bidireccional @OneToMany.

La lista General se utiliza para asignar los favoritos que existían antes de que la relación fuera obligatoria.

## Evolución del esquema con Flyway

Las migraciones aplicadas se conservan sin modificaciones posteriores. Cuando se necesita cambiar el esquema, se agrega una nueva migración versionada.

Esto permite que Flyway registre qué cambios se aplicaron en cada base de datos y mantenga un historial consistente. Modificar una migración que ya fue aplicada puede provocar diferencias entre su contenido actual y la suma de verificación registrada por Flyway, generando un error de validación.

La migración V4 primero crea una lista por defecto si no existe, después asigna esa lista a los favoritos que todavía tienen lista_id nulo y, finalmente, establece la columna como NOT NULL. Así se conservan los registros anteriores sin dejar favoritos sin lista.

## Transacciones y atomicidad

La operación POST /api/listas/{origenId}/mover-favoritos permite mover todos los favoritos de una lista de origen a una lista de destino y luego eliminar la lista de origen.

El método de servicio está anotado con @Transactional para que las modificaciones de los favoritos y la eliminación de la lista formen parte de una misma transacción.

Esto se relaciona con la atomicidad de ACID: la operación debe completarse por entero o revertirse ante un error. Sin la transacción, podría ocurrir que algunos favoritos ya hubieran sido reasignados y que después fallara la eliminación de la lista de origen. La base de datos podría quedar en un estado parcial, con parte de la operación confirmada y parte sin completar.

Endpoints
Productos
GET /api/productos
GET /api/productos/{id}

El catálogo continúa consultándose desde una API externa y no se persiste en la base de datos local.

Favoritos
POST /api/favoritos
GET /api/favoritos
GET /api/favoritos/{id}
PUT /api/favoritos/{id}
DELETE /api/favoritos/{id}

Los favoritos se almacenan en PostgreSQL y sus datos se conservan después de reiniciar la aplicación.

Listas
POST /api/listas: crear una lista.
GET /api/listas: listar las listas.
GET /api/listas/{id}: consultar una lista.
GET /api/listas/{id}/favoritos: consultar los favoritos de una lista.
DELETE /api/listas/{id}: eliminar una lista vacía.
POST /api/listas/{origenId}/mover-favoritos: mover los favoritos a otra lista y eliminar la lista de origen.

Se utiliza 404 Not Found cuando no existe el recurso solicitado y 409 Conflict cuando una operación no puede realizarse por un conflicto con el estado actual de los datos, como eliminar una lista que contiene favoritos o intentar mover favoritos a la misma lista.

## Documentación y pruebas

La API se puede explorar y probar mediante Swagger UI, disponible normalmente en:

http://localhost:8080/swagger-ui/index.html

Se probaron operaciones de creación, consulta, actualización y eliminación de favoritos, además de la gestión de listas y el movimiento transaccional de favoritos. También se verificaron respuestas de error como 404 Not Found y 409 Conflict, y se comprobó que los datos persistieran después de reiniciar la aplicación.
