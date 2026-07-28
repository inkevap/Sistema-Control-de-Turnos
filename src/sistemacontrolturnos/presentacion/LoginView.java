package sistemacontrolturnos.presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sistemacontrolturnos.controlador.LoginController;
import sistemacontrolturnos.entidad.Usuario;

public class LoginView extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private final LoginController controlador;

    public LoginView() {
        this.controlador = new LoginController();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Sistema Control de Turnos - Iniciar Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Usuario:"), gbc);

        campoUsuario = new JTextField(15);
        gbc.gridx = 1;
        add(campoUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Contrasena:"), gbc);

        campoContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        add(campoContrasena, gbc);

        JButton botonIniciarSesion = new JButton("Iniciar sesion");
        botonIniciarSesion.addActionListener(evento -> iniciarSesion());
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(botonIniciarSesion, gbc);
    }

    private void iniciarSesion() {
        String nombreUsuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        Usuario usuario = controlador.iniciarSesion(nombreUsuario, contrasena);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas",
                    "Error", JOptionPane.ERROR_MESSAGE);
            campoContrasena.setText("");
            return;
        }

        MenuPrincipalView menu = new MenuPrincipalView(usuario);
        menu.setVisible(true);
        dispose();
    }
}
