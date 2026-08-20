package sistemacontrolturnos.entidad;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsuarioTest {

    private Usuario crearUsuario(String dpi, String nombreCompleto, String nombreUsuario) {
        Usuario usuario = new Usuario();
        usuario.setDpi(dpi);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setNombreUsuario(nombreUsuario);
        return usuario;
    }

    @Test
    public void equalsEsVerdaderoSiElDpiCoincide() {
        Usuario usuario1 = crearUsuario("1234567890101", "Wendy Garcia", "wendy");
        Usuario usuario2 = crearUsuario("1234567890101", "Otro Nombre", "otro_usuario");

        assertTrue(usuario1.equals(usuario2));
    }

    @Test
    public void equalsEsVerdaderoSiElNombreUsuarioCoincide() {
        Usuario usuario1 = crearUsuario("1111111111111", "Wendy Garcia", "wendy");
        Usuario usuario2 = crearUsuario("2222222222222", "Otro Nombre", "wendy");

        assertTrue(usuario1.equals(usuario2));
    }

    @Test
    public void equalsEsVerdaderoSiElNombreCompletoCoincide() {
        Usuario usuario1 = crearUsuario("1111111111111", "Wendy Abigail Garcia Lopez", "wendy");
        Usuario usuario2 = crearUsuario("2222222222222", "Wendy Abigail Garcia Lopez", "otro_usuario");

        assertTrue(usuario1.equals(usuario2));
    }

    @Test
    public void equalsEsFalsoSiNingunCampoCoincide() {
        Usuario usuario1 = crearUsuario("1111111111111", "Wendy Garcia", "wendy");
        Usuario usuario2 = crearUsuario("2222222222222", "Kevin Pocon", "kevin");

        assertFalse(usuario1.equals(usuario2));
    }

    @Test
    public void hashCodeEsConsistenteConEquals() {
        Usuario usuario1 = crearUsuario("1234567890101", "Wendy Garcia", "wendy");
        Usuario usuario2 = crearUsuario("1234567890101", "Otro Nombre", "otro_usuario");

        assertTrue(usuario1.equals(usuario2));
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}
