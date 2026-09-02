package sistemacontrolturnos.entidad;

/**
 * Estados de una solicitud de cambio de turno (CU4/CU5).
 * A diferencia de las licencias/vacaciones, el cambio de turno lo resuelve
 * unicamente el Administrador de Area (no pasa por RRHH).
 */
public enum EstadoSolicitudTurno {
    PENDIENTE_APROBAR,
    APROBADO,
    RECHAZADO
}
