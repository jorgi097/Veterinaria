package model;

import java.util.ArrayList;
import java.util.List;

// Clase que representa un Veterinario
// Extiende de Empleado y demuestra herencia y polimorfismo
public class Veterinario extends Empleado {
    // Atributo específico del veterinario
    private String especialidad;
    // Lista de citas asignadas a este veterinario
    private List<Cita> citasAsignadas;
    
    // Constructor de la clase Veterinario
    // Llama al constructor de la clase padre (Empleado) con super()
    public Veterinario(String id, String nombre, String correo, String telefono, String horario, String especialidad) {
        // Llamar al constructor de la clase padre
        super(id, nombre, correo, telefono, horario);
        this.especialidad = especialidad;
        // Inicializar la lista de citas vacía
        this.citasAsignadas = new ArrayList<>();
    }
    
    // Getter y setter para especialidad
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    // Getter para la lista de citas
    public List<Cita> getCitasAsignadas() {
        return citasAsignadas;
    }
    
    // Agrega una cita a la lista del veterinario
    // Evita duplicados verificando si ya existe
    public void agregarCita(Cita cita) {
        if (!citasAsignadas.contains(cita)) {
            citasAsignadas.add(cita);
        }
    }
    
    // Implementación del método abstracto realizarTarea() de Empleado
    // Demuestra polimorfismo (método sobrescrito)
    // Retorna la tarea específica que realiza un veterinario
    @Override
    public String realizarTarea() {
        return "Veterinario " + getNombre() + " está atendiendo consultas médicas y realizando procedimientos veterinarios.";
    }
    
    // Método específico de Veterinario (no heredado)
    // Diagnostica una mascota
    public String diagnosticar(Mascota mascota) {
        return "Veterinario " + getNombre() + " está diagnosticando a " + mascota.getNombre();
    }
    
    // Sobrecarga del método diagnosticar
    // Demuestra sobrecarga de métodos (polimorfismo estático/en tiempo de compilación)
    // Mismo nombre pero diferentes parámetros
    public String diagnosticar(Mascota mascota, String sintomas) {
        return "Veterinario " + getNombre() + " está diagnosticando a " + mascota.getNombre() + 
               " con los siguientes síntomas: " + sintomas;
    }
    
    // Sobrescribe el método toString() para representación en texto
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
