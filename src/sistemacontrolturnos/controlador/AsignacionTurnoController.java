package sistemacontrolturnos.controlador;

import java.util.List;
import sistemacontrolturnos.dao.AsignacionTurnoDAOTexto;
import sistemacontrolturnos.dao.BitacoraDAOTexto;
import sistemacontrolturnos.dao.IAsignacionTurnoDAO;
import sistemacontrolturnos.dao.IBitacoraDAO;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dao.UsuarioDAOTexto;
import sistemacontrolturnos.dto.AsignacionTurnoDTO;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.servicio.AsignacionTurnoServiceImpl;
import sistemacontrolturnos.servicio.BitacoraServiceImpl;
import sistemacontrolturnos.servicio.IAsignacionTurnoService;
import sistemacontrolturnos.servicio.IBitacoraService;

public class AsignacionTurnoController {

    private final IAsignacionTurnoService asignacionService;

    public AsignacionTurnoController() {
        IAsignacionTurnoDAO asignacionDAO = new AsignacionTurnoDAOTexto();
        IUsuarioDAO usuarioDAO = new UsuarioDAOTexto();
        IBitacoraDAO bitacoraDAO = new BitacoraDAOTexto();
        IBitacoraService bitacoraService = new BitacoraServiceImpl(bitacoraDAO);
        this.asignacionService = new AsignacionTurnoServiceImpl(asignacionDAO, usuarioDAO, bitacoraService);
    }

    public void asignar(AsignacionTurnoDTO dto, String adminUsuario) {
        asignacionService.asignar(dto, adminUsuario);
    }

    public List<Usuario> listarEmpleadosDe(String adminUsuario) {
        return asignacionService.listarEmpleadosDe(adminUsuario);
    }

    public List<Usuario> listarSupervisores() {
        return asignacionService.listarSupervisores();
    }
}
