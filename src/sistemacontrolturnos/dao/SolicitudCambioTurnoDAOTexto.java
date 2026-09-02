package sistemacontrolturnos.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.entidad.EstadoSolicitudTurno;
import sistemacontrolturnos.entidad.SolicitudCambioTurno;
import sistemacontrolturnos.entidad.TipoTurno;
import sistemacontrolturnos.util.Constantes;
import sistemacontrolturnos.util.ManejadorArchivos;

/**
 * Persistencia de solicitudes de cambio de turno en data/solicitudes_turno.txt.
 * Formato: id|empleado|fechaInicial|turnoInicial|fechaNueva|turnoNuevo|justificacion|estado|procesadoPor
 */
public class SolicitudCambioTurnoDAOTexto implements ISolicitudCambioTurnoDAO {

    @Override
    public void guardar(SolicitudCambioTurno solicitud) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_SOLICITUDES_TURNO);
        int siguienteId = lineas.size() + 1;
        solicitud.setIdSolicitud(siguienteId);
        ManejadorArchivos.agregarLinea(Constantes.ARCHIVO_SOLICITUDES_TURNO, construirLinea(solicitud));
    }

    @Override
    public void actualizar(SolicitudCambioTurno solicitudActualizada) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_SOLICITUDES_TURNO);
        List<String> nuevasLineas = new ArrayList<>();
        for (String linea : lineas) {
            SolicitudCambioTurno actual = parsearLinea(linea);
            if (actual.getIdSolicitud() == solicitudActualizada.getIdSolicitud()) {
                nuevasLineas.add(construirLinea(solicitudActualizada));
            } else {
                nuevasLineas.add(linea);
            }
        }
        ManejadorArchivos.escribirTodasLasLineas(Constantes.ARCHIVO_SOLICITUDES_TURNO, nuevasLineas);
    }

    @Override
    public SolicitudCambioTurno buscarPorId(int idSolicitud) {
        for (SolicitudCambioTurno solicitud : listarTodos()) {
            if (solicitud.getIdSolicitud() == idSolicitud) {
                return solicitud;
            }
        }
        return null;
    }

    @Override
    public List<SolicitudCambioTurno> listarTodos() {
        List<SolicitudCambioTurno> resultado = new ArrayList<>();
        for (String linea : ManejadorArchivos.leerLineas(Constantes.ARCHIVO_SOLICITUDES_TURNO)) {
            resultado.add(parsearLinea(linea));
        }
        return resultado;
    }

    private SolicitudCambioTurno parsearLinea(String linea) {
        String[] campos = linea.split("\\" + Constantes.DELIMITADOR, -1);
        SolicitudCambioTurno solicitud = new SolicitudCambioTurno();
        solicitud.setIdSolicitud(Integer.parseInt(campos[0]));
        solicitud.setNombreUsuarioEmpleado(campos[1]);
        solicitud.setFechaInicial(LocalDate.parse(campos[2]));
        solicitud.setTurnoInicial(TipoTurno.valueOf(campos[3]));
        solicitud.setFechaNueva(LocalDate.parse(campos[4]));
        solicitud.setTurnoNuevo(TipoTurno.valueOf(campos[5]));
        solicitud.setJustificacion(campos[6]);
        solicitud.setEstado(EstadoSolicitudTurno.valueOf(campos[7]));
        solicitud.setProcesadoPor(campos.length > 8 ? campos[8] : "");
        return solicitud;
    }

    private String construirLinea(SolicitudCambioTurno solicitud) {
        String procesadoPor = solicitud.getProcesadoPor() == null ? "" : solicitud.getProcesadoPor();
        return String.join(Constantes.DELIMITADOR,
                String.valueOf(solicitud.getIdSolicitud()),
                solicitud.getNombreUsuarioEmpleado(),
                solicitud.getFechaInicial().toString(),
                solicitud.getTurnoInicial().name(),
                solicitud.getFechaNueva().toString(),
                solicitud.getTurnoNuevo().name(),
                solicitud.getJustificacion(),
                solicitud.getEstado().name(),
                procesadoPor);
    }
}
