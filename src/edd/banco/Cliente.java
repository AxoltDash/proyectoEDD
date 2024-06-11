package edd.banco;
import java.util.Random;

/**
 * Clase que representa a un cliente en el sistema de turnos del banco.
 */
public class Cliente implements Comparable<Cliente> {

    private String tramite;
    private int hora;
    private int minuto;

    // Distribución de clientes por hora
    //                                                 8:00  9:00 10:00 11:00 12:00 13:00 14:00 15:00
    public static double[] DISTRIBUCION = new double[]{0.1, 0.115, 0.12, 0.125, 0.13, 0.135, 0.14, 0.135};

    /**
     * Constructor para crear un cliente con un trámite específico y una hora y minuto de llegada.
     *
     * @param tramite El trámite que el cliente necesita realizar.
     * @param hora La hora de llegada del cliente.
     * @param minuto El minuto de llegada del cliente.
     */
    public Cliente(String tramite, int hora, int minuto) {
        this.tramite = tramite;
        this.hora = hora;
        this.minuto = minuto;
    }

    public static int HORA_INICIO = 8;

    /**
     * Compara este cliente con otro cliente basado en la hora y minuto de llegada.
     *
     * @param c El cliente a comparar.
     * @return Un valor negativo si este cliente llega antes que el cliente dado,
     *         cero si llegan al mismo tiempo, o un valor positivo si llega después.
     */
    @Override
    public int compareTo(Cliente c) {
        int v1 = hora * 60 + minuto;
        int v2 = c.hora * 60 + c.minuto;
        return v1 - v2;
    }

    /**
     * Calcula el tiempo de espera del cliente desde su llegada hasta un tiempo dado.
     *
     * @param hora La hora en que se mide el tiempo de espera.
     * @param minuto El minuto en que se mide el tiempo de espera.
     * @return El tiempo de espera en minutos.
     */
    public int tiempoEspera(int hora, int minuto) {
        int h = hora - this.hora;
        int m = minuto - this.minuto;
        return h * 60 + m;
    }

    /**
     * Genera un array de clientes con datos simulados.
     *
     * @param size El número de clientes a generar.
     * @return Un array de clientes generados.
     */
    public static Cliente[] generate(int size) {
        Cliente[] array = new Cliente[size];
        Tramite[] tramites = Tramite.tramites();
        double[] distribucion;
        int hora, minuto;
        double aux = 0.0;
        String tramite;

        Random rdm = new Random();
        distribucion = new double[DISTRIBUCION.length];

        // Calcula la distribución inicial basada en los porcentajes.
        for (int i = 0; i < distribucion.length; i++) {
            distribucion[i] = Math.round(DISTRIBUCION[i] * size);
            aux += distribucion[i];
        }

        // Ajusta la distribución si hay alguna discrepancia.
        while (aux < size) {
            distribucion[rdm.nextInt(distribucion.length)]++;
            aux++;
        }

        for (int i = 0; i < size; i++) {
            tramite = tramites[rdm.nextInt(tramites.length)].getNombre();
            minuto = rdm.nextInt(60);
            
            while (true) {
                hora = rdm.nextInt(distribucion.length);
                if (distribucion[hora] != 0) {
                    distribucion[hora]--;
                    break;
                }
            }

            array[i] = new Cliente(tramite, hora + HORA_INICIO, minuto);
        }

        return array;
    }

    /**
     * Devuelve una representación en cadena del cliente.
     *
     * @return Una cadena que representa al cliente.
     */
    @Override
    public String toString() {
        return "<Tramite: " + this.tramite + ", Hora: " + this.hora + ", Minuto: " + this.minuto + ">";
    }

    /**
     * Devuelve el nombre del trámite que el cliente necesita realizar.
     *
     * @return El nombre del trámite.
     */
    public String getTramite() {
        return tramite;
    }

    /**
     * Devuelve la hora de llegada del cliente.
     *
     * @return La hora de llegada.
     */
    public int getHora() {
        return hora;
    }

    /**
     * Devuelve el minuto de llegada del cliente.
     *
     * @return El minuto de llegada.
     */
    public int getMinuto() {
        return minuto;
    }
}
