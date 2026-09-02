package sistemacontrolturnos.controlador;

import java.util.List;
import sistemacontrolturnos.dao.BitacoraDAOTexto;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.dao.ISolicitudCambioTurnoDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dao.SolicitudCambioTurnoDAOTexto;
import sistemacontrolturnos.dao.UsuarioDAOTexto;
import sistemacontrolturnos.entidad.SolicitudCambioTurno;
import sistemacontrolturnos.servicio.BitacoraServiceImpl;
import sistemacontrolturnos.servicio.CorreoServiceImpl;
import sistemacontrolturnos.servicio.IBitacoraService;
import sistemacontrolturnos.servicio.ICorreoService;
import sistemacontrolturnos.servicio.ISolicitudCambioTurnoService;
import sistemacontrolturnos.servicio.SolicitudCambioTurnoServiceImpl;

public class SolicitudCambioTurnoController {

    private final ISolicitudCambioTurnoService solicitudService;

    public SolicitudCambioTurnoController() {
        ISolicitudCambioTurnoDAO solicitudDAO = new SolicitudCambioTurnoDAOTexto();
        IUsuarioDAO usuarioDAO = new UsuarioDAOTexto();
        IBitacoraDAO bitacoraDAO = new BitacoraDAOTexto();
        IBitacoraService bitacoraService = new BitacoraServiceImpl(bitacoraDAO);
        ICorreoService correoService = new CorreoServiceImpl();
        this.solicitudService = new SolicitudCambioTurnoServiceImpl(solicitudDAO, usuarioDAO,
                bitacoraService, correoService);
    }

    public List<SolicitudCambioTurno> listarPendientes() {
        return solicitudService.listarPendientes();
    }

    public List<SolicitudCambioTurno> listarResueltas() {
        return solicitudService.listarResueltas();
    }

    public void aprobar(int idSolicitud, String nombreUsuarioAdmin) {
        solicitudService.aprobar(idSolicitud, nombreUsuarioAdmin);
    }

    public void rechazar(int idSolicitud, String nombreUsuarioAdmin) {
        solicitudService.rechazar(idSolicitud, nombreUsuarioAdmin);
    }
}
