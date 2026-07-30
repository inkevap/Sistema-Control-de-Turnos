package sistemacontrolturnos.presentacion.usuario;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import sistemacontrolturnos.controlador.UsuarioController;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.Usuario;

public class GestionRolesView extends JFrame {

    private final UsuarioController controlador;
    private JComboBox<String> comboUsuario;
    private JComboBox<Rol> comboRol;

    public GestionRolesView() {
        this.controlador = new UsuarioController();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Gestion de Roles");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Usuario:"), gbc);

        comboUsuario = new JComboBox<>();
        List<Usuario> usuarios = controlador.consultarUsuarios(null, null);
        for (Usuario usuario : usuarios) {
            comboUsuario.addItem(usuario.getNombreUsuario());
        }
        gbc.gridx = 1;
        add(comboUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Rol:"), gbc);

        comboRol = new JComboBox<>(Rol.values());
        gbc.gridx = 1;
        add(comboRol, gbc);

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(evento -> agregarRol());
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(botonAgregar, gbc);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(evento -> eliminarRol());
        gbc.gridx = 1;
        add(botonEliminar, gbc);
    }

    private void agregarRol() {
        String nombreUsuario = (String) comboUsuario.getSelectedItem();
        Rol rol = (Rol) comboRol.getSelectedItem();
        if (nombreUsuario == null || rol == null) {
            return;
        }
        controlador.agregarRol(nombreUsuario, rol);
        JOptionPane.showMessageDialog(this, "La asignacion de rol ha sido exitosa");
    }

    private void eliminarRol() {
        String nombreUsuario = (String) comboUsuario.getSelectedItem();
        if (nombreUsuario == null) {
            return;
        }
        controlador.eliminarRol(nombreUsuario);
        JOptionPane.showMessageDialog(this, "La eliminacion del rol ha sido exitosa");
    }
}
