package view;

import controller.ClinicaController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import model.*;

/**
 * Clase que maneja la interfaz de usuario por consola
 */
public class ClinicaView {
    private ClinicaController controller;
    private Scanner scanner;
    
    public ClinicaView() {
        this.controller = ClinicaController.getInstance();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Muestra el menú principal
     */
    public void mostrarMenuPrincipal() {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║   SISTEMA DE GESTIÓN - CLÍNICA VETERINARIA        ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("1.  Gestión de Dueños");
            System.out.println("2.  Gestión de Mascotas");
            System.out.println("3.  Gestión de Empleados");
            System.out.println("4.  Gestión de Citas");
            System.out.println("5.  Servicios Disponibles");
            System.out.println("6.  Reportes");
            System.out.println("7.  Demostración de Polimorfismo");
            System.out.println("0.  Salir");
            System.out.print("\nSeleccione una opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    menuDuenos();
                    break;
                case 2:
                    menuMascotas();
                    break;
                case 3:
                    menuEmpleados();
                    break;
                case 4:
                    menuCitas();
                    break;
                case 5:
                    mostrarServicios();
                    break;
                case 6:
                    menuReportes();
                    break;
                case 7:
                    demostrarPolimorfismo();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\n¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("\n❌ Opción inválida");
            }
        }
        scanner.close();
    }
    
    // ============ MENÚ DE DUEÑOS ============
    
    private void menuDuenos() {
        System.out.println("\n--- GESTIÓN DE DUEÑOS ---");
        System.out.println("1. Registrar dueño");
        System.out.println("2. Listar dueños");
        System.out.println("3. Buscar dueño");
        System.out.println("4. Actualizar dueño");
        System.out.println("5. Eliminar dueño");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                registrarDueno();
                break;
            case 2:
                listarDuenos();
                break;
            case 3:
                buscarDueno();
                break;
            case 4:
                actualizarDueno();
                break;
            case 5:
                eliminarDueno();
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }
    
    private void registrarDueno() {
        System.out.println("\n--- REGISTRAR DUEÑO ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        if (controller.registrarDueno(id, nombre, direccion, telefono, correo)) {
            System.out.println("✅ Dueño registrado exitosamente");
        } else {
            System.out.println("❌ Error: El ID ya existe");
        }
    }
    
    private void listarDuenos() {
        System.out.println("\n--- LISTA DE DUEÑOS ---");
        List<Dueno> duenos = controller.listarDuenos();
        if (duenos.isEmpty()) {
            System.out.println("No hay dueños registrados");
        } else {
            for (Dueno dueno : duenos) {
                System.out.println(dueno);
            }
        }
    }
    
    private void buscarDueno() {
        System.out.print("\nIngrese ID del dueño: ");
        String id = scanner.nextLine();
        Dueno dueno = controller.obtenerDueno(id);
        if (dueno != null) {
            System.out.println(dueno);
            System.out.println("Mascotas: ");
            for (Mascota m : dueno.getMascotas()) {
                System.out.println("  - " + m.getNombre() + " (" + m.getEspecie() + ")");
            }
        } else {
            System.out.println("❌ Dueño no encontrado");
        }
    }
    
    private void actualizarDueno() {
        System.out.print("\nIngrese ID del dueño a actualizar: ");
        String id = scanner.nextLine();
        if (controller.obtenerDueno(id) == null) {
            System.out.println("❌ Dueño no encontrado");
            return;
        }
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Nuevo teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Nuevo correo: ");
        String correo = scanner.nextLine();
        
        if (controller.actualizarDueno(id, nombre, direccion, telefono, correo)) {
            System.out.println("✅ Dueño actualizado exitosamente");
        } else {
            System.out.println("❌ Error al actualizar");
        }
    }
    
    private void eliminarDueno() {
        System.out.print("\nIngrese ID del dueño a eliminar: ");
        String id = scanner.nextLine();
        if (controller.eliminarDueno(id)) {
            System.out.println("✅ Dueño eliminado exitosamente");
        } else {
            System.out.println("❌ Error: No se puede eliminar (puede tener mascotas asociadas)");
        }
    }
    
