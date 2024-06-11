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

/**
 * Clase que simula la gestión de turnos en un banco.
 */
public class Buscador {

    private Reporte reporte = new Reporte();

    private Cliente[] clientes;
    private Empleado[] empleados;

    private int ultimoTurno;
    private Turno[] turnos;

    private Queue<Cliente> clientesPorAtender;
    private PriorityQueue<Empleado> empleadosDisponibles;
    private PriorityQueue<Turno> turnosAtendiendo;

    int horaActual;
    int minutoActual;

    /**
     * Constructor que inicializa los clientes y empleados leyendo desde archivos JSON.
     */
    public Buscador() {
        clientes = null;
        empleados = null;
        
        try {
            String inputJSON = ReaderWriter.readLines("clientes.json");

            Type listOfMyClassObject = new TypeToken<ArrayList<Cliente>>() {}.getType();
            Gson gson = new Gson();
            List<Cliente> list = gson.fromJson(inputJSON, listOfMyClassObject);

            clientes = new Cliente[list.size()];
            for (int i = 0; i < list.size(); i++) {
                clientes[i] = list.get(i);
            }

        } catch (IOException e) {
            clientes = null;
        }

        try {
            String inputJSON = ReaderWriter.readLines("empleados.json");

            Type listOfMyClassObject = new TypeToken<ArrayList<Empleado>>() {}.getType();
            Gson gson = new Gson();
            List<Empleado> list = gson.fromJson(inputJSON, listOfMyClassObject);

            empleados = new Empleado[list.size()];
            for (int i = 0; i < list.size(); i++) {
                empleados[i] = list.get(i);
            }

        } catch (IOException e) {
            empleados = null;
        }
    }

    /**
     * Genera una lista de clientes y guarda en un archivo JSON.
     *
     * @param size El número de clientes a generar.
     */
    public void generarClientes(int size) {
        Gson gson = new Gson();

        clientes = Cliente.generate(size);
        turnos = new Turno[clientes.length];
        ultimoTurno = 0;

        save(indenta(gson.toJson(clientes)), "clientes.json");
    }

    /**
     * Genera una lista de empleados y guarda en un archivo JSON.
     *
     * @param size El número de empleados a generar.
     */
    public void generarEmpleados(int size) {
        Gson gson = new Gson();

        empleados = Empleado.generate(size);
        save(indenta(gson.toJson(empleados)), "empleados.json");
    }

    /**
     * Devuelve la cantidad de clientes.
     *
     * @return El número de clientes o -1 si no hay clientes.
     */
    public int cantidadClientes() {
        if (clientes == null)
            return -1;
        return clientes.length;
    }

    /**
     * Devuelve la cantidad de empleados.
     *
     * @return El número de empleados o -1 si no hay empleados.
     */
    public int cantidadEmpleados() {
        if (empleados == null)
            return -1;
        return empleados.length;
    }

    /**
     * Indenta una cadena JSON para hacerla más legible.
     *
     * @param str La cadena JSON a indentar.
     * @return La cadena JSON indentada.
     */
    private static String indenta(String str) {
        StringBuilder sb = new StringBuilder();
        String indentacion = "";

        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
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

    /**
     * Guarda una cadena en un archivo.
     *
     * @param json     La cadena JSON a guardar.
     * @param fileName El nombre del archivo donde se guardará la cadena.
     */
    private void save(String json, String fileName) {
        try {
            ReaderWriter.writeLines(fileName, json);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Carga los datos iniciales y organiza los clientes y empleados en colas.
     */
    public void load() {
        clientesPorAtender = (Queue<Cliente>) new LinkedQueue();
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

        // Inicializa el reporte
        reporte = new Reporte();
    }

    /**
     * Asigna un cliente a un empleado y actualiza los turnos.
     *
     * @param c El cliente a calendarizar.
     */
    public void calendariza(Cliente c) {
        int hora = c.getHora();
        int minuto = c.getMinuto();
        int valC = hora * 60 + minuto;
        int valActual = horaActual * 60 + minutoActual;

        if (valActual < valC)
            actualiza(hora, minuto);

        Empleado empleado = empleadosDisponibles.dequeue();
        clientesPorAtender.dequeue();

        Turno t = new Turno(c, empleado, horaActual, minutoActual);

        reporte.agregarTurno(t);
        turnosAtendiendo.enqueue(t);
    }

    /**
     * Completa un turno y actualiza el estado del empleado.
     *
     * @param t El turno a completar.
     */
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

    /**
     * Determina si un cliente puede ser atendido antes que un turno existente.
     *
     * @param c El cliente a evaluar.
     * @param t El turno existente.
     * @return Verdadero si el cliente puede ser atendido primero, falso en caso contrario.
     */
    public boolean first(Cliente c, Turno t) {
        if (t == null)
            return true;

        int cHora = c.getHora();
        int cMinuto = c.getMinuto();
        int cVal = cHora * 60 + cMinuto;

        int tHora = t.getHoraSalida();
        int tMinuto = t.getMinutoSalida();
        int tVal = tHora * 60 + tMinuto;

        return cVal < tVal && !empleadosDisponibles.isEmpty();
    }

    /**
     * Actualiza la hora y el minuto actuales.
     *
     * @param hora   La nueva hora.
     * @param minuto El nuevo minuto.
     */
    private void actualiza(int hora, int minuto) {
        while (minuto > 59) {
            minuto -= 60;
            hora++;
        }

        this.horaActual = hora;
        this.minutoActual = minuto;
    }

    /**
     * Simula el proceso de atención de clientes y guarda los resultados.
     *
     * @return 0 si la simulación se completó con éxito, 1 si no hay clientes, 2 si no hay empleados.
     */
    public int simular() {
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

            if (first(siguienteCliente, turnoActual)) {
                calendariza(siguienteCliente);
            } else {
                calendariza(turnoActual);
            }
        }

        while (!turnosAtendiendo.isEmpty()) {
            calendariza(turnosAtendiendo.first());
        }

        Gson gson = new Gson();
        save(indenta(gson.toJson(empleados)), "empleados_simulacion.json");
        save(indenta(gson.toJson(turnos)), "turnos.json");
        save(indenta(gson.toJson(reporte)), "reporte.json");

        return 0;
    }
}
