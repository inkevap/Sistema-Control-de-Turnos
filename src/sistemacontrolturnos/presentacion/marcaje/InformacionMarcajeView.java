package sistemacontrolturnos.presentacion.marcaje;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sistemacontrolturnos.controlador.MarcajeController;
import sistemacontrolturnos.entidad.Marcaje;
import sistemacontrolturnos.entidad.TipoMarcaje;

public class InformacionMarcajeView extends JFrame {

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    public InformacionMarcajeView(String nombreUsuario) {
        construirInterfaz(nombreUsuario);
    }

    private void construirInterfaz(String nombreUsuario) {
        setTitle("Informacion del Marcaje");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        MarcajeController controlador = new MarcajeController();
        List<Marcaje> marcajesDeHoy = controlador.obtenerMarcajesDelDia(nombreUsuario);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Usuario", "Entrada", "1er Descanso", "2do Descanso", "Salida"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modelo.addRow(new Object[]{
            nombreUsuario,
            obtenerHora(marcajesDeHoy, TipoMarcaje.ENTRADA),
            obtenerHora(marcajesDeHoy, TipoMarcaje.DESCANSO_1),
            obtenerHora(marcajesDeHoy, TipoMarcaje.DESCANSO_2),
            obtenerHora(marcajesDeHoy, TipoMarcaje.SALIDA)
        });

        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.addActionListener(evento -> dispose());
        add(botonRegresar, BorderLayout.SOUTH);
    }

    private String obtenerHora(List<Marcaje> marcajes, TipoMarcaje tipo) {
        for (Marcaje marcaje : marcajes) {
            if (marcaje.getTipo() == tipo) {
                return marcaje.getFechaHora().format(FORMATO_HORA);
            }
        }
        return "-";
    }
}
