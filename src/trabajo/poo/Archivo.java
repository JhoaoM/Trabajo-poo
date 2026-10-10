package trabajo.poo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Guarda y lee los datos en archivos de texto (una linea por registro,
// con los campos separados por ;). Los archivos se crean solos en la
// carpeta del proyecto.
public class Archivo {

    // Lee un archivo y devuelve una lista con los campos de cada linea.
    // Si el archivo no existe, devuelve una lista vacia.
    public static List<String[]> leer(String nombreArchivo, int camposEsperados) {
        List<String[]> filas = new ArrayList<>();
        Path ruta = Paths.get(nombreArchivo);

        if (!Files.exists(ruta)) {
            return filas;
        }
        try {
            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                String[] campos = linea.split(";", -1);
                if (campos.length == camposEsperados) {
                    filas.add(campos);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo " + nombreArchivo);
        }
        return filas;
    }

    // Reescribe el archivo completo con las lineas indicadas
    public static void escribir(String nombreArchivo, List<String> lineas) {
        try {
            Files.write(Paths.get(nombreArchivo), lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo " + nombreArchivo);
        }
    }

    // El ; separa los campos, asi que si un texto lo trae se cambia por una coma
    public static String limpiar(String texto) {
        return texto.replace(";", ",").replace("\n", " ").replace("\r", " ");
    }
}
