
package edd.banco;

import java.util.Random;

public class Turno implements Comparable<Turno> {

    private Cliente cliente;
    private Empleado empleado;
    private int horaEntrada;
    private int minutoEntrada;
    private int horaSalida;
    private int minutoSalida;

    //el random estuvo hard xd
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
  
    public int compareTo(Turno t){
        int v1, v2;

        v1 = horaSalida * 60 + minutoSalida;
        v2 = t.horaSalida * 60 + t.minutoSalida;

        return v1 - v2;
    }

    //Ya no puedo maaaaassssss con los geeteerr ahhh
    public Cliente getCliente(){
        return cliente;
    }

    public int getMinutoEntrada(){
        return minutoEntrada;
    }

    public int getMinutoSalida(){
        return minutoSalida;
    }

    public int getHoraEntrada(){
        return horaEntrada;
    }

    public int getHoraSalida(){
        return horaSalida;
    }

    public Empleado getEmpleado(){
        return empleado;
    }
}