package sistemacontrolturnos.dao;

import java.util.List;
import sistemacontrolturnos.entidad.AsignacionTurno;

public interface IAsignacionTurnoDAO {

    void guardar(AsignacionTurno asignacion);

    List<AsignacionTurno> listarTodos();

    List<AsignacionTurno> listarPorEmpleado(String nombreUsuarioEmpleado);
}
