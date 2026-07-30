/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos.util;

/**
 *
 * @author Nitro
 */
public class Constantes {

    public static final String DELIMITADOR = "|";

    public static final String RUTA_DATA = "data/";
    public static final String ARCHIVO_USUARIOS = RUTA_DATA + "usuarios.txt";
    public static final String ARCHIVO_TURNOS = RUTA_DATA + "turnos.txt";
    public static final String ARCHIVO_MARCAJES = RUTA_DATA + "marcajes.txt";
    public static final String ARCHIVO_SOLICITUDES_EMPLEADO = RUTA_DATA + "solicitudes_empleado.txt";
    public static final String ARCHIVO_SOLICITUDES_TURNO = RUTA_DATA + "solicitudes_turno.txt";
    public static final String ARCHIVO_BITACORA = RUTA_DATA + "bitacora.txt";

    // TODO: reemplazar con un correo y contrasena de aplicacion reales antes de usar el envio de correo
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PUERTO = "587";
    public static final String SMTP_USUARIO = "TU_CORREO@gmail.com";
    public static final String SMTP_CONTRASENA = "TU_CONTRASENA_DE_APLICACION";

    private Constantes() {
    }
}