package main;

import controller.ClinicaController;
import java.time.LocalDateTime;
import java.util.List;
import model.*;

/**
 * Clase para ejecutar casos de prueba del sistema
 * Demuestra el correcto funcionamiento de todas las funcionalidades
 */
public class CasosDePrueba {
    
    private static ClinicaController controller;
    private static int testsPasados = 0;
    private static int testsTotal = 0;
    
    public static void main(String[] args) {
        controller = ClinicaController.getInstance();
        
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║         CASOS DE PRUEBA DEL SISTEMA               ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
        
        // Ejecutar casos de prueba
        caso1_RegistroExitosoMascotaConDueno();
        caso2_ValidacionCitaDuplicada();
        caso3_RelacionUnoAMuchos();
        caso4_PolimorfismoEmpleados();
        caso5_GestionHistorialClinico();
        
        // Reporte final
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              RESUMEN DE PRUEBAS                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("Tests ejecutados: " + testsTotal);
        System.out.println("Tests pasados: " + testsPasados);
        System.out.println("Tests fallados: " + (testsTotal - testsPasados));
        
        if (testsPasados == testsTotal) {
            System.out.println("\n✅ TODOS LOS TESTS PASARON EXITOSAMENTE");
        } else {
            System.out.println("\n❌ ALGUNOS TESTS FALLARON");
        }
    }
    
    /**
     * CASO 1: Registro exitoso de mascota con dueño existente
     * Objetivo: Validar que se puede registrar una mascota con un dueño válido
     */
    private static void caso1_RegistroExitosoMascotaConDueno() {
        System.out.println("\n━━━ CASO 1: Registro exitoso de mascota con dueño ━━━");
        testsTotal++;
        
        try {
            // Registrar dueño
            boolean duenoRegistrado = controller.registrarDueno(
                "D001", "Ana López", "Calle 123", "555-0001", "ana@email.com"
            );
            
            // Registrar mascota (incluyendo alergias como dato crítico)
            boolean mascotaRegistrada = controller.registrarMascota(
                "M001", "Firulais", "Perro", "Golden Retriever", 3, 28.5, "Ninguna", "D001"
            );
            
            // Verificar que la mascota se registró correctamente
            Mascota mascota = controller.obtenerMascota("M001");
            boolean relacionCorrecta = mascota != null && 
                                      mascota.getDueno().getId().equals("D001");
            
            if (duenoRegistrado && mascotaRegistrada && relacionCorrecta) {
                System.out.println("✅ PASÓ - Mascota registrada correctamente con su dueño");
                System.out.println("   Dueño: " + mascota.getDueno().getNombre());
                System.out.println("   Mascota: " + mascota.getNombre() + " (" + mascota.getEspecie() + ")");
                testsPasados++;
            } else {
                System.out.println("❌ FALLÓ - Error en el registro");
            }
        } catch (Exception e) {
            System.out.println("❌ FALLÓ - Excepción: " + e.getMessage());
        }
    }
    
    /**
     * CASO 2: Validación de citas duplicadas en el mismo horario
     * Objetivo: Verificar que no se permitan citas duplicadas para un veterinario
     */
    private static void caso2_ValidacionCitaDuplicada() {
        System.out.println("\n━━━ CASO 2: Validación de citas duplicadas ━━━");
        testsTotal++;
        
        try {
            // Registrar veterinario
            controller.registrarVeterinario(
                "V001", "Dr. Carlos Ruiz", "carlos@vet.com", "555-1001", 
                "Lun-Vie 8-16", "Cirugía"
            );
            
            LocalDateTime fechaHora = LocalDateTime.of(2024, 12, 1, 10, 0);
            
            // Primera cita - debe registrarse exitosamente
            boolean cita1 = controller.agendarCita(
                "C001", fechaHora, "Consulta general", "M001", "D001", "V001"
            );
            
            // Segunda cita en el mismo horario - debe fallar
            boolean cita2 = controller.agendarCita(
                "C002", fechaHora, "Otra consulta", "M001", "D001", "V001"
            );
            
            if (cita1 && !cita2) {
                System.out.println("✅ PASÓ - Se previno correctamente la cita duplicada");
                System.out.println("   Primera cita: REGISTRADA");
                System.out.println("   Segunda cita (mismo horario): RECHAZADA");
                testsPasados++;
            } else {
                System.out.println("❌ FALLÓ - No se validó correctamente");
                System.out.println("   Cita 1: " + cita1 + ", Cita 2: " + cita2);
            }
        } catch (Exception e) {
            System.out.println("❌ FALLÓ - Excepción: " + e.getMessage());
        }
    }
    
