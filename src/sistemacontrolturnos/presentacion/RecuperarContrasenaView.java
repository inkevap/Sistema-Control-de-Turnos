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
import sistemacontrolturnos.controlador.UsuarioController;

/**
 * Recuperacion de contrasena self-service en dos pasos dentro de la misma ventana:
 *   Paso 1: el usuario escribe su nombre de usuario y pide un codigo (llega por correo).
 *   Paso 2: escribe el codigo recibido y la nueva contrasena que elige.
 * El mismo controlador se usa en ambos pasos; el codigo se guarda en memoria en el servicio.
 */
public class RecuperarContrasenaView extends JFrame {

    private final UsuarioController controlador;

    private JTextField campoUsuario;
    private JButton botonEnviarCodigo;
    private JTextField campoCodigo;
    private JPasswordField campoNuevaContrasena;
    private JButton botonRestablecer;

    public RecuperarContrasenaView() {
        this.controlador = new UsuarioController();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Recuperar Contrasena");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Paso 1: usuario + enviar codigo ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Usuario:"), gbc);

        campoUsuario = new JTextField(15);
        gbc.gridx = 1;
        add(campoUsuario, gbc);

        botonEnviarCodigo = new JButton("Enviar codigo");
        botonEnviarCodigo.addActionListener(evento -> enviarCodigo());
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(botonEnviarCodigo, gbc);

        // --- Paso 2: codigo + nueva contrasena (deshabilitado hasta enviar el codigo) ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Codigo:"), gbc);

        campoCodigo = new JTextField(15);
        campoCodigo.setEnabled(false);
        gbc.gridx = 1;
        add(campoCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Nueva contrasena:"), gbc);

        campoNuevaContrasena = new JPasswordField(15);
        campoNuevaContrasena.setEnabled(false);
        gbc.gridx = 1;
        add(campoNuevaContrasena, gbc);

        botonRestablecer = new JButton("Restablecer");
        botonRestablecer.setEnabled(false);
        botonRestablecer.addActionListener(evento -> restablecer());
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(botonRestablecer, gbc);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(botonRegresar, gbc);
    }

    private void enviarCodigo() {
        String nombreUsuario = campoUsuario.getText().trim();
        if (nombreUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa tu nombre de usuario");
            return;
        }
        try {
            controlador.solicitarCodigoRecuperacion(nombreUsuario);
            JOptionPane.showMessageDialog(this,
                    "Se envio un codigo de recuperacion a tu correo. Revisa tu bandeja e ingresalo abajo.");
            // Habilita el paso 2 y bloquea cambiar el usuario para no descuadrar el codigo.
            campoUsuario.setEnabled(false);
            botonEnviarCodigo.setEnabled(false);
            campoCodigo.setEnabled(true);
            campoNuevaContrasena.setEnabled(true);
            botonRestablecer.setEnabled(true);
            campoCodigo.requestFocus();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void restablecer() {
        String nombreUsuario = campoUsuario.getText().trim();
        String codigo = campoCodigo.getText().trim();
        String nuevaContrasena = new String(campoNuevaContrasena.getPassword());

        try {
            controlador.restablecerContrasena(nombreUsuario, codigo, nuevaContrasena);
            JOptionPane.showMessageDialog(this, "Tu contrasena se restablecio correctamente. Ya puedes iniciar sesion.");
            dispose();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

