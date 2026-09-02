package sistemacontrolturnos.servicio;

import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.dao.ISolicitudCambioTurnoDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.entidad.EstadoSolicitudTurno;
import sistemacontrolturnos.entidad.SolicitudCambioTurno;
import sistemacontrolturnos.entidad.Usuario;

public class SolicitudCambioTurnoServiceImpl implements ISolicitudCambioTurnoService {

    private final ISolicitudCambioTurnoDAO solicitudDAO;
    private final IUsuarioDAO usuarioDAO;
    private final IBitacoraService bitacoraService;
    private final ICorreoService correoService;

    public SolicitudCambioTurnoServiceImpl(ISolicitudCambioTurnoDAO solicitudDAO, IUsuarioDAO usuarioDAO,
            IBitacoraService bitacoraService, ICorreoService correoService) {
        this.solicitudDAO = solicitudDAO;
        this.usuarioDAO = usuarioDAO;
        this.bitacoraService = bitacoraService;
        this.correoService = correoService;
    }

    @Override
    public List<SolicitudCambioTurno> listarPendientes() {
        List<SolicitudCambioTurno> resultado = new ArrayList<>();
        for (SolicitudCambioTurno solicitud : solicitudDAO.listarTodos()) {
            if (solicitud.getEstado() == EstadoSolicitudTurno.PENDIENTE_APROBAR) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }

    @Override
    public List<SolicitudCambioTurno> listarResueltas() {
        List<SolicitudCambioTurno> resultado = new ArrayList<>();
        for (SolicitudCambioTurno solicitud : solicitudDAO.listarTodos()) {
            if (solicitud.getEstado() == EstadoSolicitudTurno.APROBADO
                    || solicitud.getEstado() == EstadoSolicitudTurno.RECHAZADO) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }

    @Override
    public void aprobar(int idSolicitud, String nombreUsuarioAdmin) {
        SolicitudCambioTurno solicitud = obtenerPendiente(idSolicitud);
        solicitud.setEstado(EstadoSolicitudTurno.APROBADO);
        solicitud.setProcesadoPor(nombreUsuarioAdmin);
        solicitudDAO.actualizar(solicitud);

        // El sistema realiza el cambio de turno real: se actualiza el turno del empleado.
        Usuario empleado = usuarioDAO.buscarPorUsuario(solicitud.getNombreUsuarioEmpleado());
        if (empleado != null) {
            empleado.setTurno(solicitud.getTurnoNuevo());
            usuarioDAO.actualizar(empleado);
            correoService.enviarCorreo(empleado.getCorreo(), "Cambio de turno aprobado",
                    "Hola " + empleado.getNombreCompleto() + ",\n\nTu cambio de turno a "
                    + solicitud.getTurnoNuevo() + " fue APROBADO.");
        }

        bitacoraService.registrar(nombreUsuarioAdmin, "Aprobo el cambio de turno #" + idSolicitud
                + " del usuario " + solicitud.getNombreUsuarioEmpleado() + " a " + solicitud.getTurnoNuevo());
    }

    @Override
    public void rechazar(int idSolicitud, String nombreUsuarioAdmin) {
        SolicitudCambioTurno solicitud = obtenerPendiente(idSolicitud);
        solicitud.setEstado(EstadoSolicitudTurno.RECHAZADO);
        solicitud.setProcesadoPor(nombreUsuarioAdmin);
        solicitudDAO.actualizar(solicitud);

        Usuario empleado = usuarioDAO.buscarPorUsuario(solicitud.getNombreUsuarioEmpleado());
        if (empleado != null) {
            correoService.enviarCorreo(empleado.getCorreo(), "Cambio de turno rechazado",
                    "Hola " + empleado.getNombreCompleto() + ",\n\nTu cambio de turno a "
                    + solicitud.getTurnoNuevo() + " fue RECHAZADO.");
        }

        bitacoraService.registrar(nombreUsuarioAdmin, "Rechazo el cambio de turno #" + idSolicitud
                + " del usuario " + solicitud.getNombreUsuarioEmpleado());
    }

    // RN02: si la solicitud ya no esta pendiente, otro admin ya la proceso.
    private SolicitudCambioTurno obtenerPendiente(int idSolicitud) {
        SolicitudCambioTurno solicitud = solicitudDAO.buscarPorId(idSolicitud);
        if (solicitud == null) {
            throw new IllegalStateException("La solicitud no existe");
        }
        if (solicitud.getEstado() != EstadoSolicitudTurno.PENDIENTE_APROBAR) {
            throw new IllegalStateException("Esta solicitud ya esta siendo procesada por otro administrador");
        }
        return solicitud;
    }
}
