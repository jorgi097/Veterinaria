package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una mascota en la clínica veterinaria
 * Aplicación de encapsulamiento con atributos privados
 */
public class Mascota {
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private double peso;
    private List<String> historialClinico;
    private Dueno dueno;
    
    /**
     * Constructor de la clase Mascota
     */
    public Mascota(String id, String nombre, String especie, String raza, int edad, double peso, Dueno dueno) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.dueno = dueno;
        this.historialClinico = new ArrayList<>();
    }
    
    // Getters y Setters (Encapsulamiento)
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
    
    public List<String> getHistorialClinico() {
        return historialClinico;
    }
    
    public void agregarHistorialClinico(String registro) {
        this.historialClinico.add(registro);
    }
    
    public Dueno getDueno() {
        return dueno;
    }
    
    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }
    
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
