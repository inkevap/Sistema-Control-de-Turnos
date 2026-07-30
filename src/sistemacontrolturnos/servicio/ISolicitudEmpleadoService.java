package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;

public interface ISolicitudEmpleadoService {

    List<SolicitudGestionEmpleado> listarPendientesRRHH();

    void aprobarPorRRHH(int idSolicitud);

    void rechazarPorRRHH(int idSolicitud);
}
