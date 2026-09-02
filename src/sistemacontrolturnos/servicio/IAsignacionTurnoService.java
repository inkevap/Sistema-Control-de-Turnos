package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.dto.AsignacionTurnoDTO;
import sistemacontrolturnos.entidad.Usuario;

public interface IAsignacionTurnoService {

    // Asigna un turno validando las reglas de negocio del CU3 (RN02, RN03, RN04).
    // adminUsuario es el administrador de area logueado que realiza la asignacion.
    void asignar(AsignacionTurnoDTO asignacion, String adminUsuario);

    // Empleados activos que tienen al administrador dado como supervisor (RN03).
    List<Usuario> listarEmpleadosDe(String adminUsuario);

    // Usuarios que pueden fungir como supervisor de turno (ADMIN_AREA / ADMIN_RRHH).
    List<Usuario> listarSupervisores();
}
