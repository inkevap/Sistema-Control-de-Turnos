package sistemacontrolturnos.presentacion.turno;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import sistemacontrolturnos.controlador.AsignacionTurnoController;
import sistemacontrolturnos.dto.AsignacionTurnoDTO;
import sistemacontrolturnos.entidad.TipoTurno;
import sistemacontrolturnos.entidad.Usuario;

/**
 * Pantalla del CU3: el administrador de area asigna un turno (y supervisor) a un
 * empleado que tiene a su cargo, para un rango de fechas.
 */
public class AsignacionTurnoView extends JFrame {

    // RN02 / pantalla del CU3: turnos asignables (el Nocturno no se ofrece).
    private static final TipoTurno[] TURNOS_ASIGNABLES = {
        TipoTurno.MATUTINO, TipoTurno.VESPERTINO, TipoTurno.DIURNO
    };

    private final AsignacionTurnoController controlador;
    private final String adminUsuario;

    private JComboBox<String> comboEmpleado;
    private JTextField campoFechaInicio;
    private JTextField campoFechaFin;
    private JComboBox<TipoTurno> comboTurno;
    private JComboBox<String> comboSupervisor;

    public AsignacionTurnoView(String adminUsuario) {
        this.adminUsuario = adminUsuario;
        this.controlador = new AsignacionTurnoController();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Asignacion de Turnos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Empleado:"), gbc);
        comboEmpleado = new JComboBox<>();
        for (Usuario empleado : controlador.listarEmpleadosDe(adminUsuario)) {
            comboEmpleado.addItem(empleado.getNombreUsuario());
        }
        gbc.gridx = 1;
        add(comboEmpleado, gbc);
        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Fecha Inicio (aaaa-mm-dd):"), gbc);
        campoFechaInicio = new JTextField(12);
        gbc.gridx = 1;
        add(campoFechaInicio, gbc);
        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Fecha Fin (aaaa-mm-dd):"), gbc);
        campoFechaFin = new JTextField(12);
        gbc.gridx = 1;
        add(campoFechaFin, gbc);
        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Turno:"), gbc);
        comboTurno = new JComboBox<>(TURNOS_ASIGNABLES);
        gbc.gridx = 1;
        add(comboTurno, gbc);
        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Supervisor:"), gbc);
        comboSupervisor = new JComboBox<>();
        for (Usuario supervisor : controlador.listarSupervisores()) {
            comboSupervisor.addItem(supervisor.getNombreUsuario());
        }
        gbc.gridx = 1;
        add(comboSupervisor, gbc);
        fila++;

        JButton botonAsignar = new JButton("Asignar");
        botonAsignar.addActionListener(evento -> asignar());
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(botonAsignar, gbc);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        gbc.gridx = 1;
        add(botonRegresar, gbc);
    }

    private void asignar() {
        String empleado = (String) comboEmpleado.getSelectedItem();
        String supervisor = (String) comboSupervisor.getSelectedItem();
        if (empleado == null) {
            JOptionPane.showMessageDialog(this, "No hay empleados a su cargo para asignar");
            return;
        }
        if (supervisor == null) {
            JOptionPane.showMessageDialog(this, "No hay supervisores disponibles");
            return;
        }

        LocalDate fechaInicio;
        LocalDate fechaFin;
        try {
            fechaInicio = LocalDate.parse(campoFechaInicio.getText().trim());
            fechaFin = LocalDate.parse(campoFechaFin.getText().trim());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Las fechas deben tener el formato aaaa-mm-dd",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AsignacionTurnoDTO dto = new AsignacionTurnoDTO();
        dto.setNombreUsuarioEmpleado(empleado);
        dto.setFechaInicio(fechaInicio);
        dto.setFechaFin(fechaFin);
        dto.setTurno((TipoTurno) comboTurno.getSelectedItem());
        dto.setSupervisorUsuario(supervisor);

        try {
            controlador.asignar(dto, adminUsuario);
            JOptionPane.showMessageDialog(this, "Asignacion creada con exito");
            dispose();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
