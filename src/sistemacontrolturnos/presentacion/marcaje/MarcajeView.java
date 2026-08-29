package sistemacontrolturnos.presentacion.marcaje;

import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import sistemacontrolturnos.controlador.MarcajeController;
import sistemacontrolturnos.entidad.TipoMarcaje;

public class MarcajeView extends JFrame {

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final MarcajeController controlador;
    private final String nombreUsuario;
    private final JLabel labelReloj = new JLabel("", SwingConstants.CENTER);
    private final Timer timerReloj = new Timer(1000, evento -> actualizarReloj()); // funcion para actualizar el reloj

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

        labelReloj.setFont(labelReloj.getFont().deriveFont(Font.BOLD, 20f));
        actualizarReloj();
        add(labelReloj);
        timerReloj.start();

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

    private void actualizarReloj() {
        labelReloj.setText(LocalTime.now().format(FORMATO_HORA));
    }

    @Override
    public void dispose() {
        timerReloj.stop();
        super.dispose();
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
