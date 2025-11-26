package utils;

import controller.ClinicaController;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.*;

/**
 * Clase para persistir datos en base de datos SQLite
 * Reemplaza la persistencia en archivos de texto por una base de datos
 */
public class PersistenciaSQLite {
    // Ruta de la base de datos SQLite
    private static final String URL_BD = "jdbc:sqlite:clinica.db";
    
    private ClinicaController controller;
    
    // Bloque estático para cargar el driver de SQLite
    static {
        try {
            // Cargar el driver de SQLite explícitamente
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de SQLite.");
            System.err.println("Asegúrate de tener sqlite-jdbc-3.44.1.0.jar en el classpath.");
        }
    }
    
    // Constructor que recibe el controlador
    public PersistenciaSQLite(ClinicaController controller) {
        this.controller = controller;
        // Crear las tablas si no existen
        crearTablas();
    }
    
    // Obtiene una conexión a la base de datos
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL_BD);
    }
    
    // Crea todas las tablas necesarias si no existen
    private void crearTablas() {
        String sqlDuenos = "CREATE TABLE IF NOT EXISTS duenos (" +
                "id TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "direccion TEXT," +
                "telefono TEXT," +
                "correo TEXT)";
        
        String sqlMascotas = "CREATE TABLE IF NOT EXISTS mascotas (" +
                "id TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "especie TEXT," +
                "raza TEXT," +
                "edad INTEGER," +
                "peso REAL," +
                "alergias TEXT," +
                "id_dueno TEXT," +
                "FOREIGN KEY (id_dueno) REFERENCES duenos(id))";
        
        String sqlHistorial = "CREATE TABLE IF NOT EXISTS historial_clinico (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_mascota TEXT," +
                "registro TEXT," +
                "FOREIGN KEY (id_mascota) REFERENCES mascotas(id))";
        
        String sqlEmpleados = "CREATE TABLE IF NOT EXISTS empleados (" +
                "id TEXT PRIMARY KEY," +
                "tipo TEXT," + // VET o ASI
                "nombre TEXT NOT NULL," +
                "correo TEXT," +
                "telefono TEXT," +
                "horario TEXT," +
                "especialidad_area TEXT)"; // especialidad para vet, area para asistente
        
        String sqlCitas = "CREATE TABLE IF NOT EXISTS citas (" +
                "id TEXT PRIMARY KEY," +
                "fecha_hora TEXT," +
                "motivo TEXT," +
                "id_mascota TEXT," +
                "id_dueno TEXT," +
                "id_veterinario TEXT," +
                "estado TEXT," +
                "FOREIGN KEY (id_mascota) REFERENCES mascotas(id)," +
                "FOREIGN KEY (id_dueno) REFERENCES duenos(id)," +
                "FOREIGN KEY (id_veterinario) REFERENCES empleados(id))";
        
        String sqlProductos = "CREATE TABLE IF NOT EXISTS productos (" +
                "id TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "categoria TEXT," +
                "cantidad INTEGER," +
                "cantidad_minima INTEGER," +
                "precio REAL)";
        
        String sqlServicios = "CREATE TABLE IF NOT EXISTS servicios (" +
                "id TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT," +
                "precio REAL)";
        
        // Tabla para relacionar citas con servicios (N:M)
        String sqlCitaServicios = "CREATE TABLE IF NOT EXISTS cita_servicios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cita TEXT," +
                "id_servicio TEXT," +
                "FOREIGN KEY (id_cita) REFERENCES citas(id)," +
                "FOREIGN KEY (id_servicio) REFERENCES servicios(id))";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlDuenos);
            stmt.execute(sqlMascotas);
            stmt.execute(sqlHistorial);
            stmt.execute(sqlEmpleados);
            stmt.execute(sqlCitas);
            stmt.execute(sqlProductos);
            stmt.execute(sqlServicios);
            stmt.execute(sqlCitaServicios);
            System.out.println("[OK] Base de datos inicializada correctamente");
        } catch (SQLException e) {
            System.err.println("Error al crear tablas: " + e.getMessage());
        }
    }
    
    // ============ GUARDAR DATOS ============
    
    /**
     * Guarda todos los datos en la base de datos
     */
    public void guardarTodo() {
        guardarDuenos();
        guardarMascotas();
        guardarEmpleados();
        guardarCitas();
        guardarProductos();
        guardarServicios();
        System.out.println("[OK] Datos guardados en la base de datos");
    }
    
    private void guardarDuenos() {
        String sql = "INSERT OR REPLACE INTO duenos (id, nombre, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            List<Dueno> duenos = controller.listarDuenos();
            for (Dueno dueno : duenos) {
                pstmt.setString(1, dueno.getId());
                pstmt.setString(2, dueno.getNombre());
                pstmt.setString(3, dueno.getDireccion());
                pstmt.setString(4, dueno.getTelefono());
                pstmt.setString(5, dueno.getCorreo());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar dueños: " + e.getMessage());
        }
    }
    
    private void guardarMascotas() {
        String sqlMascota = "INSERT OR REPLACE INTO mascotas (id, nombre, especie, raza, edad, peso, alergias, id_dueno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlHistorial = "INSERT INTO historial_clinico (id_mascota, registro) VALUES (?, ?)";
        String sqlBorrarHistorial = "DELETE FROM historial_clinico WHERE id_mascota = ?";
        
        try (Connection conn = conectar()) {
            PreparedStatement pstmtMascota = conn.prepareStatement(sqlMascota);
            PreparedStatement pstmtBorrar = conn.prepareStatement(sqlBorrarHistorial);
            PreparedStatement pstmtHistorial = conn.prepareStatement(sqlHistorial);
            
            List<Mascota> mascotas = controller.listarMascotas();
            for (Mascota mascota : mascotas) {
                // Guardar mascota
                pstmtMascota.setString(1, mascota.getId());
                pstmtMascota.setString(2, mascota.getNombre());
                pstmtMascota.setString(3, mascota.getEspecie());
                pstmtMascota.setString(4, mascota.getRaza());
                pstmtMascota.setInt(5, mascota.getEdad());
                pstmtMascota.setDouble(6, mascota.getPeso());
                pstmtMascota.setString(7, mascota.getAlergias());
                pstmtMascota.setString(8, mascota.getDueno().getId());
                pstmtMascota.executeUpdate();
                
                // Borrar historial viejo y guardar el nuevo
                pstmtBorrar.setString(1, mascota.getId());
                pstmtBorrar.executeUpdate();
                
                for (String registro : mascota.getHistorialClinico()) {
                    pstmtHistorial.setString(1, mascota.getId());
                    pstmtHistorial.setString(2, registro);
                    pstmtHistorial.executeUpdate();
                }
            }
            
            pstmtMascota.close();
            pstmtBorrar.close();
            pstmtHistorial.close();
        } catch (SQLException e) {
            System.err.println("Error al guardar mascotas: " + e.getMessage());
        }
    }
    
    private void guardarEmpleados() {
        String sql = "INSERT OR REPLACE INTO empleados (id, tipo, nombre, correo, telefono, horario, especialidad_area) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            List<Empleado> empleados = controller.listarEmpleados();
            for (Empleado emp : empleados) {
                pstmt.setString(1, emp.getId());
                if (emp instanceof Veterinario) {
                    Veterinario vet = (Veterinario) emp;
                    pstmt.setString(2, "VET");
                    pstmt.setString(7, vet.getEspecialidad());
                } else if (emp instanceof Asistente) {
                    Asistente asist = (Asistente) emp;
                    pstmt.setString(2, "ASI");
                    pstmt.setString(7, asist.getArea());
                }
                pstmt.setString(3, emp.getNombre());
                pstmt.setString(4, emp.getCorreo());
                pstmt.setString(5, emp.getTelefono());
                pstmt.setString(6, emp.getHorario());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar empleados: " + e.getMessage());
        }
    }
    
    private void guardarCitas() {
        String sql = "INSERT OR REPLACE INTO citas (id, fecha_hora, motivo, id_mascota, id_dueno, id_veterinario, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlBorrarServicios = "DELETE FROM cita_servicios WHERE id_cita = ?";
        String sqlServicioCita = "INSERT INTO cita_servicios (id_cita, id_servicio) VALUES (?, ?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmtBorrar = conn.prepareStatement(sqlBorrarServicios);
             PreparedStatement pstmtServicio = conn.prepareStatement(sqlServicioCita)) {
            
            List<Cita> citas = controller.listarCitas();
            for (Cita cita : citas) {
                pstmt.setString(1, cita.getId());
                pstmt.setString(2, cita.getFechaHora().format(formatter));
                pstmt.setString(3, cita.getMotivo());
                pstmt.setString(4, cita.getMascota().getId());
                pstmt.setString(5, cita.getDueno().getId());
                pstmt.setString(6, cita.getVeterinario().getId());
                pstmt.setString(7, cita.getEstado());
                pstmt.executeUpdate();
                
                // Guardar servicios de la cita
                pstmtBorrar.setString(1, cita.getId());
                pstmtBorrar.executeUpdate();
                
                for (Servicio servicio : cita.getServiciosRealizados()) {
                    pstmtServicio.setString(1, cita.getId());
                    pstmtServicio.setString(2, servicio.getId());
                    pstmtServicio.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar citas: " + e.getMessage());
        }
    }
    
    private void guardarProductos() {
        String sql = "INSERT OR REPLACE INTO productos (id, nombre, categoria, cantidad, cantidad_minima, precio) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            List<Producto> productos = controller.listarProductos();
            for (Producto prod : productos) {
                pstmt.setString(1, prod.getId());
                pstmt.setString(2, prod.getNombre());
                pstmt.setString(3, prod.getCategoria());
                pstmt.setInt(4, prod.getCantidad());
                pstmt.setInt(5, prod.getCantidadMinima());
                pstmt.setDouble(6, prod.getPrecio());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }
    
    private void guardarServicios() {
        String sql = "INSERT OR REPLACE INTO servicios (id, nombre, descripcion, precio) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            List<Servicio> servicios = controller.listarServicios();
            for (Servicio serv : servicios) {
                pstmt.setString(1, serv.getId());
                pstmt.setString(2, serv.getNombre());
                pstmt.setString(3, serv.getDescripcion());
                pstmt.setDouble(4, serv.getPrecio());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar servicios: " + e.getMessage());
        }
    }
    
    // ============ CARGAR DATOS ============
    
    /**
     * Carga todos los datos desde la base de datos
     */
    public void cargarTodo() {
        cargarDuenos();
        cargarMascotas();
        cargarEmpleados();
        cargarServicios(); // Cargar servicios antes de citas
        cargarCitas();
        cargarProductos();
        System.out.println("[OK] Datos cargados desde la base de datos");
    }
    
    private void cargarDuenos() {
        String sql = "SELECT * FROM duenos";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                controller.registrarDueno(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar dueños: " + e.getMessage());
        }
    }
    
    private void cargarMascotas() {
        String sqlMascotas = "SELECT * FROM mascotas";
        String sqlHistorial = "SELECT registro FROM historial_clinico WHERE id_mascota = ?";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlMascotas)) {
            
            PreparedStatement pstmtHistorial = conn.prepareStatement(sqlHistorial);
            
            while (rs.next()) {
                String idMascota = rs.getString("id");
                controller.registrarMascota(
                    idMascota,
                    rs.getString("nombre"),
                    rs.getString("especie"),
                    rs.getString("raza"),
                    rs.getInt("edad"),
                    rs.getDouble("peso"),
                    rs.getString("alergias"),
                    rs.getString("id_dueno")
                );
                
                // Cargar historial clinico
                pstmtHistorial.setString(1, idMascota);
                ResultSet rsHistorial = pstmtHistorial.executeQuery();
                while (rsHistorial.next()) {
                    controller.agregarHistorialClinico(idMascota, rsHistorial.getString("registro"));
                }
                rsHistorial.close();
            }
            pstmtHistorial.close();
        } catch (SQLException e) {
            System.err.println("Error al cargar mascotas: " + e.getMessage());
        }
    }
    
    private void cargarEmpleados() {
        String sql = "SELECT * FROM empleados";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("VET".equals(tipo)) {
                    controller.registrarVeterinario(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("horario"),
                        rs.getString("especialidad_area")
                    );
                } else if ("ASI".equals(tipo)) {
                    controller.registrarAsistente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("horario"),
                        rs.getString("especialidad_area")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar empleados: " + e.getMessage());
        }
    }
    
    private void cargarCitas() {
        String sql = "SELECT * FROM citas";
        String sqlServicios = "SELECT id_servicio FROM cita_servicios WHERE id_cita = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             PreparedStatement pstmtServicios = conn.prepareStatement(sqlServicios)) {
            
            while (rs.next()) {
                String idCita = rs.getString("id");
                LocalDateTime fechaHora = LocalDateTime.parse(rs.getString("fecha_hora"), formatter);
                controller.agendarCita(
                    idCita,
                    fechaHora,
                    rs.getString("motivo"),
                    rs.getString("id_mascota"),
                    rs.getString("id_dueno"),
                    rs.getString("id_veterinario")
                );
                
                // Restaurar estado de la cita
                Cita cita = controller.obtenerCita(idCita);
                if (cita != null) {
                    cita.setEstado(rs.getString("estado"));
                    
                    // Cargar servicios de la cita
                    pstmtServicios.setString(1, idCita);
                    ResultSet rsServicios = pstmtServicios.executeQuery();
                    while (rsServicios.next()) {
                        controller.agregarServicioACita(idCita, rsServicios.getString("id_servicio"));
                    }
                    rsServicios.close();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar citas: " + e.getMessage());
        }
    }
    
    private void cargarProductos() {
        String sql = "SELECT * FROM productos";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                controller.registrarProducto(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad"),
                    rs.getInt("cantidad_minima"),
                    rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
        }
    }
    
    private void cargarServicios() {
        String sql = "SELECT * FROM servicios";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Usar agregarServicio para evitar duplicados con los predeterminados
                controller.agregarServicio(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar servicios: " + e.getMessage());
        }
    }
}
