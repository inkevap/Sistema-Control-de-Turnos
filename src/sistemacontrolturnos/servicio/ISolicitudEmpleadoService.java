package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;

public interface ISolicitudEmpleadoService {

    List<SolicitudGestionEmpleado> listarPendientesRRHH();

    List<SolicitudGestionEmpleado> listarResueltas();

    void aprobarPorRRHH(int idSolicitud, String nombreUsuarioAdmin);

    void rechazarPorRRHH(int idSolicitud, String nombreUsuarioAdmin);
}
