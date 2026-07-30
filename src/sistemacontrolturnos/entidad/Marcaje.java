package sistemacontrolturnos.entidad;

import java.time.LocalDateTime;

public class Marcaje {

    private int idMarcaje;
    private String nombreUsuario;
    private TipoMarcaje tipo;
    private LocalDateTime fechaHora;
    private boolean entradaTardia;

    public int getIdMarcaje() {
        return idMarcaje;
    }

    public void setIdMarcaje(int idMarcaje) {
        this.idMarcaje = idMarcaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public TipoMarcaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoMarcaje tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isEntradaTardia() {
        return entradaTardia;
    }

    public void setEntradaTardia(boolean entradaTardia) {
        this.entradaTardia = entradaTardia;
    }
}
