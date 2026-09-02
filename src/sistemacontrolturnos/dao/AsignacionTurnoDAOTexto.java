package sistemacontrolturnos.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.entidad.AsignacionTurno;
import sistemacontrolturnos.entidad.TipoTurno;
import sistemacontrolturnos.util.Constantes;
import sistemacontrolturnos.util.ManejadorArchivos;

/**
 * Persistencia de asignaciones de turno en data/turnos.txt.
 * Formato de linea: id|empleado|fechaInicio|fechaFin|turno|supervisor|asignadoPor
 */
public class AsignacionTurnoDAOTexto implements IAsignacionTurnoDAO {

    @Override
    public void guardar(AsignacionTurno asignacion) {
        List<String> lineas = ManejadorArchivos.leerLineas(Constantes.ARCHIVO_TURNOS);
        int siguienteId = lineas.size() + 1;
        asignacion.setIdAsignacion(siguienteId);
        ManejadorArchivos.agregarLinea(Constantes.ARCHIVO_TURNOS, construirLinea(asignacion));
    }

    @Override
    public List<AsignacionTurno> listarTodos() {
        List<AsignacionTurno> resultado = new ArrayList<>();
        for (String linea : ManejadorArchivos.leerLineas(Constantes.ARCHIVO_TURNOS)) {
            resultado.add(parsearLinea(linea));
        }
        return resultado;
    }

    @Override
    public List<AsignacionTurno> listarPorEmpleado(String nombreUsuarioEmpleado) {
        List<AsignacionTurno> resultado = new ArrayList<>();
        for (AsignacionTurno asignacion : listarTodos()) {
            if (asignacion.getNombreUsuarioEmpleado().equalsIgnoreCase(nombreUsuarioEmpleado)) {
                resultado.add(asignacion);
            }
        }
        return resultado;
    }

    private AsignacionTurno parsearLinea(String linea) {
        String[] campos = linea.split("\\" + Constantes.DELIMITADOR, -1);
        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setIdAsignacion(Integer.parseInt(campos[0]));
        asignacion.setNombreUsuarioEmpleado(campos[1]);
        asignacion.setFechaInicio(LocalDate.parse(campos[2]));
        asignacion.setFechaFin(LocalDate.parse(campos[3]));
        asignacion.setTurno(TipoTurno.valueOf(campos[4]));
        asignacion.setSupervisorUsuario(campos[5]);
        asignacion.setAsignadoPor(campos[6]);
        return asignacion;
    }

    private String construirLinea(AsignacionTurno asignacion) {
        return String.join(Constantes.DELIMITADOR,
                String.valueOf(asignacion.getIdAsignacion()),
                asignacion.getNombreUsuarioEmpleado(),
                asignacion.getFechaInicio().toString(),
                asignacion.getFechaFin().toString(),
                asignacion.getTurno().name(),
                asignacion.getSupervisorUsuario(),
                asignacion.getAsignadoPor());
    }
}
