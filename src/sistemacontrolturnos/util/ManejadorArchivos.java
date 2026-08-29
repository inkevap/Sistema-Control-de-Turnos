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
        List<String> lineas = new ArrayList<>(); // lista en memoria
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return lineas; // devuelve un array vacio
        }
        try (Scanner scanner = new Scanner(archivo, "UTF-8")) { // trae se lee el archivo
            while (scanner.hasNextLine()) { // se crea un bucle para leer todas las lineas
                String linea = scanner.nextLine();
                if (!linea.trim().isEmpty()) { // se sanitiza las lineas
                    lineas.add(linea); // se concatena la linea a la lista en memoria
                }
            }
        } catch (IOException e) {
            RegistroErrores.registrar("ManejadorArchivos.leerLineas: " + rutaArchivo, e);
            throw new RuntimeException("Error al leer el archivo: " + rutaArchivo, e); // se devuelve un error si hubo un error
        }
        return lineas; // si no hubo error se devuelve la lista de lineas
    }

    public static void agregarLinea(String rutaArchivo, String linea) {
        crearArchivoSiNoExiste(rutaArchivo);
        try (PrintStream out = new PrintStream(new FileOutputStream(rutaArchivo, true), true, "UTF-8")) {
            out.println(linea);
        } catch (IOException e) {
            RegistroErrores.registrar("ManejadorArchivos.agregarLinea: " + rutaArchivo, e);
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
            RegistroErrores.registrar("ManejadorArchivos.escribirTodasLasLineas: " + rutaArchivo, e);
            throw new RuntimeException("Error al escribir en el archivo: " + rutaArchivo, e);
        }
    }

    private static void crearArchivoSiNoExiste(String rutaArchivo) { //si el archivo no existe
        try {
            File archivo = new File(rutaArchivo); 
            File carpetaPadre = archivo.getParentFile(); // verifica si existe la carpeta donde van los archivos
            if (carpetaPadre != null && !carpetaPadre.exists()) { 
                carpetaPadre.mkdirs(); //si no existe se crea la carpeta
            }
            if (!archivo.exists()) {
                archivo.createNewFile(); // se verifica si el archivo no existe, si no existe se crea
            }
        } catch (IOException e) {
            RegistroErrores.registrar("ManejadorArchivos.crearArchivoSiNoExiste: " + rutaArchivo, e);
            throw new RuntimeException("Error al crear el archivo: " + rutaArchivo, e); // si hay algun error gestionando los archivos se tira el error
        }
    }
}
