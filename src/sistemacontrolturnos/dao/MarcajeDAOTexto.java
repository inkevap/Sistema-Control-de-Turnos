package sistemacontrolturnos.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.entidad.Marcaje;
import sistemacontrolturnos.entidad.TipoMarcaje;
import sistemacontrolturnos.util.Constantes;
import sistemacontrolturnos.util.ManejadorArchivos;

public class MarcajeDAOTexto implements IMarcajeDAO {

    @Override
    public void guardar(Marcaje marcaje) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_MARCAJES);
        int siguienteId = lineas.size() + 1;
        marcaje.setIdMarcaje(siguienteId);
        ManejadorArchivos.agregarLinea(Constantes.ARCHIVO_MARCAJES, construirLinea(marcaje));
    }

    @Override
    public List<Marcaje> listarPorUsuarioYFecha(String nombreUsuario, LocalDate fecha) {
        List<Marcaje> resultado = new ArrayList<>();
        for (Marcaje marcaje : listarTodos()) {
            if (marcaje.getNombreUsuario().equalsIgnoreCase(nombreUsuario)
                    && marcaje.getFechaHora().toLocalDate().equals(fecha)) {
                resultado.add(marcaje);
            }
        }
        return resultado;
    }

    @Override
    public List<Marcaje> listarTodos() {
        List<Marcaje> resultado = new ArrayList<>();
        for (String linea : ManejadorArchivos.leerLineas(Constantes.ARCHIVO_MARCAJES)) {
            resultado.add(parsearLinea(linea));
        }
        return resultado;
    }

    private Marcaje parsearLinea(String linea) {
        String[] campos = linea.split("\\" + Constantes.DELIMITADOR, -1);
        Marcaje marcaje = new Marcaje();
        marcaje.setIdMarcaje(Integer.parseInt(campos[0]));
        marcaje.setNombreUsuario(campos[1]);
        marcaje.setTipo(TipoMarcaje.valueOf(campos[2]));
        marcaje.setFechaHora(LocalDateTime.parse(campos[3]));
        marcaje.setEntradaTardia(Boolean.parseBoolean(campos[4]));
        return marcaje;
    }

    private String construirLinea(Marcaje marcaje) {
        return String.join(Constantes.DELIMITADOR,
                String.valueOf(marcaje.getIdMarcaje()),
                marcaje.getNombreUsuario(),
                marcaje.getTipo().name(),
                marcaje.getFechaHora().toString(),
                String.valueOf(marcaje.isEntradaTardia()));
    }
}
