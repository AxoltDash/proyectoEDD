package edd.banco;

import edd.estructuras.lineales.LinkedQueue;
import edd.estructuras.lineales.PriorityQueue;
import edd.estructuras.lineales.Queue;

import edd.readerwriter.ReaderWriter;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class Buscador {

    private Reporte reporte = new Reporte();

    private Cliente[] clientes;
    private Empleado[] empleados;
    // private Tramite[] tramites;

    private int ultimoTurno;
    private Turno[] turnos;

    // private Queue<Turno> turnos;

    // private PriorityQueue<Cliente> clientesPorAtender;
    private Queue<Cliente> clientesPorAtender;
    private PriorityQueue<Empleado> empleadosDisponibles;
    private PriorityQueue<Turno> turnosAtendiendo;

    int horaActual;
    int minutoActual;
    
    //BUSCADOR
    //VICHCIS CLASE
    // TypeToken<ArrayList<Cliente>>() {}.getType();
    public Buscador(){
        clientes = null;
        empleados = null;
        
        try {
            String inputJSON = ReaderWriter.readLines("clientes.json");

            Type listOfMyClassObject = new TypeToken<java.util.ArrayList<Cliente>>() {}.getType();
            Gson gson = new Gson();
            java.util.List<Cliente> list = gson.fromJson(inputJSON, listOfMyClassObject);

            clientes = new Cliente[list.size()];
            for (int i = 0; i < list.size(); i++) {
                clientes[i] = list.get(i);
            }

        } catch (IOException e) {
            clientes = null;
        } 

        try {
            String inputJSON = ReaderWriter.readLines("empleados.json");

            Type listOfMyClassObject = new TypeToken<java.util.ArrayList<Empleado>>() {}.getType();
            Gson gson = new Gson();
            java.util.List<Empleado> list = gson.fromJson(inputJSON, listOfMyClassObject);

            empleados = new Empleado[list.size()];
            for (int i = 0; i < list.size(); i++) {
                empleados[i] = list.get(i);
            }

        } catch (IOException e) {
            empleados = null;
        } 
    }

    public void generarClientes(int size){
        Gson gson = new Gson();

        clientes = Cliente.generate(size);
        turnos = new Turno[clientes.length];
        ultimoTurno = 0;

        save(indenta(gson.toJson(clientes)), "clientes.json");
    }

    public void generarEmpleados(int size){
        Gson gson = new Gson();

        empleados = Empleado.generate(size);
        save(indenta(gson.toJson(empleados)), "empleados.json");
    }

    public int cantidadClientes(){
        if (clientes == null)
            return -1;
        return clientes.length;
    }

    public int cantidadEmpleados(){
        if (empleados == null)
            return -1;
        return empleados.length;
    }

    //sacado vilchiz 14 09
    private static String indenta(String str) {
        StringBuilder sb = new StringBuilder();
        String indentacion = "";

        for (int i = 0; i < str.length(); i++) {
            switch(str.charAt(i)) {
                case ':':
                    sb.append(str.charAt(i));
                    sb.append(" ");
                    break;
                case '[':
                case '{':
                    indentacion += "    ";
                case ',':
                    sb.append(str.charAt(i));
                    sb.append("\n");
                    sb.append(indentacion);
                    break;
                case '}':
                case ']':
                    indentacion = indentacion.substring(4);
                    sb.append("\n");
                    sb.append(indentacion);
                    sb.append(str.charAt(i));
                    break;
                default:
                    sb.append(str.charAt(i));
                    break;
            }
        }

        return sb.toString();
    }

    private void save (String json, String fileName){
        try{
            ReaderWriter.writeLines(fileName, json);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void load() {
        clientesPorAtender = (Queue<Cliente>)new LinkedQueue();
        empleadosDisponibles = new PriorityQueue();
        turnosAtendiendo = new PriorityQueue();

        for (int i = 1; i < clientes.length; i++) {
            for (int j = i; j > 0 && clientes[j].compareTo(clientes[j - 1]) < 0; j--) {
                
                Cliente aux = clientes[j];
                clientes[j] = clientes[j - 1];
                clientes[j - 1] = aux;
            } 
        } 

        for (Cliente c : clientes) {
            clientesPorAtender.enqueue(c); 
        }

        for (Empleado e : empleados) {
            empleadosDisponibles.enqueue(e); 
        }

        horaActual = Cliente.HORA_INICIO;
        minutoActual = 0;
        turnos = new Turno[clientes.length];
        ultimoTurno = 0;

        //NEW ====================
        reporte = new Reporte();
        //========================
    }

    //DIos, soi yo de nuevo
    public void calendariza(Cliente c) {
        int hora = c.getHora();
        int minuto = c.getMinuto();
        int valC = hora * 60 + minuto;
        int valActual = horaActual * 60 + minutoActual;

        if (valActual < valC) 
            actualiza(hora, minuto); 
        
        Empleado empleado = (Empleado)empleadosDisponibles.dequeue();
        clientesPorAtender.dequeue();

        Turno t = new Turno(c, empleado, horaActual, minutoActual);
        
        reporte.agregarTurno(t);
        turnosAtendiendo.enqueue(t);
    }
    //DIos, soi yo de nuevo nuevo
    public void calendariza(Turno t) {
        int hora = t.getHoraSalida();
        int minuto = t.getMinutoSalida();
        int valT = hora * 60 + minuto;
        int valActual = this.horaActual * 60 + this.minutoActual;

        if (valActual < valT)
            actualiza(hora, minuto);
        
        t.getEmpleado().increaseAtendidos();

        empleadosDisponibles.enqueue(t.getEmpleado());
        turnosAtendiendo.dequeue();
        reporte.sacarTurno(t);
        turnos[ultimoTurno++] = t;
    }

    public boolean first(Cliente c, Turno t) {

        if (t == null)
            return true;
        
        int cHora = c.getHora();
        int cMinuto = c.getMinuto();
        int cVal = cHora * 60 + cMinuto;

        int tHora = t.getHoraSalida();
        int tMinuto = t.getMinutoSalida();
        int tVal = tHora * 60 + tMinuto;

        if (cVal < tVal && !empleadosDisponibles.isEmpty())
            return true;
        
        return false;
    }
    
    //NUEVO MET. ACTUALIZA (Mas facil que hacerlo cada vez xd)
    private void actualiza(int hora, int minuto) {
        while (minuto > 59) {
          minuto -= 60;
          hora++;
        } 

        this.horaActual = hora;
        this.minutoActual = minuto;
    }
    
    public int simular(){
        Cliente siguienteCliente;
        Turno turnoActual;

        if (cantidadClientes() == -1)
            return 1;
        if (cantidadEmpleados() == -1)
            return 2;
        
        load();

        while (!clientesPorAtender.isEmpty()) {
            siguienteCliente = clientesPorAtender.first();
            turnoActual = turnosAtendiendo.first();
        
            if (first(siguienteCliente, turnoActual)){
                calendariza(siguienteCliente);
            } else {
                calendariza(turnoActual);
            }
        }

        while (!turnosAtendiendo.isEmpty()) {
            calendariza(turnosAtendiendo.first());
        }

        //dejo mi bendicion para que el dios computologo compile correctamente
        Gson gson = new Gson();
        save(indenta(gson.toJson(empleados)), "empleados_simulacion.json");
        save(indenta(gson.toJson(turnos)), "turnos.json");
        save(indenta(gson.toJson(reporte    )), "reporte.json");

        return 0;
    }
}
