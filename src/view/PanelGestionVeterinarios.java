package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Veterinario;

// Panel de interfaz gráfica para gestionar veterinarios
// Permite agregar, eliminar y visualizar veterinarios en una tabla
public class PanelGestionVeterinarios extends JPanel {
    
    // Referencia al controlador para acceder a la lógica de negocio
    private final ClinicaController controller;
    // Tabla para mostrar la lista de veterinarios
    private JTable tabla;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    // Campos de texto del formulario
    private JTextField txtId, txtNombre, txtCorreo, txtTelefono, txtHorario, txtEspecialidad;
    
    // Constructor del panel
    public PanelGestionVeterinarios(ClinicaController controller) {
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
        JPanel panel = new JPanel();
        // Color naranja para el encabezado
        panel.setBackground(new Color(230, 126, 34));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Título del panel
        JLabel lbl = new JLabel("Gestión de Veterinarios");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea el formulario para ingresar datos del veterinario
    private void crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Veterinario"));
        
        // Configuración del layout GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir horizontalmente
        
        // Inicializar los campos de texto
        txtId = new JTextField(12);
        txtNombre = new JTextField(12);
        txtCorreo = new JTextField(12);
        txtTelefono = new JTextField(12);
        txtHorario = new JTextField(12);
        txtEspecialidad = new JTextField(12);
        
        // Posicionar los componentes en el GridBag
        // Fila 1: ID y Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panel.add(txtNombre, gbc);
        
        // Fila 2: Correo y Teléfono
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCorreo, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 3;
        panel.add(txtTelefono, gbc);
        
        // Fila 3: Horario y Especialidad
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Horario:"), gbc);
        gbc.gridx = 1;
        panel.add(txtHorario, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Especialidad:"), gbc);
        gbc.gridx = 3;
        panel.add(txtEspecialidad, gbc);
        
        // Panel con los botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        // Crear botones con colores distintivos
        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113)); // Verde
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60)); // Rojo
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166)); // Gris
        
        // Asociar acciones a los botones
        btnAgregar.addActionListener(e -> agregar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        
        // Agregar botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // Agregar panel de botones al formulario
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4; // Ocupar todas las columnas
        panel.add(panelBotones, gbc);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea un botón con estilo personalizado
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); // Quitar borde de foco
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }
    
    // Crea la tabla para mostrar la lista de veterinarios
    private void crearTabla() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Correo", "Teléfono", "Horario", "Especialidad"};
        // Crear modelo de tabla no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas no editables
            }
        };
        
        // Crear la tabla con el modelo
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25); // Altura de las filas
        // Estilizar el encabezado
        tabla.getTableHeader().setBackground(new Color(230, 126, 34));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Listener para cargar datos al seleccionar una fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            // Evitar eventos duplicados y verificar que hay una selección
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarSeleccionado();
            }
        });
        
        // Agregar la tabla dentro de un scroll pane
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Veterinarios"));
        add(scroll, BorderLayout.CENTER);
    }
    
    // Carga todos los veterinarios en la tabla
    private void cargarDatos() {
        // Limpiar las filas existentes
        modeloTabla.setRowCount(0);
        // Obtener la lista de veterinarios del controlador
        List<Veterinario> veterinarios = controller.listarVeterinarios();
        
        // Agregar cada veterinario como una fila en la tabla
        for (Veterinario vet : veterinarios) {
            modeloTabla.addRow(new Object[]{
                vet.getId(),
                vet.getNombre(),
                vet.getCorreo(),
                vet.getTelefono(),
                vet.getHorario(),
                vet.getEspecialidad()
            });
        }
    }
    
    // Carga los datos del veterinario seleccionado en el formulario
    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            // Llenar los campos de texto con los datos de la fila seleccionada
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtCorreo.setText(tabla.getValueAt(fila, 2).toString());
            txtTelefono.setText(tabla.getValueAt(fila, 3).toString());
            txtHorario.setText(tabla.getValueAt(fila, 4).toString());
            txtEspecialidad.setText(tabla.getValueAt(fila, 5).toString());
        }
    }
    
    // Agrega un nuevo veterinario al sistema
    private void agregar() {
        // Validar que todos los campos estén llenos
        if (validar()) {
            // Intentar registrar el veterinario
            boolean exito = controller.registrarVeterinario(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim(),
                txtHorario.getText().trim(),
                txtEspecialidad.getText().trim()
            );
            
            // Mostrar mensaje de resultado
            if (exito) {
                JOptionPane.showMessageDialog(this, "Veterinario registrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                cargarDatos(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(this, "ID ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Elimina el veterinario seleccionado
    private void eliminar() {
        // Verificar que hay un ID para eliminar
        if (!txtId.getText().trim().isEmpty()) {
            // Confirmar la eliminación
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar veterinario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                // Eliminar del sistema
                controller.eliminarEmpleado(txtId.getText().trim());
                limpiar();
                cargarDatos(); // Actualizar la tabla
            }
        }
    }
    
    // Valida que todos los campos del formulario estén llenos
    private boolean validar() {
        return !txtId.getText().trim().isEmpty() &&
               !txtNombre.getText().trim().isEmpty() &&
               !txtCorreo.getText().trim().isEmpty() &&
               !txtTelefono.getText().trim().isEmpty() &&
               !txtHorario.getText().trim().isEmpty() &&
               !txtEspecialidad.getText().trim().isEmpty();
    }
    
    // Limpia todos los campos del formulario
    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtHorario.setText("");
        txtEspecialidad.setText("");
        tabla.clearSelection(); // Quitar selección de la tabla
    }
}
