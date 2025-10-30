package main;

import controller.ClinicaController;
import javax.swing.*;
import utils.PersistenciaArchivos;
import view.VentanaPrincipal;

// Clase principal del sistema
// Punto de entrada de la aplicación (método main)
public class Main {
    public static void main(String[] args) {
        // Obtener la instancia única del controlador (patrón Singleton)
        ClinicaController controller = ClinicaController.getInstance();
        
        // Inicializar el sistema de persistencia para cargar/guardar datos
        PersistenciaArchivos persistencia = new PersistenciaArchivos(controller);
        
        // Cargar los datos guardados previamente desde los archivos
        persistencia.cargarTodo();
        
        // Si no hay datos cargados, cargar datos de ejemplo para demostración
        if (controller.listarDuenos().isEmpty()) {
            cargarDatosEjemplo(controller);
        }
        
        // Iniciar la interfaz gráfica en el hilo de eventos de Swing
        // SwingUtilities.invokeLater asegura que la GUI se cree en el hilo correcto
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        
        // Agregar un hook (gancho) para guardar datos cuando se cierre la aplicación
        // Se ejecuta automáticamente al terminar el programa
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            persistencia.guardarTodo();
        }));
    }
    
    // Carga datos de ejemplo para demostración del sistema
    // Se ejecuta solo si no hay datos previos guardados
    private static void cargarDatosEjemplo(ClinicaController controller) {
        // ===== REGISTRAR DUEÑOS =====
        controller.registrarDueno("D001", "Juan Pérez", "Calle Principal 123", "555-0001", "juan@email.com");
        controller.registrarDueno("D002", "María García", "Av. Central 456", "555-0002", "maria@email.com");
        controller.registrarDueno("D003", "Carlos López", "Calle Sur 789", "555-0003", "carlos@email.com");
        
        // ===== REGISTRAR MASCOTAS =====
        controller.registrarMascota("M001", "Max", "Perro", "Labrador", 3, 30.5, "D001");
        controller.registrarMascota("M002", "Luna", "Gato", "Siamés", 2, 4.2, "D001");
        controller.registrarMascota("M003", "Rocky", "Perro", "Pastor Alemán", 5, 35.0, "D002");
        controller.registrarMascota("M004", "Michi", "Gato", "Persa", 1, 3.8, "D003");
        
        // ===== AGREGAR HISTORIAL CLÍNICO =====
        controller.agregarHistorialClinico("M001", "2024-10-15 - Vacuna antirrábica aplicada");
        controller.agregarHistorialClinico("M001", "2024-11-01 - Consulta general - Estado saludable");
        controller.agregarHistorialClinico("M002", "2024-10-20 - Desparasitación");
        
        // ===== REGISTRAR EMPLEADOS =====
        // Veterinarios
        controller.registrarVeterinario("V001", "Dr. Ana Martínez", "ana@clinica.com", "555-1001", "Lun-Vie 8:00-16:00", "Cirugía");
        controller.registrarVeterinario("V002", "Dr. Pedro Sánchez", "pedro@clinica.com", "555-1002", "Lun-Vie 14:00-22:00", "Medicina General");
        // Asistentes
        controller.registrarAsistente("A001", "Laura Torres", "laura@clinica.com", "555-2001", "Lun-Vie 8:00-16:00", "Recepción");
        controller.registrarAsistente("A002", "Miguel Ríos", "miguel@clinica.com", "555-2002", "Lun-Vie 14:00-22:00", "Asistencia Quirúrgica");
        
        // ===== AGENDAR CITAS =====
        // Crear fechas para las citas de ejemplo
        java.time.LocalDateTime fecha1 = java.time.LocalDateTime.of(2024, 11, 15, 10, 0);
        java.time.LocalDateTime fecha2 = java.time.LocalDateTime.of(2024, 11, 16, 15, 30);
        java.time.LocalDateTime fecha3 = java.time.LocalDateTime.of(2024, 11, 17, 11, 0);
        
        // Agendar las citas de ejemplo
        controller.agendarCita("C001", fecha1, "Vacunación anual", "M001", "D001", "V001");
        controller.agendarCita("C002", fecha2, "Consulta de rutina", "M003", "D002", "V002");
        controller.agendarCita("C003", fecha3, "Control post-operatorio", "M002", "D001", "V001");
    }
}
