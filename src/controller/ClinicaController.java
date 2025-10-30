package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.*;

/**
 * Controlador principal que gestiona todas las operaciones de la clínica
 * Implementa el patrón Singleton para garantizar una única instancia
 */
public class ClinicaController {
    private static ClinicaController instance;
    
    // Colecciones para almacenar datos
    private Map<String, Mascota> mascotas;
    private Map<String, Dueno> duenos;
    private Map<String, Empleado> empleados;
    private Map<String, Cita> citas;
    private Map<String, Servicio> servicios;
    
    /**
     * Constructor privado para implementar Singleton
     */
    private ClinicaController() {
        mascotas = new HashMap<>();
        duenos = new HashMap<>();
        empleados = new HashMap<>();
        citas = new HashMap<>();
        servicios = new HashMap<>();
        inicializarServicios();
    }
    
    /**
     * Obtiene la instancia única del controlador
     */
    public static ClinicaController getInstance() {
        if (instance == null) {
            instance = new ClinicaController();
        }
        return instance;
    }
    
    /**
     * Inicializa los servicios disponibles en la clínica
     */
    private void inicializarServicios() {
        servicios.put("S001", new Servicio("S001", "Consulta General", "Consulta veterinaria general", 300.0));
        servicios.put("S002", new Servicio("S002", "Vacunación", "Aplicación de vacunas", 250.0));
        servicios.put("S003", new Servicio("S003", "Baño y Estética", "Servicio de baño y corte", 200.0));
        servicios.put("S004", new Servicio("S004", "Cirugía Menor", "Procedimientos quirúrgicos menores", 1500.0));
        servicios.put("S005", new Servicio("S005", "Desparasitación", "Tratamiento antiparasitario", 150.0));
        servicios.put("S006", new Servicio("S006", "Análisis Clínicos", "Estudios de laboratorio", 500.0));
    }
    
    // ============ GESTIÓN DE DUEÑOS ============
    
    /**
     * Registra un nuevo dueño
     */
    public boolean registrarDueno(String id, String nombre, String direccion, String telefono, String correo) {
        if (duenos.containsKey(id)) {
            return false;
        }
        Dueno dueno = new Dueno(id, nombre, direccion, telefono, correo);
        duenos.put(id, dueno);
        return true;
    }
    
    /**
     * Obtiene un dueño por ID
     */
    public Dueno obtenerDueno(String id) {
        return duenos.get(id);
    }
    
    /**
     * Lista todos los dueños
     */
    public List<Dueno> listarDuenos() {
        return new ArrayList<>(duenos.values());
    }
    
    /**
     * Actualiza información de un dueño
     */
    public boolean actualizarDueno(String id, String nombre, String direccion, String telefono, String correo) {
        Dueno dueno = duenos.get(id);
        if (dueno == null) {
            return false;
        }
        dueno.setNombre(nombre);
        dueno.setDireccion(direccion);
        dueno.setTelefono(telefono);
        dueno.setCorreo(correo);
        return true;
    }
    
    /**
     * Elimina un dueño
     */
    public boolean eliminarDueno(String id) {
        Dueno dueno = duenos.get(id);
        if (dueno == null || !dueno.getMascotas().isEmpty()) {
            return false; // No se puede eliminar si tiene mascotas
        }
        duenos.remove(id);
        return true;
    }
    
    // ============ GESTIÓN DE MASCOTAS ============
    
    /**
     * Registra una nueva mascota
     */
    public boolean registrarMascota(String id, String nombre, String especie, String raza, 
                                   int edad, double peso, String idDueno) {
        if (mascotas.containsKey(id)) {
            return false;
        }
        Dueno dueno = duenos.get(idDueno);
        if (dueno == null) {
            return false;
        }
        Mascota mascota = new Mascota(id, nombre, especie, raza, edad, peso, dueno);
        mascotas.put(id, mascota);
        dueno.agregarMascota(mascota);
        return true;
    }
    
    /**
     * Obtiene una mascota por ID
     */
    public Mascota obtenerMascota(String id) {
        return mascotas.get(id);
    }
    
    /**
     * Lista todas las mascotas
     */
    public List<Mascota> listarMascotas() {
        return new ArrayList<>(mascotas.values());
    }
    
    /**
     * Actualiza información de una mascota
     */
    public boolean actualizarMascota(String id, String nombre, String especie, String raza, 
                                    int edad, double peso) {
        Mascota mascota = mascotas.get(id);
        if (mascota == null) {
            return false;
        }
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setEdad(edad);
        mascota.setPeso(peso);
        return true;
    }
    
    /**
     * Elimina una mascota
     */
    public boolean eliminarMascota(String id) {
        Mascota mascota = mascotas.get(id);
        if (mascota == null) {
            return false;
        }
        Dueno dueno = mascota.getDueno();
        if (dueno != null) {
            dueno.eliminarMascota(mascota);
        }
        mascotas.remove(id);
        return true;
    }
    
    /**
     * Agrega un registro al historial clínico de una mascota
     */
    public boolean agregarHistorialClinico(String idMascota, String registro) {
        Mascota mascota = mascotas.get(idMascota);
        if (mascota == null) {
            return false;
        }
        mascota.agregarHistorialClinico(registro);
        return true;
    }
    
