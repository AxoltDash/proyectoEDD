package edd.banco;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.IOException;

import edd.readerwriter.ReaderWriter;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase que representa un trámite en el sistema de turnos del banco.
 */
public class Tramite {

    private String nombre;
    private String tipo;
    private int minimo;
    private int maximo;
    private static Tramite[] tramites = null;

    /**
     * Constructor para crear un trámite con los parámetros especificados.
     * 
     * @param nombre El nombre del trámite.
     * @param tipo El tipo del trámite (por ejemplo, "ventanilla" o "asesor").
     * @param minimo El tiempo mínimo requerido para el trámite.
     * @param maximo El tiempo máximo requerido para el trámite.
     */
    public Tramite(String nombre, String tipo, int minimo, int maximo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public String getNombre(){
        return nombre;
    }

    public String getTipo(){
        return tipo;
    }

    public int getMinimo(){
        return minimo;
    }
    
    public int getMaximo(){
        return maximo;
    }

    @Override
    public boolean equals(Object t) {
        
        if (!(t instanceof Tramite)) return false;
        if (t == null) return false;
        if (this == t) return true;

        // Verifica si los tipos son diferentes
        if (getClass() != t.getClass())
            return false;
        Tramite tmp = (Tramite) t;
        if (maximo != tmp.maximo) 
            return false;
        if (minimo != tmp.minimo) 
            return false;
        if (nombre == null) { 
            if (tmp.nombre != null)
            return false;
        } else if (!nombre.equals(tmp.nombre))
            return false;

        if (tipo == null) { 
            if (tmp.tipo != null)
                return false;
        } else if (!tipo.equals(tmp.tipo))
            return false;
        
        return true; // Si pasa todo
    }

    /**
     * Obtiene una lista de todos los trámites cargados desde un archivo JSON.
     * 
     * @return Un arreglo de objetos Tramite.
     */
    public static Tramite[] tramites() {
        if (tramites != null)
            return tramites; 
        try {
            String inputJSON = ReaderWriter.readLines("tramites.json");

            Type listOfMyClassObject = new TypeToken<java.util.ArrayList<Tramite>>() {}.getType();
            Gson gson = new Gson();
            java.util.List<Tramite> list = gson.fromJson(inputJSON, listOfMyClassObject);

            tramites = new Tramite[list.size()];
            for (int i = 0; i < list.size(); i++) {
                tramites[i] = list.get(i);
            }
            return tramites;

        } catch (IOException e) {
            System.out.println(e);
            return null;
        } 
    }

    /**
     * Obtiene un trámite específico por su nombre.
     * 
     * @param tramite El nombre del trámite a buscar.
     * @return El objeto Tramite correspondiente, o null si no se encuentra.
     */
    public static Tramite tramite(String tramite) {
        if (tramites == null) {
            tramites(); 
        }    

        for (Tramite t : tramites) {
            if (t.nombre.equals(tramite))
                return t; 
        } 
        return null;
    }
}
