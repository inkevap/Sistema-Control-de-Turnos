package sistemacontrolturnos.controlador;

import java.util.List;
import sistemacontrolturnos.dao.BitacoraDAOTexto;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.dao.IMarcajeDAO;
import sistemacontrolturnos.dao.MarcajeDAOTexto;
import sistemacontrolturnos.dto.MarcajeDTO;
import sistemacontrolturnos.entidad.Marcaje;
import sistemacontrolturnos.entidad.TipoMarcaje;
import sistemacontrolturnos.servicio.BitacoraServiceImpl;
import sistemacontrolturnos.servicio.IBitacoraService;
import sistemacontrolturnos.servicio.IMarcajeService;
import sistemacontrolturnos.servicio.MarcajeServiceImpl;

public class MarcajeController {

    private final IMarcajeService marcajeService;

    public MarcajeController() {
        IMarcajeDAO marcajeDAO = new MarcajeDAOTexto();
        IBitacoraDAO bitacoraDAO = new BitacoraDAOTexto();
        IBitacoraService bitacoraService = new BitacoraServiceImpl(bitacoraDAO);
        this.marcajeService = new MarcajeServiceImpl(marcajeDAO, bitacoraService);
    }

    public void registrarMarcaje(String nombreUsuario, TipoMarcaje tipo) {
        MarcajeDTO dto = new MarcajeDTO();
        dto.setNombreUsuario(nombreUsuario);
        dto.setTipo(tipo);
        marcajeService.registrarMarcaje(dto);
    }

    public List<Marcaje> obtenerMarcajesDelDia(String nombreUsuario) {
        return marcajeService.obtenerMarcajesDelDia(nombreUsuario);
    }
}
