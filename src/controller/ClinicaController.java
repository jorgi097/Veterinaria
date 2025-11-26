package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.*;

// Controlador principal que gestiona todas las operaciones de la clínica

public class ClinicaController {
    // Instancia única del controlador (patrón Singleton)
    private static ClinicaController instance;
    
    // Colecciones para almacenar los datos del sistema
    // Mapas que asocian IDs con objetos
    private Map<String, Mascota> mascotas;
    private Map<String, Dueno> duenos;
    private Map<String, Empleado> empleados;
    private Map<String, Cita> citas;
    private Map<String, Servicio> servicios;
    private Map<String, Producto> productos; // Nuevo mapa para inventario
    
    // Constructor privado para implementar Singleton
    // Solo se puede crear una instancia desde dentro de la clase
    private ClinicaController() {
        // Inicializar las colecciones vacías
        mascotas = new HashMap<>();
        duenos = new HashMap<>();
        empleados = new HashMap<>();
        citas = new HashMap<>();
        servicios = new HashMap<>();
        productos = new HashMap<>();
        // Cargar los servicios predeterminados
        inicializarServicios();
    }
    
    // Obtiene la instancia única del controlador
    // Si no existe, la crea; si existe, devuelve la existente
    public static ClinicaController getInstance() {
        if (instance == null) {
            instance = new ClinicaController();
        }
        return instance;
    }
    
    // Inicializa los servicios ofrecidosc en la clínica con datos predeterminados
    private void inicializarServicios() {
        servicios.put("S001", new Servicio("S001", "Consulta General", "Consulta veterinaria general", 300.0));
        servicios.put("S002", new Servicio("S002", "Vacunación", "Aplicación de vacunas", 250.0));
        servicios.put("S003", new Servicio("S003", "Baño y Estética", "Servicio de baño y corte", 200.0));
        servicios.put("S004", new Servicio("S004", "Cirugía Menor", "Procedimientos quirúrgicos menores", 1500.0));
        servicios.put("S005", new Servicio("S005", "Desparasitación", "Tratamiento antiparasitario", 150.0));
        servicios.put("S006", new Servicio("S006", "Análisis Clínicos", "Estudios de laboratorio", 500.0));
    }
    
    // ============ GESTIÓN DE DUEÑOS ============
    
    // Registra un nuevo dueño en el sistema
    // Retorna true si el registro fue exitoso, false si el ID ya existe
    public boolean registrarDueno(String id, String nombre, String direccion, String telefono, String correo) {
        // Verificar que el ID no esté duplicado
        if (duenos.containsKey(id)) {
            return false;
        }
        // Crear nuevo objeto Dueno
        Dueno dueno = new Dueno(id, nombre, direccion, telefono, correo);
        // Agregar al mapa de dueños
        duenos.put(id, dueno);
        return true;
    }
    
    // Obtiene un dueño por su ID
    // Retorna el objeto Dueno o null si no existe
    public Dueno obtenerDueno(String id) {
        return duenos.get(id);
    }
    
    // Lista todos los dueños registrados en el sistema
    // Retorna una lista con todos los dueños
    public List<Dueno> listarDuenos() {
        return new ArrayList<>(duenos.values());
    }
    
    // Actualiza la información de un dueño existente
    // Retorna true si la actualización fue exitosa, false si el dueño no existe
    public boolean actualizarDueno(String id, String nombre, String direccion, String telefono, String correo) {
        Dueno dueno = duenos.get(id);
        // Verificar que el dueño exista
        if (dueno == null) {
            return false;
        }
        // Actualizar los datos del dueño
        dueno.setNombre(nombre);
        dueno.setDireccion(direccion);
        dueno.setTelefono(telefono);
        dueno.setCorreo(correo);
        return true;
    }
    
    // Elimina un dueño del sistema
    // Solo permite eliminarlo si no tiene mascotas asociadas
    // Retorna true si se eliminó, false si no existe o tiene mascotas
    public boolean eliminarDueno(String id) {
        Dueno dueno = duenos.get(id);
        // No se puede eliminar si no existe o tiene mascotas asociadas
        if (dueno == null || !dueno.getMascotas().isEmpty()) {
            return false; // No se puede eliminar si tiene mascotas
        }
        duenos.remove(id);
        return true;
    }
    
