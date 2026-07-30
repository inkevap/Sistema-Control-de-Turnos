package sistemacontrolturnos.dto;

import sistemacontrolturnos.entidad.TipoMarcaje;

public class MarcajeDTO {

    private String nombreUsuario;
    private TipoMarcaje tipo;

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
}
