package sistemacontrolturnos.presentacion.usuario;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MantenimientoUsuarioView extends JFrame {

    private final String nombreUsuarioAdmin;

    public MantenimientoUsuarioView(String nombreUsuarioAdmin) {
        this.nombreUsuarioAdmin = nombreUsuarioAdmin;
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Mantenimiento de Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(320, 260);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton botonAgregar = new JButton("Agregar Empleado");
        botonAgregar.addActionListener(evento -> new AgregarEmpleadoView().setVisible(true));
        panel.add(botonAgregar);

        JButton botonConsultar = new JButton("Consultar Usuario");
        botonConsultar.addActionListener(evento -> new ConsultarUsuarioView().setVisible(true));
        panel.add(botonConsultar);

        JButton botonSolicitudes = new JButton("Solicitudes");
        botonSolicitudes.addActionListener(evento -> new SolicitudesRRHHView(nombreUsuarioAdmin).setVisible(true));
        panel.add(botonSolicitudes);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        panel.add(botonRegresar);

        add(panel);
    }
}
