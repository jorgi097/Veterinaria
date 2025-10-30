package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Mascota;

public class PanelReporteMascotas extends JPanel {
    
    public PanelReporteMascotas(ClinicaController controller, String idDueno) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(236, 240, 241));
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(155, 89, 182));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel lbl = new JLabel("Reporte de Mascotas - Dueño: " + idDueno);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        panelSuperior.add(lbl);
        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Edad", "Peso (kg)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        List<Mascota> mascotas = controller.obtenerMascotasPorDueno(idDueno);
        
        for (Mascota mascota : mascotas) {
            modelo.addRow(new Object[]{
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getEdad(),
                mascota.getPeso()
            });
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(155, 89, 182));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        JLabel lblTotal = new JLabel("Total de mascotas: " + mascotas.size());
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTotal, BorderLayout.SOUTH);
    }
}
