package sistemacontrolturnos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;

/**
 * Registro central de errores del sistema. Escribe cada error en data/log.txt
 * con su fecha/hora, un contexto y la traza de la excepcion.
 *
 * A diferencia de ManejadorArchivos, este util NUNCA lanza una excepcion: es el
 * ultimo recurso de logueo, asi que si falla al escribir el archivo solo lo
 * reporta en consola (si tambien lanzara, un error de disco provocaria un bucle
 * de errores al intentar registrar el propio fallo de escritura).
 */
public class RegistroErrores {

    private RegistroErrores() {
    }

    public static void registrar(String contexto, Throwable error) {
        String descripcion = (error == null)
                ? "(sin excepcion)"
                : error.getClass().getName() + ": " + error.getMessage();
        String linea = LocalDateTime.now() + " | " + contexto + " | " + descripcion;

        System.err.println(linea);

        try {
            asegurarCarpeta();
            try (PrintStream out = new PrintStream(new FileOutputStream(Constantes.ARCHIVO_LOG, true), true, "UTF-8")) {
                out.println(linea);
                if (error != null) {
                    error.printStackTrace(out);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log: " + e.getMessage());
        }
    }

    private static void asegurarCarpeta() {
        File carpeta = new File(Constantes.RUTA_DATA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}