    /**
     * CASO 3: Relación uno-a-muchos entre Dueño y Mascotas
     * Objetivo: Validar que un dueño puede tener múltiples mascotas
     */
    private static void caso3_RelacionUnoAMuchos() {
        System.out.println("\n━━━ CASO 3: Relación uno-a-muchos (Dueño-Mascotas) ━━━");
        testsTotal++;
        
        try {
            // Registrar un dueño
            controller.registrarDueno(
                "D002", "Pedro Gómez", "Av. Principal 456", "555-0002", "pedro@email.com"
            );
            
            // Registrar múltiples mascotas para el mismo dueño (con alergias)
            controller.registrarMascota("M002", "Luna", "Gato", "Persa", 2, 4.5, "Alergia a penicilina", "D002");
            controller.registrarMascota("M003", "Max", "Perro", "Beagle", 4, 12.0, "Ninguna", "D002");
            controller.registrarMascota("M004", "Coco", "Loro", "Guacamayo", 1, 0.8, "Ninguna", "D002");
            
            // Obtener dueño y verificar sus mascotas
            Dueno dueno = controller.obtenerDueno("D002");
            List<Mascota> mascotas = dueno.getMascotas();
            
            if (mascotas.size() == 3) {
                System.out.println("✅ PASÓ - Relación uno-a-muchos funciona correctamente");
                System.out.println("   Dueño: " + dueno.getNombre());
                System.out.println("   Número de mascotas: " + mascotas.size());
                for (Mascota m : mascotas) {
                    System.out.println("     - " + m.getNombre() + " (" + m.getEspecie() + ")");
                }
                testsPasados++;
            } else {
                System.out.println("❌ FALLÓ - Número incorrecto de mascotas: " + mascotas.size());
            }
        } catch (Exception e) {
            System.out.println("❌ FALLÓ - Excepción: " + e.getMessage());
        }
    }
    
    /**
     * CASO 4: Demostración de polimorfismo con empleados
     * Objetivo: Verificar que el método realizarTarea() funciona polimórficamente
     */
    private static void caso4_PolimorfismoEmpleados() {
        System.out.println("\n━━━ CASO 4: Polimorfismo en jerarquía de empleados ━━━");
        testsTotal++;
        
        try {
            // Registrar diferentes tipos de empleados
            controller.registrarVeterinario(
                "V002", "Dra. Laura Sánchez", "laura@vet.com", "555-1002",
                "Mar-Sab 9-17", "Medicina General"
            );
            
            controller.registrarAsistente(
                "A001", "José Martínez", "jose@vet.com", "555-2001",
                "Lun-Vie 8-16", "Recepción"
            );
            
            // Obtener empleados y demostrar polimorfismo
            Empleado vet = controller.obtenerEmpleado("V002");
            Empleado asist = controller.obtenerEmpleado("A001");
            
            String tareaVet = vet.realizarTarea();
            String tareaAsist = asist.realizarTarea();
            
            // Verificar que cada uno ejecuta su propia implementación
            boolean polimorfismoFunciona = 
                tareaVet.contains("Veterinario") && tareaVet.contains("consultas") &&
                tareaAsist.contains("Asistente") && tareaAsist.contains("apoyando");
            
            if (polimorfismoFunciona) {
                System.out.println("✅ PASÓ - Polimorfismo funciona correctamente");
                System.out.println("   Veterinario: " + tareaVet);
                System.out.println("   Asistente: " + tareaAsist);
                
                // Demostrar sobrecarga de métodos
                if (vet instanceof Veterinario) {
                    Veterinario veterinario = (Veterinario) vet;
                    Mascota mascota = controller.obtenerMascota("M001");
                    if (mascota != null) {
                        System.out.println("\n   Sobrecarga de métodos:");
                        System.out.println("   " + veterinario.diagnosticar(mascota));
                        System.out.println("   " + veterinario.diagnosticar(mascota, "fiebre alta"));
                    }
                }
                testsPasados++;
            } else {
                System.out.println("❌ FALLÓ - Polimorfismo no funciona correctamente");
            }
        } catch (Exception e) {
            System.out.println("❌ FALLÓ - Excepción: " + e.getMessage());
        }
    }
    
    /**
     * CASO 5: Gestión de historial clínico
     * Objetivo: Validar que se puede agregar y consultar el historial de una mascota
     */
    private static void caso5_GestionHistorialClinico() {
        System.out.println("\n━━━ CASO 5: Gestión de historial clínico ━━━");
        testsTotal++;
        
        try {
            // Agregar registros al historial
            controller.agregarHistorialClinico("M001", "2024-10-15 - Vacuna antirrábica");
            controller.agregarHistorialClinico("M001", "2024-11-01 - Consulta general - Saludable");
            controller.agregarHistorialClinico("M001", "2024-11-15 - Desparasitación");
            
            // Obtener mascota y verificar historial
            Mascota mascota = controller.obtenerMascota("M001");
            List<String> historial = mascota.getHistorialClinico();
            
            if (historial.size() >= 3) {
                System.out.println("✅ PASÓ - Historial clínico gestionado correctamente");
                System.out.println("   Mascota: " + mascota.getNombre());
                System.out.println("   Registros en historial: " + historial.size());
                System.out.println("   Últimos registros:");
                for (int i = Math.max(0, historial.size() - 3); i < historial.size(); i++) {
                    System.out.println("     " + historial.get(i));
                }
                testsPasados++;
            } else {
                System.out.println("❌ FALLÓ - Historial incompleto: " + historial.size() + " registros");
            }
        } catch (Exception e) {
            System.out.println("❌ FALLÓ - Excepción: " + e.getMessage());
        }
    }
}
