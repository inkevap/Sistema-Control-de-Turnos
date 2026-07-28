package sistemacontrolturnos.dao;

import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.entidad.EstadoUsuario;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.TipoTurno;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.util.Constantes;
import sistemacontrolturnos.util.ManejadorArchivos;

public class UsuarioDAOTexto implements IUsuarioDAO {

    @Override
    public Usuario buscarPorUsuario(String nombreUsuario) {
        for (Usuario usuario : listarTodos()) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> resultado = new ArrayList<>();
        for (String linea : ManejadorArchivos.leerLineas(Constantes.ARCHIVO_USUARIOS)) {
            String[] campos = linea.split("\\" + Constantes.DELIMITADOR, -1);
            Usuario usuario = new Usuario();
            usuario.setDpi(campos[0]);
            usuario.setNombreCompleto(campos[1]);
            usuario.setNombreUsuario(campos[2]);
            usuario.setArea(campos[3]);
            usuario.setTurno(TipoTurno.valueOf(campos[4]));
            usuario.setRol(Rol.valueOf(campos[5]));
            usuario.setSupervisorUsuario(campos[6]);
            usuario.setCorreo(campos[7]);
            usuario.setContrasenaHash(campos[8]);
            usuario.setEstado(EstadoUsuario.valueOf(campos[9]));
            resultado.add(usuario);
        }
        return resultado;
    }

    @Override
    public void guardar(Usuario usuario) {
        String supervisor = usuario.getSupervisorUsuario() == null ? "" : usuario.getSupervisorUsuario();

        String linea = String.join(Constantes.DELIMITADOR,
                usuario.getDpi(),
                usuario.getNombreCompleto(),
                usuario.getNombreUsuario(),
                usuario.getArea(),
                usuario.getTurno().name(),
                usuario.getRol().name(),
                supervisor,
                usuario.getCorreo(),
                usuario.getContrasenaHash(),
                usuario.getEstado().name());

        ManejadorArchivos.agregarLinea(Constantes.ARCHIVO_USUARIOS, linea);
    }
}
