package sistemacontrolturnos.servicio;

import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.dao.ISolicitudEmpleadoDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.entidad.EstadoSolicitud;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;
import sistemacontrolturnos.entidad.Usuario;

public class SolicitudEmpleadoServiceImpl implements ISolicitudEmpleadoService {

    private final ISolicitudEmpleadoDAO solicitudDAO;
    private final IUsuarioDAO usuarioDAO;
    private final IBitacoraService bitacoraService;
    private final ICorreoService correoService;

    public SolicitudEmpleadoServiceImpl(ISolicitudEmpleadoDAO solicitudDAO, IUsuarioDAO usuarioDAO,
            IBitacoraService bitacoraService, ICorreoService correoService) {
        this.solicitudDAO = solicitudDAO;
        this.usuarioDAO = usuarioDAO;
        this.bitacoraService = bitacoraService;
        this.correoService = correoService;
    }

    @Override
    public List<SolicitudGestionEmpleado> listarPendientesRRHH() {
        List<SolicitudGestionEmpleado> resultado = new ArrayList<>();
        for (SolicitudGestionEmpleado solicitud : solicitudDAO.listarTodos()) {
            if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE_RRHH) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }

    @Override
    public List<SolicitudGestionEmpleado> listarResueltas() {
        List<SolicitudGestionEmpleado> resultado = new ArrayList<>();
        for (SolicitudGestionEmpleado solicitud : solicitudDAO.listarTodos()) {
            // Se filtran las solicitudes ya respondidas
            if (solicitud.getEstado() == EstadoSolicitud.APROBADA
                    || solicitud.getEstado() == EstadoSolicitud.RECHAZADA) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }

    @Override
    public void aprobarPorRRHH(int idSolicitud, String nombreUsuarioAdmin) {
        SolicitudGestionEmpleado solicitud = obtenerPendienteORechazarDuplicado(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.APROBADA);
        solicitud.setProcesadoPor(nombreUsuarioAdmin);
        solicitudDAO.actualizar(solicitud);
        notificar(solicitud, "APROBADA");
        bitacoraService.registrar(nombreUsuarioAdmin,
                "RRHH aprobo la solicitud #" + idSolicitud + " del usuario " + solicitud.getNombreUsuarioEmpleado());
    }

    @Override
    public void rechazarPorRRHH(int idSolicitud, String nombreUsuarioAdmin) {
        SolicitudGestionEmpleado solicitud = obtenerPendienteORechazarDuplicado(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setProcesadoPor(nombreUsuarioAdmin);
        solicitudDAO.actualizar(solicitud);
        notificar(solicitud, "RECHAZADA");
        bitacoraService.registrar(nombreUsuarioAdmin,
                "RRHH rechazo la solicitud #" + idSolicitud + " del usuario " + solicitud.getNombreUsuarioEmpleado());
    }

    private SolicitudGestionEmpleado obtenerPendienteORechazarDuplicado(int idSolicitud) {
        SolicitudGestionEmpleado solicitud = solicitudDAO.buscarPorId(idSolicitud);
        if (solicitud == null) {
            throw new IllegalStateException("La solicitud no existe");
        }
        // RN02: si ya no esta pendiente de RRHH, otro administrador ya la proceso
        // Esta logica esta pendiente de ser revisada porque al ser resuelta se mueve automaticamente a otro estado
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE_RRHH) {
            throw new IllegalStateException("Esta solicitud ya esta siendo procesada por otro administrador RHH");
        }
        return solicitud;
    }

    private void notificar(SolicitudGestionEmpleado solicitud, String resultado) {
        Usuario empleado = usuarioDAO.buscarPorUsuario(solicitud.getNombreUsuarioEmpleado());
        if (empleado == null) {
            return;
        }
        correoService.enviarCorreo(empleado.getCorreo(), "Respuesta a tu solicitud",
                "Hola " + empleado.getNombreCompleto() + ",\n\nTu solicitud de " + solicitud.getTipoGestion()
                + " ha sido " + resultado + " por Recursos Humanos.");
    }
}
