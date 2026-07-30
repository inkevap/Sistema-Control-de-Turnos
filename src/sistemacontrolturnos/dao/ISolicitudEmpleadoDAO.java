package sistemacontrolturnos.dao;

import java.util.List;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;

public interface ISolicitudEmpleadoDAO {

    void guardar(SolicitudGestionEmpleado solicitud);

    void actualizar(SolicitudGestionEmpleado solicitud);

    SolicitudGestionEmpleado buscarPorId(int idSolicitud);

    List<SolicitudGestionEmpleado> listarTodos();
}
