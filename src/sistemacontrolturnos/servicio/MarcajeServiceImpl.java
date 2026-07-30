package sistemacontrolturnos.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import sistemacontrolturnos.dao.IMarcajeDAO;
import sistemacontrolturnos.dto.MarcajeDTO;
import sistemacontrolturnos.entidad.Marcaje;
import sistemacontrolturnos.entidad.TipoMarcaje;

public class MarcajeServiceImpl implements IMarcajeService {

    private static final LocalTime HORA_LIMITE_ENTRADA = LocalTime.of(8, 0);

    private final IMarcajeDAO marcajeDAO;
    private final IBitacoraService bitacoraService;

    public MarcajeServiceImpl(IMarcajeDAO marcajeDAO, IBitacoraService bitacoraService) {
        this.marcajeDAO = marcajeDAO;
        this.bitacoraService = bitacoraService;
    }

    @Override
    public void registrarMarcaje(MarcajeDTO marcajeDTO) {
        LocalDate hoy = LocalDate.now();
        List<Marcaje> marcajesDeHoy = marcajeDAO.listarPorUsuarioYFecha(marcajeDTO.getNombreUsuario(), hoy);

        // RN02 / FA10: no repetir el mismo tipo de marcaje el mismo dia
        if (contieneTipo(marcajesDeHoy, marcajeDTO.getTipo())) {
            throw new IllegalStateException("No puede repetir el mismo marcaje");
        }

        boolean tieneEntrada = contieneTipo(marcajesDeHoy, TipoMarcaje.ENTRADA);
        boolean tieneDescanso1 = contieneTipo(marcajesDeHoy, TipoMarcaje.DESCANSO_1);
        boolean tieneDescanso2 = contieneTipo(marcajesDeHoy, TipoMarcaje.DESCANSO_2);

        switch (marcajeDTO.getTipo()) {
            case DESCANSO_1:
                // FA05
                if (!tieneEntrada) {
                    throw new IllegalStateException("Debe marcar la entrada antes de registrar el descanso");
                }
                break;
            case DESCANSO_2:
                // FA06
                if (!tieneDescanso1) {
                    throw new IllegalStateException("Debe marcar el primer descanso antes de registrar el segundo descanso");
                }
                break;
            case SALIDA:
                // FA08/FA09
                if (!tieneDescanso1 || !tieneDescanso2) {
                    throw new IllegalStateException("Debe marcar ambos descansos antes de registrar la salida");
                }
                break;
            default:
                break;
        }

        LocalDateTime ahora = LocalDateTime.now();

        Marcaje marcaje = new Marcaje();
        marcaje.setNombreUsuario(marcajeDTO.getNombreUsuario());
        marcaje.setTipo(marcajeDTO.getTipo());
        marcaje.setFechaHora(ahora);
        // RN01: entrada despues de las 8:00 am se marca como tardia
        marcaje.setEntradaTardia(marcajeDTO.getTipo() == TipoMarcaje.ENTRADA
                && ahora.toLocalTime().isAfter(HORA_LIMITE_ENTRADA));

        marcajeDAO.guardar(marcaje);
        bitacoraService.registrar(marcajeDTO.getNombreUsuario(), "Registro marcaje: " + marcajeDTO.getTipo());
    }

    @Override
    public List<Marcaje> obtenerMarcajesDelDia(String nombreUsuario) {
        return marcajeDAO.listarPorUsuarioYFecha(nombreUsuario, LocalDate.now());
    }

    private boolean contieneTipo(List<Marcaje> marcajes, TipoMarcaje tipo) {
        for (Marcaje marcaje : marcajes) {
            if (marcaje.getTipo() == tipo) {
                return true;
            }
        }
        return false;
    }
}
