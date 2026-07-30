package sistemacontrolturnos.presentacion.usuario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import sistemacontrolturnos.controlador.UsuarioController;
import sistemacontrolturnos.entidad.Usuario;

public class ConsultarUsuarioView extends JFrame {

    private final UsuarioController controlador;
    private JTextField campoFiltroUsuario;
    private JTextField campoFiltroArea;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public ConsultarUsuarioView() {
        this.controlador = new UsuarioController();
        construirInterfaz();
        buscar();
    }

    private void construirInterfaz() {
        setTitle("Consultar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Usuario:"));
        campoFiltroUsuario = new JTextField(10);
        panelFiltros.add(campoFiltroUsuario);
        panelFiltros.add(new JLabel("Area:"));
        campoFiltroArea = new JTextField(10);
        panelFiltros.add(campoFiltroArea);
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(evento -> buscar());
        panelFiltros.add(botonBuscar);

        add(panelFiltros, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"Usuario", "Area", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        panelAcciones.add(botonRegresar);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    private void buscar() {
        modeloTabla.setRowCount(0);
        List<Usuario> usuarios = controlador.consultarUsuarios(
                campoFiltroUsuario.getText().trim(), campoFiltroArea.getText().trim());
        for (Usuario usuario : usuarios) {
            modeloTabla.addRow(new Object[]{usuario.getNombreUsuario(), usuario.getArea(), usuario.getEstado()});
        }
    }
}
