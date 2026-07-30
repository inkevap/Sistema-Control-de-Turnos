package sistemacontrolturnos.controlador;

import sistemacontrolturnos.dao.BitacoraDAOTexto;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dao.UsuarioDAOTexto;
import sistemacontrolturnos.dto.CredencialesDTO;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.servicio.BitacoraServiceImpl;
import sistemacontrolturnos.servicio.CorreoServiceImpl;
import sistemacontrolturnos.servicio.IBitacoraService;
import sistemacontrolturnos.servicio.ICorreoService;
import sistemacontrolturnos.servicio.IUsuarioService;
import sistemacontrolturnos.servicio.UsuarioServiceImpl;

public class LoginController {

    private final IUsuarioService usuarioService;

    public LoginController() {
        IUsuarioDAO usuarioDAO = new UsuarioDAOTexto();
        IBitacoraDAO bitacoraDAO = new BitacoraDAOTexto();
        IBitacoraService bitacoraService = new BitacoraServiceImpl(bitacoraDAO);
        ICorreoService correoService = new CorreoServiceImpl();
        this.usuarioService = new UsuarioServiceImpl(usuarioDAO, bitacoraService, correoService);
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        CredencialesDTO credenciales = new CredencialesDTO(nombreUsuario, contrasena);
        return usuarioService.autenticar(credenciales);
    }
}
