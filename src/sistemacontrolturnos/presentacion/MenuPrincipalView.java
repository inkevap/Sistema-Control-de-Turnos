package sistemacontrolturnos.presentacion;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.presentacion.marcaje.MarcajeView;
import sistemacontrolturnos.presentacion.turno.AsignacionTurnoView;
import sistemacontrolturnos.presentacion.usuario.GestionRolesView;
import sistemacontrolturnos.presentacion.usuario.MantenimientoUsuarioView;

public class MenuPrincipalView extends JFrame {

    private final Usuario usuarioActivo;

    public MenuPrincipalView(Usuario usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Sistema Control de Turnos - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        panel.add(new JLabel("Bienvenido, " + usuarioActivo.getNombreCompleto()
                + " (" + usuarioActivo.getRol() + ")"));

        Rol rol = usuarioActivo.getRol();

        if (rol == Rol.ADMIN_RRHH) {
            JButton botonMantenimiento = new JButton("Mantenimiento de usuario");
            botonMantenimiento.addActionListener(evento ->
                    new MantenimientoUsuarioView(usuarioActivo.getNombreUsuario()).setVisible(true));
            panel.add(botonMantenimiento);

            JButton botonRoles = new JButton("Gestion de Roles");
            botonRoles.addActionListener(evento -> new GestionRolesView().setVisible(true));
            panel.add(botonRoles);
        }
        if (rol == Rol.ADMIN_AREA) {
            JButton botonAsignacion = new JButton("Asignacion de Turnos");
            botonAsignacion.addActionListener(evento ->
                    new AsignacionTurnoView(usuarioActivo.getNombreUsuario()).setVisible(true));
            panel.add(botonAsignacion);

            panel.add(new JButton("Gestion de Solicitudes"));
        }
        if (rol == Rol.EMPLEADO) {
            JButton botonMarcaje = new JButton("Marcaje");
            botonMarcaje.addActionListener(evento -> new MarcajeView(usuarioActivo.getNombreUsuario()).setVisible(true));
            panel.add(botonMarcaje);

            panel.add(new JButton("Gestiones del Empleado"));
        }

        add(panel);
    }
}
