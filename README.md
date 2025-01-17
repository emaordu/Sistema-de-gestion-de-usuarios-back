Sistema de Gestión de Usuarios
-
Este proyecto es una API RESTful diseñada para la gestión de usuarios como parte de un sistema más grande. La API implementa funcionalidades de autenticación, autorización y manejo de usuarios mediante Spring Boot, Spring Security, y JWT, con conexión a una base de datos MySQL utilizando JPA y Hibernate.

Objetivo del Proyecto
-
Proveer un sistema backend que permita:

Registrar y autenticar usuarios.
Gestionar los datos de los usuarios (CRUD).
Asignar y manejar permisos sobre los sistemas disponibles.
Integrarse con otros sistemas internos de la organización.

## Características principales

- **Autenticación y autorización**: Uso de **JWT** (JSON Web Tokens) para proteger los endpoints.
- **Gestión de usuarios**: Alta, baja, modificación y consulta de usuarios desde una API REST.
- **Permisos**: Asignación de permisos personalizados a los usuarios según los sistemas definidos.
- **Datos iniciales**: Carga automática de sistemas y usuarios al iniciar la aplicación.
- **Seguridad**: Uso de **Spring Security** y encriptación de contraseñas con BCrypt.
- **Arquitectura limpia**: Dividida en capas (Controllers, Services, Repositories, DTOs y Models).

---

## Tecnologías utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.3.5**:
    - Spring Data JPA
    - Spring Security
    - Spring Web
- **JWT** para autenticación.
- **MySQL** como base de datos.

---

## Requisitos

Antes de ejecutar el proyecto, asegúrate de cumplir con los siguientes requisitos:

1. **Java**: Versión 17 o superior.
2. **Maven**: Para manejar las dependencias del proyecto.
3. **Base de datos**: MySQL instalado y configurado.
4. **Credenciales de base de datos**: Configuradas en el archivo `application.properties`.


Endpoints principales
-
Método del endpoint y su descripción

POST /login	        Genera un JWT para autenticación.

Ejemplo de json para login:

{

"username" : "emaordu",

"password" : "emanuel1234"

}

POST /authorize	    Verifica la validez del JWT.

GET	/users	        Lista todos los usuarios.

POST /register       Crea un nuevo usuario

Ejemplo de json para register:

{

"id" : 132424

"username" : "emaordu",

"password" : "emanuel1234"

"firstName" : "emanuel"

"lastName" : "orduña"


}

POST /users          Obtiene todos los usuarios de la base de datos

PUT	/users/{id}	    Actualiza un usuario por su ID.

DELETE /users/{id} 	Elimina un usuario por su ID.

Seguridad

JWT:

Se utiliza para autenticar y autorizar solicitudes.
Token generado en el endpoint /login.
Roles y permisos:

Los permisos son asignados a los usuarios en función de los sistemas disponibles.
Encriptación:

Las contraseñas son encriptadas con BCrypt antes de almacenarse en la base de datos.

Estructura del proyecto
-

src

├── main

│   ├── java/com/grupo1/demo

│   │   ├── Auth           # Clases relacionadas con autenticación y JWT

│   │   ├── Config         # Configuración de Spring Security

│   │   ├── Controllers    # Endpoints de la API

│   │   ├── DTOs           # Clases de transferencia de datos

│   │   ├── Models         # Entidades JPA

│   │   ├── Repositories   # Interfaces para acceso a datos

│   │   ├── Services       # Lógica de negocio

│   └── resources

│       ├── application.properties # Configuración de la aplicación



