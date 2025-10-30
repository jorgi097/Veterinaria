package main;

import controller.ClinicaController;
import javax.swing.*;
import utils.PersistenciaArchivos;
import view.VentanaPrincipal;

/**
 * Clase principal del sistema
 * Punto de entrada de la aplicación
 */
public class Main {
    public static void main(String[] args) {
        // Obtener instancia del controlador
        ClinicaController controller = ClinicaController.getInstance();
        
        // Inicializar persistencia
        PersistenciaArchivos persistencia = new PersistenciaArchivos(controller);
        
        // Cargar datos guardados
        persistencia.cargarTodo();
        
        // Cargar datos de ejemplo si no hay datos
        if (controller.listarDuenos().isEmpty()) {
            cargarDatosEjemplo(controller);
        }
        
        // Iniciar la interfaz gráfica
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        
        // Agregar hook para guardar al cerrar
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            persistencia.guardarTodo();
        }));
    }
    
    /**
     * Carga datos de ejemplo para demostración
     */
    private static void cargarDatosEjemplo(ClinicaController controller) {
        // Registrar dueños
        controller.registrarDueno("D001", "Juan Pérez", "Calle Principal 123", "555-0001", "juan@email.com");
        controller.registrarDueno("D002", "María García", "Av. Central 456", "555-0002", "maria@email.com");
        controller.registrarDueno("D003", "Carlos López", "Calle Sur 789", "555-0003", "carlos@email.com");
        
        // Registrar mascotas
        controller.registrarMascota("M001", "Max", "Perro", "Labrador", 3, 30.5, "D001");
        controller.registrarMascota("M002", "Luna", "Gato", "Siamés", 2, 4.2, "D001");
        controller.registrarMascota("M003", "Rocky", "Perro", "Pastor Alemán", 5, 35.0, "D002");
        controller.registrarMascota("M004", "Michi", "Gato", "Persa", 1, 3.8, "D003");
        
        // Agregar historial clínico
        controller.agregarHistorialClinico("M001", "2024-10-15 - Vacuna antirrábica aplicada");
        controller.agregarHistorialClinico("M001", "2024-11-01 - Consulta general - Estado saludable");
        controller.agregarHistorialClinico("M002", "2024-10-20 - Desparasitación");
        
        // Registrar empleados
        controller.registrarVeterinario("V001", "Dr. Ana Martínez", "ana@clinica.com", "555-1001", "Lun-Vie 8:00-16:00", "Cirugía");
        controller.registrarVeterinario("V002", "Dr. Pedro Sánchez", "pedro@clinica.com", "555-1002", "Lun-Vie 14:00-22:00", "Medicina General");
        controller.registrarAsistente("A001", "Laura Torres", "laura@clinica.com", "555-2001", "Lun-Vie 8:00-16:00", "Recepción");
        controller.registrarAsistente("A002", "Miguel Ríos", "miguel@clinica.com", "555-2002", "Lun-Vie 14:00-22:00", "Asistencia Quirúrgica");
        
        // Agendar citas
        java.time.LocalDateTime fecha1 = java.time.LocalDateTime.of(2024, 11, 15, 10, 0);
        java.time.LocalDateTime fecha2 = java.time.LocalDateTime.of(2024, 11, 16, 15, 30);
        java.time.LocalDateTime fecha3 = java.time.LocalDateTime.of(2024, 11, 17, 11, 0);
        
        controller.agendarCita("C001", fecha1, "Vacunación anual", "M001", "D001", "V001");
        controller.agendarCita("C002", fecha2, "Consulta de rutina", "M003", "D002", "V002");
        controller.agendarCita("C003", fecha3, "Control post-operatorio", "M002", "D001", "V001");
    }
}
