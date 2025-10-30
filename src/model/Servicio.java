package model;

import java.time.LocalDate;

/**
 * Clase que representa un servicio ofrecido por la clínica
 */
public class Servicio {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Mascota mascota;
    private LocalDate fechaRealizado;
    
    /**
     * Constructor de la clase Servicio
     */
    public Servicio(String id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    /**
     * Constructor sobrecargado con mascota y fecha
     */
    public Servicio(String id, String nombre, String descripcion, double precio, 
                    Mascota mascota, LocalDate fechaRealizado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.mascota = mascota;
        this.fechaRealizado = fechaRealizado;
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
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public LocalDate getFechaRealizado() {
        return fechaRealizado;
    }
    
    public void setFechaRealizado(LocalDate fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }
    
    /**
     * Aplica un descuento al servicio
     */
    public double aplicarDescuento(double porcentaje) {
        return precio - (precio * porcentaje / 100);
    }
    
    @Override
    public String toString() {
        return "Servicio{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripción='" + descripcion + '\'' +
                ", precio=$" + precio +
                (mascota != null ? ", mascota=" + mascota.getNombre() : "") +
                (fechaRealizado != null ? ", fecha=" + fechaRealizado : "") +
                '}';
    }
}
