package sistemacontrolturnos.servicio;

import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.dao.IAsignacionTurnoDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dto.AsignacionTurnoDTO;
import sistemacontrolturnos.entidad.AsignacionTurno;
import sistemacontrolturnos.entidad.EstadoUsuario;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.TipoTurno;
import sistemacontrolturnos.entidad.Usuario;

public class AsignacionTurnoServiceImpl implements IAsignacionTurnoService {

    private final IAsignacionTurnoDAO asignacionDAO;
    private final IUsuarioDAO usuarioDAO;
    private final IBitacoraService bitacoraService;

    public AsignacionTurnoServiceImpl(IAsignacionTurnoDAO asignacionDAO, IUsuarioDAO usuarioDAO,
            IBitacoraService bitacoraService) {
        this.asignacionDAO = asignacionDAO;
        this.usuarioDAO = usuarioDAO;
        this.bitacoraService = bitacoraService;
    }

    @Override
    public void asignar(AsignacionTurnoDTO dto, String adminUsuario) {
        // Validaciones basicas de los campos del formulario.
        if (dto.getNombreUsuarioEmpleado() == null || dto.getNombreUsuarioEmpleado().isEmpty()) {
            throw new IllegalStateException("Debe seleccionar un empleado");
        }
        if (dto.getSupervisorUsuario() == null || dto.getSupervisorUsuario().isEmpty()) {
            throw new IllegalStateException("Debe seleccionar un supervisor");
        }
        if (dto.getTurno() == null) {
            throw new IllegalStateException("Debe seleccionar un turno");
        }
        if (dto.getFechaInicio() == null || dto.getFechaFin() == null) {
            throw new IllegalStateException("Debe indicar la fecha de inicio y la fecha fin");
        }
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new IllegalStateException("La fecha fin no puede ser anterior a la fecha de inicio");
        }

        // RN02: los turnos asignables son Matutino, Vespertino y Diurno (segun la
        // pantalla del CU3). El turno es de 8 horas fijas para todos.
        if (!esTurnoAsignable(dto.getTurno())) {
            throw new IllegalStateException("El turno seleccionado no es asignable (solo Matutino, Vespertino o Diurno)");
        }

        Usuario empleado = usuarioDAO.buscarPorUsuario(dto.getNombreUsuarioEmpleado());
        if (empleado == null) {
            throw new IllegalStateException("El empleado no existe");
        }

        // RN03: el admin de area solo puede asignar turnos a empleados que lo tengan
        // como supervisor.
        if (empleado.getSupervisorUsuario() == null
                || !empleado.getSupervisorUsuario().equalsIgnoreCase(adminUsuario)) {
            throw new IllegalStateException("Solo puede asignar turnos a empleados que lo tengan como supervisor");
        }

        Usuario supervisor = usuarioDAO.buscarPorUsuario(dto.getSupervisorUsuario());
        if (supervisor == null) {
            throw new IllegalStateException("El supervisor no existe");
        }

        // RN04.1: el supervisor debe pertenecer a la misma area del empleado.
        if (supervisor.getArea() == null || !supervisor.getArea().equalsIgnoreCase(empleado.getArea())) {
            throw new IllegalStateException("El supervisor debe pertenecer a la misma area del empleado");
        }

        // RN04.2: el supervisor debe corresponder al turno seleccionado.
        if (supervisor.getTurno() != dto.getTurno()) {
            throw new IllegalStateException("El supervisor no corresponde al turno seleccionado");
        }

        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setNombreUsuarioEmpleado(empleado.getNombreUsuario());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFin(dto.getFechaFin());
        asignacion.setTurno(dto.getTurno());
        asignacion.setSupervisorUsuario(supervisor.getNombreUsuario());
        asignacion.setAsignadoPor(adminUsuario);
        asignacionDAO.guardar(asignacion);

        bitacoraService.registrar(adminUsuario, "Asigno el turno " + dto.getTurno()
                + " al empleado " + empleado.getNombreUsuario() + " (" + dto.getFechaInicio()
                + " a " + dto.getFechaFin() + ")");
    }

    @Override
    public List<Usuario> listarEmpleadosDe(String adminUsuario) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarioDAO.listarTodos()) {
            if (usuario.getRol() == Rol.EMPLEADO
                    && usuario.getEstado() == EstadoUsuario.ACTIVO
                    && usuario.getSupervisorUsuario() != null
                    && usuario.getSupervisorUsuario().equalsIgnoreCase(adminUsuario)) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    @Override
    public List<Usuario> listarSupervisores() {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarioDAO.listarTodos()) {
            if ((usuario.getRol() == Rol.ADMIN_AREA || usuario.getRol() == Rol.ADMIN_RRHH)
                    && usuario.getEstado() == EstadoUsuario.ACTIVO) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    private boolean esTurnoAsignable(TipoTurno turno) {
        return turno == TipoTurno.MATUTINO || turno == TipoTurno.VESPERTINO || turno == TipoTurno.DIURNO;
    }
}
