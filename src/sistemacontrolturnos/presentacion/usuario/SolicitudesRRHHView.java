package sistemacontrolturnos.presentacion.usuario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
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

    private final SolicitudEmpleadoController controlador;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public SolicitudesRRHHView() {
        this.controlador = new SolicitudEmpleadoController();
        construirInterfaz();
        cargarSolicitudes();
    }

    private void construirInterfaz() {
        setTitle("Solicitudes pendientes de RRHH");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Empleado", "Tipo", "Fecha Inicio", "Fecha Fin", "Motivo"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

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

    private void cargarSolicitudes() {
        modeloTabla.setRowCount(0);
        List<SolicitudGestionEmpleado> solicitudes = controlador.listarPendientesRRHH();
        for (SolicitudGestionEmpleado solicitud : solicitudes) {
            modeloTabla.addRow(new Object[]{
                solicitud.getIdSolicitud(),
                solicitud.getNombreUsuarioEmpleado(),
                solicitud.getTipoGestion(),
                solicitud.getFechaInicio(),
                solicitud.getFechaFin(),
                solicitud.getMotivo()
            });
        }
    }

    private void procesar(boolean aprobar) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud primero");
            return;
        }
        int idSolicitud = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        try {
            if (aprobar) {
                controlador.aprobar(idSolicitud);
            } else {
                controlador.rechazar(idSolicitud);
            }
            JOptionPane.showMessageDialog(this, "Solicitud procesada correctamente");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarSolicitudes();
    }
}
