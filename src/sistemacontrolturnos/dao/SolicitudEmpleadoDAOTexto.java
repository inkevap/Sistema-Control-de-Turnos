package sistemacontrolturnos.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.entidad.EstadoSolicitud;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;
import sistemacontrolturnos.entidad.TipoGestion;
import sistemacontrolturnos.util.Constantes;
import sistemacontrolturnos.util.ManejadorArchivos;

public class SolicitudEmpleadoDAOTexto implements ISolicitudEmpleadoDAO {

    @Override
    public void guardar(SolicitudGestionEmpleado solicitud) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_SOLICITUDES_EMPLEADO);
        int siguienteId = lineas.size() + 1;
        solicitud.setIdSolicitud(siguienteId);
        ManejadorArchivos.agregarLinea(Constantes.ARCHIVO_SOLICITUDES_EMPLEADO, construirLinea(solicitud));
    }

    @Override
    public void actualizar(SolicitudGestionEmpleado solicitudActualizada) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_SOLICITUDES_EMPLEADO);
        List<String> nuevasLineas = new ArrayList<>();
        for (String linea : lineas) {
            SolicitudGestionEmpleado actual = parsearLinea(linea);
            if (actual.getIdSolicitud() == solicitudActualizada.getIdSolicitud()) {
                nuevasLineas.add(construirLinea(solicitudActualizada));
            } else {
                nuevasLineas.add(linea);
            }
        }
        ManejadorArchivos.escribirTodasLasLineas(Constantes.ARCHIVO_SOLICITUDES_EMPLEADO, nuevasLineas);
    }

    @Override
    public SolicitudGestionEmpleado buscarPorId(int idSolicitud) {
        for (SolicitudGestionEmpleado solicitud : listarTodos()) {
            if (solicitud.getIdSolicitud() == idSolicitud) {
                return solicitud;
            }
        }
        return null;
    }

    @Override
    public List<SolicitudGestionEmpleado> listarTodos() {
        List<SolicitudGestionEmpleado> resultado = new ArrayList<>();
        for (String linea : ManejadorArchivos.leerLineas(Constantes.ARCHIVO_SOLICITUDES_EMPLEADO)) {
            resultado.add(parsearLinea(linea));
        }
        return resultado;
    }

    private SolicitudGestionEmpleado parsearLinea(String linea) {
        String[] campos = linea.split("\\" + Constantes.DELIMITADOR, -1);
        SolicitudGestionEmpleado solicitud = new SolicitudGestionEmpleado();
        solicitud.setIdSolicitud(Integer.parseInt(campos[0]));
        solicitud.setNombreUsuarioEmpleado(campos[1]);
        solicitud.setTipoGestion(TipoGestion.valueOf(campos[2]));
        solicitud.setFechaInicio(LocalDate.parse(campos[3]));
        solicitud.setFechaFin(LocalDate.parse(campos[4]));
        solicitud.setMotivo(campos[5]);
        solicitud.setEstado(EstadoSolicitud.valueOf(campos[6]));
        return solicitud;
    }

    private String construirLinea(SolicitudGestionEmpleado solicitud) {
        return String.join(Constantes.DELIMITADOR,
                String.valueOf(solicitud.getIdSolicitud()),
                solicitud.getNombreUsuarioEmpleado(),
                solicitud.getTipoGestion().name(),
                solicitud.getFechaInicio().toString(),
                solicitud.getFechaFin().toString(),
                solicitud.getMotivo(),
                solicitud.getEstado().name());
    }
}
