package sistemacontrolturnos.presentacion.turno;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sistemacontrolturnos.controlador.SolicitudCambioTurnoController;
import sistemacontrolturnos.controlador.SolicitudEmpleadoController;
import sistemacontrolturnos.entidad.SolicitudCambioTurno;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;

/**
 * Pantalla del CU4 (Administrador de Area). Tiene dos secciones:
 *   - Solicitudes de Cambio de Turno: las resuelve el area (aprobar aplica el
 *     cambio de turno real).
 *   - Solicitudes de Licencias y Vacaciones: primera etapa; aprobar las envia a
 *     RRHH (segunda etapa), rechazar termina el flujo.
 */
public class GestionSolicitudesView extends JFrame {

    private static final Object[] COLUMNAS_TURNO = {
        "ID", "Empleado", "Fecha Inicial", "Turno Actual", "Fecha Nueva", "Turno Nuevo", "Justificacion"
    };
    private static final Object[] COLUMNAS_LICENCIA = {
        "ID", "Empleado", "Tipo", "Fecha Inicio", "Fecha Fin", "Motivo"
    };

    private final String adminUsuario;
    private final SolicitudCambioTurnoController cambioTurnoControlador;
    private final SolicitudEmpleadoController licenciaControlador;

    private DefaultTableModel modeloTurno;
    private JTable tablaTurno;
    private DefaultTableModel modeloLicencia;
    private JTable tablaLicencia;

    public GestionSolicitudesView(String adminUsuario) {
        this.adminUsuario = adminUsuario;
        this.cambioTurnoControlador = new SolicitudCambioTurnoController();
        this.licenciaControlador = new SolicitudEmpleadoController();
        construirInterfaz();
        cargarSolicitudes();
    }

    private void construirInterfaz() {
        setTitle("Gestion de Solicitudes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Solicitudes Cambio de Turno", construirPanelTurno());
        pestanas.addTab("Solicitudes Licencias y Vacaciones", construirPanelLicencia());
        add(pestanas, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        panelSur.add(botonRegresar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private JPanel construirPanelTurno() {
        modeloTurno = crearModelo(COLUMNAS_TURNO);
        tablaTurno = new JTable(modeloTurno);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tablaTurno), BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton aprobar = new JButton("Aprobar");
        aprobar.addActionListener(evento -> procesarTurno(true));
        JButton rechazar = new JButton("Rechazar");
        rechazar.addActionListener(evento -> procesarTurno(false));
        acciones.add(aprobar);
        acciones.add(rechazar);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel construirPanelLicencia() {
        modeloLicencia = crearModelo(COLUMNAS_LICENCIA);
        tablaLicencia = new JTable(modeloLicencia);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tablaLicencia), BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton aprobar = new JButton("Aprobar");
        aprobar.addActionListener(evento -> procesarLicencia(true));
        JButton rechazar = new JButton("Rechazar");
        rechazar.addActionListener(evento -> procesarLicencia(false));
        acciones.add(aprobar);
        acciones.add(rechazar);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    private DefaultTableModel crearModelo(Object[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    private void cargarSolicitudes() {
        modeloTurno.setRowCount(0);
        for (SolicitudCambioTurno s : cambioTurnoControlador.listarPendientes()) {
            modeloTurno.addRow(new Object[]{
                s.getIdSolicitud(), s.getNombreUsuarioEmpleado(), s.getFechaInicial(),
                s.getTurnoInicial(), s.getFechaNueva(), s.getTurnoNuevo(), s.getJustificacion()
            });
        }

        modeloLicencia.setRowCount(0);
        for (SolicitudGestionEmpleado s : licenciaControlador.listarPendientesArea()) {
            modeloLicencia.addRow(new Object[]{
                s.getIdSolicitud(), s.getNombreUsuarioEmpleado(), s.getTipoGestion(),
                s.getFechaInicio(), s.getFechaFin(), s.getMotivo()
            });
        }
    }

    private void procesarTurno(boolean aprobar) {
        int fila = tablaTurno.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud de cambio de turno primero");
            return;
        }
        int id = (int) modeloTurno.getValueAt(fila, 0);
        try {
            if (aprobar) {
                cambioTurnoControlador.aprobar(id, adminUsuario);
            } else {
                cambioTurnoControlador.rechazar(id, adminUsuario);
            }
            JOptionPane.showMessageDialog(this, "Solicitud procesada correctamente");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarSolicitudes();
    }

    private void procesarLicencia(boolean aprobar) {
        int fila = tablaLicencia.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud de licencia/vacaciones primero");
            return;
        }
        int id = (int) modeloLicencia.getValueAt(fila, 0);
        try {
            if (aprobar) {
                licenciaControlador.aprobarPorArea(id, adminUsuario);
            } else {
                licenciaControlador.rechazarPorArea(id, adminUsuario);
            }
            JOptionPane.showMessageDialog(this, "Solicitud procesada correctamente");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarSolicitudes();
    }
}