    // ============ GESTIÓN DE MASCOTAS ============
    
    // Registra una nueva mascota en el sistema
    // La mascota debe estar asociada a un dueño existente
    // Retorna true si el registro fue exitoso, false si el ID ya existe o el dueño no existe
    public boolean registrarMascota(String id, String nombre, String especie, String raza, 
                                   int edad, double peso, String alergias, String idDueno) {
        // Verificar que el ID de la mascota no esté duplicado
        if (mascotas.containsKey(id)) {
            return false;
        }
        // Obtener el dueño asociado
        Dueno dueno = duenos.get(idDueno);
        // Verificar que el dueño exista
        if (dueno == null) {
            return false;
        }
        // Crear nueva mascota con su dueño
        Mascota mascota = new Mascota(id, nombre, especie, raza, edad, peso, alergias, dueno);
        // Agregar al mapa de mascotas
        mascotas.put(id, mascota);
        // Agregar la mascota a la lista del dueño (relación bidireccional)
        dueno.agregarMascota(mascota);
        return true;
    }
    
    // Obtiene una mascota por su ID
    // Retorna el objeto Mascota o null si no existe
    public Mascota obtenerMascota(String id) {
        return mascotas.get(id);
    }
    
    // Lista todas las mascotas registradas en el sistema
    // Retorna una lista con todas las mascotas
    public List<Mascota> listarMascotas() {
        return new ArrayList<>(mascotas.values());
    }
    
    // Actualiza la información de una mascota existente
    // Retorna true si la actualización fue exitosa, false si la mascota no existe
    public boolean actualizarMascota(String id, String nombre, String especie, String raza, 
                                    int edad, double peso, String alergias) {
        Mascota mascota = mascotas.get(id);
        // Verificar que la mascota exista
        if (mascota == null) {
            return false;
        }
        // Actualizar los datos de la mascota
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setEdad(edad);
        mascota.setPeso(peso);
        mascota.setAlergias(alergias);
        return true;
    }
    
    // Elimina una mascota del sistema
    // También la elimina de la lista de mascotas de su dueño
    // Retorna true si se eliminó, false si no existe
    public boolean eliminarMascota(String id) {
        Mascota mascota = mascotas.get(id);
        // Verificar que la mascota exista
        if (mascota == null) {
            return false;
        }
        // Obtener el dueño de la mascota
        Dueno dueno = mascota.getDueno();
        // Si tiene dueño, eliminarla de su lista
        if (dueno != null) {
            dueno.eliminarMascota(mascota);
        }
        // Eliminar del mapa de mascotas
        mascotas.remove(id);
        return true;
    }
    
    // Agrega un registro al historial clínico de una mascota
    // Retorna true si se agregó, false si la mascota no existe
    public boolean agregarHistorialClinico(String idMascota, String registro) {
        Mascota mascota = mascotas.get(idMascota);
        // Verificar que la mascota exista
        if (mascota == null) {
            return false;
        }
        // Agregar el registro al historial
        mascota.agregarHistorialClinico(registro);
        return true;
    }
    
    // ============ GESTIÓN DE EMPLEADOS ============
    
    // Registra un nuevo veterinario en el sistema
    // Retorna true si el registro fue exitoso, false si el ID ya existe
    public boolean registrarVeterinario(String id, String nombre, String correo, String telefono, 
                                       String horario, String especialidad) {
        // Verificar que el ID no esté duplicado
        if (empleados.containsKey(id)) {
            return false;
        }
        // Crear nuevo veterinario
        Veterinario veterinario = new Veterinario(id, nombre, correo, telefono, horario, especialidad);
        // Agregar al mapa de empleados
        empleados.put(id, veterinario);
        return true;
    }
    
    // Registra un nuevo asistente en el sistema
    // Retorna true si el registro fue exitoso, false si el ID ya existe
    public boolean registrarAsistente(String id, String nombre, String correo, String telefono, 
                                     String horario, String area) {
        // Verificar que el ID no esté duplicado
        if (empleados.containsKey(id)) {
            return false;
        }
        // Crear nuevo asistente
        Asistente asistente = new Asistente(id, nombre, correo, telefono, horario, area);
        // Agregar al mapa de empleados
        empleados.put(id, asistente);
        return true;
    }
    
