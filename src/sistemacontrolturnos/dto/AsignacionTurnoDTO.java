package sistemacontrolturnos.dto;

import java.time.LocalDate;
import sistemacontrolturnos.entidad.TipoTurno;

/**
 * Datos que la vista de Asignacion de Turnos envia al servicio. No incluye el
 * administrador que asigna: ese lo aporta la capa de presentacion (usuario logueado).
 */
public class AsignacionTurnoDTO {

    private String nombreUsuarioEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoTurno turno;
    private String supervisorUsuario;

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
}
