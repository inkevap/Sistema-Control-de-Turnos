package sistemacontrolturnos.entidad;

import java.time.LocalDate;

/**
 * Solicitud de cambio de turno enviada por un empleado (CU5) y resuelta por el
 * Administrador de Area (CU4). Guarda el turno actual y el turno deseado.
 */
public class SolicitudCambioTurno {

    private int idSolicitud;
    private String nombreUsuarioEmpleado;
    private LocalDate fechaInicial;
    private TipoTurno turnoInicial;
    private LocalDate fechaNueva;
    private TipoTurno turnoNuevo;
    private String justificacion;
    private EstadoSolicitudTurno estado;
    private String procesadoPor;

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreUsuarioEmpleado() {
        return nombreUsuarioEmpleado;
    }

    public void setNombreUsuarioEmpleado(String nombreUsuarioEmpleado) {
        this.nombreUsuarioEmpleado = nombreUsuarioEmpleado;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public TipoTurno getTurnoInicial() {
        return turnoInicial;
    }

    public void setTurnoInicial(TipoTurno turnoInicial) {
        this.turnoInicial = turnoInicial;
    }

    public LocalDate getFechaNueva() {
        return fechaNueva;
    }

    public void setFechaNueva(LocalDate fechaNueva) {
        this.fechaNueva = fechaNueva;
    }

    public TipoTurno getTurnoNuevo() {
        return turnoNuevo;
    }

    public void setTurnoNuevo(TipoTurno turnoNuevo) {
        this.turnoNuevo = turnoNuevo;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public EstadoSolicitudTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitudTurno estado) {
        this.estado = estado;
    }

    public String getProcesadoPor() {
        return procesadoPor;
    }

    public void setProcesadoPor(String procesadoPor) {
        this.procesadoPor = procesadoPor;
    }
}
