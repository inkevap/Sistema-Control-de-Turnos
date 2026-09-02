package sistemacontrolturnos.dao;

import java.util.List;
import sistemacontrolturnos.entidad.SolicitudCambioTurno;

public interface ISolicitudCambioTurnoDAO {

    void guardar(SolicitudCambioTurno solicitud);

    void actualizar(SolicitudCambioTurno solicitud);

    SolicitudCambioTurno buscarPorId(int idSolicitud);

    List<SolicitudCambioTurno> listarTodos();
}
