package sistemacontrolturnos.presentacion.marcaje;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sistemacontrolturnos.controlador.MarcajeController;
import sistemacontrolturnos.entidad.TipoMarcaje;

public class MarcajeView extends JFrame {

    private final MarcajeController controlador;
    private final String nombreUsuario;

    public MarcajeView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.controlador = new MarcajeController();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setTitle("Marcaje");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 10, 10));

        agregarBoton("Marcar Entrada", TipoMarcaje.ENTRADA);
        agregarBoton("Marcar Primer Descanso", TipoMarcaje.DESCANSO_1);
        agregarBoton("Marcar Segundo Descanso", TipoMarcaje.DESCANSO_2);
        agregarBoton("Marcar Salida", TipoMarcaje.SALIDA);

        JButton botonInformacion = new JButton("Informacion del Marcaje");
        botonInformacion.addActionListener(evento -> new InformacionMarcajeView(nombreUsuario).setVisible(true));
        add(botonInformacion);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        add(botonRegresar);
    }

    private void agregarBoton(String texto, TipoMarcaje tipo) {
        JButton boton = new JButton(texto);
        boton.addActionListener(evento -> marcar(tipo));
        add(boton);
    }

    private void marcar(TipoMarcaje tipo) {
        try {
            controlador.registrarMarcaje(nombreUsuario, tipo);
            JOptionPane.showMessageDialog(this, "Marcaje registrado correctamente");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
