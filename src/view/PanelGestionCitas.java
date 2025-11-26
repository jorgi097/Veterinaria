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
// Incluye gestión de servicios realizados para facturación
public class PanelGestionCitas extends JPanel {
    
    // Referencia al controlador para acceder a la lógica de negocio
    private final ClinicaController controller;
    // Tabla para mostrar la lista de citas
    private JTable tabla;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    // Campos de texto del formulario
    private JTextField txtId, txtFecha, txtMotivo, txtIdMascota, txtIdDueno, txtIdVeterinario;
    
    // Componentes para servicios de la cita
    private JTable tablaServicios;
    private DefaultTableModel modeloServicios;
    private JComboBox<String> comboServicios;
    private JLabel lblTotal;
    
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
        crearPanelCentral();
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
    
    // Crea el panel central con formulario, tabla y servicios
    private void crearPanelCentral() {
        // Panel principal con división izquierda/derecha
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.6);
        
        // Panel izquierdo: Formulario y tabla de citas
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.add(crearPanelFormulario(), BorderLayout.NORTH);
        panelIzquierdo.add(crearTablaCitas(), BorderLayout.CENTER);
        
        // Panel derecho: Servicios de la cita
        JPanel panelDerecho = crearPanelServicios();
        
        splitPane.setLeftComponent(panelIzquierdo);
        splitPane.setRightComponent(panelDerecho);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    // Crea el formulario para ingresar datos de la cita
    private JPanel crearPanelFormulario() {
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
        
        return panel;
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
    private JScrollPane crearTablaCitas() {
        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Fecha/Hora", "Motivo", "Mascota", "Dueño", "Veterinario", "Estado", "Total"};
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
        return scroll;
    }
    
    // Crea el panel de servicios para la cita seleccionada
    private JPanel crearPanelServicios() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Servicios de la Cita"));
        
        // Panel superior: ComboBox y botón para agregar servicio
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAgregar.setBackground(Color.WHITE);
        
        comboServicios = new JComboBox<>();
        actualizarComboServicios();
        
        JButton btnAgregarServicio = crearBoton("Agregar", new Color(46, 204, 113));
        JButton btnQuitarServicio = crearBoton("Quitar", new Color(231, 76, 60));
        
        btnAgregarServicio.addActionListener(e -> agregarServicioACita());
        btnQuitarServicio.addActionListener(e -> quitarServicioDeCita());
        
        panelAgregar.add(new JLabel("Servicio:"));
        panelAgregar.add(comboServicios);
        panelAgregar.add(btnAgregarServicio);
        panelAgregar.add(btnQuitarServicio);
        
        panel.add(panelAgregar, BorderLayout.NORTH);
        
        // Tabla de servicios de la cita
        String[] columnas = {"ID", "Servicio", "Precio"};
        modeloServicios = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaServicios = new JTable(modeloServicios);
        tablaServicios.setRowHeight(25);
        tablaServicios.getTableHeader().setBackground(new Color(243, 156, 18));
        tablaServicios.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollServicios = new JScrollPane(tablaServicios);
        panel.add(scrollServicios, BorderLayout.CENTER);
        
        // Panel inferior: Total
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.setBackground(new Color(243, 156, 18));
        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(Color.WHITE);
        panelTotal.add(lblTotal);
        
        panel.add(panelTotal, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Actualiza el combo de servicios disponibles
    private void actualizarComboServicios() {
        comboServicios.removeAllItems();
        for (Servicio s : controller.listarServicios()) {
            comboServicios.addItem(s.getId() + " - " + s.getNombre() + " ($" + s.getPrecio() + ")");
        }
    }
    
    // Agrega un servicio a la cita seleccionada
    private void agregarServicioACita() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita primero", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (comboServicios.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener el ID del servicio del combo
        String seleccion = comboServicios.getSelectedItem().toString();
        String idServicio = seleccion.split(" - ")[0];
        
        boolean exito = controller.agregarServicioACita(txtId.getText().trim(), idServicio);
        
        if (exito) {
            cargarServiciosCita();
            cargarDatos();
            JOptionPane.showMessageDialog(this, "Servicio agregado a la cita", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar servicio", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Quita un servicio de la cita seleccionada
    private void quitarServicioDeCita() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita primero", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int filaServicio = tablaServicios.getSelectedRow();
        if (filaServicio == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String idServicio = tablaServicios.getValueAt(filaServicio, 0).toString();
        boolean exito = controller.eliminarServicioDeCita(txtId.getText().trim(), idServicio);
        
        if (exito) {
            cargarServiciosCita();
            cargarDatos();
            JOptionPane.showMessageDialog(this, "Servicio eliminado de la cita", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Carga los servicios de la cita seleccionada
    private void cargarServiciosCita() {
        modeloServicios.setRowCount(0);
        
        if (txtId.getText().trim().isEmpty()) {
            lblTotal.setText("TOTAL: $0.00");
            return;
        }
        
        Cita cita = controller.obtenerCita(txtId.getText().trim());
        if (cita != null) {
            for (Servicio s : cita.getServiciosRealizados()) {
                modeloServicios.addRow(new Object[]{
                    s.getId(),
                    s.getNombre(),
                    String.format("$%.2f", s.getPrecio())
                });
            }
            lblTotal.setText(String.format("TOTAL: $%.2f", cita.calcularTotal()));
        }
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
                cita.getEstado(),
                String.format("$%.2f", cita.calcularTotal())
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
            
            // Cargar IDs desde la cita real
            Cita cita = controller.obtenerCita(txtId.getText().trim());
            if (cita != null) {
                txtIdMascota.setText(cita.getMascota().getId());
                txtIdDueno.setText(cita.getDueno().getId());
                txtIdVeterinario.setText(cita.getVeterinario().getId());
            }
            
            // Cargar los servicios de esta cita
            cargarServiciosCita();
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
        modeloServicios.setRowCount(0);
        lblTotal.setText("TOTAL: $0.00");
    }
}
