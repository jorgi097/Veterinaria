package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Producto;

// Panel de interfaz gráfica para gestionar el inventario de productos
// Permite agregar, actualizar, eliminar y visualizar productos en una tabla
// Incluye alertas de stock bajo
public class PanelGestionInventario extends JPanel {
    
    // Referencia al controlador para acceder a la lógica de negocio
    private final ClinicaController controller;
    // Tabla para mostrar la lista de productos
    private JTable tabla;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    // Campos de texto del formulario
    private JTextField txtId, txtNombre, txtCategoria, txtCantidad, txtCantidadMinima, txtPrecio;
    
    // Constructor del panel
    public PanelGestionInventario(ClinicaController controller) {
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
        // Cargar los datos iniciales en la tabla
        cargarDatos();
        
        // Mostrar alertas de stock bajo si existen
        mostrarAlertasStockBajo();
    }
    
    // Crea el panel superior con el título
    private void crearPanelSuperior() {
        JPanel panel = new JPanel();
        // Color amarillo/mostaza para el encabezado del inventario
        panel.setBackground(new Color(243, 156, 18));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Título del panel
        JLabel lbl = new JLabel("Gestión de Inventario");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea el formulario para ingresar datos del producto
    private void crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        
        // Configuración del layout GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Inicializar los campos de texto
        txtId = new JTextField(12);
        txtNombre = new JTextField(12);
        txtCategoria = new JTextField(12);
        txtCantidad = new JTextField(12);
        txtCantidadMinima = new JTextField(12);
        txtPrecio = new JTextField(12);
        
        // Fila 1: ID y Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panel.add(txtNombre, gbc);
        
        // Fila 2: Categoría y Cantidad
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCategoria, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 3;
        panel.add(txtCantidad, gbc);
        
        // Fila 3: Cantidad Mínima y Precio
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Cant. Mínima:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCantidadMinima, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 3;
        panel.add(txtPrecio, gbc);
        
        // Panel con los botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        // Crear botones con colores distintivos
        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113)); // Verde
        JButton btnActualizar = crearBoton("Actualizar", new Color(52, 152, 219)); // Azul
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60)); // Rojo
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166)); // Gris
        JButton btnVerAlertas = crearBoton("Ver Alertas", new Color(230, 126, 34)); // Naranja
        
        // Asociar acciones a los botones
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnVerAlertas.addActionListener(e -> mostrarAlertasStockBajo());
        
        // Agregar botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVerAlertas);
        
        // Agregar panel de botones al formulario
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        panel.add(panelBotones, gbc);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea un botón con estilo personalizado
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }
    
    // Crea la tabla para mostrar la lista de productos
    private void crearTabla() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Categoría", "Cantidad", "Cant. Mínima", "Precio", "Estado"};
        // Crear modelo de tabla no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Crear la tabla con el modelo
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        // Estilizar el encabezado
        tabla.getTableHeader().setBackground(new Color(243, 156, 18));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Listener para cargar datos al seleccionar una fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarSeleccionado();
            }
        });
        
        // Agregar la tabla dentro de un scroll pane
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
        add(scroll, BorderLayout.CENTER);
    }
    
    // Carga todos los productos en la tabla
    private void cargarDatos() {
        // Limpiar las filas existentes
        modeloTabla.setRowCount(0);
        // Obtener la lista de productos del controlador
        List<Producto> productos = controller.listarProductos();
        
        // Agregar cada producto como una fila en la tabla
        for (Producto prod : productos) {
            String estado = prod.tieneStockBajo() ? "⚠️ BAJO" : "OK";
            modeloTabla.addRow(new Object[]{
                prod.getId(),
                prod.getNombre(),
                prod.getCategoria(),
                prod.getCantidad(),
                prod.getCantidadMinima(),
                String.format("$%.2f", prod.getPrecio()),
                estado
            });
        }
    }
    
    // Carga los datos del producto seleccionado en el formulario
    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtCategoria.setText(tabla.getValueAt(fila, 2).toString());
            txtCantidad.setText(tabla.getValueAt(fila, 3).toString());
            txtCantidadMinima.setText(tabla.getValueAt(fila, 4).toString());
            // Quitar el símbolo $ del precio
            String precio = tabla.getValueAt(fila, 5).toString().replace("$", "");
            txtPrecio.setText(precio);
        }
    }
    
    // Agrega un nuevo producto al inventario
    private void agregar() {
        if (validar()) {
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                int cantidadMinima = Integer.parseInt(txtCantidadMinima.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                
                boolean exito = controller.registrarProducto(
                    txtId.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCategoria.getText().trim(),
                    cantidad,
                    cantidadMinima,
                    precio
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto registrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "ID ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cantidad, Cant. Mínima y Precio deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Actualiza un producto existente
    private void actualizar() {
        if (validar()) {
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                int cantidadMinima = Integer.parseInt(txtCantidadMinima.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                
                boolean exito = controller.actualizarProducto(
                    txtId.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCategoria.getText().trim(),
                    cantidad,
                    cantidadMinima,
                    precio
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cantidad, Cant. Mínima y Precio deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Elimina el producto seleccionado
    private void eliminar() {
        if (!txtId.getText().trim().isEmpty()) {
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                controller.eliminarProducto(txtId.getText().trim());
                limpiar();
                cargarDatos();
            }
        }
    }
    
    // Muestra las alertas de stock bajo
    private void mostrarAlertasStockBajo() {
        List<Producto> productosConStockBajo = controller.obtenerProductosConStockBajo();
        
        if (productosConStockBajo.isEmpty()) {
            // No hay alertas, no mostrar nada
            return;
        }
        
        // Construir mensaje de alerta
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("⚠️ ALERTA DE STOCK BAJO ⚠️\n\n");
        mensaje.append("Los siguientes productos necesitan reabastecimiento:\n\n");
        
        for (Producto prod : productosConStockBajo) {
            mensaje.append("• ").append(prod.getNombre())
                   .append(" (").append(prod.getCategoria()).append(")")
                   .append(" - Cantidad actual: ").append(prod.getCantidad())
                   .append(" / Mínimo: ").append(prod.getCantidadMinima())
                   .append("\n");
        }
        
        // Mostrar popup de alerta
        JOptionPane.showMessageDialog(this, 
            mensaje.toString(), 
            "Alerta de Inventario", 
            JOptionPane.WARNING_MESSAGE);
    }
    
    // Valida que todos los campos del formulario estén llenos
    private boolean validar() {
        return !txtId.getText().trim().isEmpty() &&
               !txtNombre.getText().trim().isEmpty() &&
               !txtCategoria.getText().trim().isEmpty() &&
               !txtCantidad.getText().trim().isEmpty() &&
               !txtCantidadMinima.getText().trim().isEmpty() &&
               !txtPrecio.getText().trim().isEmpty();
    }
    
    // Limpia todos los campos del formulario
    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtCantidad.setText("");
        txtCantidadMinima.setText("");
        txtPrecio.setText("");
        tabla.clearSelection();
    }
}
