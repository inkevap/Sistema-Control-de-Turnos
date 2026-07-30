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
    public void aprobarPorRRHH(int idSolicitud) {
        SolicitudGestionEmpleado solicitud = obtenerPendienteORechazarDuplicado(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.APROBADA);
        solicitudDAO.actualizar(solicitud);
        notificar(solicitud, "APROBADA");
        bitacoraService.registrar(solicitud.getNombreUsuarioEmpleado(),
                "RRHH aprobo la solicitud #" + idSolicitud);
    }

    @Override
    public void rechazarPorRRHH(int idSolicitud) {
        SolicitudGestionEmpleado solicitud = obtenerPendienteORechazarDuplicado(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitudDAO.actualizar(solicitud);
        notificar(solicitud, "RECHAZADA");
        bitacoraService.registrar(solicitud.getNombreUsuarioEmpleado(),
                "RRHH rechazo la solicitud #" + idSolicitud);
    }

    private SolicitudGestionEmpleado obtenerPendienteORechazarDuplicado(int idSolicitud) {
        SolicitudGestionEmpleado solicitud = solicitudDAO.buscarPorId(idSolicitud);
        if (solicitud == null) {
            throw new IllegalStateException("La solicitud no existe");
        }
        // RN02: si ya no esta pendiente de RRHH, otro administrador ya la proceso
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
