
package edd.banco;
import java.util.Random;

public class Cliente implements Comparable<Cliente> {

    private String tramite;
    private int hora;
    private int minuto;

    //                                                 8:00 9:00  10:00 11:00 12:00  13:00  14:00 15:00
    public static double[] DISTRIBUCION = new double[]{0.1, 0.115, 0.12, 0.125, 0.13, 0.135, 0.14, 0.135};

    public Cliente (String tramite, int hora, int minuto){
        this.tramite = tramite;
        this.hora = hora;
        this.minuto = minuto;
    }
    
    public static int HORA_INICIO = 8;

    public int compareTo(Cliente c){
        int v1, v2;

        v1 = hora * 60 + minuto;
        v2 = c.hora * 60 + c.minuto;

        return v1 - v2;
    }

    public int tiempoEspera(int hora, int minuto){
        int h, m;
        h = hora - this.hora;
        m = minuto - this.minuto;

        return h * 60 + m;
    }

    public static Cliente[] generate(int size){
        Cliente[] array = new Cliente[size];
        Tramite[] tramites = Tramite.tramites();
        double[] distribucion;
        int hora, minuto;
        double aux = 0.0;
        String tramite;

        Random rdm = new Random();
        distribucion = new double[DISTRIBUCION.length];
        
        for (int i = 0; i < distribucion.length; i++){
            distribucion[i] = Math.round(DISTRIBUCION[i] * size);
            aux += distribucion[i];
        }

        while (aux < size) {
            distribucion[rdm.nextInt(distribucion.length)]++;
            aux++;
        }

        hora = 0;
        minuto = 0;

        for (int i = 0; i < size; i++) {
            tramite = tramites[rdm.nextInt(tramites.length)].getNombre();
            minuto = rdm.nextInt(60);
            
            while (true) {
                hora = rdm.nextInt(distribucion.length);
                if (distribucion[hora]!= 0) {
                    distribucion[hora]--;
                    break;
                }
            }
            
            array[i] = new Cliente(tramite, hora + HORA_INICIO, minuto);
            
        }

        return array;
    }

    public String toString(){
        return "<Tramite: " + this.tramite + ", Hora: " + this.hora + ", Minuto: " + this.minuto + ">";
    }

    public String getTramite(){
        return tramite;
    }

    public int getHora(){
        return hora;
    }

    public int getMinuto(){
        return minuto;
    }    
}
