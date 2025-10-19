# Huella a Casa 🐾

**Huella a Casa** es una aplicación backend desarrollada en Java / Spring Boot para gestionar casos de mascotas **perdidas** y **encontradas**. La idea es ofrecer una plataforma donde usuarios puedan reportar mascotas perdidas, registrar mascotas encontradas, subir fotos, y consultar casos paginados — todo con una API REST robusta.

---

## 📋 Características principales

- Gestión de casos de mascotas perdidas (**Lost**) y encontradas (**Found**), con historia, ubicación aproximada, foto, contacto, etc.  
- Subida de archivos (fotos) via endpoint multipart.  
- Endpoints paginados para consultar listados.  
- DTOs para separar la capa de entidad y exposición.  
- Validaciones de entrada (por ejemplo con `@Valid`, `@NotBlank`).  
- Manejador global de excepciones.  
- Uso de herencia JPA: clase base común `BaseCase` con atributos compartidos.  
- Repositorios Spring Data JPA para persistencia.  
- Servicio centralizado (`CaseService`) que encapsula lógica de negocio.  
- Controlador REST con rutas bien definidas para “lost” y “found”.

---

## 🛠️ Tecnologías utilizadas

| Tecnología / Framework | Uso principal |
|------------------------|----------------|
| Java + Spring Boot     | Backend API REST |
| Spring Web              | Controladores, rutas HTTP |
| Spring Data JPA         | Persistencia ORM con entidades |
| Lombok                  | Simplificar getters/setters, constructores, builders |
| (Opcional) MapStruct   | Mapeo DTO ↔ Entidad |
| MultipartFile / archivos | Subir imágenes/fotos |
| Validación (`javax.validation`) | Validar datos entrantes |

---

## 🗂️ Estructura del proyecto

├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── ar/com/huella/huella/
│ │ │ ├── entity/ # Entidades JPA (BaseCase, Lost, Found, Location, CaseType)
│ │ │ ├── dto/ # DTOs para requests y respuestas
│ │ │ ├── repository/ # Interfaces JpaRepository (LostRepository, FoundRepository)
│ │ │ ├── service/ # Lógica de negocio (CaseService)
│ │ │ ├── controller/ # Endpoints REST (CaseController, FileController para subida)
│ │ │ └── exception/ # Excepciones globales (ResourceNotFoundException, GlobalExceptionHandler)
│ └── resources/
│ └── application.properties # Configuraciones (upload-dir, datasource, etc.)
├── pom.xml
└── README.md



---

## 🚀 Cómo ejecutar el proyecto en local

1. Cloná el repositorio:

   ```bash
   git clone https://github.com/Roman-Dario-Esquivel/huella-a-casa.git
   cd huella-a-casa
Configurá el archivo application.properties (en src/main/resources) con tus datos:

properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
spring.datasource.username=usuario
spring.datasource.password=contraseña

file.upload-dir=/ruta/local/para/guardar/archivos
Ejecutá con Maven:

bash
Copiar código
mvn clean install
mvn spring-boot:run
La aplicación se levantará por defecto en http://localhost:8080/

🧩 Endpoints disponibles (API)
Casos de mascotas
Método	Ruta	Descripción
POST /api/cases/lost	Crear nuevo caso perdido (envía LostCreateDto)	
POST /api/cases/found	Crear nuevo caso encontrado (envía FoundCreateDto)	
GET /api/cases/lost	Obtener listado de casos perdidos	
GET /api/cases/found	Obtener listado de casos encontrados	
GET /api/cases/lost/{id}	Obtener un caso perdido por ID	
GET /api/cases/found/{id}	Obtener un caso encontrado por ID	
DELETE /api/cases/lost/{id}	Eliminar caso perdido por ID	
DELETE /api/cases/found/{id}	Eliminar caso encontrado por ID	

Subida de fotos / archivos
Método	Ruta	Descripción
POST /api/upload/upload	Subir un archivo real (form-data con campo file)	
POST /api/upload/uploadUrl	Subir archivo y devolver URL para visualización	
GET /api/upload/view/{filename}	Visualizar el recurso (imagen) directamente	
GET /api/upload/files/{filename}	Descargar archivo adjunto	

Paginación de casos
Para obtener páginas de resultados (por ejemplo, casos encontrados):

arduino
Copiar código
GET /api/cases/found?page={page}&size={size}
(El controlador retorna un objeto Page<FoundDto> con content, totalPages, etc.)

✅ Buenas prácticas / recomendaciones
Usá @Valid en los parámetros de entrada para evitar entradas inválidas.

Comprobá que los uploads se realicen con multipart/form-data.

En rutas de visualización / descarga, manejá bien los tipos de contenido (MediaType).

Manejá errores con @ControllerAdvice para respuestas consistentes en fallas.

Si usás @SuperBuilder + MapStruct, asegurate de tener correctamente configurado el procesamiento de anotaciones en tu build.

📌 Futuras mejoras sugeridas
Agregar autenticación/autorización (usuarios, roles) para que solo usuarios verificados puedan reportar casos.

Permitir filtros por especie, zona geográfica, fecha.

Subida de múltiples fotos por caso.

Integración con frontend (React, Angular) para UI de reporte / consulta.

Configurar hosting en la nube y almacenamiento de archivos remoto (S3 u otro).

Notificaciones (correo / push) cuando alguien declara haber encontrado la mascota.

👥 Equipo / Autor
Roman Dario Esquivel — repositorio original

Contribuidores futuros son bienvenidos: forquear, proponer PRs, abrir issues.

## 📄 Licencia

Distribuido bajo la licencia [MIT](./LICENSE).  
© 2025 Roman Dario Esquivel. Todos los derechos reservados.

---

💙 Si este proyecto te resultó útil, ¡considerá dejar una estrella en GitHub!