    // ============ MENÚ DE MASCOTAS ============
    
    private void menuMascotas() {
        System.out.println("\n--- GESTIÓN DE MASCOTAS ---");
        System.out.println("1. Registrar mascota");
        System.out.println("2. Listar mascotas");
        System.out.println("3. Buscar mascota");
        System.out.println("4. Actualizar mascota");
        System.out.println("5. Eliminar mascota");
        System.out.println("6. Agregar al historial clínico");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                registrarMascota();
                break;
            case 2:
                listarMascotas();
                break;
            case 3:
                buscarMascota();
                break;
            case 4:
                actualizarMascota();
                break;
            case 5:
                eliminarMascota();
                break;
            case 6:
                agregarHistorial();
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }
    
    private void registrarMascota() {
        System.out.println("\n--- REGISTRAR MASCOTA ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Especie: ");
        String especie = scanner.nextLine();
        System.out.print("Raza: ");
        String raza = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = leerEntero();
        System.out.print("Peso (kg): ");
        double peso = leerDecimal();
        System.out.print("ID del dueño: ");
        String idDueno = scanner.nextLine();
        
        if (controller.registrarMascota(id, nombre, especie, raza, edad, peso, idDueno)) {
            System.out.println("✅ Mascota registrada exitosamente");
        } else {
            System.out.println("❌ Error: ID duplicado o dueño no encontrado");
        }
    }
    
