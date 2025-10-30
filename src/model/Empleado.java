package model;

/**
 * Clase abstracta que representa un empleado de la clínica
 * Demuestra el uso de herencia y polimorfismo
 */
public abstract class Empleado {
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String horario;
    
    /**
     * Constructor de la clase abstracta Empleado
     */
    public Empleado(String id, String nombre, String correo, String telefono, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.horario = horario;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    /**
     * Método abstracto que debe ser implementado por las subclases
     * Demuestra polimorfismo
     */
    public abstract String realizarTarea();
    
    /**
     * Método común para todos los empleados
     */
    public String obtenerInformacion() {
        return "ID: " + id + ", Nombre: " + nombre + ", Horario: " + horario;
    }
    
    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}
