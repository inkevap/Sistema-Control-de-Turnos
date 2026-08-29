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
        // esto va a generar ID duplicado si se borra una linea, porque solo esta contando las 
        // lineas existentes y le suma uno
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
        // Utilizamos Regex, tomamos en cuenta que \ es un caracter especial que 
        // utilizamos para referirnos a un caracter de texto y no a su uso especial
        // en este caso a \\ le concatenamos el delimitador,
        // Esto con el fin de que el delimitador no sea tomado como un caracter especial
        // o como OR en el REGEX de split()
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
