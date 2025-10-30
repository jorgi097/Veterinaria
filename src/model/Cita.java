package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase que representa una cita médica en la clínica
// Relaciona a una mascota, un dueño y un veterinario en una fecha/hora específica
public class Cita {
    // Atributos privados (encapsulamiento)
    private String id;
    private LocalDateTime fechaHora; // Fecha y hora de la cita
    private String motivo; // Motivo de la consulta
    private Mascota mascota; // Mascota que será atendida
    private Dueno dueno; // Dueño de la mascota
    private Veterinario veterinario; // Veterinario que atenderá
    private String estado; // Estado de la cita: Programada, Completada, Cancelada
    
    // Constructor de la clase Cita
    // Inicializa todas las relaciones y el estado predeterminado
    public Cita(String id, LocalDateTime fechaHora, String motivo, Mascota mascota, 
                Dueno dueno, Veterinario veterinario) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        this.dueno = dueno;
        this.veterinario = veterinario;
        // Estado inicial de toda cita nueva
        this.estado = "Programada";
    }
    
    // ===== GETTERS Y SETTERS (Encapsulamiento) =====
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public Dueno getDueno() {
        return dueno;
    }
    
    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }
    
    public Veterinario getVeterinario() {
        return veterinario;
    }
    
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    // Sobrescribe el método toString() para representación en texto
    // Formatea la fecha/hora en formato legible (dd/MM/yyyy HH:mm)
    @Override
    public String toString() {
        // Crear formateador de fecha personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Cita{" +
                "id='" + id + '\'' +
                ", fecha=" + fechaHora.format(formatter) +
                ", motivo='" + motivo + '\'' +
                ", mascota=" + (mascota != null ? mascota.getNombre() : "N/A") +
                ", dueño=" + (dueno != null ? dueno.getNombre() : "N/A") +
                ", veterinario=" + (veterinario != null ? veterinario.getNombre() : "N/A") +
                ", estado='" + estado + '\'' +
                '}';
    }
}
