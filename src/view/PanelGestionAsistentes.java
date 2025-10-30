package view;

import controller.ClinicaController;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Asistente;

public class PanelGestionAsistentes extends JPanel {
    
    private final ClinicaController controller;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre, txtCorreo, txtTelefono, txtHorario, txtArea;
    
    public PanelGestionAsistentes(ClinicaController controller) {
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
        JPanel panel = new JPanel();
        panel.setBackground(new Color(26, 188, 156));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lbl = new JLabel("Gestión de Asistentes");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    private void crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Asistente"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtId = new JTextField(12);
        txtNombre = new JTextField(12);
        txtCorreo = new JTextField(12);
        txtTelefono = new JTextField(12);
        txtHorario = new JTextField(12);
        txtArea = new JTextField(12);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCorreo, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 3;
        panel.add(txtTelefono, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Horario:"), gbc);
        gbc.gridx = 1;
        panel.add(txtHorario, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Área:"), gbc);
        gbc.gridx = 3;
        panel.add(txtArea, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        btnAgregar.addActionListener(e -> agregar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        panel.add(panelBotones, gbc);
        
        add(panel, BorderLayout.NORTH);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }
    
    private void crearTabla() {
        String[] columnas = {"ID", "Nombre", "Correo", "Teléfono", "Horario", "Área"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(26, 188, 156));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarSeleccionado();
            }
        });
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Asistentes"));
        add(scroll, BorderLayout.CENTER);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Asistente> asistentes = controller.listarEmpleados().stream()
                .filter(e -> e instanceof Asistente)
                .map(e -> (Asistente) e)
                .collect(Collectors.toList());
        
        for (Asistente asist : asistentes) {
            modeloTabla.addRow(new Object[]{
                asist.getId(),
                asist.getNombre(),
                asist.getCorreo(),
                asist.getTelefono(),
                asist.getHorario(),
                asist.getArea()
            });
        }
    }
    
    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtCorreo.setText(tabla.getValueAt(fila, 2).toString());
            txtTelefono.setText(tabla.getValueAt(fila, 3).toString());
            txtHorario.setText(tabla.getValueAt(fila, 4).toString());
            txtArea.setText(tabla.getValueAt(fila, 5).toString());
        }
    }
    
    private void agregar() {
        if (validar()) {
            boolean exito = controller.registrarAsistente(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim(),
                txtHorario.getText().trim(),
                txtArea.getText().trim()
            );
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Asistente registrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "ID ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminar() {
        if (!txtId.getText().trim().isEmpty()) {
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar asistente?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                controller.eliminarEmpleado(txtId.getText().trim());
                limpiar();
                cargarDatos();
            }
        }
    }
    
    private boolean validar() {
        return !txtId.getText().trim().isEmpty() &&
               !txtNombre.getText().trim().isEmpty() &&
               !txtCorreo.getText().trim().isEmpty() &&
               !txtTelefono.getText().trim().isEmpty() &&
               !txtHorario.getText().trim().isEmpty() &&
               !txtArea.getText().trim().isEmpty();
    }
    
    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtHorario.setText("");
        txtArea.setText("");
        tabla.clearSelection();
    }
}