    // ============ GESTIÓN DE EMPLEADOS ============
    
    /**
     * Registra un nuevo veterinario
     */
    public boolean registrarVeterinario(String id, String nombre, String correo, String telefono, 
                                       String horario, String especialidad) {
        if (empleados.containsKey(id)) {
            return false;
        }
        Veterinario veterinario = new Veterinario(id, nombre, correo, telefono, horario, especialidad);
        empleados.put(id, veterinario);
        return true;
    }
    
    /**
     * Registra un nuevo asistente
     */
    public boolean registrarAsistente(String id, String nombre, String correo, String telefono, 
                                     String horario, String area) {
        if (empleados.containsKey(id)) {
            return false;
        }
        Asistente asistente = new Asistente(id, nombre, correo, telefono, horario, area);
        empleados.put(id, asistente);
        return true;
    }
    
    /**
     * Obtiene un empleado por ID
     */
    public Empleado obtenerEmpleado(String id) {
        return empleados.get(id);
    }
    
    /**
     * Lista todos los empleados
     */
    public List<Empleado> listarEmpleados() {
        return new ArrayList<>(empleados.values());
    }
    
    /**
     * Lista solo los veterinarios
     */
    public List<Veterinario> listarVeterinarios() {
        return empleados.values().stream()
                .filter(e -> e instanceof Veterinario)
                .map(e -> (Veterinario) e)
                .collect(Collectors.toList());
    }
    
    /**
     * Elimina un empleado
     */
    public boolean eliminarEmpleado(String id) {
        if (!empleados.containsKey(id)) {
            return false;
        }
        empleados.remove(id);
        return true;
    }
    
    // ============ GESTIÓN DE CITAS ============
    
    /**
     * Agenda una nueva cita
     * Valida que no haya conflictos de horario
     */
    public boolean agendarCita(String id, LocalDateTime fechaHora, String motivo, 
                              String idMascota, String idDueno, String idVeterinario) {
        if (citas.containsKey(id)) {
            return false;
        }
        
        Mascota mascota = mascotas.get(idMascota);
        Dueno dueno = duenos.get(idDueno);
        Empleado emp = empleados.get(idVeterinario);
        
        if (mascota == null || dueno == null || !(emp instanceof Veterinario)) {
            return false;
        }
        
        Veterinario veterinario = (Veterinario) emp;
        
        // Validar que no haya cita duplicada en el mismo horario
        if (validarDisponibilidadVeterinario(veterinario, fechaHora)) {
            Cita cita = new Cita(id, fechaHora, motivo, mascota, dueno, veterinario);
            citas.put(id, cita);
            veterinario.agregarCita(cita);
            return true;
        }
        return false;
    }
    
    /**
     * Valida que el veterinario esté disponible en el horario
     */
    private boolean validarDisponibilidadVeterinario(Veterinario veterinario, LocalDateTime fechaHora) {
        for (Cita cita : veterinario.getCitasAsignadas()) {
            if (cita.getFechaHora().equals(fechaHora) && cita.getEstado().equals("Programada")) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Obtiene una cita por ID
     */
    public Cita obtenerCita(String id) {
        return citas.get(id);
    }
    
    /**
     * Lista todas las citas
     */
    public List<Cita> listarCitas() {
        return new ArrayList<>(citas.values());
    }
    
    /**
     * Cancela una cita
     */
    public boolean cancelarCita(String id) {
        Cita cita = citas.get(id);
        if (cita == null) {
            return false;
        }
        cita.setEstado("Cancelada");
        return true;
    }
    
    /**
     * Completa una cita
     */
    public boolean completarCita(String id) {
        Cita cita = citas.get(id);
        if (cita == null) {
            return false;
        }
        cita.setEstado("Completada");
        return true;
    }
    
    // ============ GESTIÓN DE SERVICIOS ============
    
    /**
     * Obtiene un servicio por ID
     */
    public Servicio obtenerServicio(String id) {
        return servicios.get(id);
    }
    
    /**
     * Lista todos los servicios disponibles
     */
    public List<Servicio> listarServicios() {
        return new ArrayList<>(servicios.values());
    }
    
    /**
     * Agrega un nuevo servicio
     */
    public boolean agregarServicio(String id, String nombre, String descripcion, double precio) {
        if (servicios.containsKey(id)) {
            return false;
        }
        servicios.put(id, new Servicio(id, nombre, descripcion, precio));
        return true;
    }
    
    // ============ MÉTODOS DE UTILIDAD ============
    
    /**
     * Genera un reporte de todas las citas de un veterinario
     */
    public List<Cita> obtenerCitasPorVeterinario(String idVeterinario) {
        return citas.values().stream()
                .filter(c -> c.getVeterinario().getId().equals(idVeterinario))
                .collect(Collectors.toList());
    }
    
    /**
     * Genera un reporte de todas las mascotas de un dueño
     */
    public List<Mascota> obtenerMascotasPorDueno(String idDueno) {
        Dueno dueno = duenos.get(idDueno);
        return dueno != null ? dueno.getMascotas() : new ArrayList<>();
    }
    
    /**
     * Demuestra polimorfismo con los empleados
     */
    public void mostrarTareasEmpleados() {
        for (Empleado empleado : empleados.values()) {
            System.out.println(empleado.realizarTarea());
        }
    }
}
