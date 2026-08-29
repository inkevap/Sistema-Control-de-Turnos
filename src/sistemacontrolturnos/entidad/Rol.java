/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos.entidad;

/**
 *
 * @author Nitro
 */
public enum Rol {
    // SIN_ROL es el rol "nulo" / vacio: el que queda cuando se le elimina el rol
    // a un usuario. Sigue la convencion del patron Null Object (un valor explicito
    // en vez de null) para no dejar el campo rol en null y evitar NullPointerException.
    SIN_ROL, EMPLEADO, ADMIN_AREA, ADMIN_RRHH
}
