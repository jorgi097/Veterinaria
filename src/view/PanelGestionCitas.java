package view;

import controller.ClinicaController;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;

public class PanelGestionCitas extends JPanel {
    
    private final ClinicaController controller;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtFecha, txtMotivo, txtIdMascota, txtIdDueno, txtIdVeterinario;
    
    public PanelGestionCitas(ClinicaController controller) {
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
        panel.setBackground(new Color(231, 76, 60));
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lbl = new JLabel("Gestión de Citas Médicas");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        
        add(panel, BorderLayout.NORTH);
    }
    
    private void crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Cita"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtId = new JTextField(12);
        txtFecha = new JTextField(12);
        txtMotivo = new JTextField(12);
        txtIdMascota = new JTextField(12);
        txtIdDueno = new JTextField(12);
        txtIdVeterinario = new JTextField(12);
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Cita:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Fecha (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 3;
        panel.add(txtFecha, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMotivo, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("ID Mascota:"), gbc);
        gbc.gridx = 3;
        panel.add(txtIdMascota, gbc);
        
        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("ID Dueño:"), gbc);
        gbc.gridx = 1;
        panel.add(txtIdDueno, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("ID Veterinario:"), gbc);
        gbc.gridx = 3;
        panel.add(txtIdVeterinario, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnAgregar = crearBoton("Agendar", new Color(46, 204, 113));
        JButton btnCancelar = crearBoton("Cancelar Cita", new Color(231, 76, 60));
        JButton btnCompletar = crearBoton("Completar", new Color(52, 152, 219));
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        btnAgregar.addActionListener(e -> agregar());
        btnCancelar.addActionListener(e -> cancelar());
        btnCompletar.addActionListener(e -> completar());
        btnLimpiar.addActionListener(e -> limpiar());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnCompletar);
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
        String[] columnas = {"ID", "Fecha/Hora", "Motivo", "Mascota", "Dueño", "Veterinario", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(231, 76, 60));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarSeleccionado();
            }
        });
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Citas"));
        add(scroll, BorderLayout.CENTER);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Cita> citas = controller.listarCitas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Cita cita : citas) {
            modeloTabla.addRow(new Object[]{
                cita.getId(),
                cita.getFechaHora().format(formatter),
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getDueno().getNombre(),
                cita.getVeterinario().getNombre(),
                cita.getEstado()
            });
        }
    }
    
    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtFecha.setText(tabla.getValueAt(fila, 1).toString());
            txtMotivo.setText(tabla.getValueAt(fila, 2).toString());
        }
    }
    
    private void agregar() {
        if (validar()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime fechaHora = LocalDateTime.parse(txtFecha.getText().trim(), formatter);
                
                boolean exito = controller.agendarCita(
                    txtId.getText().trim(),
                    fechaHora,
                    txtMotivo.getText().trim(),
                    txtIdMascota.getText().trim(),
                    txtIdDueno.getText().trim(),
                    txtIdVeterinario.getText().trim()
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cita agendada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Conflicto de horario o datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use: dd/MM/yyyy HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelar() {
        if (!txtId.getText().trim().isEmpty()) {
            controller.cancelarCita(txtId.getText().trim());
            JOptionPane.showMessageDialog(this, "Cita cancelada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
        }
    }
    
    private void completar() {
        if (!txtId.getText().trim().isEmpty()) {
            controller.completarCita(txtId.getText().trim());
            JOptionPane.showMessageDialog(this, "Cita completada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
        }
    }
    
    private boolean validar() {
        return !txtId.getText().trim().isEmpty() &&
               !txtFecha.getText().trim().isEmpty() &&
               !txtMotivo.getText().trim().isEmpty() &&
               !txtIdMascota.getText().trim().isEmpty() &&
               !txtIdDueno.getText().trim().isEmpty() &&
               !txtIdVeterinario.getText().trim().isEmpty();
    }
    
    private void limpiar() {
        txtId.setText("");
        txtFecha.setText("");
        txtMotivo.setText("");
        txtIdMascota.setText("");
        txtIdDueno.setText("");
        txtIdVeterinario.setText("");
        tabla.clearSelection();
    }
}
