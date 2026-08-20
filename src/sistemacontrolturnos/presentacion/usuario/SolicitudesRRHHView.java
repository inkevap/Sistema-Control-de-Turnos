package sistemacontrolturnos.presentacion.usuario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sistemacontrolturnos.controlador.SolicitudEmpleadoController;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;

public class SolicitudesRRHHView extends JFrame {

    private static final Object[] COLUMNAS = {
        "ID", "Empleado", "Tipo", "Fecha Inicio", "Fecha Fin", "Motivo", "Estado", "Procesado Por"
    };

    private final SolicitudEmpleadoController controlador;
    private final String nombreUsuarioAdmin;

    private DefaultTableModel modeloPendientes;
    private JTable tablaPendientes;
    private DefaultTableModel modeloResueltas;
    private JTable tablaResueltas;

    public SolicitudesRRHHView(String nombreUsuarioAdmin) {
        this.nombreUsuarioAdmin = nombreUsuarioAdmin;
        this.controlador = new SolicitudEmpleadoController();
        construirInterfaz();
        cargarSolicitudes();
    }

    private void construirInterfaz() {
        setTitle("Solicitudes RRHH");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelTablas = new JPanel(new GridLayout(2, 1, 0, 10));

        modeloPendientes = crearModeloTabla();
        tablaPendientes = new JTable(modeloPendientes);
        JPanel panelPendientes = new JPanel(new BorderLayout());
        panelPendientes.setBorder(BorderFactory.createTitledBorder("Pendientes"));
        panelPendientes.add(new JScrollPane(tablaPendientes), BorderLayout.CENTER);
        panelTablas.add(panelPendientes);

        modeloResueltas = crearModeloTabla();
        tablaResueltas = new JTable(modeloResueltas);
        JPanel panelResueltas = new JPanel(new BorderLayout());
        panelResueltas.setBorder(BorderFactory.createTitledBorder("Resueltas"));
        panelResueltas.add(new JScrollPane(tablaResueltas), BorderLayout.CENTER);
        panelTablas.add(panelResueltas);

        add(panelTablas, BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton botonAprobar = new JButton("Aprobar");
        botonAprobar.addActionListener(evento -> procesar(true));
        panelAcciones.add(botonAprobar);

        JButton botonRechazar = new JButton("Rechazar");
        botonRechazar.addActionListener(evento -> procesar(false));
        panelAcciones.add(botonRechazar);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        panelAcciones.add(botonRegresar);

        add(panelAcciones, BorderLayout.SOUTH);
    }

    private DefaultTableModel crearModeloTabla() {
        return new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    private void cargarSolicitudes() {
        modeloPendientes.setRowCount(0);
        for (SolicitudGestionEmpleado solicitud : controlador.listarPendientesRRHH()) {
            modeloPendientes.addRow(construirFila(solicitud));
        }

        modeloResueltas.setRowCount(0);
        for (SolicitudGestionEmpleado solicitud : controlador.listarResueltas()) {
            modeloResueltas.addRow(construirFila(solicitud));
        }
    }

    private Object[] construirFila(SolicitudGestionEmpleado solicitud) {
        // Si esta pendiente, la columna "Procesado Por" no muestra nada.
        String procesadoPor = solicitud.getEstado().name().startsWith("PENDIENTE")
                ? "" : solicitud.getProcesadoPor();

        return new Object[]{
            solicitud.getIdSolicitud(),
            solicitud.getNombreUsuarioEmpleado(),
            solicitud.getTipoGestion(),
            solicitud.getFechaInicio(),
            solicitud.getFechaFin(),
            solicitud.getMotivo(),
            solicitud.getEstado(),
            procesadoPor
        };
    }

    private void procesar(boolean aprobar) {
        int filaSeleccionada = tablaPendientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud pendiente primero");
            return;
        }
        int idSolicitud = (int) modeloPendientes.getValueAt(filaSeleccionada, 0);

        try {
            if (aprobar) {
                controlador.aprobar(idSolicitud, nombreUsuarioAdmin);
            } else {
                controlador.rechazar(idSolicitud, nombreUsuarioAdmin);
            }
            JOptionPane.showMessageDialog(this, "Solicitud procesada correctamente");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarSolicitudes();
    }
}
