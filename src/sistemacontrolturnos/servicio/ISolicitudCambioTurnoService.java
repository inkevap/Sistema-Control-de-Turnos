package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.entidad.SolicitudCambioTurno;

public interface ISolicitudCambioTurnoService {

    List<SolicitudCambioTurno> listarPendientes();

    List<SolicitudCambioTurno> listarResueltas();

    // Aprobar: cambia el estado a APROBADO y aplica el cambio de turno al empleado.
    void aprobar(int idSolicitud, String nombreUsuarioAdmin);

    // Rechazar: cambia el estado a RECHAZADO.
    void rechazar(int idSolicitud, String nombreUsuarioAdmin);
}