    // Obtiene un empleado por su ID
    // Retorna el objeto Empleado (puede ser Veterinario o Asistente) o null si no existe
    public Empleado obtenerEmpleado(String id) {
        return empleados.get(id);
    }
    
    // Lista todos los empleados registrados (veterinarios y asistentes)
    // Retorna una lista con todos los empleados
    public List<Empleado> listarEmpleados() {
        return new ArrayList<>(empleados.values());
    }
    
    // Lista solo los veterinarios (filtra los empleados por tipo)
    // Demuestra el uso de Streams y filtrado por instanceof
    // Retorna una lista solo con veterinarios
    public List<Veterinario> listarVeterinarios() {
        return empleados.values().stream()
                .filter(e -> e instanceof Veterinario)
                .map(e -> (Veterinario) e)
                .collect(Collectors.toList());
    }
    
    // Elimina un empleado del sistema
    // Retorna true si se eliminó, false si no existe
    public boolean eliminarEmpleado(String id) {
        // Verificar que el empleado exista
        if (!empleados.containsKey(id)) {
            return false;
        }
        // Eliminar del mapa de empleados
        empleados.remove(id);
        return true;
    }
    
    // ============ GESTIÓN DE CITAS ============
    
    // Agenda una nueva cita médica en el sistema
    // Valida que no haya conflictos de horario para el veterinario
    // Retorna true si se agendó exitosamente, false si hay conflicto o datos inválidos
    public boolean agendarCita(String id, LocalDateTime fechaHora, String motivo, 
                              String idMascota, String idDueno, String idVeterinario) {
        // Verificar que el ID de la cita no esté duplicado
        if (citas.containsKey(id)) {
            return false;
        }
        
        // Obtener las entidades relacionadas
        Mascota mascota = mascotas.get(idMascota);
        Dueno dueno = duenos.get(idDueno);
        Empleado emp = empleados.get(idVeterinario);
        
        // Validar que existan y que el empleado sea un veterinario
        if (mascota == null || dueno == null || !(emp instanceof Veterinario)) {
            return false;
        }
        
        Veterinario veterinario = (Veterinario) emp;
        
        // Validar que no haya cita duplicada en el mismo horario
        if (validarDisponibilidadVeterinario(veterinario, fechaHora)) {
            // Crear la cita con todas las relaciones
            Cita cita = new Cita(id, fechaHora, motivo, mascota, dueno, veterinario);
            // Agregar al mapa de citas
            citas.put(id, cita);
            // Agregar la cita a la lista del veterinario
            veterinario.agregarCita(cita);
            return true;
        }
        return false;
    }
    
    // Valida que el veterinario esté disponible en el horario especificado
    // Revisa todas las citas del veterinario para evitar conflictos
    // Retorna true si está disponible, false si ya tiene una cita programada
    private boolean validarDisponibilidadVeterinario(Veterinario veterinario, LocalDateTime fechaHora) {
        // Recorrer todas las citas asignadas al veterinario
        for (Cita cita : veterinario.getCitasAsignadas()) {
            // Si hay una cita en el mismo horario y está programada, hay conflicto
            if (cita.getFechaHora().equals(fechaHora) && cita.getEstado().equals("Programada")) {
                return false;
            }
        }
        return true;
    }
    
    // Obtiene una cita por su ID
    // Retorna el objeto Cita o null si no existe
    public Cita obtenerCita(String id) {
        return citas.get(id);
    }
    
    // Lista todas las citas registradas en el sistema
    // Retorna una lista con todas las citas
    public List<Cita> listarCitas() {
        return new ArrayList<>(citas.values());
    }
    
    // Cancela una cita cambiando su estado a "Cancelada"
    // Retorna true si se canceló, false si la cita no existe
    public boolean cancelarCita(String id) {
        Cita cita = citas.get(id);
        // Verificar que la cita exista
        if (cita == null) {
            return false;
        }
        // Cambiar el estado a cancelada
        cita.setEstado("Cancelada");
        return true;
    }
    
    // Marca una cita como completada cambiando su estado a "Completada"
    // Retorna true si se completó, false si la cita no existe
    public boolean completarCita(String id) {
        Cita cita = citas.get(id);
        // Verificar que la cita exista
        if (cita == null) {
            return false;
        }
        // Cambiar el estado a completada
        cita.setEstado("Completada");
        return true;
    }
    
