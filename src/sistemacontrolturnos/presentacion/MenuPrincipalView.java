package sistemacontrolturnos.presentacion;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.presentacion.marcaje.MarcajeView;
import sistemacontrolturnos.presentacion.usuario.AgregarEmpleadoView;
import sistemacontrolturnos.presentacion.usuario.ConsultarUsuarioView;
import sistemacontrolturnos.presentacion.usuario.GestionRolesView;
import sistemacontrolturnos.presentacion.usuario.SolicitudesRRHHView;

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
            JButton botonAgregar = new JButton("Agregar Empleado");
            botonAgregar.addActionListener(evento -> new AgregarEmpleadoView().setVisible(true));
            panel.add(botonAgregar);

            JButton botonConsultar = new JButton("Consultar Usuario");
            botonConsultar.addActionListener(evento -> new ConsultarUsuarioView().setVisible(true));
            panel.add(botonConsultar);

            JButton botonRoles = new JButton("Gestion de Roles");
            botonRoles.addActionListener(evento -> new GestionRolesView().setVisible(true));
            panel.add(botonRoles);

            JButton botonSolicitudes = new JButton("Solicitudes RRHH");
            botonSolicitudes.addActionListener(evento -> new SolicitudesRRHHView().setVisible(true));
            panel.add(botonSolicitudes);
        }
        if (rol == Rol.ADMIN_AREA) {
            panel.add(new JButton("Asignacion de Turnos"));
            panel.add(new JButton("Gestion de Solicitudes"));
        }
        if (rol == Rol.EMPLEADO) {
            JButton botonMarcaje = new JButton("Marcaje");
            botonMarcaje.addActionListener(evento -> new MarcajeView(usuarioActivo.getNombreUsuario()).setVisible(true));
            panel.add(botonMarcaje);

            panel.add(new JButton("Gestiones del Empleado"));
        }

        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(evento -> System.exit(0));
        panel.add(botonSalir);

        add(panel);
    }
}
