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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sistemacontrolturnos.controlador.UsuarioController;
import sistemacontrolturnos.dto.UsuarioDTO;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.TipoTurno;
import sistemacontrolturnos.entidad.Usuario;

public class AgregarEmpleadoView extends JFrame {

    private final UsuarioController controlador;

    private JTextField campoDpi;
    private JTextField campoNombreCompleto;
    private JTextField campoNombreUsuario;
    private JTextField campoArea;
    private JComboBox<TipoTurno> comboTurno;
    private JComboBox<Rol> comboRol;
    private JComboBox<String> comboSupervisor;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    private int filaActual = 0;

    public AgregarEmpleadoView() {
        this.controlador = new UsuarioController();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Agregar Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoDpi = agregarCampoTexto("DPI:", gbc);
        campoNombreCompleto = agregarCampoTexto("Nombre Completo:", gbc);
        campoNombreUsuario = agregarCampoTexto("Usuario:", gbc);
        campoArea = agregarCampoTexto("Area:", gbc);

        gbc.gridx = 0;
        gbc.gridy = filaActual;
        add(new JLabel("Turno:"), gbc);
        comboTurno = new JComboBox<>(TipoTurno.values());
        gbc.gridx = 1;
        add(comboTurno, gbc);
        filaActual++;

        gbc.gridx = 0;
        gbc.gridy = filaActual;
        add(new JLabel("Rol:"), gbc);
        comboRol = new JComboBox<>(Rol.values());
        gbc.gridx = 1;
        add(comboRol, gbc);
        filaActual++;

        gbc.gridx = 0;
        gbc.gridy = filaActual;
        add(new JLabel("Supervisor:"), gbc);
        comboSupervisor = new JComboBox<>();
        comboSupervisor.addItem("");
        List<Usuario> usuarios = controlador.consultarUsuarios(null, null);
        for (Usuario usuario : usuarios) {
            comboSupervisor.addItem(usuario.getNombreUsuario());
        }
        gbc.gridx = 1;
        add(comboSupervisor, gbc);
        filaActual++;

        campoCorreo = agregarCampoTexto("Correo:", gbc);

        gbc.gridx = 0;
        gbc.gridy = filaActual;
        add(new JLabel("Contrasena:"), gbc);
        campoContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        add(campoContrasena, gbc);
        filaActual++;

        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.addActionListener(evento -> registrar());
        gbc.gridx = 0;
        gbc.gridy = filaActual;
        add(botonRegistrar, gbc);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        gbc.gridx = 1;
        add(botonRegresar, gbc);
    }

    private JTextField agregarCampoTexto(String etiqueta, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = filaActual;
        add(new JLabel(etiqueta), gbc);

        JTextField campo = new JTextField(15);
        gbc.gridx = 1;
        add(campo, gbc);
        filaActual++;
        return campo;
    }

    private void registrar() {
        try {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setDpi(campoDpi.getText().trim());
            dto.setNombreCompleto(campoNombreCompleto.getText().trim());
            dto.setNombreUsuario(campoNombreUsuario.getText().trim());
            dto.setArea(campoArea.getText().trim());
            dto.setTurno((TipoTurno) comboTurno.getSelectedItem());
            dto.setRol((Rol) comboRol.getSelectedItem());
            String supervisor = (String) comboSupervisor.getSelectedItem();
            dto.setSupervisorUsuario(supervisor == null || supervisor.isEmpty() ? null : supervisor);
            dto.setCorreo(campoCorreo.getText().trim());
            dto.setContrasena(new String(campoContrasena.getPassword()));

            controlador.registrarEmpleado(dto);

            JOptionPane.showMessageDialog(this, "se creo correctamente");
            dispose();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
