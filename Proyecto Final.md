# Proyecto Final

### 1. ELECCIÓN DEL PROYECTO

Identificar un lugar que tenga la necesidad de implementar un sistema:

- Veterinaria

---

### Requerimientos

- Se implementó el método de **Entrevistas Estructuradas** y **Observación** directa para capturar las necesidades.
    
    
    | **Entrevistado** | **Pregunta** | **Respuesta** | **Propuesta Requerimiento** |
    | --- | --- | --- | --- |
    | Dueño | ¿Quiénes van a usar el sistema y para qué? | Solo el personal. Los veterinarios para ver consultas, ver historial y la recepcionista para agendar y registrar cobros. Todos pueden manejar el inventario. | El sistema debe diferenciar entre veterinarios y recepcionistas para que cada uno tenga sus campos correctos. |
    | Veterinario | ¿Qué información es la crítica o necesaria para atender una emergencia? | Lo que se tiene que tener forzosamente es la especie, la raza, la edad y su peso y también las alergias de la mascota. | El sistema debe mantener un historial clínico digital completo y con acceso rápido a datos clave de la mascota. |
    | Recepcionista | ¿Cuál es la parte del  proceso más lenta para agendar una cita? | Tengo que llamar o revisar la agenda de cada doctor manualmente. Además,  a veces se me olvida enviar recordatorios a los dueños y se pierden citas. | El sistema debe gestionar una agenda de citas de varios veterinarios y enviar recordatorios automáticos. |
    | Dueño | ¿Cómo llevan su inventario? | Tenemos un Excel en la computadora de recepción.  | El sistema debe gestionar el inventario . |
    | Recepcionista | ¿Qué problemas ven con el sistema que tienen? | Necesito saber cuándo pedir vacunas o los alimentos, el problema es que es estar revisando manualmente la cantidad y muchas veces ni siquiera esta bien actualizado. | El sistema debe generar alertas de producto escaso. |
    | Recepcionista | ¿Cómo registras a un dueño que trae varias mascotas? | En el Excel, si traen tres mascotas por ejemplo, tengo que escribir el nombre del dueño 3 veces en 3 filas distintas. Si cambia su teléfono, tengo que editarlo 3 veces y a veces es difícil actualizar porque se pierden entre tantos pacientes | Se requiere una estructura donde un dueño se registre una sola vez y contenga una lista interna de sus mascotas. |
    | Veterinario | ¿Cómo llevan ha cabo el control de lo que le pasa a cada mascota? | En papel se llena un formulario que es su historia clínica cada vez, el problema es que se doblan o se manchan y luego no se le entiende a la letra de las personas. | El sistema debe tener un historial clínico digital simple y acumulativo. |
    | Recepcionista | Cuando un cliente agenda ¿Qué problemas tienes? | A veces anoto una cita a las 4:00 PM para un doctor, pero no vi que ya tenía otra anotada en la página anterior y se juntan y quedamos mal con el cliente. | El sistema debe validar automáticamente al crear una cita que el veterinario no tenga ya una cita en ese mismo momento. |
    | Veterinario | ¿Cómo calculan cuánto cobrarle al cliente? | Todos se saben los precios o miran la lista pegada en la pared hacen la suma y cobran, pero luego no hay nota y no sabemos de que es cada cobro. | El sistema debe tener un catálogo de servicios con su precio.
    
    El sistema debe tener un acumulado de servicios por visita. |
    | Veterinario | Al llegar a tu turno, ¿Cómo sabes qué pacientes te tocan? | Tengo que preguntarle a recepción o me pasan la agenda para revisarlo yo. | Cada veterinario debe poder ver sus citas sin ver la de otros doctores. |
    | Recepcionista | ¿Han tenido problemas por perdida de información? | Pues si estábamos usando Excel y no guardamos, si se ha llegado a perder lo de ese momento. y tenemos que ver que es lo que no se guardo. | El sistema debe implementar persistencia para asegurar que dueños, mascotas y citas no se pierdan al cerrar el programa. |
    | Veterinario | ¿Cómo buscan el teléfono de un dueño si hay una emergencia? | Tengo que buscar su expediente y buscar en su hoja el numero que dejaron, hasta ahora solo ha habido un incidente porque no se leía bien el número y no pudimos contactar pero el perrito salió bien. | El sistema debe permitir la búsqueda inmediata de un dueño para ver sus datos de contacto y sus mascotas en pantalla. |
    
    | **Observación (Lo que se vio en la clínica)** | **Posible Requerimiento (Solución en el sistema)** |
    | --- | --- |
    | **1.** Se observó que la recepcionista tacha con pluma roja las citas en la libreta cuando el cliente cancela o no llega, lo que hace que la hoja se vea sucia y difícil de leer al final del día. | El sistema debe manejar un estado de la cita (Programada, Cancelada, Completada) editable digitalmente para mantener el orden visual sin tachones. |
    | **2.** Durante una consulta, el veterinario tuvo que hojear varias páginas de una carpeta física para encontrar cuándo fue la última vacuna aplicada, perdiendo cerca de 3 minutos en la búsqueda. | El historial clínico debe mostrar los registros de forma cronológica y secuencial en pantalla para consulta inmediata. |
    | **3.** Al momento de cobrar una "Cirugía menor", la recepcionista tuvo que gritarle al doctor para preguntar el precio actual, ya que la lista de precios pegada en la pared estaba borrosa. | El sistema debe tener un catálogo de servicios precargado con precios fijos visible para el usuario. |
    | **4.** Se notó que al registrar un nuevo paciente, la recepcionista escribía la dirección completa del dueño nuevamente, a pesar de que ese dueño ya tenía otro perro registrado en el Excel. | El sistema debe permitir seleccionar un dueño existente y asignarle una nueva mascota sin tener que volver a capturar sus datos de contacto. |
