package sistemacontrolturnos.presentacion;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.Usuario;

public class MenuPrincipalView extends JFrame {

    private final Usuario usuarioActivo;

    public MenuPrincipalView(Usuario usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Sistema Control de Turnos - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        panel.add(new JLabel("Bienvenido, " + usuarioActivo.getNombreCompleto()
                + " (" + usuarioActivo.getRol() + ")"));

        Rol rol = usuarioActivo.getRol();

        if (rol == Rol.ADMIN_RRHH) {
            panel.add(new JButton("Mantenimiento de Usuarios"));
        }
        if (rol == Rol.ADMIN_AREA) {
            panel.add(new JButton("Asignacion de Turnos"));
            panel.add(new JButton("Gestion de Solicitudes"));
        }
        if (rol == Rol.EMPLEADO) {
            panel.add(new JButton("Marcaje"));
            panel.add(new JButton("Gestiones del Empleado"));
        }

        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(evento -> System.exit(0));
        panel.add(botonSalir);

        add(panel);
    }
}
