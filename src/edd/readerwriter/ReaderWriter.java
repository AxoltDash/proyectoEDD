package edd.readerwriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

// import java.util.Iterator;

// import edd.estructuras.lineales.ArrayList;
// import edd.estructuras.lineales.List;

public class ReaderWriter {

    public static void writeLines(String fileName, String lines) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(new File(fileName)));

        bufferedWriter.write(lines);

        System.out.println("WROTE " + fileName);
        bufferedWriter.close();
    }

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