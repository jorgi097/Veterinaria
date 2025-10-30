package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Servicio;

// Panel de interfaz gráfica para mostrar los servicios disponibles
// Solo muestra una tabla de consulta (sin funcionalidad de edición)
public class PanelServicios extends JPanel {
    
    // Referencia al controlador para acceder a los servicios
    private final ClinicaController controller;
    // Tabla para mostrar los servicios
    private JTable tabla;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    
    // Constructor del panel
    public PanelServicios(ClinicaController controller) {
        this.controller = controller;
        // Establecer layout BorderLayout con espaciado
        setLayout(new BorderLayout(10, 10));
        // Agregar borde con márgenes internos
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Color de fondo gris claro
        setBackground(new Color(236, 240, 241));
        
        // Crear los componentes del panel
        crearPanelSuperior();
        crearTabla();
        // Cargar los datos de servicios
        cargarDatos();
    }
    
    // Crea el panel superior con el título
    private void crearPanelSuperior() {
        JPanel panel = new JPanel();
        // Color morado para el encabezado
        panel.setBackground(new Color(142, 68, 173));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Título del panel
        JLabel lbl = new JLabel("Servicios Disponibles");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea la tabla para mostrar el catálogo de servicios
    private void crearTabla() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio ($)"};
        // Crear modelo de tabla no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas no editables - solo consulta
            }
        };
        
        // Crear la tabla con el modelo
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30); // Altura mayor para mejor legibilidad
        // Estilizar el encabezado
        tabla.getTableHeader().setBackground(new Color(142, 68, 173));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        // Fuente para el contenido de la tabla
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Agregar la tabla dentro de un scroll pane
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Catálogo de Servicios"));
        add(scroll, BorderLayout.CENTER);
    }
    
    // Carga todos los servicios en la tabla
    private void cargarDatos() {
        // Limpiar las filas existentes
        modeloTabla.setRowCount(0);
        // Obtener la lista de servicios del controlador
        List<Servicio> servicios = controller.listarServicios();
        
        // Agregar cada servicio como una fila en la tabla
        for (Servicio servicio : servicios) {
            modeloTabla.addRow(new Object[]{
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                String.format("$%.2f", servicio.getPrecio()) // Formato de precio con 2 decimales
            });
        }
    }
}
