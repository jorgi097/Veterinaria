package view;

import controller.ClinicaController;
import java.awt.*;
import javax.swing.*;
import utils.PersistenciaArchivos;

// Ventana principal del sistema con interfaz gráfica
public class VentanaPrincipal extends JFrame {
    
    // Controlador principal del sistema
    private final ClinicaController controller;
    // Maneja la persistencia de datos en archivos
    private final PersistenciaArchivos persistencia;
    
    // Panel principal que contiene todos los demás paneles
    private JPanel panelPrincipal;
    
    // Constructor de la ventana principal
    public VentanaPrincipal() {
        // Obtener la instancia única del controlador (patrón Singleton)
        this.controller = ClinicaController.getInstance();
        // Crear el manejador de persistencia
        this.persistencia = new PersistenciaArchivos(controller);
        
        // Cargar los datos guardados previamente desde los archivos
        persistencia.cargarTodo();
        
        // Configurar las propiedades básicas de la ventana
        configurarVentana();
        // Crear la barra de menú superior
        crearMenuBar();
        // Mostrar el panel de bienvenida inicial
        crearPanelBienvenida();
        
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        // Hacer visible la ventana
        setVisible(true);
    }
    
    // Configura las propiedades iniciales de la ventana
    private void configurarVentana() {
        // Establecer el título de la ventana
        setTitle("Sistema de Gestión - Clínica Veterinaria");
        // Definir el tamaño inicial de la ventana
        setSize(1200, 700);
        // No cerrar automáticamente al hacer clic en X (controlamos el cierre manualmente)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Establecer el tamaño mínimo permitido
        setMinimumSize(new Dimension(800, 600));
        
        // Agregar un listener para controlar el evento de cierre de ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Mostrar diálogo de confirmación antes de salir
                int respuesta = JOptionPane.showConfirmDialog(
                    VentanaPrincipal.this,
                    "¿Está seguro de que desea salir?\nLos datos se guardarán automáticamente.",
                    "Confirmar Salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                // Si el usuario confirma, guardar datos y cerrar la aplicación
                if (respuesta == JOptionPane.YES_OPTION) {
                    persistencia.guardarTodo();
                    System.exit(0);
                }
            }
        });
        
        // Crear el panel principal con diseño BorderLayout
        panelPrincipal = new JPanel(new BorderLayout());
        // Establecer el panel principal como el contenido de la ventana
        setContentPane(panelPrincipal);
    }
    
    // Crea la barra de menú superior con todas las opciones del sistema
    private void crearMenuBar() {
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        // Establecer color de fondo azul
        menuBar.setBackground(new Color(52, 152, 219));
        
        // ===== MENÚ ARCHIVO =====
        JMenu menuArchivo = crearMenu("Archivo", "icons/file.png");
        // Opción para guardar datos manualmente
        menuArchivo.add(crearMenuItem("Guardar Datos", "icons/save.png", e -> {
            persistencia.guardarTodo();
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }));
        // Opción para recargar datos desde archivos
        menuArchivo.add(crearMenuItem("Cargar Datos", "icons/load.png", e -> {
            persistencia.cargarTodo();
            JOptionPane.showMessageDialog(this, "Datos cargados exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }));
        // Separador visual en el menú
        menuArchivo.addSeparator();
        // Opción para salir de la aplicación
        menuArchivo.add(crearMenuItem("Salir", "icons/exit.png", e -> {
            // Disparar el evento de cierre de ventana para mostrar confirmación
            dispatchEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
        }));
        
        // ===== MENÚ DUEÑOS =====
        JMenu menuDuenos = crearMenu("Dueños", "icons/owner.png");
        // Opción para gestionar dueños de mascotas
        menuDuenos.add(crearMenuItem("Gestionar Dueños", "icons/manage.png", e -> mostrarGestionDuenos()));
        
        // ===== MENÚ MASCOTAS =====
        JMenu menuMascotas = crearMenu("Mascotas", "icons/pet.png");
        // Opción para gestionar mascotas
        menuMascotas.add(crearMenuItem("Gestionar Mascotas", "icons/manage.png", e -> mostrarGestionMascotas()));
        
        // ===== MENÚ EMPLEADOS =====
        JMenu menuEmpleados = crearMenu("Empleados", "icons/employee.png");
        // Opción para gestionar veterinarios
        menuEmpleados.add(crearMenuItem("Gestionar Veterinarios", "icons/vet.png", e -> mostrarGestionVeterinarios()));
        // Opción para gestionar asistentes
        menuEmpleados.add(crearMenuItem("Gestionar Asistentes", "icons/assistant.png", e -> mostrarGestionAsistentes()));
        
        // ===== MENÚ CITAS =====
        JMenu menuCitas = crearMenu("Citas", "icons/calendar.png");
        // Opción para gestionar citas médicas
        menuCitas.add(crearMenuItem("Gestionar Citas", "icons/appointment.png", e -> mostrarGestionCitas()));
        
        // ===== MENÚ SERVICIOS =====
        JMenu menuServicios = crearMenu("Servicios", "icons/service.png");
        // Opción para ver servicios disponibles
        menuServicios.add(crearMenuItem("Ver Servicios", "icons/list.png", e -> mostrarServicios()));
        
        // ===== MENÚ REPORTES =====
        JMenu menuReportes = crearMenu("Reportes", "icons/report.png");
        // Opción para ver reporte de citas por veterinario
        menuReportes.add(crearMenuItem("Citas por Veterinario", "icons/chart.png", e -> mostrarReporteCitas()));
        // Opción para ver reporte de mascotas por dueño
        menuReportes.add(crearMenuItem("Mascotas por Dueño", "icons/chart.png", e -> mostrarReporteMascotas()));
        
        // ===== MENÚ AYUDA =====
        JMenu menuAyuda = crearMenu("Ayuda", "icons/help.png");
        // Opción para mostrar información del sistema
        menuAyuda.add(crearMenuItem("Acerca de", "icons/info.png", e -> mostrarAcercaDe()));
        // Opción para demostrar polimorfismo (concepto de POO)
        menuAyuda.add(crearMenuItem("Demostración Polimorfismo", "icons/demo.png", e -> mostrarDemoPolimorfismo()));
        
        // Agregar todos los menús a la barra de menú
        menuBar.add(menuArchivo);
        menuBar.add(menuDuenos);
        menuBar.add(menuMascotas);
        menuBar.add(menuEmpleados);
        menuBar.add(menuCitas);
        menuBar.add(menuServicios);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);
        
        // Establecer la barra de menú en la ventana
        setJMenuBar(menuBar);
    }
    
    // Crea un menú con el texto especificado
    private JMenu crearMenu(String texto, String iconPath) {
        JMenu menu = new JMenu(texto);
        // Color de texto blanco
        menu.setForeground(Color.WHITE);
        // Fuente en negrita
        menu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return menu;
    }
    
    // Crea un ítem de menú con texto y acción asociada
    private JMenuItem crearMenuItem(String texto, String iconPath, java.awt.event.ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        // Fuente estándar para ítems
        item.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        // Asociar la acción al hacer clic
        item.addActionListener(listener);
        return item;
    }
    
    // Crea y muestra el panel de bienvenida con estadísticas del sistema
    private void crearPanelBienvenida() {
        // Panel principal de bienvenida con diseño BorderLayout
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        // Color de fondo gris claro
        panelBienvenida.setBackground(new Color(236, 240, 241));
        
        // ===== PANEL SUPERIOR CON TÍTULO =====
        JPanel panelTitulo = new JPanel();
        // Color de fondo azul
        panelTitulo.setBackground(new Color(52, 152, 219));
        // Altura fija de 100 píxeles
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 100));
        
        // Etiqueta con el título principal
        JLabel lblTitulo = new JLabel("Sistema de Gestión - Clínica Veterinaria");
        // Fuente grande en negrita
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // Texto en blanco
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL CENTRAL CON INFORMACIÓN =====
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(new Color(236, 240, 241));
        
        // Configurar restricciones para el layout GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        // Cada componente ocupa todo el ancho disponible
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // Centrar componentes
        gbc.anchor = GridBagConstraints.CENTER;
        // Espaciado entre componentes
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Mensaje de bienvenida
        JLabel lblBienvenida = new JLabel("¡Bienvenido al Sistema!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(44, 62, 80));
        panelInfo.add(lblBienvenida, gbc);
        
        // Instrucciones de uso del sistema
        JLabel lblInstrucciones = new JLabel("<html><center>Use el menú superior para navegar por las diferentes opciones<br>" +
                "Gestione dueños, mascotas, empleados, citas y más</center></html>");
        lblInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInstrucciones.setForeground(new Color(127, 140, 141));
        panelInfo.add(lblInstrucciones, gbc);
        
        // ===== PANEL CON ESTADÍSTICAS =====
        // Panel con 4 columnas (una para cada estadística)
        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 4, 20, 0));
        panelEstadisticas.setBackground(new Color(236, 240, 241));
        // Margen interior del panel
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Tarjeta con cantidad de dueños (color verde)
        panelEstadisticas.add(crearTarjetaEstadistica("Dueños", 
            String.valueOf(controller.listarDuenos().size()), new Color(46, 204, 113)));
        // Tarjeta con cantidad de mascotas (color morado)
        panelEstadisticas.add(crearTarjetaEstadistica("Mascotas", 
            String.valueOf(controller.listarMascotas().size()), new Color(155, 89, 182)));
        // Tarjeta con cantidad de empleados (color naranja)
        panelEstadisticas.add(crearTarjetaEstadistica("Empleados", 
            String.valueOf(controller.listarEmpleados().size()), new Color(230, 126, 34)));
        // Tarjeta con cantidad de citas (color rojo)
        panelEstadisticas.add(crearTarjetaEstadistica("Citas", 
            String.valueOf(controller.listarCitas().size()), new Color(231, 76, 60)));
        
        // Agregar panel de estadísticas al panel de información
        panelInfo.add(panelEstadisticas, gbc);
        
        // Agregar paneles al panel de bienvenida
        panelBienvenida.add(panelTitulo, BorderLayout.NORTH);
        panelBienvenida.add(panelInfo, BorderLayout.CENTER);
        
        // Limpiar el panel principal y mostrar la bienvenida
        panelPrincipal.removeAll();
        panelPrincipal.add(panelBienvenida, BorderLayout.CENTER);
        // Recalcular el layout
        panelPrincipal.revalidate();
        // Repintar la interfaz
        panelPrincipal.repaint();
    }
    
    // Crea una tarjeta visual con título, valor numérico y color personalizado
    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        // Panel con diseño BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        // Fondo blanco
        panel.setBackground(Color.WHITE);
        // Borde compuesto: línea de color + margen interno
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Etiqueta con el título de la estadística
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(44, 62, 80));
        
        // Etiqueta con el valor numérico
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        // Color según la categoría (verde, morado, naranja, rojo)
        lblValor.setForeground(color);
        
        // Agregar componentes al panel
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Muestra el panel de gestión de dueños
    private void mostrarGestionDuenos() {
        // Limpiar panel principal
        panelPrincipal.removeAll();
        // Agregar panel de gestión de dueños
        panelPrincipal.add(new PanelGestionDuenos(controller), BorderLayout.CENTER);
        // Actualizar la interfaz
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    // Muestra el panel de gestión de mascotas
    private void mostrarGestionMascotas() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionMascotas(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    // Muestra el panel de gestión de veterinarios
    private void mostrarGestionVeterinarios() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionVeterinarios(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    // Muestra el panel de gestión de asistentes
    private void mostrarGestionAsistentes() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionAsistentes(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    // Muestra el panel de gestión de citas
    private void mostrarGestionCitas() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelGestionCitas(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    // Muestra el panel de servicios disponibles
    private void mostrarServicios() {
        panelPrincipal.removeAll();
        panelPrincipal.add(new PanelServicios(controller), BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    // Muestra el reporte de citas de un veterinario específico
    private void mostrarReporteCitas() {
        // Solicitar al usuario el ID del veterinario
        String idVet = JOptionPane.showInputDialog(this, "Ingrese ID del Veterinario:");
        // Validar que se ingresó un ID
        if (idVet != null && !idVet.trim().isEmpty()) {
            panelPrincipal.removeAll();
            // Mostrar panel de reporte con las citas del veterinario
            panelPrincipal.add(new PanelReporteCitas(controller, idVet), BorderLayout.CENTER);
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }
    
    // Muestra el reporte de mascotas de un dueño específico
    private void mostrarReporteMascotas() {
        // Solicitar al usuario el ID del dueño
        String idDueno = JOptionPane.showInputDialog(this, "Ingrese ID del Dueño:");
        // Validar que se ingresó un ID
        if (idDueno != null && !idDueno.trim().isEmpty()) {
            panelPrincipal.removeAll();
            // Mostrar panel de reporte con las mascotas del dueño
            panelPrincipal.add(new PanelReporteMascotas(controller, idDueno), BorderLayout.CENTER);
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }
    
    // Muestra una demostración del concepto de polimorfismo con empleados
    private void mostrarDemoPolimorfismo() {
        // Construir el texto de demostración
        StringBuilder sb = new StringBuilder();
        sb.append("DEMOSTRACIÓN DE POLIMORFISMO\n\n");
        sb.append("Ejecutando método realizarTarea() para cada empleado:\n\n");
        
        // Obtener la tarea de cada empleado (veterinarios y asistentes)
        // Cada tipo de empleado ejecuta su propia implementación del método
        controller.listarEmpleados().forEach(emp -> {
            sb.append(emp.realizarTarea()).append("\n\n");
        });
        
        // Crear área de texto para mostrar el resultado
        JTextArea textArea = new JTextArea(sb.toString());
        // No permitir edición
        textArea.setEditable(false);
        // Fuente monoespaciada para mejor visualización
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Agregar scroll al área de texto
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        // Mostrar diálogo con la demostración
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Demostración de Polimorfismo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Muestra información acerca del sistema
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
