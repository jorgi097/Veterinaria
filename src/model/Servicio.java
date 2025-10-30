package model;

import java.time.LocalDate;

// Clase que representa un servicio ofrecido por la clínica
// Por ejemplo: consulta, vacunación, cirugía, etc.
public class Servicio {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Mascota mascota; // Mascota a la que se le aplicó el servicio (opcional)
    private LocalDate fechaRealizado; // Fecha en que se realizó (opcional)
    
    // Constructor básico de la clase Servicio
    // Usado para definir servicios en el catálogo
    public Servicio(String id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    // Constructor sobrecargado con mascota y fecha
    // Usado cuando el servicio se aplica a una mascota específica
    public Servicio(String id, String nombre, String descripcion, double precio, 
                    Mascota mascota, LocalDate fechaRealizado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.mascota = mascota;
        this.fechaRealizado = fechaRealizado;
    }
    
    // ===== GETTERS Y SETTERS (Encapsulamiento) =====
    
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
    
    // Aplica un descuento al servicio según el porcentaje especificado
    // Retorna el precio con descuento aplicado
    public double aplicarDescuento(double porcentaje) {
        return precio - (precio * porcentaje / 100);
    }
    
    // Sobrescribe el método toString() para representación en texto
    // Incluye información de la mascota y fecha si están disponibles
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
