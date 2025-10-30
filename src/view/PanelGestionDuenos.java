package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Dueno;

// Panel de interfaz gráfica para gestionar dueños de mascotas
// Permite agregar, actualizar, eliminar y visualizar dueños en una tabla
public class PanelGestionDuenos extends JPanel {
    
    // Referencia al controlador para acceder a la lógica de negocio
    private final ClinicaController controller;
    // Tabla para mostrar la lista de dueños
    private JTable tablaDuenos;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    
    // Campos de texto del formulario
    private JTextField txtId, txtNombre, txtDireccion, txtTelefono, txtCorreo;
    // Botones de acción del formulario
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    
    // Constructor del panel
    public PanelGestionDuenos(ClinicaController controller) {
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
    }
    
    // Crea el panel superior con el título
    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel();
        // Color azul para el encabezado
        panelSuperior.setBackground(new Color(52, 152, 219));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Título del panel
        JLabel lblTitulo = new JLabel("Gestión de Dueños");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    // Crea el formulario para ingresar y editar datos del dueño
    private void crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        // Borde compuesto con título y espaciado interno
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Dueño"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Configuración del layout GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir horizontalmente
        
        // Inicializar los campos de texto
        txtId = new JTextField(15);
        txtNombre = new JTextField(15);
        txtDireccion = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtCorreo = new JTextField(15);
        
        // Posicionar los campos en el GridBag
        // ID - columna izquierda
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtId, gbc);
        
        // Nombre - columna izquierda
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        
        // Dirección - columna izquierda
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);
        
        // Teléfono - columna derecha
        gbc.gridx = 2; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtTelefono, gbc);
        
        // Correo - columna derecha
        gbc.gridx = 2; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtCorreo, gbc);
        
        // Panel con los botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        // Crear botones con colores distintivos
        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113)); // Verde
        btnActualizar = crearBoton("Actualizar", new Color(52, 152, 219)); // Azul
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60)); // Rojo
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166)); // Gris
        
        // Asociar acciones a los botones
        btnAgregar.addActionListener(e -> agregarDueno());
        btnActualizar.addActionListener(e -> actualizarDueno());
        btnEliminar.addActionListener(e -> eliminarDueno());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        // Agregar botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // Agregar panel de botones al formulario
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4; // Ocupar todas las columnas
        panelFormulario.add(panelBotones, gbc);
        
        add(panelFormulario, BorderLayout.NORTH);
    }
    
    // Crea un botón con estilo personalizado
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false); // Quitar borde de foco
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar cursor al pasar sobre el botón
        return boton;
    }
    
    // Crea la tabla para mostrar la lista de dueños
    private void crearTabla() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Dirección", "Teléfono", "Correo", "# Mascotas"};
        // Crear modelo de tabla no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas no editables
            }
        };
        
        // Crear la tabla con el modelo
        tablaDuenos = new JTable(modeloTabla);
        tablaDuenos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo una fila seleccionable
        tablaDuenos.setRowHeight(25); // Altura de las filas
        // Estilizar el encabezado
        tablaDuenos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaDuenos.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaDuenos.getTableHeader().setForeground(Color.WHITE);
        
        // Listener para cargar datos al seleccionar una fila
        tablaDuenos.getSelectionModel().addListSelectionListener(e -> {
            // Evitar eventos duplicados y verificar que hay una selección
            if (!e.getValueIsAdjusting() && tablaDuenos.getSelectedRow() != -1) {
                cargarDuenoSeleccionado();
            }
        });
        
        // Agregar la tabla dentro de un scroll pane
        JScrollPane scrollPane = new JScrollPane(tablaDuenos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Dueños"));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Carga todos los dueños en la tabla
    private void cargarDatos() {
        // Limpiar las filas existentes
        modeloTabla.setRowCount(0);
        // Obtener la lista de dueños del controlador
        List<Dueno> duenos = controller.listarDuenos();
        
        // Agregar cada dueño como una fila en la tabla
        for (Dueno dueno : duenos) {
            Object[] fila = {
                dueno.getId(),
                dueno.getNombre(),
                dueno.getDireccion(),
                dueno.getTelefono(),
                dueno.getCorreo(),
                dueno.getMascotas().size() // Cantidad de mascotas del dueño
            };
            modeloTabla.addRow(fila);
        }
    }
    
    // Carga los datos del dueño seleccionado en el formulario
    private void cargarDuenoSeleccionado() {
        int fila = tablaDuenos.getSelectedRow();
        if (fila != -1) {
            // Llenar los campos de texto con los datos de la fila seleccionada
            txtId.setText(tablaDuenos.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaDuenos.getValueAt(fila, 1).toString());
            txtDireccion.setText(tablaDuenos.getValueAt(fila, 2).toString());
            txtTelefono.setText(tablaDuenos.getValueAt(fila, 3).toString());
            txtCorreo.setText(tablaDuenos.getValueAt(fila, 4).toString());
            txtId.setEditable(false); // El ID no se puede editar una vez cargado
        }
    }
    
    // Agrega un nuevo dueño al sistema
    private void agregarDueno() {
        // Validar que todos los campos estén llenos
        if (validarCampos()) {
            // Intentar registrar el dueño
            boolean exito = controller.registrarDueno(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
            );
            
            // Mostrar mensaje de resultado
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Dueño registrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: El ID ya existe", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Actualiza los datos del dueño seleccionado
    private void actualizarDueno() {
        // Validar que todos los campos estén llenos
        if (validarCampos()) {
            // Intentar actualizar el dueño
            boolean exito = controller.actualizarDueno(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
            );
            
            // Mostrar mensaje de resultado
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Dueño actualizado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: Dueño no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Elimina el dueño seleccionado
    private void eliminarDueno() {
        // Verificar que hay un ID para eliminar
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un dueño para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar la eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este dueño?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar del sistema
            boolean exito = controller.eliminarDueno(txtId.getText().trim());
            
            // Mostrar mensaje de resultado
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Dueño eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: No se puede eliminar (puede tener mascotas asociadas)", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Valida que todos los campos del formulario estén llenos
    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtDireccion.getText().trim().isEmpty() ||
            txtTelefono.getText().trim().isEmpty() ||
            txtCorreo.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    // Limpia todos los campos del formulario
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtId.setEditable(true); // Volver a habilitar la edición del ID
        tablaDuenos.clearSelection(); // Quitar selección de la tabla
    }
}
