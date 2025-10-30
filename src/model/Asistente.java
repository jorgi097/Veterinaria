package model;

/**
 * Clase que representa un Asistente de veterinaria
 * Extiende de Empleado y demuestra herencia y polimorfismo
 */
public class Asistente extends Empleado {
    private String area;
    
    /**
     * Constructor de la clase Asistente
     */
    public Asistente(String id, String nombre, String correo, String telefono, String horario, String area) {
        super(id, nombre, correo, telefono, horario);
        this.area = area;
    }
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    /**
     * Implementación del método abstracto realizarTarea
     * Demuestra polimorfismo (método sobrescrito)
     */
    @Override
    public String realizarTarea() {
        return "Asistente " + getNombre() + " está apoyando en el área de " + area + 
               " y asistiendo a los veterinarios.";
    }
    
    /**
     * Método específico de Asistente
     */
    public String prepararSala(String numeroSala) {
        return "Asistente " + getNombre() + " está preparando la sala " + numeroSala + " para atención.";
    }
    
    /**
     * Sobrecarga del método prepararSala
     * Demuestra sobrecarga de métodos (polimorfismo estático)
     */
    public String prepararSala(String numeroSala, String tipoProcedimiento) {
        return "Asistente " + getNombre() + " está preparando la sala " + numeroSala + 
               " para " + tipoProcedimiento;
    }
    
    @Override
    public String toString() {
        return "Asistente{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", area='" + area + '\'' +
                ", horario='" + getHorario() + '\'' +
                '}';
    }
}
