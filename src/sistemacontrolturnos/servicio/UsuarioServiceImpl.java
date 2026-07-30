package sistemacontrolturnos.servicio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dto.CredencialesDTO;
import sistemacontrolturnos.dto.UsuarioDTO;
import sistemacontrolturnos.entidad.EstadoUsuario;
import sistemacontrolturnos.entidad.Usuario;

public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioDAO usuarioDAO;
    private final IBitacoraService bitacoraService;
    private final ICorreoService correoService;

    public UsuarioServiceImpl(IUsuarioDAO usuarioDAO, IBitacoraService bitacoraService, ICorreoService correoService) {
        this.usuarioDAO = usuarioDAO;
        this.bitacoraService = bitacoraService;
        this.correoService = correoService;
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

    @Override
    public void registrar(UsuarioDTO usuarioDTO) {
        if (usuarioDAO.buscarPorUsuario(usuarioDTO.getNombreUsuario()) != null) {
            throw new IllegalStateException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setDpi(usuarioDTO.getDpi());
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setArea(usuarioDTO.getArea());
        usuario.setTurno(usuarioDTO.getTurno());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setSupervisorUsuario(usuarioDTO.getSupervisorUsuario());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasenaHash(hashear(usuarioDTO.getContrasena()));
        usuario.setEstado(EstadoUsuario.ACTIVO);

        usuarioDAO.guardar(usuario);
        bitacoraService.registrar(usuarioDTO.getNombreUsuario(), "Se registro el empleado " + usuarioDTO.getNombreUsuario());
    }

    @Override
    public List<Usuario> buscar(String filtroUsuario, String filtroArea) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarioDAO.listarTodos()) {
            boolean coincideUsuario = filtroUsuario == null || filtroUsuario.isEmpty()
                    || usuario.getNombreUsuario().toLowerCase().contains(filtroUsuario.toLowerCase());
            boolean coincideArea = filtroArea == null || filtroArea.isEmpty()
                    || usuario.getArea().toLowerCase().contains(filtroArea.toLowerCase());
            if (coincideUsuario && coincideArea) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    @Override
    public void inactivar(String nombreUsuario, String motivo) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);
        if (usuario == null) {
            throw new IllegalStateException("El usuario no existe");
        }

        usuario.setEstado(EstadoUsuario.INACTIVO);
        usuarioDAO.actualizar(usuario);

        correoService.enviarCorreo(usuario.getCorreo(), "Notificacion de inactivacion",
                "Hola " + usuario.getNombreCompleto() + ",\n\nTu cuenta ha sido inactivada.\nMotivo: " + motivo);

        bitacoraService.registrar(nombreUsuario, "Se inactivo el usuario " + nombreUsuario + " (motivo: " + motivo + ")");
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
