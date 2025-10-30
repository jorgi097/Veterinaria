package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una cita médica en la clínica
 */
public class Cita {
    private String id;
    private LocalDateTime fechaHora;
    private String motivo;
    private Mascota mascota;
    private Dueno dueno;
    private Veterinario veterinario;
    private String estado; // Programada, Completada, Cancelada
    
    /**
     * Constructor de la clase Cita
     */
    public Cita(String id, LocalDateTime fechaHora, String motivo, Mascota mascota, 
                Dueno dueno, Veterinario veterinario) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        this.dueno = dueno;
        this.veterinario = veterinario;
        this.estado = "Programada";
    }
    
    // Getters y Setters
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
    
    @Override
    public String toString() {
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
