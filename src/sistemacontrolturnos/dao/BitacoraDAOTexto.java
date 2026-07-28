package sistemacontrolturnos.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.entidad.Bitacora;
import sistemacontrolturnos.util.Constantes;
import sistemacontrolturnos.util.ManejadorArchivos;

public class BitacoraDAOTexto implements IBitacoraDAO {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void guardar(Bitacora bitacora) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_BITACORA);
        int siguienteId = lineas.size() + 1;
        bitacora.setIdBitacora(siguienteId);

        String linea = String.join(Constantes.DELIMITADOR,
                String.valueOf(bitacora.getIdBitacora()),
                bitacora.getNombreUsuario(),
                bitacora.getAccion(),
                bitacora.getFechaHora().format(FORMATO_FECHA));

        ManejadorArchivos.agregarLinea(Constantes.ARCHIVO_BITACORA, linea);
    }

    @Override
    public List<Bitacora> listarTodos() {
        List<Bitacora> resultado = new ArrayList<>();
        for (String linea : ManejadorArchivos.leerLineas(Constantes.ARCHIVO_BITACORA)) {
            String[] campos = linea.split("\\" + Constantes.DELIMITADOR, -1);
            Bitacora bitacora = new Bitacora();
            bitacora.setIdBitacora(Integer.parseInt(campos[0]));
            bitacora.setNombreUsuario(campos[1]);
            bitacora.setAccion(campos[2]);
            bitacora.setFechaHora(LocalDateTime.parse(campos[3], FORMATO_FECHA));
            resultado.add(bitacora);
        }
        return resultado;
    }
}
