package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Mascota;

// Panel de reporte que muestra todas las mascotas de un dueño específico
// Este panel es de solo lectura y se genera dinámicamente con el ID del dueño
public class PanelReporteMascotas extends JPanel {
    
    // Constructor que recibe el controlador y el ID del dueño
    public PanelReporteMascotas(ClinicaController controller, String idDueno) {
        // Establecer layout BorderLayout con espaciado
        setLayout(new BorderLayout(10, 10));
        // Agregar borde con márgenes internos
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Color de fondo gris claro
        setBackground(new Color(236, 240, 241));
        
        // Panel superior con el título del reporte
        JPanel panelSuperior = new JPanel();
        // Color morado para el encabezado del reporte
        panelSuperior.setBackground(new Color(155, 89, 182));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        // Título que incluye el ID del dueño
        JLabel lbl = new JLabel("Reporte de Mascotas - Dueño: " + idDueno);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        panelSuperior.add(lbl);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Definir las columnas de la tabla del reporte
        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Edad", "Peso (kg)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        // Obtener las mascotas filtradas por dueño
        List<Mascota> mascotas = controller.obtenerMascotasPorDueno(idDueno);
        
        // Agregar cada mascota como una fila en la tabla
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
        
        // Crear la tabla con el modelo
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25); // Altura de las filas
        // Estilizar el encabezado
        tabla.getTableHeader().setBackground(new Color(155, 89, 182));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        // Agregar la tabla en un scroll pane al centro del panel
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        // Etiqueta inferior que muestra el total de mascotas
        JLabel lblTotal = new JLabel("Total de mascotas: " + mascotas.size());
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTotal, BorderLayout.SOUTH);
    }
}
