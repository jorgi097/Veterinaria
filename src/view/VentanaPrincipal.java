package view;

import controller.ClinicaController;
import java.awt.*;
import javax.swing.*;
import utils.PersistenciaArchivos;

/**
 * Ventana principal del sistema con interfaz gráfica
 */
public class VentanaPrincipal extends JFrame {
    
    private final ClinicaController controller;
    private final PersistenciaArchivos persistencia;
    
    // Paneles de contenido
    private JPanel panelPrincipal;
    
    public VentanaPrincipal() {
        this.controller = ClinicaController.getInstance();
        this.persistencia = new PersistenciaArchivos(controller);
        
        // Cargar datos guardados
        persistencia.cargarTodo();
        
        // Configurar ventana
        configurarVentana();
        crearMenuBar();
        crearPanelBienvenida();
        
        // Centrar en pantalla
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Gestión - Clínica Veterinaria");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        
        // Agregar listener para guardar al cerrar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int respuesta = JOptionPane.showConfirmDialog(
                    VentanaPrincipal.this,
                    "¿Está seguro de que desea salir?\nLos datos se guardarán automáticamente.",
                    "Confirmar Salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    persistencia.guardarTodo();
                    System.exit(0);
                }
            }
        });
        
        // Panel principal con BorderLayout
        panelPrincipal = new JPanel(new BorderLayout());
        setContentPane(panelPrincipal);
    }
    
    private void crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(52, 152, 219));
        
        // Menú Archivo
        JMenu menuArchivo = crearMenu("Archivo", "icons/file.png");
        menuArchivo.add(crearMenuItem("Guardar Datos", "icons/save.png", e -> {
            persistencia.guardarTodo();
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }));
        menuArchivo.add(crearMenuItem("Cargar Datos", "icons/load.png", e -> {
            persistencia.cargarTodo();
            JOptionPane.showMessageDialog(this, "Datos cargados exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }));
        menuArchivo.addSeparator();
        menuArchivo.add(crearMenuItem("Salir", "icons/exit.png", e -> {
            dispatchEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
        }));
        
        // Menú Dueños
        JMenu menuDuenos = crearMenu("Dueños", "icons/owner.png");
        menuDuenos.add(crearMenuItem("Gestionar Dueños", "icons/manage.png", e -> mostrarGestionDuenos()));
        
        // Menú Mascotas
        JMenu menuMascotas = crearMenu("Mascotas", "icons/pet.png");
        menuMascotas.add(crearMenuItem("Gestionar Mascotas", "icons/manage.png", e -> mostrarGestionMascotas()));
        
        // Menú Empleados
        JMenu menuEmpleados = crearMenu("Empleados", "icons/employee.png");
        menuEmpleados.add(crearMenuItem("Gestionar Veterinarios", "icons/vet.png", e -> mostrarGestionVeterinarios()));
        menuEmpleados.add(crearMenuItem("Gestionar Asistentes", "icons/assistant.png", e -> mostrarGestionAsistentes()));
        
        // Menú Citas
        JMenu menuCitas = crearMenu("Citas", "icons/calendar.png");
        menuCitas.add(crearMenuItem("Gestionar Citas", "icons/appointment.png", e -> mostrarGestionCitas()));
        
        // Menú Servicios
        JMenu menuServicios = crearMenu("Servicios", "icons/service.png");
        menuServicios.add(crearMenuItem("Ver Servicios", "icons/list.png", e -> mostrarServicios()));
        
        // Menú Reportes
        JMenu menuReportes = crearMenu("Reportes", "icons/report.png");
        menuReportes.add(crearMenuItem("Citas por Veterinario", "icons/chart.png", e -> mostrarReporteCitas()));
        menuReportes.add(crearMenuItem("Mascotas por Dueño", "icons/chart.png", e -> mostrarReporteMascotas()));
        
        // Menú Ayuda
        JMenu menuAyuda = crearMenu("Ayuda", "icons/help.png");
        menuAyuda.add(crearMenuItem("Acerca de", "icons/info.png", e -> mostrarAcercaDe()));
        menuAyuda.add(crearMenuItem("Demostración Polimorfismo", "icons/demo.png", e -> mostrarDemoPolimorfismo()));
        
        // Agregar menús a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuDuenos);
        menuBar.add(menuMascotas);
        menuBar.add(menuEmpleados);
        menuBar.add(menuCitas);
        menuBar.add(menuServicios);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
    }
    
    private JMenu crearMenu(String texto, String iconPath) {
        JMenu menu = new JMenu(texto);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return menu;
    }
    
    private JMenuItem crearMenuItem(String texto, String iconPath, java.awt.event.ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        item.addActionListener(listener);
        return item;
    }
    
    private void crearPanelBienvenida() {
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        panelBienvenida.setBackground(new Color(236, 240, 241));
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 100));
        
        JLabel lblTitulo = new JLabel("Sistema de Gestión - Clínica Veterinaria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel central con información
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblBienvenida = new JLabel("¡Bienvenido al Sistema!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(44, 62, 80));
        panelInfo.add(lblBienvenida, gbc);
        
        JLabel lblInstrucciones = new JLabel("<html><center>Use el menú superior para navegar por las diferentes opciones<br>" +
                "Gestione dueños, mascotas, empleados, citas y más</center></html>");
        lblInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInstrucciones.setForeground(new Color(127, 140, 141));
        panelInfo.add(lblInstrucciones, gbc);
        
        // Panel con estadísticas
        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 4, 20, 0));
        panelEstadisticas.setBackground(new Color(236, 240, 241));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        panelEstadisticas.add(crearTarjetaEstadistica("Dueños", 
            String.valueOf(controller.listarDuenos().size()), new Color(46, 204, 113)));
        panelEstadisticas.add(crearTarjetaEstadistica("Mascotas", 
            String.valueOf(controller.listarMascotas().size()), new Color(155, 89, 182)));
        panelEstadisticas.add(crearTarjetaEstadistica("Empleados", 
            String.valueOf(controller.listarEmpleados().size()), new Color(230, 126, 34)));
        panelEstadisticas.add(crearTarjetaEstadistica("Citas", 
            String.valueOf(controller.listarCitas().size()), new Color(231, 76, 60)));
        
        panelInfo.add(panelEstadisticas, gbc);
        
        panelBienvenida.add(panelTitulo, BorderLayout.NORTH);
        panelBienvenida.add(panelInfo, BorderLayout.CENTER);
        
        panelPrincipal.removeAll();
        panelPrincipal.add(panelBienvenida, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(44, 62, 80));
        
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValor.setForeground(color);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void mostrarGestionDuenos() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionDuenos(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void mostrarGestionMascotas() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionMascotas(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void mostrarGestionVeterinarios() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionVeterinarios(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void mostrarGestionAsistentes() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionAsistentes(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void mostrarGestionCitas() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionCitas(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void mostrarServicios() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelServicios(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void mostrarReporteCitas() {
        String idVet = JOptionPane.showInputDialog(this, "Ingrese ID del Veterinario:");
        if (idVet != null && !idVet.trim().isEmpty()) {
            panelPrincipal.removeAll();
            panelPrincipal.add(new PanelReporteCitas(controller, idVet), BorderLayout.CENTER);
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }
    
    private void mostrarReporteMascotas() {
        String idDueno = JOptionPane.showInputDialog(this, "Ingrese ID del Dueño:");
        if (idDueno != null && !idDueno.trim().isEmpty()) {
            panelPrincipal.removeAll();
            panelPrincipal.add(new PanelReporteMascotas(controller, idDueno), BorderLayout.CENTER);
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }
    
    private void mostrarDemoPolimorfismo() {
        StringBuilder sb = new StringBuilder();
        sb.append("DEMOSTRACIÓN DE POLIMORFISMO\n\n");
        sb.append("Ejecutando método realizarTarea() para cada empleado:\n\n");
        
        controller.listarEmpleados().forEach(emp -> {
            sb.append(emp.realizarTarea()).append("\n\n");
        });
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Demostración de Polimorfismo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "<html><h2>Sistema de Gestión - Clínica Veterinaria</h2>" +
            "<p><b>Versión:</b> 1.0</p>" +
            "<p><b>Desarrollado en:</b> Java con Swing</p>" +
            "<p><b>Características:</b></p>" +
            "<ul>" +
            "<li>Gestión completa de mascotas y dueños</li>" +
            "<li>Control de personal (veterinarios y asistentes)</li>" +
            "<li>Agendamiento de citas</li>" +
            "<li>Persistencia de datos</li>" +
            "<li>Aplicación de principios POO</li>" +
            "</ul>" +
            "<p><b>Fecha:</b> Octubre 2025</p></html>",
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
