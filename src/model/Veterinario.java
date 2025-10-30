package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Veterinario
 * Extiende de Empleado y demuestra herencia y polimorfismo
 */
public class Veterinario extends Empleado {
    private String especialidad;
    private List<Cita> citasAsignadas;
    
    /**
     * Constructor de la clase Veterinario
     */
    public Veterinario(String id, String nombre, String correo, String telefono, String horario, String especialidad) {
        super(id, nombre, correo, telefono, horario);
        this.especialidad = especialidad;
        this.citasAsignadas = new ArrayList<>();
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public List<Cita> getCitasAsignadas() {
        return citasAsignadas;
    }
    
    /**
     * Agrega una cita a la lista del veterinario
     */
    public void agregarCita(Cita cita) {
        if (!citasAsignadas.contains(cita)) {
            citasAsignadas.add(cita);
        }
    }
    
    /**
     * Implementación del método abstracto realizarTarea
     * Demuestra polimorfismo
     */
    @Override
    public String realizarTarea() {
        return "Veterinario " + getNombre() + " está atendiendo consultas médicas y realizando procedimientos veterinarios.";
    }
    
    /**
     * Método específico de Veterinario
     */
    public String diagnosticar(Mascota mascota) {
        return "Veterinario " + getNombre() + " está diagnosticando a " + mascota.getNombre();
    }
    
    /**
     * Sobrecarga del método diagnosticar
     * Demuestra sobrecarga de métodos (polimorfismo estático)
     */
    public String diagnosticar(Mascota mascota, String sintomas) {
        return "Veterinario " + getNombre() + " está diagnosticando a " + mascota.getNombre() + 
               " con los siguientes síntomas: " + sintomas;
    }
    
    @Override
    public String toString() {
        return "Veterinario{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", horario='" + getHorario() + '\'' +
                ", citas asignadas=" + citasAsignadas.size() +
                '}';
    }
}
