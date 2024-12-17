# Login con Spring Security, JSF, PrimeFaces y Spring Boot

Este proyecto contiene las configuraciones necesarias para integrar **Spring Security** con **JSF y PrimeFaces**, permitiendo proteger tu aplicación mediante un formulario de inicio de sesión, gestionar accesos según roles de usuario y personalizar las respuestas a errores HTTP:

-   **403 (Prohibido)**: Acceso denegado a recursos restringidos.
-   **404 (No encontrado)**: Manejo de rutas inexistentes.

Además, incluye:

-   **Conexión a base de datos** para almacenar usuarios, roles y permisos.
-   Un **menú dinámico** construido con **PrimeFaces**, que adapta las opciones de navegación según los roles y permisos del usuario autenticado.
## Características

- **Login:** Página de inicio de sesión para la autenticación de usuarios.

- **Home:** Página principal para redirigir al usuario autenticado.

- **Errores personalizados:** Páginas diseñadas para manejar errores 403 (Prohibido) y 404 (No encontrado).

- **Estilos modernos:** Uso de PrimeFlex CSS para diseño responsivo y PrimeIcons para íconos.

##  Herramientas utilizadas:
- **Java [17 o Superior]** como lenguaje de programación.
- **MySQL [8 o Superior]** como motor de la base de datos.
- **Spring Boot 3 [3.3.5]**  para el uso de herramientas de microservicios.
- **Jakarta Server Faces [4.0]** para la creación de las vistas en XHTML.
- **PrimeFaces [14.0.0]**  para el uso de componentes.
- **JoinFaces [5.4.0]** para integrar Spring Boot y JSF.


## Explicación del proyecto

Puedes ver a detalle el proyecto directamente desde mi canal de YouTube [https://youtu.be/A6IitV7X-bk?si=ewnpF-ITK-ap6pht](https://youtu.be/A6IitV7X-bk?si=ewnpF-ITK-ap6pht)

## Script de la base de datos

```
INSERT INTO rol (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO rol (nombre) VALUES ('ROLE_USER');

INSERT INTO usuario (username, password, estado) VALUES ('admin', '$2a$10$.RiksIT6TUUrb8zJKSkmYuACSWynE1MGeZ8jbW8ww4dEixi9tbPai', true);
INSERT INTO usuario (username, password, estado) VALUES ('user', '$2a$10$.RiksIT6TUUrb8zJKSkmYuACSWynE1MGeZ8jbW8ww4dEixi9tbPai', true);

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 1); -- admin -> ROLE_ADMIN
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (2, 2); -- user -> ROLE_USER

INSERT INTO ruta (ruta_url) VALUES ('/home.xhtml');
INSERT INTO ruta (ruta_url) VALUES ('/nosotros.xhtml');
INSERT INTO ruta (ruta_url) VALUES ('/preguntas.xhtml');
INSERT INTO ruta (ruta_url) VALUES ('/admin/**');
INSERT INTO ruta (ruta_url) VALUES ('/operario/**');

INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (1, 1);
INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (1, 2);
INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (2, 1);
INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (3, 1);
INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (3, 2);
INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (4, 1);
INSERT INTO ruta_rol (id_ruta, id_rol) VALUES (5, 2);

```



## Cómo ejecutar el proyecto:

-   Clona este repositorio.
-   Configura las propiedades de la base de datos en `application.yml`.
-   Ejecuta la aplicación con tu IDE o desde la terminal.
-   Accede a la aplicación en `http://localhost:8080/login.xhtml`.

## Contribuciones

¡Gracias por visitar este repositorio! Si te resulta útil, no olvides darle una ⭐ y compartirlo con otros desarrolladores. 😊

¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar este proyecto, siéntete libre de abrir un issue o enviar un pull request.

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
