package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.entidad.SolicitudGestionEmpleado;

public interface ISolicitudEmpleadoService {

    // --- Etapa 1: Administrador de Area (CU4, FA03/FA04) ---
    List<SolicitudGestionEmpleado> listarPendientesArea();

    // Aprobar por area: la solicitud pasa a PENDIENTE_RRHH (se envia a RRHH).
    void aprobarPorArea(int idSolicitud, String nombreUsuarioAdmin);

    // Rechazar por area: la solicitud pasa a RECHAZADA (fin del flujo).
    void rechazarPorArea(int idSolicitud, String nombreUsuarioAdmin);

    // --- Etapa 2: Administrador de RRHH (CU1) ---
    List<SolicitudGestionEmpleado> listarPendientesRRHH();

    List<SolicitudGestionEmpleado> listarResueltas();

    void aprobarPorRRHH(int idSolicitud, String nombreUsuarioAdmin);

    void rechazarPorRRHH(int idSolicitud, String nombreUsuarioAdmin);
}
