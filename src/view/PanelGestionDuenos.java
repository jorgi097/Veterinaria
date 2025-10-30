package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Dueno;

/**
 * Panel para gestionar dueños con tabla y formulario
 */
public class PanelGestionDuenos extends JPanel {
    
    private final ClinicaController controller;
    private JTable tablaDuenos;
    private DefaultTableModel modeloTabla;
    
    // Campos del formulario
    private JTextField txtId, txtNombre, txtDireccion, txtTelefono, txtCorreo;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    
    public PanelGestionDuenos(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(236, 240, 241));
        
        crearPanelSuperior();
        crearPanelFormulario();
        crearTabla();
        
        cargarDatos();
    }
    
    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(52, 152, 219));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lblTitulo = new JLabel("Gestión de Dueños");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    private void crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Dueño"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos del formulario
        txtId = new JTextField(15);
        txtNombre = new JTextField(15);
        txtDireccion = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtCorreo = new JTextField(15);
        
        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtId, gbc);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        
        // Dirección
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);
        
        // Teléfono
        gbc.gridx = 2; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtTelefono, gbc);
        
        // Correo
        gbc.gridx = 2; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtCorreo, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        btnActualizar = crearBoton("Actualizar", new Color(52, 152, 219));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        btnAgregar.addActionListener(e -> agregarDueno());
        btnActualizar.addActionListener(e -> actualizarDueno());
        btnEliminar.addActionListener(e -> eliminarDueno());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        panelFormulario.add(panelBotones, gbc);
        
        add(panelFormulario, BorderLayout.NORTH);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
    
    private void crearTabla() {
        String[] columnas = {"ID", "Nombre", "Dirección", "Teléfono", "Correo", "# Mascotas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaDuenos = new JTable(modeloTabla);
        tablaDuenos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDuenos.setRowHeight(25);
        tablaDuenos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaDuenos.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaDuenos.getTableHeader().setForeground(Color.WHITE);
        
        // Listener para cargar datos al seleccionar
        tablaDuenos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaDuenos.getSelectedRow() != -1) {
                cargarDuenoSeleccionado();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaDuenos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Dueños"));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Dueno> duenos = controller.listarDuenos();
        
        for (Dueno dueno : duenos) {
            Object[] fila = {
                dueno.getId(),
                dueno.getNombre(),
                dueno.getDireccion(),
                dueno.getTelefono(),
                dueno.getCorreo(),
                dueno.getMascotas().size()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void cargarDuenoSeleccionado() {
        int fila = tablaDuenos.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tablaDuenos.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaDuenos.getValueAt(fila, 1).toString());
            txtDireccion.setText(tablaDuenos.getValueAt(fila, 2).toString());
            txtTelefono.setText(tablaDuenos.getValueAt(fila, 3).toString());
            txtCorreo.setText(tablaDuenos.getValueAt(fila, 4).toString());
            txtId.setEditable(false);
        }
    }
    
    private void agregarDueno() {
        if (validarCampos()) {
            boolean exito = controller.registrarDueno(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
            );
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Dueño registrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: El ID ya existe", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarDueno() {
        if (validarCampos()) {
            boolean exito = controller.actualizarDueno(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
            );
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Dueño actualizado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: Dueño no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarDueno() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un dueño para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este dueño?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controller.eliminarDueno(txtId.getText().trim());
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Dueño eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: No se puede eliminar (puede tener mascotas asociadas)", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
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
    
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtId.setEditable(true);
        tablaDuenos.clearSelection();
    }
}
