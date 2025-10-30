package utils;

import controller.ClinicaController;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.*;

/**
 * Clase para persistir datos en archivos de texto
 */
public class PersistenciaArchivos {
    private static final String ARCHIVO_DUENOS = "datos/duenos.txt";
    private static final String ARCHIVO_MASCOTAS = "datos/mascotas.txt";
    private static final String ARCHIVO_EMPLEADOS = "datos/empleados.txt";
    private static final String ARCHIVO_CITAS = "datos/citas.txt";
    
    private ClinicaController controller;
    
    public PersistenciaArchivos(ClinicaController controller) {
        this.controller = controller;
        crearDirectorioDatos();
    }
    
    /**
     * Crea el directorio de datos si no existe
     */
    private void crearDirectorioDatos() {
        File directorio = new File("datos");
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }
    
    // ============ GUARDAR DATOS ============
    
    /**
     * Guarda todos los datos en archivos
     */
    public void guardarTodo() {
        guardarDuenos();
        guardarMascotas();
        guardarEmpleados();
        guardarCitas();
        System.out.println("✅ Datos guardados exitosamente");
    }
    
    private void guardarDuenos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_DUENOS))) {
            List<Dueno> duenos = controller.listarDuenos();
            for (Dueno dueno : duenos) {
                writer.println(dueno.getId() + "|" + dueno.getNombre() + "|" + 
                              dueno.getDireccion() + "|" + dueno.getTelefono() + "|" + 
                              dueno.getCorreo());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar dueños: " + e.getMessage());
        }
    }
    
    private void guardarMascotas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_MASCOTAS))) {
            List<Mascota> mascotas = controller.listarMascotas();
            for (Mascota mascota : mascotas) {
                writer.println(mascota.getId() + "|" + mascota.getNombre() + "|" + 
                              mascota.getEspecie() + "|" + mascota.getRaza() + "|" + 
                              mascota.getEdad() + "|" + mascota.getPeso() + "|" + 
                              mascota.getDueno().getId());
                
                // Guardar historial clínico
                for (String registro : mascota.getHistorialClinico()) {
                    writer.println("HISTORIAL:" + mascota.getId() + ":" + registro);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar mascotas: " + e.getMessage());
        }
    }
    
    private void guardarEmpleados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
            List<Empleado> empleados = controller.listarEmpleados();
            for (Empleado empleado : empleados) {
                if (empleado instanceof Veterinario) {
                    Veterinario vet = (Veterinario) empleado;
                    writer.println("VET|" + vet.getId() + "|" + vet.getNombre() + "|" + 
                                  vet.getCorreo() + "|" + vet.getTelefono() + "|" + 
                                  vet.getHorario() + "|" + vet.getEspecialidad());
                } else if (empleado instanceof Asistente) {
                    Asistente asist = (Asistente) empleado;
                    writer.println("ASI|" + asist.getId() + "|" + asist.getNombre() + "|" + 
                                  asist.getCorreo() + "|" + asist.getTelefono() + "|" + 
                                  asist.getHorario() + "|" + asist.getArea());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar empleados: " + e.getMessage());
        }
    }
    
    private void guardarCitas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_CITAS))) {
            List<Cita> citas = controller.listarCitas();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            for (Cita cita : citas) {
                writer.println(cita.getId() + "|" + cita.getFechaHora().format(formatter) + "|" + 
                              cita.getMotivo() + "|" + cita.getMascota().getId() + "|" + 
                              cita.getDueno().getId() + "|" + cita.getVeterinario().getId() + "|" + 
                              cita.getEstado());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar citas: " + e.getMessage());
        }
    }
    
    // ============ CARGAR DATOS ============
    
    /**
     * Carga todos los datos desde archivos
     */
    public void cargarTodo() {
        cargarDuenos();
        cargarMascotas();
        cargarEmpleados();
        cargarCitas();
        System.out.println("✅ Datos cargados exitosamente");
    }
    
    private void cargarDuenos() {
        File archivo = new File(ARCHIVO_DUENOS);
        if (!archivo.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 5) {
                    controller.registrarDueno(datos[0], datos[1], datos[2], datos[3], datos[4]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar dueños: " + e.getMessage());
        }
    }
    
    private void cargarMascotas() {
        File archivo = new File(ARCHIVO_MASCOTAS);
        if (!archivo.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("HISTORIAL:")) {
                    String[] partes = linea.substring(10).split(":", 2);
                    if (partes.length == 2) {
                        controller.agregarHistorialClinico(partes[0], partes[1]);
                    }
                } else {
                    String[] datos = linea.split("\\|");
                    if (datos.length >= 7) {
                        controller.registrarMascota(datos[0], datos[1], datos[2], datos[3],
                                                   Integer.parseInt(datos[4]), 
                                                   Double.parseDouble(datos[5]), 
                                                   datos[6]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar mascotas: " + e.getMessage());
        }
    }
    
    private void cargarEmpleados() {
        File archivo = new File(ARCHIVO_EMPLEADOS);
        if (!archivo.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 7) {
                    if (datos[0].equals("VET")) {
                        controller.registrarVeterinario(datos[1], datos[2], datos[3], 
                                                       datos[4], datos[5], datos[6]);
                    } else if (datos[0].equals("ASI")) {
                        controller.registrarAsistente(datos[1], datos[2], datos[3], 
                                                     datos[4], datos[5], datos[6]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar empleados: " + e.getMessage());
        }
    }
    
    private void cargarCitas() {
        File archivo = new File(ARCHIVO_CITAS);
        if (!archivo.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 7) {
                    LocalDateTime fechaHora = LocalDateTime.parse(datos[1], formatter);
                    controller.agendarCita(datos[0], fechaHora, datos[2], 
                                          datos[3], datos[4], datos[5]);
                    
                    // Restaurar estado
                    Cita cita = controller.obtenerCita(datos[0]);
                    if (cita != null) {
                        cita.setEstado(datos[6]);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar citas: " + e.getMessage());
        }
    }
}
