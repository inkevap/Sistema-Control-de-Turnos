package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.dto.MarcajeDTO;
import sistemacontrolturnos.entidad.Marcaje;

public interface IMarcajeService {

    void registrarMarcaje(MarcajeDTO marcajeDTO);

    List<Marcaje> obtenerMarcajesDelDia(String nombreUsuario);
}
