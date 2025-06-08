🧠 Healthy Bot
Healthy Bot es una aplicación backend desarrollada en Java utilizando el framework Spring Boot. Está diseñada para gestionar usuarios y facilitar la interacción con un bot de salud, implementando una arquitectura MVC (Modelo-Vista-Controlador) para una estructura modular y escalable.

🛠 Tecnologías utilizadas
Java 17: Lenguaje de programación principal.
Spring Boot: Framework para el desarrollo rápido de aplicaciones Java.
Spring Security: Módulo para la autenticación y autorización de usuarios.
Hibernate: Framework ORM para la gestión de la base de datos.
MySQL: Sistema de gestión de bases de datos relacional.
Maven: Herramienta de gestión de dependencias y construcción del proyecto.

🗂 Estructura del proyecto
La estructura del proyecto sigue el patrón de diseño MVC:


src/
 └── main/
     ├── java/
     │    └── com/
     │        └── healthybot/
     │            ├── controller/        # Controladores REST
     │            ├── model/             # Entidades JPA
     │            ├── repository/        # Interfaces de repositorio
     │            ├── security/          # Configuración de seguridad
     │            └── service/           # Lógica de negocio
     └── resources/
          ├── application.properties    # Configuración de la aplicación
          └── static/                   # Archivos estáticos (si los hay)
⚙️ Configuración de la base de datos
La conexión a la base de datos MySQL se configura en el archivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/healthybot
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Asegúrate de reemplazar tu_contraseña con la contraseña de tu base de datos.

🚀 Ejecución del proyecto
Para ejecutar la aplicación, sigue estos pasos:

Clona el repositorio:

git clone https://github.com/CSamuelRod/healthy.bot.git
cd healthy.bot
Compila y ejecuta la aplicación:

bash
Copiar
Editar
./mvnw spring-boot:run
O si prefieres usar Maven directamente:


mvn spring-boot:run
La aplicación estará disponible en http://localhost:8080.

🔐 Seguridad
La aplicación utiliza Spring Security para proteger las rutas y gestionar la autenticación de usuarios. Los detalles de configuración se encuentran en el paquete security.
