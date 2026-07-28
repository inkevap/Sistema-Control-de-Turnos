package sistemacontrolturnos.servicio;

import java.time.LocalDateTime;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.entidad.Bitacora;

public class BitacoraServiceImpl implements IBitacoraService {

    private final IBitacoraDAO bitacoraDAO;

    public BitacoraServiceImpl(IBitacoraDAO bitacoraDAO) {
        this.bitacoraDAO = bitacoraDAO;
    }

    @Override
    public void registrar(String nombreUsuario, String accion) {
        Bitacora bitacora = new Bitacora();
        bitacora.setNombreUsuario(nombreUsuario);
        bitacora.setAccion(accion);
        bitacora.setFechaHora(LocalDateTime.now());
        bitacoraDAO.guardar(bitacora);
    }
}
