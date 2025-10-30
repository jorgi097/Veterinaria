package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa al dueño de una o más mascotas
 * Implementa relación uno-a-muchos con Mascota
 */
public class Dueno {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private List<Mascota> mascotas;
    
    /**
     * Constructor de la clase Dueno
     */
    public Dueno(String id, String nombre, String direccion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.mascotas = new ArrayList<>();
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
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public List<Mascota> getMascotas() {
        return mascotas;
    }
    
    /**
     * Agrega una mascota a la lista del dueño
     * Implementa relación uno-a-muchos
     */
    public void agregarMascota(Mascota mascota) {
        if (!mascotas.contains(mascota)) {
            mascotas.add(mascota);
        }
    }
    
    /**
     * Elimina una mascota de la lista del dueño
     */
    public void eliminarMascota(Mascota mascota) {
        mascotas.remove(mascota);
    }
    
    @Override
    public String toString() {
        return "Dueño{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", teléfono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", mascotas=" + mascotas.size() +
                '}';
    }
}
