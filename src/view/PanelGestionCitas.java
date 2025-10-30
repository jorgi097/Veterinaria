package view;

import controller.ClinicaController;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;

// Panel de interfaz gráfica para gestionar citas médicas
// Permite agendar, cancelar, completar y visualizar citas en una tabla
public class PanelGestionCitas extends JPanel {
    
    // Referencia al controlador para acceder a la lógica de negocio
    private final ClinicaController controller;
    // Tabla para mostrar la lista de citas
    private JTable tabla;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    // Campos de texto del formulario
    private JTextField txtId, txtFecha, txtMotivo, txtIdMascota, txtIdDueno, txtIdVeterinario;
    
    // Constructor del panel
    public PanelGestionCitas(ClinicaController controller) {
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
        // Color rojo para el encabezado
        panel.setBackground(new Color(231, 76, 60));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Título del panel
        JLabel lbl = new JLabel("Gestión de Citas Médicas");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    // Crea el formulario para ingresar datos de la cita
    private void crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Cita"));
        
        // Configuración del layout GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir horizontalmente
        
        // Inicializar los campos de texto
        txtId = new JTextField(12);
        txtFecha = new JTextField(12);
        txtMotivo = new JTextField(12);
        txtIdMascota = new JTextField(12);
        txtIdDueno = new JTextField(12);
        txtIdVeterinario = new JTextField(12);
        
        // Posicionar los componentes en el GridBag
        // Fila 1: ID de la cita y Fecha/Hora
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Cita:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Fecha (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 3;
        panel.add(txtFecha, gbc);
        
        // Fila 2: Motivo e ID de la Mascota
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMotivo, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("ID Mascota:"), gbc);
        gbc.gridx = 3;
        panel.add(txtIdMascota, gbc);
        
        // Fila 3: ID del Dueño e ID del Veterinario
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("ID Dueño:"), gbc);
        gbc.gridx = 1;
        panel.add(txtIdDueno, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("ID Veterinario:"), gbc);
        gbc.gridx = 3;
        panel.add(txtIdVeterinario, gbc);
        
        // Panel con los botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        // Crear botones con colores distintivos
        JButton btnAgregar = crearBoton("Agendar", new Color(46, 204, 113)); // Verde
        JButton btnCancelar = crearBoton("Cancelar Cita", new Color(231, 76, 60)); // Rojo
        JButton btnCompletar = crearBoton("Completar", new Color(52, 152, 219)); // Azul
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166)); // Gris
        
        // Asociar acciones a los botones
        btnAgregar.addActionListener(e -> agregar());
        btnCancelar.addActionListener(e -> cancelar());
        btnCompletar.addActionListener(e -> completar());
        btnLimpiar.addActionListener(e -> limpiar());
        
        // Agregar botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnCompletar);
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
    
    // Crea la tabla para mostrar la lista de citas
    private void crearTabla() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Fecha/Hora", "Motivo", "Mascota", "Dueño", "Veterinario", "Estado"};
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
        tabla.getTableHeader().setBackground(new Color(231, 76, 60));
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
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Citas"));
        add(scroll, BorderLayout.CENTER);
    }
    
    // Carga todas las citas en la tabla
    private void cargarDatos() {
        // Limpiar las filas existentes
        modeloTabla.setRowCount(0);
        // Obtener la lista de citas del controlador
        List<Cita> citas = controller.listarCitas();
        // Formateador para mostrar la fecha en formato legible
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        // Agregar cada cita como una fila en la tabla
        for (Cita cita : citas) {
            modeloTabla.addRow(new Object[]{
                cita.getId(),
                cita.getFechaHora().format(formatter), // Formatear la fecha
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getDueno().getNombre(),
                cita.getVeterinario().getNombre(),
                cita.getEstado()
            });
        }
    }
    
    // Carga los datos de la cita seleccionada en el formulario
    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            // Llenar los campos de texto con los datos de la fila seleccionada
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtFecha.setText(tabla.getValueAt(fila, 1).toString());
            txtMotivo.setText(tabla.getValueAt(fila, 2).toString());
            // Nota: IDs de mascota, dueño y veterinario no se muestran en la tabla
            // por lo que no se cargan automáticamente
        }
    }
    
    // Agenda una nueva cita en el sistema
    private void agregar() {
        // Validar que todos los campos estén llenos
        if (validar()) {
            try {
                // Parsear la fecha ingresada
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime fechaHora = LocalDateTime.parse(txtFecha.getText().trim(), formatter);
                
                // Intentar agendar la cita
                boolean exito = controller.agendarCita(
                    txtId.getText().trim(),
                    fechaHora,
                    txtMotivo.getText().trim(),
                    txtIdMascota.getText().trim(),
                    txtIdDueno.getText().trim(),
                    txtIdVeterinario.getText().trim()
                );
                
                // Mostrar mensaje de resultado
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cita agendada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarDatos(); // Actualizar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Conflicto de horario o datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                // Error en el formato de fecha
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use: dd/MM/yyyy HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Cancela la cita seleccionada
    private void cancelar() {
        // Verificar que hay un ID para cancelar
        if (!txtId.getText().trim().isEmpty()) {
            // Cancelar la cita en el controlador
            controller.cancelarCita(txtId.getText().trim());
            JOptionPane.showMessageDialog(this, "Cita cancelada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos(); // Actualizar la tabla
        }
    }
    
    // Marca la cita seleccionada como completada
    private void completar() {
        // Verificar que hay un ID para completar
        if (!txtId.getText().trim().isEmpty()) {
            // Completar la cita en el controlador
            controller.completarCita(txtId.getText().trim());
            JOptionPane.showMessageDialog(this, "Cita completada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos(); // Actualizar la tabla
        }
    }
    
    // Valida que todos los campos del formulario estén llenos
    private boolean validar() {
        return !txtId.getText().trim().isEmpty() &&
               !txtFecha.getText().trim().isEmpty() &&
               !txtMotivo.getText().trim().isEmpty() &&
               !txtIdMascota.getText().trim().isEmpty() &&
               !txtIdDueno.getText().trim().isEmpty() &&
               !txtIdVeterinario.getText().trim().isEmpty();
    }
    
    // Limpia todos los campos del formulario
    private void limpiar() {
        txtId.setText("");
        txtFecha.setText("");
        txtMotivo.setText("");
        txtIdMascota.setText("");
        txtIdDueno.setText("");
        txtIdVeterinario.setText("");
        tabla.clearSelection(); // Quitar selección de la tabla
    }
}
