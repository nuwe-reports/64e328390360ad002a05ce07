# Sistema de Gestión de Citas - Documentación de API
Este repositorio contiene una API para la gestión de citas en un hospital. Permite la creación, consulta y eliminación de citas médicas. La API está construida utilizando Spring Boot y se integra con una base de datos MySQL.

## Endpoints
Obtener todas las citas
```http
GET /api/appointments
```
Este endpoint devuelve una lista de todas las citas médicas registradas en el sistema.

### Obtener una cita por ID
```http
GET /api/appointments/{id}
```
Este endpoint permite obtener los detalles de una cita médica en base a su ID.

### Crear una nueva cita
```http
POST /api/appointment
```
Crea una nueva cita médica en el sistema. Debe proporcionarse un objeto JSON en el cuerpo de la solicitud que incluya los detalles de la cita, como el paciente, médico, sala, fecha y hora de inicio y fin.

### Eliminar una cita por ID
```http
DELETE /api/appointments/{id}
```
Elimina una cita médica del sistema en base a su ID.

### Eliminar todas las citas
```http
DELETE /api/appointments
```
Elimina todas las citas médicas registradas en el sistema.

# Uso
1. Clona este repositorio en tu máquina local.
2. Configura la base de datos MySQL en application.properties.
3. Ejecuta la aplicación Spring Boot.
4. Accede a los diferentes endpoints utilizando una herramienta como Postman o tu navegador web.