- Validar los requerimientos.
- **Clasificar los requisitos**:
    - **Requisitos de negocio.**
        - El sistema debe eliminar la dependencia de agendas físicas y archivos de Excel propensos a errores.
        - El sistema debe centralizar la información para evitar la redundancia de datos de clientes.
        - El sistema debe garantizar que no se pierdan datos operativos al cerrar la aplicación (persistencia local).
        - El sistema debe agilizar el proceso de recepción y consulta médica.
    - **Requisitos de las partes interesadas.**
        
        **Veterinarios:**
        
        - Acceder únicamente a su propia agenda de citas del día.
        - Consultar el historial clínico acumulativo de las mascotas asignadas.
        - Registrar nuevos diagnósticos y tratamientos en el historial clínico (texto).
        - Visualizar los datos clave del paciente (especie, raza, edad, alergias) para emergencias.
        
        **Asistentes (Recepción):**
        
        - Registrar nuevos dueños y asociar múltiples mascotas a un solo perfil.
        - Agendar, cancelar y modificar el estado de las citas (Programada, Completada, Cancelada).
        - Validar la disponibilidad de horarios de los veterinarios para evitar duplicados.
        - Consultar el catálogo de servicios y precios para informar a los clientes.
        
        **Administrador de la Clínica:**
        
        - Gestionar el alta y baja de empleados (Veterinarios y Asistentes).
        - Configurar el catálogo de servicios (agregar servicios y actualizar precios).
        - Generar reportes básicos de productividad (citas por veterinario, mascotas por dueño).
        - Asegurar el resguardo de la información mediante la persistencia de datos.
    - **Requisitos de la solución.**
        - **Requisitos funcionales**
            - **Gestión de Dueños y Mascotas:** El sistema debe permitir el registro único de un dueño y la asociación de múltiples mascotas, permitiendo búsquedas rápidas por ID.
            - **Gestión de Citas:** El sistema debe permitir agendar citas validando automáticamente que el veterinario seleccionado no tenga conflictos de horario.
            - **Historial Clínico:** El sistema debe permitir agregar notas de texto secuenciales al expediente de cada mascota, creando un historial de consulta permanente.
            - **Gestión de Servicios:** El sistema debe mantener un catálogo de servicios con descripción y precio fijo para estandarizar los cobros.
            - **Gestión de Empleados:** El sistema debe diferenciar las funciones y atributos entre Veterinarios (especialidad, agenda) y Asistentes (área, soporte).
        - **Requisitos no funcionales**
            - **Requisito de rendimiento**
                - El sistema deberá ejecutarse de manera fluida en equipos de escritorio estándar con recursos limitados.
                - El tiempo de carga y guardado de datos no debe exceder los 2 segundos.
            - **Seguridad**
                - El sistema almacenará los datos de manera local, restringiendo el acceso únicamente al personal con acceso físico al equipo de la clínica.
                - La estructura de clases debe prevenir que un Asistente realice funciones exclusivas de diagnóstico médico (controlado por lógica de negocio).
            - **Fiabilidad**
                - El sistema debe asegurar la integridad de los datos en la base de datos, evitando corrupciones al cerrar el programa inesperadamente.
                - **Persistencia de Datos:** El sistema debe guardar automáticamente toda la información (dueños, mascotas, citas, empleados) la base de datos y cargarla al iniciar.
                - El sistema debe validar entradas de datos (fechas, números) para evitar errores de ejecución (crashes).
            - **Disponibilidad**
                - El sistema debe estar disponible el 100% del tiempo mientras el equipo de cómputo esté encendido, sin depender de conexión a internet.
            - **Mantenibilidad**
                - El código debe estar estructurado bajo el patrón MVC y principios POO para facilitar futuras actualizaciones o corrección de errores.
            - **Portabilidad**
                - El sistema debe ser ejecutable en cualquier sistema operativo que soporte la Máquina Virtual de Java (JVM).
        - **Requisitos de transición.**
            - Se debe realizar la carga inicial manual de los expedientes físicos más activos al nuevo sistema digital.
            - El sistema debe ser instalado y configurado en el equipo principal de la recepción.
            - El personal debe recibir capacitación sobre el flujo de trabajo: Registro -> Cita -> Consulta -> Guardado.
