package model;

// Clase que representa un Asistente de veterinaria
// Extiende de Empleado y demuestra herencia y polimorfismo
public class Asistente extends Empleado {
    // Atributo específico del asistente: área de trabajo
    private String area;
    
    // Constructor de la clase Asistente
    // Llama al constructor de la clase padre (Empleado) con super()
    public Asistente(String id, String nombre, String correo, String telefono, String horario, String area) {
        // Llamar al constructor de la clase padre
        super(id, nombre, correo, telefono, horario);
        this.area = area;
    }
    
    // Getter y setter para el área
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    // Implementación del método abstracto realizarTarea() de Empleado
    // Demuestra polimorfismo (método sobrescrito)
    // Retorna la tarea específica que realiza un asistente
    @Override
    public String realizarTarea() {
        return "Asistente " + getNombre() + " está apoyando en el área de " + area + 
               " y asistiendo a los veterinarios.";
    }
    
    // Método específico de Asistente
    // Prepara una sala para atención
    public String prepararSala(String numeroSala) {
        return "Asistente " + getNombre() + " está preparando la sala " + numeroSala + " para atención.";
    }
    
    // Sobrecarga del método prepararSala
    // Demuestra sobrecarga de métodos (polimorfismo estático/en tiempo de compilación)
    // Mismo nombre pero diferentes parámetros
    public String prepararSala(String numeroSala, String tipoProcedimiento) {
        return "Asistente " + getNombre() + " está preparando la sala " + numeroSala + 
               " para " + tipoProcedimiento;
    }
    
    // Sobrescribe el método toString() para representación en texto
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
