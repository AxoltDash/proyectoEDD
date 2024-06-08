
package edd.banco;

import java.util.Random;

public class Empleado implements Comparable<Empleado>{

    private String id;
    private double velocidad;
    private int atendidos;

    public Empleado (String id, double velocidad, int atendidos){
        this.id = id;
        this.velocidad = velocidad;
        this.atendidos = atendidos;
    }
    
    // @Override
    // public int compareTo(Empleado e) {
    //     return this.atendidos - e.atendidos;
    // }

    @Override
    public int compareTo(Empleado e) {
        return (int)(this.velocidad - e.velocidad);
    }
    
    public String getID(){
        return id;
    }

    public double getVelocidad(){
        return velocidad;
    }

    public int getAtendidos(){
        return atendidos;
    }
    
    public void increaseAtendidos(){
        atendidos++;
    }

    public static Empleado[] generate(int size){
        Empleado[] array = new Empleado[size];
        
        double aux;

        Random rdm = new Random();
        for (int i = 0; i < size; i++){
            aux = rdm.nextDouble();
            aux = aux * 2;
            aux = aux -1;
            aux = Math.exp(aux);

            array[i] = new Empleado("" + i, aux, 0);
        }
        
        return array;
    }
}