    private void listarMascotas() {
        System.out.println("\n--- LISTA DE MASCOTAS ---");
        List<Mascota> mascotas = controller.listarMascotas();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas");
        } else {
            for (Mascota mascota : mascotas) {
                System.out.println(mascota);
            }
        }
    }
    
    private void buscarMascota() {
        System.out.print("\nIngrese ID de la mascota: ");
        String id = scanner.nextLine();
        Mascota mascota = controller.obtenerMascota(id);
        if (mascota != null) {
            System.out.println(mascota);
            System.out.println("Historial Clínico:");
            if (mascota.getHistorialClinico().isEmpty()) {
                System.out.println("  Sin registros");
            } else {
                for (String registro : mascota.getHistorialClinico()) {
                    System.out.println("  - " + registro);
                }
            }
        } else {
            System.out.println("❌ Mascota no encontrada");
        }
    }
    
    private void actualizarMascota() {
        System.out.print("\nIngrese ID de la mascota a actualizar: ");
        String id = scanner.nextLine();
        if (controller.obtenerMascota(id) == null) {
            System.out.println("❌ Mascota no encontrada");
            return;
        }
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva especie: ");
        String especie = scanner.nextLine();
        System.out.print("Nueva raza: ");
        String raza = scanner.nextLine();
        System.out.print("Nueva edad: ");
        int edad = leerEntero();
        System.out.print("Nuevo peso: ");
        double peso = leerDecimal();
        
        if (controller.actualizarMascota(id, nombre, especie, raza, edad, peso)) {
            System.out.println("✅ Mascota actualizada exitosamente");
        } else {
            System.out.println("❌ Error al actualizar");
        }
    }
    
    private void eliminarMascota() {
        System.out.print("\nIngrese ID de la mascota a eliminar: ");
        String id = scanner.nextLine();
        if (controller.eliminarMascota(id)) {
            System.out.println("✅ Mascota eliminada exitosamente");
        } else {
            System.out.println("❌ Error: Mascota no encontrada");
        }
    }
    
    private void agregarHistorial() {
        System.out.print("\nIngrese ID de la mascota: ");
        String id = scanner.nextLine();
        System.out.print("Registro médico: ");
        String registro = scanner.nextLine();
        
        if (controller.agregarHistorialClinico(id, LocalDate.now() + " - " + registro)) {
            System.out.println("✅ Registro agregado al historial");
        } else {
            System.out.println("❌ Mascota no encontrada");
        }
    }
    
    // ============ MENÚ DE EMPLEADOS ============
    
    private void menuEmpleados() {
        System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
        System.out.println("1. Registrar veterinario");
        System.out.println("2. Registrar asistente");
        System.out.println("3. Listar empleados");
        System.out.println("4. Buscar empleado");
        System.out.println("5. Eliminar empleado");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                registrarVeterinario();
                break;
            case 2:
                registrarAsistente();
                break;
            case 3:
                listarEmpleados();
                break;
            case 4:
                buscarEmpleado();
                break;
            case 5:
                eliminarEmpleado();
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }
    
    private void registrarVeterinario() {
        System.out.println("\n--- REGISTRAR VETERINARIO ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Horario: ");
        String horario = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();
        
        if (controller.registrarVeterinario(id, nombre, correo, telefono, horario, especialidad)) {
            System.out.println("✅ Veterinario registrado exitosamente");
        } else {
            System.out.println("❌ Error: El ID ya existe");
        }
    }
    
    private void registrarAsistente() {
        System.out.println("\n--- REGISTRAR ASISTENTE ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Horario: ");
        String horario = scanner.nextLine();
        System.out.print("Área: ");
        String area = scanner.nextLine();
        
        if (controller.registrarAsistente(id, nombre, correo, telefono, horario, area)) {
            System.out.println("✅ Asistente registrado exitosamente");
        } else {
            System.out.println("❌ Error: El ID ya existe");
        }
    }
    
    private void listarEmpleados() {
        System.out.println("\n--- LISTA DE EMPLEADOS ---");
        List<Empleado> empleados = controller.listarEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados");
        } else {
            for (Empleado empleado : empleados) {
                System.out.println(empleado);
            }
        }
    }
    
    private void buscarEmpleado() {
        System.out.print("\nIngrese ID del empleado: ");
        String id = scanner.nextLine();
        Empleado empleado = controller.obtenerEmpleado(id);
        if (empleado != null) {
            System.out.println(empleado);
        } else {
            System.out.println("❌ Empleado no encontrado");
        }
    }
    
    private void eliminarEmpleado() {
        System.out.print("\nIngrese ID del empleado a eliminar: ");
        String id = scanner.nextLine();
        if (controller.eliminarEmpleado(id)) {
            System.out.println("✅ Empleado eliminado exitosamente");
        } else {
            System.out.println("❌ Error: Empleado no encontrado");
        }
    }
    
    // ============ MENÚ DE CITAS ============
    
    private void menuCitas() {
        System.out.println("\n--- GESTIÓN DE CITAS ---");
        System.out.println("1. Agendar cita");
        System.out.println("2. Listar citas");
        System.out.println("3. Buscar cita");
        System.out.println("4. Cancelar cita");
        System.out.println("5. Completar cita");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                agendarCita();
                break;
            case 2:
                listarCitas();
                break;
            case 3:
                buscarCita();
                break;
            case 4:
                cancelarCita();
                break;
            case 5:
                completarCita();
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }
    
    private void agendarCita() {
        System.out.println("\n--- AGENDAR CITA ---");
        System.out.print("ID de la cita: ");
        String id = scanner.nextLine();
        System.out.print("Fecha y hora (dd/MM/yyyy HH:mm): ");
        String fechaHoraStr = scanner.nextLine();
        
        LocalDateTime fechaHora;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);
        } catch (Exception e) {
            System.out.println("❌ Formato de fecha inválido");
            return;
        }
        
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();
        System.out.print("ID de la mascota: ");
        String idMascota = scanner.nextLine();
        System.out.print("ID del dueño: ");
        String idDueno = scanner.nextLine();
        
        // Mostrar veterinarios disponibles
        System.out.println("\nVeterinarios disponibles:");
        List<Veterinario> veterinarios = controller.listarVeterinarios();
        for (Veterinario vet : veterinarios) {
            System.out.println("  " + vet.getId() + " - " + vet.getNombre() + " (" + vet.getEspecialidad() + ")");
        }
        
        System.out.print("ID del veterinario: ");
        String idVeterinario = scanner.nextLine();
        
        if (controller.agendarCita(id, fechaHora, motivo, idMascota, idDueno, idVeterinario)) {
            System.out.println("✅ Cita agendada exitosamente");
        } else {
            System.out.println("❌ Error: Datos inválidos o conflicto de horario");
        }
    }
    
    private void listarCitas() {
        System.out.println("\n--- LISTA DE CITAS ---");
        List<Cita> citas = controller.listarCitas();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas");
        } else {
            for (Cita cita : citas) {
                System.out.println(cita);
            }
        }
    }
    
    private void buscarCita() {
        System.out.print("\nIngrese ID de la cita: ");
        String id = scanner.nextLine();
        Cita cita = controller.obtenerCita(id);
        if (cita != null) {
            System.out.println(cita);
        } else {
            System.out.println("❌ Cita no encontrada");
        }
    }
    
    private void cancelarCita() {
        System.out.print("\nIngrese ID de la cita a cancelar: ");
        String id = scanner.nextLine();
        if (controller.cancelarCita(id)) {
            System.out.println("✅ Cita cancelada exitosamente");
        } else {
            System.out.println("❌ Error: Cita no encontrada");
        }
    }
    
    private void completarCita() {
        System.out.print("\nIngrese ID de la cita a completar: ");
        String id = scanner.nextLine();
        if (controller.completarCita(id)) {
            System.out.println("✅ Cita marcada como completada");
        } else {
            System.out.println("❌ Error: Cita no encontrada");
        }
    }
    
    // ============ SERVICIOS ============
    
    private void mostrarServicios() {
        System.out.println("\n--- SERVICIOS DISPONIBLES ---");
        List<Servicio> servicios = controller.listarServicios();
        for (Servicio servicio : servicios) {
            System.out.println(servicio);
        }
    }
    
    // ============ REPORTES ============
    
    private void menuReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("1. Citas por veterinario");
        System.out.println("2. Mascotas por dueño");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                reporteCitasPorVeterinario();
                break;
            case 2:
                reporteMascotasPorDueno();
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }
    
    private void reporteCitasPorVeterinario() {
        System.out.print("\nIngrese ID del veterinario: ");
        String id = scanner.nextLine();
        List<Cita> citas = controller.obtenerCitasPorVeterinario(id);
        
        System.out.println("\n--- CITAS DEL VETERINARIO " + id + " ---");
        if (citas.isEmpty()) {
            System.out.println("No tiene citas asignadas");
        } else {
            for (Cita cita : citas) {
                System.out.println(cita);
            }
        }
    }
    
    private void reporteMascotasPorDueno() {
        System.out.print("\nIngrese ID del dueño: ");
        String id = scanner.nextLine();
        List<Mascota> mascotas = controller.obtenerMascotasPorDueno(id);
        
        System.out.println("\n--- MASCOTAS DEL DUEÑO " + id + " ---");
        if (mascotas.isEmpty()) {
            System.out.println("No tiene mascotas registradas");
        } else {
            for (Mascota mascota : mascotas) {
                System.out.println(mascota);
            }
        }
    }
    
    // ============ DEMOSTRACIÓN DE POLIMORFISMO ============
    
    private void demostrarPolimorfismo() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║       DEMOSTRACIÓN DE POLIMORFISMO                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("\nEjecutando método realizarTarea() para cada empleado:");
        System.out.println("(Mismo método, diferentes implementaciones)\n");
        
        controller.mostrarTareasEmpleados();
        
        // Demostración adicional de sobrecarga
        List<Veterinario> veterinarios = controller.listarVeterinarios();
        if (!veterinarios.isEmpty()) {
            Veterinario vet = veterinarios.get(0);
            List<Mascota> mascotas = controller.listarMascotas();
            if (!mascotas.isEmpty()) {
                Mascota mascota = mascotas.get(0);
                System.out.println("\n--- Sobrecarga de métodos ---");
                System.out.println(vet.diagnosticar(mascota));
                System.out.println(vet.diagnosticar(mascota, "fiebre y vómitos"));
            }
        }
    }
    
    // ============ MÉTODOS AUXILIARES ============
    
    private int leerEntero() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("❌ Ingrese un número válido: ");
            }
        }
    }
    
    private double leerDecimal() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("❌ Ingrese un número válido: ");
            }
        }
    }
}
