/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos.entidad;

import java.time.LocalDateTime;

/**
 *
 * @author Nitro
 */
public class Bitacora {
    private int idBitacora;
    private String nombreUsuario;
    private String accion;
    private LocalDateTime fechaHora;

    public Bitacora() {
    }

    public Bitacora(int idBitacora, String nombreUsuario, String accion, LocalDateTime fechaHora) {
        this.idBitacora = idBitacora;
        this.nombreUsuario = nombreUsuario;
        this.accion = accion;
        this.fechaHora = fechaHora;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
