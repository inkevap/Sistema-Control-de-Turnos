/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Nitro
 */
public class ManejadorArchivos {

    private ManejadorArchivos() {
    }

    public static List<String> leerLineas(String rutaArchivo) {
        List<String> lineas = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return lineas;
        }
        try (Scanner scanner = new Scanner(archivo, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + rutaArchivo, e);
        }
        return lineas;
    }

    public static void agregarLinea(String rutaArchivo, String linea) {
        crearArchivoSiNoExiste(rutaArchivo);
        try (PrintStream out = new PrintStream(new FileOutputStream(rutaArchivo, true), true, "UTF-8")) {
            out.println(linea);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el archivo: " + rutaArchivo, e);
        }
    }

    public static void escribirTodasLasLineas(String rutaArchivo, List<String> lineas) {
        crearArchivoSiNoExiste(rutaArchivo);
        try (PrintStream out = new PrintStream(new FileOutputStream(rutaArchivo, false), true, "UTF-8")) {
            for (String linea : lineas) {
                out.println(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el archivo: " + rutaArchivo, e);
        }
    }

    private static void crearArchivoSiNoExiste(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            File carpetaPadre = archivo.getParentFile();
            if (carpetaPadre != null && !carpetaPadre.exists()) {
                carpetaPadre.mkdirs();
            }
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo: " + rutaArchivo, e);
        }
    }
}