- Entregar el **ERS (Especificación de Requisitos del Sistema)** con los requerimientos.
    
    
    | Identificación del requerimiento: | **RF01** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Gestión de Dueños y Mascotas |
    | **Características:** | Registro único de dueño con múltiples mascotas (1:N) |
    | **Descripción del requerimiento:** | El sistema debe permitir registrar un dueño una única vez y asociarle múltiples mascotas. Debe permitir la búsqueda rápida de un dueño por su ID para visualizar todas sus mascotas asociadas sin reingresar datos de contacto. |
    | **Prioridad del requerimiento:** | Alta |
    
    | Identificación del requerimiento: | **RF02** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Gestión de Citas y Validación |
    | **Características:** | Agendamiento con validación de disponibilidad |
    | **Descripción del requerimiento:** | El sistema debe permitir agendar citas relacionando fecha, motivo, mascota, dueño y veterinario. Debe validar automáticamente que el veterinario seleccionado no tenga ya una cita asignada en ese mismo horario para evitar duplicados. |
    | **Prioridad del requerimiento:** | Alta |
    
    | Identificación del requerimiento: | **RF03** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Historial Clínico Digital |
    | **Características:** | Registro acumulativo de notas de texto |
    | **Descripción del requerimiento:** | El sistema debe permitir a los veterinarios agregar notas de texto secuenciales al expediente de una mascota. El historial debe ser acumulativo y de fácil lectura para consultas posteriores. |
    | **Prioridad del requerimiento:** | Alta |
    
    | Identificación del requerimiento: | **RF04** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Gestión de Servicios |
    | **Características:** | Catálogo de precios fijos |
    | **Descripción del requerimiento:** | El sistema debe contar con un catálogo de servicios (ej. Consulta, Vacuna, Cirugía) con descripción y precio definido, para estandarizar los cobros y evitar errores manuales por parte del personal. |
    | **Prioridad del requerimiento:** | Media |
    
    | Identificación del requerimiento: | **RF05** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Gestión de Empleados y Roles |
    | **Características:** | Diferenciación entre Veterinario y Asistente |
    | **Descripción del requerimiento:** | El sistema debe gestionar una jerarquía de empleados, diferenciando atributos y comportamientos entre Veterinarios (que tienen especialidad y atienden citas) y Asistentes (que tienen área asignada y apoyan en gestión). |
    | **Prioridad del requerimiento:** | Alta |
    
    | Identificación del requerimiento: | **RF06** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Reporte de Agenda por Veterinario |
    | **Características:** | Filtrado de citas personales |
    | **Descripción del requerimiento:** | El sistema debe permitir generar un reporte en pantalla donde, al ingresar el ID de un veterinario, se listen únicamente las citas programadas para ese doctor específico. |
    | **Prioridad del requerimiento:** | Media |
    
    | Identificación del requerimiento: | **RF07** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Control de Estado de Citas |
    | **Características:** | Edición de flujo de trabajo (Programada/Cancelada) |
    | **Descripción del requerimiento:** | El sistema debe permitir modificar el estado de una cita existente entre las opciones: "Programada", "Completada" (si ya se atendió) o "Cancelada" (si el cliente no asistió), para mantener el orden administrativo. |
    | **Prioridad del requerimiento:** | Media |
    
    | Identificación del requerimiento: | **RNF01** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Persistencia de Datos |
    | **Características:** | 1. Almacenamiento local 2. Integridad de datos |
    | **Descripción del requerimiento:** | El sistema deberá implementar persistencia de datos mediante base de datos, asegurando que la información de dueños, mascotas, citas y empleados se guarde y recupere automáticamente al cerrar y abrir el programa. |
    | **Prioridad del requerimiento:** | Alta |
    
    | Identificación del requerimiento: | **RNF02** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Rendimiento y Respuesta |
    | **Características:** | Velocidad en entorno de escritorio |
    | **Descripción del requerimiento:** | El sistema debe ser capaz de cargar y guardar la información en los en un tiempo inferior a 2 segundos para garantizar una operación fluida en la recepción de la clínica. |
    | **Prioridad del requerimiento:** | Media |
    
    | Identificación del requerimiento: | **RNF03** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Seguridad de Operación |
    | **Características:** | 1. Validación de lógica 2. Control de errores |
    | **Descripción del requerimiento:** | El sistema debe validar las entradas de datos (formatos de fecha, números) para prevenir cierres inesperados (crashes). El acceso a la información está restringido físicamente al equipo de cómputo de la clínica (no web). |
    | **Prioridad del requerimiento:** | Alta |
    
    | Identificación del requerimiento: | **RNF04** |
    | --- | --- |
    | **Nombre del Requerimiento:** | Portabilidad |
    | **Características:** | Compatibilidad JVM |
    | **Descripción del requerimiento:** | El sistema debe ser compatible con cualquier equipo de escritorio que soporte la Máquina Virtual de Java (Windows, Linux o Mac), facilitando su instalación sin dependencias complejas de bases de datos externas. |
    | **Prioridad del requerimiento:** | Alta |