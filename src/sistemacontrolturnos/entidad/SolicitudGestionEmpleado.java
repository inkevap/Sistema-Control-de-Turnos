package sistemacontrolturnos.entidad;

import java.time.LocalDate;

public class SolicitudGestionEmpleado {

    private int idSolicitud;
    private String nombreUsuarioEmpleado;
    private TipoGestion tipoGestion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivo;
    private EstadoSolicitud estado;

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

    public TipoGestion getTipoGestion() {
        return tipoGestion;
    }

    public void setTipoGestion(TipoGestion tipoGestion) {
        this.tipoGestion = tipoGestion;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}
