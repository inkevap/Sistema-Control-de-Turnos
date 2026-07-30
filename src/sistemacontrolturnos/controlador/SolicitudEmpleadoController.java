package sistemacontrolturnos.controlador;

import java.util.List;
import sistemacontrolturnos.dao.BitacoraDAOTexto;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.dao.ISolicitudEmpleadoDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dao.SolicitudEmpleadoDAOTexto;
import sistemacontrolturnos.dao.UsuarioDAOTexto;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;
import sistemacontrolturnos.servicio.BitacoraServiceImpl;
import sistemacontrolturnos.servicio.CorreoServiceImpl;
import sistemacontrolturnos.servicio.IBitacoraService;
import sistemacontrolturnos.servicio.ICorreoService;
import sistemacontrolturnos.servicio.ISolicitudEmpleadoService;
import sistemacontrolturnos.servicio.SolicitudEmpleadoServiceImpl;

public class SolicitudEmpleadoController {

    private final ISolicitudEmpleadoService solicitudService;

    public SolicitudEmpleadoController() {
        ISolicitudEmpleadoDAO solicitudDAO = new SolicitudEmpleadoDAOTexto();
        IUsuarioDAO usuarioDAO = new UsuarioDAOTexto();
        IBitacoraDAO bitacoraDAO = new BitacoraDAOTexto();
        IBitacoraService bitacoraService = new BitacoraServiceImpl(bitacoraDAO);
        ICorreoService correoService = new CorreoServiceImpl();
        this.solicitudService = new SolicitudEmpleadoServiceImpl(solicitudDAO, usuarioDAO, bitacoraService, correoService);
    }

    public List<SolicitudGestionEmpleado> listarPendientesRRHH() {
        return solicitudService.listarPendientesRRHH();
    }

    public void aprobar(int idSolicitud) {
        solicitudService.aprobarPorRRHH(idSolicitud);
    }

    public void rechazar(int idSolicitud) {
        solicitudService.rechazarPorRRHH(idSolicitud);
    }
}
