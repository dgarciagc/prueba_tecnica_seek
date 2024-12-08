# Sistema de Gestión de Candidatos

Este proyecto es un backend para gestionar información de candidatos en procesos de selección y contratación. Utiliza **Spring Boot**, **MySQL** y una arquitectura hexagonal, ofreciendo una API REST protegida con autenticación JWT.

## Características

- **CRUD Completo**: Operaciones para crear, leer, actualizar y eliminar candidatos.
- **Seguridad**: Autenticación y autorización con JWT.
- **Gestor de Migraciones**: Flyway para la gestión de la base de datos.
- **Documentación de API**: Swagger UI integrado.
- **Pruebas**: Pruebas unitarias e integradas usando JUnit y Mockito.
- **Arquitectura Hexagonal**: División en capas de dominio, repositorio y servicio.

## Requisitos Previos

- **Java 17** o superior
- **MySQL** (o acceso a un servidor de base de datos compatible)
- **Maven o Gradle** para la construcción del proyecto
- **Postman** o Thunder Client para probar los endpoints

## Tecnologías Usadas

- Spring Boot
- Spring Data JPA
- Spring Security
- Flyway
- Swagger/OpenAPI
- JWT (Json Web Token)
- MySQL
- JUnit y Mockito para pruebas

## Configuración del Proyecto

### 1. Clonar el Repositorio
```bash
https://github.com/usuario/proyecto-candidatos.git
```
### 2. LINK Del Swagger
```bash
Swagger (https://pruebatecnicaseek-production.up.railway.app/api/v1/swagger-ui/index.html#/)
```
### 3. LINK Del servidor 
```bash
Server ([https://pruebatecnicaseek-production.up.railway.app/api/v1/swagger-ui/index.html#/](https://pruebatecnicaseek-production.up.railway.app/api/v1/))
```

### 4. Configurar la Base de Datos
```bash
- Crea una base de datos en MySQL llamada `recruitment_db`.
- Configura las credenciales en el archivo `application.yml`.
```yaml

spring:
  datasource:
    url: jdbc:mysql://<TU_HOST>:3306/recruitment_db
    username: <TU_USUARIO>
  driver-class-name: com.mysql.cj.jdbc.Driver
```
