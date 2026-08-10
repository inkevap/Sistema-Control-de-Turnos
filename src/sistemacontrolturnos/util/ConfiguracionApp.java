package sistemacontrolturnos.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionApp {

    private static final String ARCHIVO_CONFIG = "config.properties";
    private static final Properties propiedades = new Properties();

    static {
        try (FileInputStream entrada = new FileInputStream(ARCHIVO_CONFIG)) {
            propiedades.load(entrada);
        } catch (IOException e) {
            System.err.println("No se encontro '" + ARCHIVO_CONFIG + "'. Copia 'config.properties.example' "
                    + "a 'config.properties' en la raiz del proyecto y completa tus credenciales de correo.");
        }
    }

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
