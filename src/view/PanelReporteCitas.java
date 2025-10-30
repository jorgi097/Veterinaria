package view;

import controller.ClinicaController;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Cita;

// Panel de reporte que muestra todas las citas de un veterinario específico
// Este panel es de solo lectura y se genera dinámicamente con el ID del veterinario
public class PanelReporteCitas extends JPanel {
    
    // Constructor que recibe el controlador y el ID del veterinario
    public PanelReporteCitas(ClinicaController controller, String idVeterinario) {
        // Establecer layout BorderLayout con espaciado
        setLayout(new BorderLayout(10, 10));
        // Agregar borde con márgenes internos
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Color de fondo gris claro
        setBackground(new Color(236, 240, 241));
        
        // Panel superior con el título del reporte
        JPanel panelSuperior = new JPanel();
        // Color azul para el encabezado del reporte
        panelSuperior.setBackground(new Color(52, 152, 219));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        // Título que incluye el ID del veterinario
        JLabel lbl = new JLabel("Reporte de Citas - Veterinario: " + idVeterinario);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        panelSuperior.add(lbl);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Definir las columnas de la tabla del reporte
        String[] columnas = {"ID", "Fecha/Hora", "Motivo", "Mascota", "Dueño", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        // Obtener las citas filtradas por veterinario
        List<Cita> citas = controller.obtenerCitasPorVeterinario(idVeterinario);
        // Formateador para mostrar la fecha en formato legible
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        // Agregar cada cita como una fila en la tabla
        for (Cita cita : citas) {
            modelo.addRow(new Object[]{
                cita.getId(),
                cita.getFechaHora().format(formatter), // Formatear la fecha
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getDueno().getNombre(),
                cita.getEstado()
            });
        }
        
        // Crear la tabla con el modelo
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25); // Altura de las filas
        // Estilizar el encabezado
        tabla.getTableHeader().setBackground(new Color(52, 152, 219));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        // Agregar la tabla en un scroll pane al centro del panel
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        // Etiqueta inferior que muestra el total de citas
        JLabel lblTotal = new JLabel("Total de citas: " + citas.size());
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTotal, BorderLayout.SOUTH);
    }
}
