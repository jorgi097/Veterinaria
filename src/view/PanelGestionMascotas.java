package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Mascota;

/**
 * Panel para gestionar mascotas con tabla y formulario
 */
public class PanelGestionMascotas extends JPanel {
    
    private final ClinicaController controller;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    
    // Campos del formulario
    private JTextField txtId, txtNombre, txtEspecie, txtRaza, txtEdad, txtPeso, txtIdDueno;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnHistorial;
    
    public PanelGestionMascotas(ClinicaController controller) {
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
        panelSuperior.setBackground(new Color(155, 89, 182));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lblTitulo = new JLabel("Gestión de Mascotas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    private void crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos de la Mascota"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos del formulario
        txtId = new JTextField(12);
        txtNombre = new JTextField(12);
        txtEspecie = new JTextField(12);
        txtRaza = new JTextField(12);
        txtEdad = new JTextField(12);
        txtPeso = new JTextField(12);
        txtIdDueno = new JTextField(12);
        
        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtId, gbc);
        
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtNombre, gbc);
        
        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Especie:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEspecie, gbc);
        
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Raza:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtRaza, gbc);
        
        // Fila 3
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEdad, gbc);
        
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtPeso, gbc);
        
        // Fila 4
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("ID Dueño:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtIdDueno, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        btnActualizar = crearBoton("Actualizar", new Color(52, 152, 219));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnHistorial = crearBoton("Historial", new Color(243, 156, 18));
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        btnAgregar.addActionListener(e -> agregarMascota());
        btnActualizar.addActionListener(e -> actualizarMascota());
        btnEliminar.addActionListener(e -> eliminarMascota());
        btnHistorial.addActionListener(e -> gestionarHistorial());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnLimpiar);
        
        gbc.gridx = 0; gbc.gridy = 4;
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
        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Edad", "Peso", "Dueño"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaMascotas = new JTable(modeloTabla);
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMascotas.setRowHeight(25);
        tablaMascotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaMascotas.getTableHeader().setBackground(new Color(155, 89, 182));
        tablaMascotas.getTableHeader().setForeground(Color.WHITE);
        
        // Listener para cargar datos al seleccionar
        tablaMascotas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaMascotas.getSelectedRow() != -1) {
                cargarMascotaSeleccionada();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaMascotas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Mascotas"));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Mascota> mascotas = controller.listarMascotas();
        
        for (Mascota mascota : mascotas) {
            Object[] fila = {
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getEdad(),
                mascota.getPeso(),
                mascota.getDueno() != null ? mascota.getDueno().getNombre() : "N/A"
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void cargarMascotaSeleccionada() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila != -1) {
            String id = tablaMascotas.getValueAt(fila, 0).toString();
            Mascota mascota = controller.obtenerMascota(id);
            
            if (mascota != null) {
                txtId.setText(mascota.getId());
                txtNombre.setText(mascota.getNombre());
                txtEspecie.setText(mascota.getEspecie());
                txtRaza.setText(mascota.getRaza());
                txtEdad.setText(String.valueOf(mascota.getEdad()));
                txtPeso.setText(String.valueOf(mascota.getPeso()));
                txtIdDueno.setText(mascota.getDueno().getId());
                txtId.setEditable(false);
            }
        }
    }
    
    private void agregarMascota() {
        if (validarCampos()) {
            try {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                double peso = Double.parseDouble(txtPeso.getText().trim());
                
                boolean exito = controller.registrarMascota(
                    txtId.getText().trim(),
                    txtNombre.getText().trim(),
                    txtEspecie.getText().trim(),
                    txtRaza.getText().trim(),
                    edad,
                    peso,
                    txtIdDueno.getText().trim()
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                        "Mascota registrada exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error: ID duplicado o dueño no encontrado", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Edad y Peso deben ser números válidos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarMascota() {
        if (validarCampos()) {
            try {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                double peso = Double.parseDouble(txtPeso.getText().trim());
                
                boolean exito = controller.actualizarMascota(
                    txtId.getText().trim(),
                    txtNombre.getText().trim(),
                    txtEspecie.getText().trim(),
                    txtRaza.getText().trim(),
                    edad,
                    peso
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                        "Mascota actualizada exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Mascota no encontrada", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Edad y Peso deben ser números válidos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarMascota() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una mascota para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar esta mascota?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controller.eliminarMascota(txtId.getText().trim());
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Mascota eliminada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: Mascota no encontrada", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void gestionarHistorial() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una mascota", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Mascota mascota = controller.obtenerMascota(txtId.getText().trim());
        if (mascota != null) {
            DialogoHistorialClinico dialogo = new DialogoHistorialClinico(
                (Frame) SwingUtilities.getWindowAncestor(this), 
                mascota, 
                controller
            );
            dialogo.setVisible(true);
        }
    }
    
    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtEspecie.getText().trim().isEmpty() ||
            txtRaza.getText().trim().isEmpty() ||
            txtEdad.getText().trim().isEmpty() ||
            txtPeso.getText().trim().isEmpty() ||
            txtIdDueno.getText().trim().isEmpty()) {
            
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
        txtEspecie.setText("");
        txtRaza.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtIdDueno.setText("");
        txtId.setEditable(true);
        tablaMascotas.clearSelection();
    }
}

/**
 * Diálogo para gestionar el historial clínico
 */
class DialogoHistorialClinico extends JDialog {
    
    private final Mascota mascota;
    private final ClinicaController controller;
    private JTextArea areaHistorial;
    
    public DialogoHistorialClinico(Frame padre, Mascota mascota, ClinicaController controller) {
        super(padre, "Historial Clínico - " + mascota.getNombre(), true);
        this.mascota = mascota;
        this.controller = controller;
        
        setSize(600, 400);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout(10, 10));
        
        crearComponentes();
    }
    
    private void crearComponentes() {
        // Panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(243, 156, 18));
        JLabel lblTitulo = new JLabel("Historial Clínico");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Área de texto con historial
        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaHistorial.setLineWrap(true);
        areaHistorial.setWrapStyleWord(true);
        
        // Cargar historial
        StringBuilder sb = new StringBuilder();
        List<String> historial = mascota.getHistorialClinico();
        if (historial.isEmpty()) {
            sb.append("No hay registros en el historial clínico.");
        } else {
            for (String registro : historial) {
                sb.append(registro).append("\n\n");
            }
        }
        areaHistorial.setText(sb.toString());
        
        JScrollPane scrollPane = new JScrollPane(areaHistorial);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton btnAgregar = new JButton("Agregar Registro");
        btnAgregar.setBackground(new Color(46, 204, 113));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> agregarRegistro());
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(149, 165, 166));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void agregarRegistro() {
        String registro = JOptionPane.showInputDialog(this, 
            "Ingrese el registro médico:", 
            "Nuevo Registro", 
            JOptionPane.PLAIN_MESSAGE);
        
        if (registro != null && !registro.trim().isEmpty()) {
            String registroCompleto = java.time.LocalDate.now() + " - " + registro;
            controller.agregarHistorialClinico(mascota.getId(), registroCompleto);
            
            // Actualizar área de texto
            areaHistorial.append(registroCompleto + "\n\n");
            
            JOptionPane.showMessageDialog(this, 
                "Registro agregado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
