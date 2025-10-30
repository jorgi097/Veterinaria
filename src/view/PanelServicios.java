package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Servicio;

public class PanelServicios extends JPanel {
    
    private final ClinicaController controller;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    
    public PanelServicios(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(236, 240, 241));
        
        crearPanelSuperior();
        crearTabla();
        cargarDatos();
    }
    
    private void crearPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 68, 173));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lbl = new JLabel("Servicios Disponibles");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    private void crearTabla() {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setBackground(new Color(142, 68, 173));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Catálogo de Servicios"));
        add(scroll, BorderLayout.CENTER);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Servicio> servicios = controller.listarServicios();
        
        for (Servicio servicio : servicios) {
            modeloTabla.addRow(new Object[]{
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                String.format("$%.2f", servicio.getPrecio())
            });
        }
    }
}
