package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Servicio;

// Panel de interfaz gráfica para gestionar servicios
// Permite agregar, editar y eliminar servicios del catálogo
public class PanelServicios extends JPanel {
    
    // Referencia al controlador para acceder a los servicios
    private final ClinicaController controller;
    // Tabla para mostrar los servicios
    private JTable tabla;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    // Campos del formulario
    private JTextField txtId, txtNombre, txtDescripcion, txtPrecio;
    
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
        crearPanelFormulario();
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
        JLabel lbl = new JLabel("Gestión de Servicios");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea el formulario para agregar/editar servicios
    private void crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Servicio"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Inicializar campos
        txtId = new JTextField(10);
        txtNombre = new JTextField(15);
        txtDescripcion = new JTextField(20);
        txtPrecio = new JTextField(10);
        
        // Fila 1: ID y Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panel.add(txtNombre, gbc);
        
        // Fila 2: Descripción y Precio
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        panel.add(txtDescripcion, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Precio ($):"), gbc);
        gbc.gridx = 3;
        panel.add(txtPrecio, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        JButton btnActualizar = crearBoton("Actualizar", new Color(52, 152, 219));
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        btnAgregar.addActionListener(e -> agregarServicio());
        btnActualizar.addActionListener(e -> actualizarServicio());
        btnEliminar.addActionListener(e -> eliminarServicio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 4;
        panel.add(panelBotones, gbc);
        
        // Crear panel contenedor para formulario (arriba de la tabla)
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.NORTH);
        add(contenedor, BorderLayout.NORTH);
    }
    
    // Crea un botón con estilo
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }
    
    // Crea la tabla para mostrar el catálogo de servicios
    private void crearTabla() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio ($)"};
        // Crear modelo de tabla no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Crear la tabla con el modelo
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        // Estilizar el encabezado
        tabla.getTableHeader().setBackground(new Color(142, 68, 173));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Listener para cargar datos al seleccionar
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarServicioSeleccionado();
            }
        });
        
        // Agregar la tabla dentro de un scroll pane
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Catálogo de Servicios"));
        add(scroll, BorderLayout.CENTER);
    }
    
    // Carga todos los servicios en la tabla
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
    
    // Carga el servicio seleccionado en el formulario
    private void cargarServicioSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtDescripcion.setText(tabla.getValueAt(fila, 2).toString());
            // Quitar el símbolo $ del precio
            String precio = tabla.getValueAt(fila, 3).toString().replace("$", "");
            txtPrecio.setText(precio);
            txtId.setEditable(false);
        }
    }
    
    // Agrega un nuevo servicio
    private void agregarServicio() {
        if (validarCampos()) {
            try {
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                boolean exito = controller.agregarServicio(
                    txtId.getText().trim(),
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    precio
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Servicio agregado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: El ID ya existe", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Actualiza un servicio existente
    private void actualizarServicio() {
        if (validarCampos()) {
            try {
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                boolean exito = controller.actualizarServicio(
                    txtId.getText().trim(),
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    precio
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Servicio actualizado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Servicio no encontrado", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Elimina un servicio
    private void eliminarServicio() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este servicio?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controller.eliminarServicio(txtId.getText().trim());
            if (exito) {
                JOptionPane.showMessageDialog(this, "Servicio eliminado", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Valida que los campos estén llenos
    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtDescripcion.getText().trim().isEmpty() ||
            txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    // Limpia los campos del formulario
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtId.setEditable(true);
        tabla.clearSelection();
    }
}
