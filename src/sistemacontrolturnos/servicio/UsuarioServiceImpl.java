package sistemacontrolturnos.servicio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dto.CredencialesDTO;
import sistemacontrolturnos.entidad.EstadoUsuario;
import sistemacontrolturnos.entidad.Usuario;

public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioDAO usuarioDAO;

    public UsuarioServiceImpl(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public Usuario autenticar(CredencialesDTO credenciales) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(credenciales.getNombreUsuario());
        if (usuario == null) {
            return null;
        }
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            return null;
        }
        if (!hashear(credenciales.getContrasena()).equals(usuario.getContrasenaHash())) {
            return null;
        }
        return usuario;
    }

    public static String hashear(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash", e);
        }
    }
}
