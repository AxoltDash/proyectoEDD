package edd.readerwriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase utilitaria para leer y escribir archivos de texto.
 */
public class ReaderWriter {

    /**
     * Escribe las líneas dadas en un archivo especificado.
     * 
     * @param fileName El nombre del archivo donde se escribirán las líneas.
     * @param lines    Las líneas de texto que se escribirán en el archivo.
     * @throws IOException Si ocurre un error de entrada/salida al escribir en el archivo.
     */
    public static void writeLines(String fileName, String lines) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(new File(fileName)));

        bufferedWriter.write(lines);

        System.out.println("WROTE " + fileName);
        bufferedWriter.close();
    }

    /**
     * Lee todas las líneas de un archivo especificado y las devuelve como una cadena de texto.
     * 
     * @param fileName El nombre del archivo del que se leerán las líneas.
     * @return Una cadena que contiene todas las líneas leídas del archivo.
     * @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
     */
    public static String readLines(String fileName) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
            i++;
        }

        bufferedReader.close();

        System.out.println("READ " + fileName + ": " + i);

        return sb.toString();
    }
}
