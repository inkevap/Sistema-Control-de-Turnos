package sistemacontrolturnos.servicio;

import sistemacontrolturnos.dto.CredencialesDTO;
import sistemacontrolturnos.entidad.Usuario;

public interface IUsuarioService {

    Usuario autenticar(CredencialesDTO credenciales);
}
