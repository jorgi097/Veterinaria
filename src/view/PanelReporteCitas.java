package view;

import controller.ClinicaController;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Cita;

public class PanelReporteCitas extends JPanel {
    
    public PanelReporteCitas(ClinicaController controller, String idVeterinario) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(236, 240, 241));
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(52, 152, 219));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel lbl = new JLabel("Reporte de Citas - Veterinario: " + idVeterinario);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        panelSuperior.add(lbl);
        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Fecha/Hora", "Motivo", "Mascota", "Dueño", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        List<Cita> citas = controller.obtenerCitasPorVeterinario(idVeterinario);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Cita cita : citas) {
            modelo.addRow(new Object[]{
                cita.getId(),
                cita.getFechaHora().format(formatter),
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getDueno().getNombre(),
                cita.getEstado()
            });
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(52, 152, 219));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        JLabel lblTotal = new JLabel("Total de citas: " + citas.size());
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTotal, BorderLayout.SOUTH);
    }
}
