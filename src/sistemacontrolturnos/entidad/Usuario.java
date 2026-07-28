/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos.entidad;

import java.util.Objects;

/**
 *
 * @author Nitro
 */
public class Usuario {
    private String dpi;
    private String nombreCompleto;
    private String nombreUsuario;
    private String area;
    private TipoTurno turno;
    private Rol rol;
    private String supervisorUsuario;
    private String correo;
    private String contrasenaHash;
    private EstadoUsuario estado;

    public Usuario(String dpi, String nombreCompleto, String nombreUsuario, String area, TipoTurno turno, Rol rol, String supervisorUsuario, String correo, String contrasenaHash, EstadoUsuario estado) {
        this.dpi = dpi;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.area = area;
        this.turno = turno;
        this.rol = rol;
        this.supervisorUsuario = supervisorUsuario;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.estado = estado;
    }

    public Usuario() {
    }

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

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.dpi);
        hash = 89 * hash + Objects.hashCode(this.nombreUsuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.dpi, other.dpi)) {
            return false;
        }
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        return true;
    }
}
