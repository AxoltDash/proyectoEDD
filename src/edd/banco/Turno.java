package edd.banco;

import java.util.Random;

/**
 * Clase que representa un turno en el sistema de turnos del banco.
 */
public class Turno implements Comparable<Turno> {

    private Cliente cliente;
    private Empleado empleado;
    private int horaEntrada;
    private int minutoEntrada;
    private int horaSalida;
    private int minutoSalida;

    /**
     * Constructor para crear un turno con el cliente, empleado, hora y minuto de entrada especificados.
     * 
     * @param cliente El cliente asociado al turno.
     * @param empleado El empleado asociado al turno.
     * @param hora La hora de entrada del turno.
     * @param minuto El minuto de entrada del turno.
     */
    public Turno(Cliente cliente, Empleado empleado, int hora, int minuto) {
        
        this.cliente = cliente;
        this.empleado = empleado;
        this.horaEntrada = hora;
        this.minutoEntrada = minuto;
        
        Tramite tramite = Tramite.tramite(cliente.getTramite());

        Random rdm = new Random();
        double d = (rdm.nextInt(tramite.getMaximo() - tramite.getMinimo()) + tramite.getMinimo());
        int m = (int)Math.round(empleado.getVelocidad() * d);

        this.horaSalida = hora + m / 60;
        this.minutoSalida = minuto + m % 60;

        while (this.minutoSalida > 59) {
          this.minutoSalida -= 60;
          this.horaSalida++;
        } 
    }
  
    /**
     * Compara este turno con otro turno en función de la hora de salida.
     * 
     * @param t El turno a comparar.
     * @return Un número negativo, cero o un número positivo según este turno sea
     *         anterior, igual o posterior al turno especificado.
     */
    @Override
    public int compareTo(Turno t){
        int v1, v2;

        v1 = horaSalida * 60 + minutoSalida;
        v2 = t.horaSalida * 60 + t.minutoSalida;

        return v1 - v2;
    }

    /**
     * Obtiene el cliente asociado a este turno.
     * 
     * @return El cliente asociado a este turno.
     */
    public Cliente getCliente(){
        return cliente;
    }

    /**
     * Obtiene el minuto de entrada de este turno.
     * 
     * @return El minuto de entrada de este turno.
     */
    public int getMinutoEntrada(){
        return minutoEntrada;
    }

    /**
     * Obtiene el minuto de salida de este turno.
     * 
     * @return El minuto de salida de este turno.
     */
    public int getMinutoSalida(){
        return minutoSalida;
    }

    /**
     * Obtiene la hora de entrada de este turno.
     * 
     * @return La hora de entrada de este turno.
     */
    public int getHoraEntrada(){
        return horaEntrada;
    }

    /**
     * Obtiene la hora de salida de este turno.
     * 
     * @return La hora de salida de este turno.
     */
    public int getHoraSalida(){
        return horaSalida;
    }

    /**
     * Obtiene el empleado asociado a este turno.
     * 
     * @return El empleado asociado a este turno.
     */
    public Empleado getEmpleado(){
        return empleado;
    }
}
