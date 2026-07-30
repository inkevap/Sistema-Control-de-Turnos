package sistemacontrolturnos.dto;

import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.TipoTurno;

public class UsuarioDTO {

    private String dpi;
    private String nombreCompleto;
    private String nombreUsuario;
    private String area;
    private TipoTurno turno;
    private Rol rol;
    private String supervisorUsuario;
    private String correo;
    private String contrasena;

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public TipoTurno getTurno() {
        return turno;
    }

    public void setTurno(TipoTurno turno) {
        this.turno = turno;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getSupervisorUsuario() {
        return supervisorUsuario;
    }

    public void setSupervisorUsuario(String supervisorUsuario) {
        this.supervisorUsuario = supervisorUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
