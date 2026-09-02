package sistemacontrolturnos.entidad;

import java.time.LocalDate;

/**
 * Turno asignado a un empleado para un rango de fechas, junto con el supervisor
 * responsable. Es el resultado del CU3 (Asignacion de Turnos).
 */
public class AsignacionTurno {

    private int idAsignacion;
    private String nombreUsuarioEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoTurno turno;
    private String supervisorUsuario;
    private String asignadoPor;

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getNombreUsuarioEmpleado() {
        return nombreUsuarioEmpleado;
    }

    public void setNombreUsuarioEmpleado(String nombreUsuarioEmpleado) {
        this.nombreUsuarioEmpleado = nombreUsuarioEmpleado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public TipoTurno getTurno() {
        return turno;
    }

    public void setTurno(TipoTurno turno) {
        this.turno = turno;
    }

    public String getSupervisorUsuario() {
        return supervisorUsuario;
    }

    public void setSupervisorUsuario(String supervisorUsuario) {
        this.supervisorUsuario = supervisorUsuario;
    }

    public String getAsignadoPor() {
        return asignadoPor;
    }

    public void setAsignadoPor(String asignadoPor) {
        this.asignadoPor = asignadoPor;
    }
}
