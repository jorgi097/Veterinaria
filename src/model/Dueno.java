package model;

import java.util.ArrayList;
import java.util.List;

// Clase que representa al dueño de una o más mascotas
// Implementa relación uno-a-muchos con Mascota
public class Dueno {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    // Lista de mascotas del dueño (relación uno-a-muchos)
    private List<Mascota> mascotas;
    
    // Constructor de la clase Dueno
    // Inicializa la lista de mascotas vacía
    public Dueno(String id, String nombre, String direccion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        // Inicializar lista vacía de mascotas
        this.mascotas = new ArrayList<>();
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
    
    // Retorna la lista completa de mascotas del dueño
    public List<Mascota> getMascotas() {
        return mascotas;
    }
    
    // Agrega una mascota a la lista del dueño
    // Implementa relación uno-a-muchos (un dueño puede tener muchas mascotas)
    // Evita duplicados verificando si ya existe
    public void agregarMascota(Mascota mascota) {
        if (!mascotas.contains(mascota)) {
            mascotas.add(mascota);
        }
    }
    
    // Elimina una mascota de la lista del dueño
    public void eliminarMascota(Mascota mascota) {
        mascotas.remove(mascota);
    }
    
    // Sobrescribe el método toString() para representación en texto
    // Incluye el número de mascotas que tiene
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
