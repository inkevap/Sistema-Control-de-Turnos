package sistemacontrolturnos.dao;

import java.util.List;
import sistemacontrolturnos.entidad.Usuario;

public interface IUsuarioDAO {

    Usuario buscarPorUsuario(String nombreUsuario);

    List<Usuario> listarTodos();

    void guardar(Usuario usuario);

    void actualizar(Usuario usuario);
}
