package sistemacontrolturnos.servicio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sistemacontrolturnos.dao.IUsuarioDAO;
import sistemacontrolturnos.dto.CredencialesDTO;
import sistemacontrolturnos.dto.UsuarioDTO;
import sistemacontrolturnos.entidad.EstadoUsuario;
import sistemacontrolturnos.entidad.Rol;
import sistemacontrolturnos.entidad.Usuario;
import sistemacontrolturnos.util.RegistroErrores;

public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioDAO usuarioDAO;
    private final IBitacoraService bitacoraService;
    private final ICorreoService correoService;

    // Almacen EN MEMORIA de los codigos de recuperacion (clave = nombre de usuario
    // en minusculas). Es static para que sobreviva entre instancias del servicio
    // durante la misma ejecucion de la app; se pierde al cerrar el programa.
    private static final Map<String, CodigoRecuperacion> CODIGOS_RECUPERACION = new HashMap<>(); //hashmap para almacenar codigos xd
    private static final int MINUTOS_VALIDEZ_CODIGO = 15; // tiempo de valides
    private static final SecureRandom ALEATORIO = new SecureRandom(); //Generador de números aleatorios criptográficamente seguro

    // Guarda el codigo generado junto con su momento de expiracion.
    // Es una clase interna porque solo se usa aca para no crear un map dentro de otro map
    private static final class CodigoRecuperacion {
        final String codigo;
        final LocalDateTime expira;

        CodigoRecuperacion(String codigo, LocalDateTime expira) {
            this.codigo = codigo;
            this.expira = expira;
        }
    }

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
        // Validacion de duplicados en la capa de servicio (regla de negocio):
        // se considera duplicado si coincide el DPI o el nombre de usuario con
        // algun usuario ya existente. El nombre completo NO se valida (dos
        // empleados pueden llamarse igual).
        if (existeDuplicado(usuarioDTO.getDpi(), usuarioDTO.getNombreUsuario())) {
            throw new IllegalStateException("Error: Ha ocurrido un error al registrar el empleado");
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

    // Recorre los usuarios existentes comparando DPI y nombre de usuario (sin
    // distinguir mayusculas/minusculas). Vive en el servicio porque es una regla
    // de negocio, no una responsabilidad de acceso a datos.
    private boolean existeDuplicado(String dpi, String nombreUsuario) {
        for (Usuario existente : usuarioDAO.listarTodos()) {
            if (existente.getDpi().equalsIgnoreCase(dpi)
                    || existente.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Usuario> buscar(String filtroUsuario, String filtroArea) {
        List<Usuario> resultado = new ArrayList<>();
        
        for (Usuario usuario : usuarioDAO.listarTodos()) {
            //filtros de busqueda
            //Primer filtro por nombre de usuario
            boolean coincideUsuario = filtroUsuario == null || filtroUsuario.isEmpty()
                    || usuario.getNombreUsuario().toLowerCase().contains(filtroUsuario.toLowerCase());
            //Segundo filtro por area
            boolean coincideArea = filtroArea == null || filtroArea.isEmpty()
                    || usuario.getArea().toLowerCase().contains(filtroArea.toLowerCase());
            //Si el usuario coincide se agrega a la lista y se devuelve
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

        // se envia el correo
        correoService.enviarCorreo(usuario.getCorreo(), "Notificacion de inactivacion",
                "Hola " + usuario.getNombreCompleto() + ",\n\nTu cuenta ha sido inactivada.\nMotivo: " + motivo);
        
        //se guarda en la bitacora quien hizo el cambio
        bitacoraService.registrar(nombreUsuario, "Se inactivo el usuario " + nombreUsuario + " (motivo: " + motivo + ")");
    }

    @Override
    public void agregarRol(String nombreUsuario, Rol nuevoRol) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);
        if (usuario == null) {
            // Esto en teoria no se da por el combo box "EN TEORIA" pero lo pongo por si acaso
            throw new IllegalStateException("El usuario no existe");
        }
        usuario.setRol(nuevoRol);
        usuarioDAO.actualizar(usuario);
        bitacoraService.registrar(nombreUsuario, "Se asigno el rol " + nuevoRol + " al usuario " + nombreUsuario);
    }

    @Override
    public void eliminarRol(String nombreUsuario) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);
        if (usuario == null) {
            throw new IllegalStateException("El usuario no existe");
        }
        /* La entidad Usuario modela un solo rol (no una lista). Al "eliminar" el
         rol se deja el rol vacio SIN_ROL (patron Null Object) en lugar de null,
         de modo que el usuario queda sin permisos hasta que se le asigne otro rol.
         */
        usuario.setRol(Rol.SIN_ROL);
        usuarioDAO.actualizar(usuario);
        bitacoraService.registrar(nombreUsuario, "Se elimino el rol del usuario " + nombreUsuario + " (queda SIN_ROL)");
    }

    @Override
    public void solicitarCodigoRecuperacion(String nombreUsuario) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);
        // Solo un usuario existente y activo puede recuperar su contrasena.
        if (usuario == null || usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new IllegalStateException("No existe un usuario activo con ese nombre de usuario");
        }

        // Codigo de 6 digitos (000000-999999) con relleno de ceros a la izquierda.
        String codigo = String.format("%06d", ALEATORIO.nextInt(1_000_000)); // se crea le codigo de 6 digitos, el "_" es inerte solo para separacion visual
        String clave = usuario.getNombreUsuario().toLowerCase(); // ese codigo lo relacionamos con el nombre de usuario
        CODIGOS_RECUPERACION.put(clave, // Este put papi sobreescribe cualquier otro codigo anterior, solo el ultimo mandado es valido
                //aqui guardamos lla llave [Usuario,Llave de recuperacion] y la llave de recuperacion
                // representa el codigo y el tiempo que se creo mas el tiempo de validez
                new CodigoRecuperacion(codigo, LocalDateTime.now().plusMinutes(MINUTOS_VALIDEZ_CODIGO)));
               // se envia al correo
        correoService.enviarCorreo(usuario.getCorreo(), "Codigo de recuperacion de contrasena",
                "Hola " + usuario.getNombreCompleto() + ",\n\nTu codigo de recuperacion es: " + codigo
                + "\nEste codigo vence en " + MINUTOS_VALIDEZ_CODIGO + " minutos.\n\n"
                        + "\nSolo el ultimo codigo solicitado es valido.\n\n"
                + "Si no solicitaste este cambio, ignora este correo.");
        bitacoraService.registrar(usuario.getNombreUsuario(), "Se solicito un codigo de recuperacion de contrasena");
    }

    @Override
    public void restablecerContrasena(String nombreUsuario, String codigo, String nuevaContrasena) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);
        if (usuario == null) {
            throw new IllegalStateException("El usuario no existe");
        }

        String clave = usuario.getNombreUsuario().toLowerCase();
        // recuperamos de todos los codigos de recuperacion, el codigo de recuperacion especifico del usuario
        CodigoRecuperacion registro = CODIGOS_RECUPERACION.get(clave);
        
        //Validaciones
        if (registro == null) { // codigo ya usado
            throw new IllegalStateException("No hay un codigo de recuperacion vigente. Solicita uno nuevo.");
        }
        if (LocalDateTime.now().isAfter(registro.expira)) { // codigo esxpirado
            CODIGOS_RECUPERACION.remove(clave); // si esta expirado se elimina
            throw new IllegalStateException("El codigo ha expirado. Solicita uno nuevo.");
        }
        if (codigo == null || !registro.codigo.equals(codigo.trim())) { // codigo escrito incorrecto | El trim sanitiza papi
            throw new IllegalStateException("El codigo ingresado no es correcto");
        }
        if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()) { // dejo la nueva contraseña vacia
            throw new IllegalStateException("La nueva contrasena no puede estar vacia");
        }

        usuario.setContrasenaHash(hashear(nuevaContrasena));
        usuarioDAO.actualizar(usuario);
        // El codigo es de un solo uso: se elimina apenas se usa con exito.
        CODIGOS_RECUPERACION.remove(clave); // se elimina el codigo usado

        correoService.enviarCorreo(usuario.getCorreo(), "Tu contrasena fue restablecida",
                "Hola " + usuario.getNombreCompleto() + ",\n\nTu contrasena se restablecio correctamente.");
        bitacoraService.registrar(usuario.getNombreUsuario(), "Restablecio su contrasena mediante codigo de recuperacion");
    }


    // Metodo para crear el hash cifrado en SHA-256, pude haber usado Bcrypt pero soy flojo lo siento
    // Ya tenia este codigo en algun otro repo
    // Como funciona? Solo Dios sabe, pero ahorita vemos que pedales
    public static String hashear(String texto) {
        try {
            // Se devuelve un encriptador de SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); 
            // Se convierte el texto a bytes usando siempre la misma codificacion UTF 8 para
            // que no use la codificacion por defecto y termine generando hashes distintos
            // luego se devuelve en bytes el texto ya encriptado.
            byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8)); 
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                // Con la ayuda del strin Builder convertimos todos esos bytes
                // en un codigo hexadecimal que es devuelto como texto plano
                // Es lo que luego vamos a almacenar en nuesta BBDD
                sb.append(String.format("%02x", b)); 
            }
            return sb.toString(); // una vez todos los bytes se conviertieron a hexadecimal se devuelve el resultado
        } catch (NoSuchAlgorithmException e) { // No deberia dar error, pero en caso de que si aqui se gestiona ese error
            RegistroErrores.registrar("UsuarioServiceImpl.hashear", e);
            throw new RuntimeException("Error al generar el hash", e);
        }
    }
}