    // ============ GESTIÓN DE SERVICIOS ============
    
    // Obtiene un servicio por su ID
    // Retorna el objeto Servicio o null si no existe
    public Servicio obtenerServicio(String id) {
        return servicios.get(id);
    }
    
    // Lista todos los servicios disponibles en la clínica
    // Retorna una lista con todos los servicios
    public List<Servicio> listarServicios() {
        return new ArrayList<>(servicios.values());
    }
    
    // Agrega un nuevo servicio al catálogo de la clínica
    // Retorna true si se agregó, false si el ID ya existe
    public boolean agregarServicio(String id, String nombre, String descripcion, double precio) {
        // Verificar que el ID no esté duplicado
        if (servicios.containsKey(id)) {
            return false;
        }
        // Crear y agregar el nuevo servicio
        servicios.put(id, new Servicio(id, nombre, descripcion, precio));
        return true;
    }
    
    // ============ MÉTODOS DE UTILIDAD ============
    
    // Genera un reporte de todas las citas de un veterinario específico
    // Usa programación funcional (Streams) para filtrar
    // Retorna lista de citas del veterinario especificado
    public List<Cita> obtenerCitasPorVeterinario(String idVeterinario) {
        return citas.values().stream()
                .filter(c -> c.getVeterinario().getId().equals(idVeterinario))
                .collect(Collectors.toList());
    }
    
    // Genera un reporte de todas las mascotas de un dueño específico
    // Retorna lista de mascotas del dueño o lista vacía si el dueño no existe
    public List<Mascota> obtenerMascotasPorDueno(String idDueno) {
        Dueno dueno = duenos.get(idDueno);
        return dueno != null ? dueno.getMascotas() : new ArrayList<>();
    }
    
    // Demuestra polimorfismo con los empleados
    // Cada tipo de empleado (Veterinario/Asistente) ejecuta su propia versión del método
    public void mostrarTareasEmpleados() {
        for (Empleado empleado : empleados.values()) {
            // Llamada polimórfica: cada empleado ejecuta su propia implementación
            System.out.println(empleado.realizarTarea());
        }
    }
    
    // ============ GESTIÓN DE INVENTARIO ============
    
    // Registra un nuevo producto en el inventario
    // Retorna true si el registro fue exitoso, false si el ID ya existe
    public boolean registrarProducto(String id, String nombre, String categoria, 
                                    int cantidad, int cantidadMinima, double precio) {
        // Verificar que el ID no esté duplicado
        if (productos.containsKey(id)) {
            return false;
        }
        // Crear nuevo producto
        Producto producto = new Producto(id, nombre, categoria, cantidad, cantidadMinima, precio);
        // Agregar al mapa de productos
        productos.put(id, producto);
        return true;
    }
    
    // Obtiene un producto por su ID
    // Retorna el objeto Producto o null si no existe
    public Producto obtenerProducto(String id) {
        return productos.get(id);
    }
    
    // Lista todos los productos del inventario
    // Retorna una lista con todos los productos
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos.values());
    }
    
    // Actualiza un producto existente
    // Retorna true si la actualización fue exitosa, false si no existe
    public boolean actualizarProducto(String id, String nombre, String categoria, 
                                     int cantidad, int cantidadMinima, double precio) {
        Producto producto = productos.get(id);
        if (producto == null) {
            return false;
        }
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setCantidad(cantidad);
        producto.setCantidadMinima(cantidadMinima);
        producto.setPrecio(precio);
        return true;
    }
    
    // Elimina un producto del inventario
    // Retorna true si se eliminó, false si no existe
    public boolean eliminarProducto(String id) {
        if (!productos.containsKey(id)) {
            return false;
        }
        productos.remove(id);
        return true;
    }
    
    // Obtiene la lista de productos con stock bajo
    // Retorna los productos cuya cantidad es menor o igual a la cantidad mínima
    public List<Producto> obtenerProductosConStockBajo() {
        return productos.values().stream()
                .filter(Producto::tieneStockBajo)
                .collect(Collectors.toList());
    }
    
    // Verifica si hay productos con stock bajo
    // Retorna true si hay al menos un producto con stock bajo
    public boolean hayAlertasInventario() {
        return productos.values().stream()
                .anyMatch(Producto::tieneStockBajo);
    }
}
