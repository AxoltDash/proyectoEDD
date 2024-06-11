package edd.banco;

import java.util.Random;

/**
 * Clase que representa a un empleado en el sistema de turnos del banco.
 */
public class Empleado implements Comparable<Empleado> {

    private String id;
    private double velocidad;
    private int atendidos;

    /**
     * Constructor para crear un empleado con un identificador, una velocidad de atención y un número de clientes atendidos.
     *
     * @param id El identificador del empleado.
     * @param velocidad La velocidad de atención del empleado.
     * @param atendidos El número de clientes atendidos por el empleado.
     */
    public Empleado(String id, double velocidad, int atendidos) {
        this.id = id;
        this.velocidad = velocidad;
        this.atendidos = atendidos;
    }

    /**
     * Compara este empleado con otro empleado basado o en la velocidad de atención o en la atención.
     *
     * @param e El empleado a comparar.
     * @return negativo si es menor a la comparación
     */
    @Override
    public int compareTo(Empleado e) {
        return (int)(this.velocidad - e.velocidad);
        // return (int)(this.atendidos - e.atendidos);
    }

    /**
     * Devuelve el identificador del empleado.
     *
     * @return El identificador del empleado.
     */
    public String getID() {
        return id;
    }

    /**
     * Devuelve la velocidad de atención del empleado.
     *
     * @return La velocidad de atención.
     */
    public double getVelocidad() {
        return velocidad;
    }

    /**
     * Devuelve el número de clientes atendidos por el empleado.
     *
     * @return El número de clientes atendidos.
     */
    public int getAtendidos() {
        return atendidos;
    }

    /**
     * Incrementa en uno el número de clientes atendidos por el empleado.
     */
    public void increaseAtendidos() {
        atendidos++;
    }

    /**
     * Genera un array de empleados con datos simulados.
     *
     * @param size El número de empleados a generar.
     * @return Un array de empleados generados.
     */
    public static Empleado[] generate(int size) {
        Empleado[] array = new Empleado[size];

        double aux;
        Random rdm = new Random();

        for (int i = 0; i < size; i++) {
            aux = rdm.nextDouble();
            aux = aux * 2;
            aux = aux - 1;
            aux = Math.exp(aux);

            array[i] = new Empleado("" + i, aux, 0);
        }

        return array;
    }
}
