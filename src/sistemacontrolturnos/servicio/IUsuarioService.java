package sistemacontrolturnos.servicio;

import java.util.List;
import sistemacontrolturnos.dto.CredencialesDTO;
import sistemacontrolturnos.dto.UsuarioDTO;
import sistemacontrolturnos.entidad.Usuario;

public interface IUsuarioService {

    Usuario autenticar(CredencialesDTO credenciales);

    void registrar(UsuarioDTO usuarioDTO);

    List<Usuario> buscar(String filtroUsuario, String filtroArea);

    void inactivar(String nombreUsuario, String motivo);
}
