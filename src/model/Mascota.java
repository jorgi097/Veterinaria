package model;

import java.util.ArrayList;
import java.util.List;

// Clase que representa una mascota en la clínica veterinaria
// Aplicación de encapsulamiento con atributos privados y getters/setters
public class Mascota {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private double peso;
    // Lista que almacena el historial clínico de la mascota
    private List<String> historialClinico;
    // Relación con el dueño (asociación)
    private Dueno dueno;
    
    // Constructor de la clase Mascota
    // Inicializa todos los atributos incluyendo la relación con el dueño
    public Mascota(String id, String nombre, String especie, String raza, int edad, double peso, Dueno dueno) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.dueno = dueno;
        // Inicializar la lista de historial clínico vacía
        this.historialClinico = new ArrayList<>();
    }
    
    // ===== GETTERS Y SETTERS (Encapsulamiento) =====
    // Permiten acceso controlado a los atributos privados
    
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
    
    public String getEspecie() {
        return especie;
    }
    
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    // Retorna la lista completa del historial clínico
    public List<String> getHistorialClinico() {
        return historialClinico;
    }
    
    // Agrega un nuevo registro al historial clínico de la mascota
    public void agregarHistorialClinico(String registro) {
        this.historialClinico.add(registro);
    }
    
    // Retorna el dueño de la mascota
    public Dueno getDueno() {
        return dueno;
    }
    
    // Establece o cambia el dueño de la mascota
    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }
    
    // Sobrescribe el método toString() para representación en texto
    // Incluye información del dueño si existe
    @Override
    public String toString() {
        return "Mascota{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", dueño=" + (dueno != null ? dueno.getNombre() : "Sin dueño") +
                '}';
    }
}
