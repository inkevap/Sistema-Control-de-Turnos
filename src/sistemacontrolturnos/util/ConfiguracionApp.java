package sistemacontrolturnos.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionApp {

    private static final String ARCHIVO_CONFIG = "config.properties";
    // este properties es propio de la aplicacion, aqui se carga cualquier configuracion
    // adicional, podriamos incluso agregar compatibilidad con Internalizacion (I18N y I10N )
    
    // Esta madre tiene que ser final(Constante) para que jale el SMTP porque el señorito JavaMail
    // Se fresea al intentar acceder a una variable que no es final (constante)
    private static final Properties propiedades = new Properties();

    static {
        try (FileInputStream entrada = new FileInputStream(ARCHIVO_CONFIG)) {
            propiedades.load(entrada);
        } catch (IOException e) {
            // Esto deberia ser una view ?
            System.err.println("No se encontro '" + ARCHIVO_CONFIG + "'. Copia 'config.properties.example' "
                    + "a 'config.properties' en la raiz del proyecto y completa tus credenciales de correo.");
        }
    }

    
    //configuramos valores por default.
    public static String getSmtpHost() {
        return propiedades.getProperty("smtp.host", "smtp.gmail.com");
    }

    public static String getSmtpPuerto() {
        return propiedades.getProperty("smtp.puerto", "587");
    }

    public static String getSmtpUsuario() {
        return propiedades.getProperty("smtp.usuario", "");
    }

    public static String getSmtpContrasena() {
        return propiedades.getProperty("smtp.contrasena", "");
    }

    private ConfiguracionApp() {
    }
}
