package sistemacontrolturnos.controlador;

import java.util.List;
import sistemacontrolturnos.dao.BitacoraDAOTexto;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dao.UsuarioDAOTexto;
import sistemacontrolturnos.dto.UsuarioDTO;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.servicio.BitacoraServiceImpl;
import sistemacontrolturnos.servicio.IBitacoraService;
import sistemacontrolturnos.servicio.IUsuarioService;
import sistemacontrolturnos.servicio.UsuarioServiceImpl;

public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController() {
        IUsuarioDAO usuarioDAO = new UsuarioDAOTexto();
        IBitacoraDAO bitacoraDAO = new BitacoraDAOTexto();
        IBitacoraService bitacoraService = new BitacoraServiceImpl(bitacoraDAO);
        this.usuarioService = new UsuarioServiceImpl(usuarioDAO, bitacoraService);
    }

    public void registrarEmpleado(UsuarioDTO usuarioDTO) {
        usuarioService.registrar(usuarioDTO);
    }

    public List<Usuario> consultarUsuarios(String filtroUsuario, String filtroArea) {
        return usuarioService.buscar(filtroUsuario, filtroArea);
    }
}
