
package edd.banco;

public class Reporte {

    private int tiempoMaximoEspera;
    private double tiempoPromedioEspera;

    private int ventanillasActivas;
    private int maximoVentanillas;
    private double promedioVentanillas;

    private int asesoresActivos;
    private int maximoAsesores;
    private double promedioAsesores;

    private int cantidad;

    public Reporte() {
        this.tiempoMaximoEspera = Integer.MIN_VALUE;
        this.tiempoPromedioEspera = 0.0;

        this.ventanillasActivas = 0;
        this.maximoVentanillas = Integer.MIN_VALUE;
        this.promedioVentanillas = 0.0;

        this.asesoresActivos = 0;
        this.maximoAsesores = Integer.MIN_VALUE;
        this.promedioAsesores = 0.0;

        this.cantidad = 0;
    }

    public void agregarTurno(Turno t) {
        Tramite r;
        int h, m, v;

        h = t.getHoraSalida() - t.getCliente().getHora();
        m = t.getMinutoSalida() - t.getCliente().getMinuto();

        v = h * 60 + m;

        if (v > tiempoMaximoEspera) tiempoMaximoEspera = v;
        tiempoPromedioEspera *= cantidad;
        tiempoPromedioEspera += v;
        tiempoPromedioEspera /= (cantidad + 1);

        r = Tramite.tramite(t.getCliente().getTramite());

        if (r.getTipo().equals("ventanilla")) {
            ventanillasActivas++;
            if (ventanillasActivas > maximoVentanillas) maximoVentanillas = ventanillasActivas;
            promedioVentanillas *= cantidad;
            promedioVentanillas += ventanillasActivas;
            promedioVentanillas /= (cantidad + 1);
        } else {
            asesoresActivos++;
            if (asesoresActivos > maximoAsesores) maximoAsesores = asesoresActivos;
            promedioAsesores *= cantidad;
            promedioAsesores += asesoresActivos;
            promedioAsesores /= (cantidad + 1);
        }

        cantidad++;
    }

    public void sacarTurno(Turno t) {
        Tramite r;

        r = Tramite.tramite(t.getCliente().getTramite());

        if (r.getTipo().equals("ventanilla")) {
            ventanillasActivas--;
        } else {
            asesoresActivos--;
        }
    }

    @Override
    public String toString() {
        return "<Tiempo Maximo de Espera: " + tiempoMaximoEspera + ", Tiempo Promedio de Espera: " + tiempoPromedioEspera + ", #Maximo de Ventanillas: " + maximoVentanillas + ", #Promedio de Ventanillas: " + promedioVentanillas + ", #Maximo de Asesoreses: " + maximoAsesores + ", #Promedio de Asesoreses: " + promedioAsesores + ", Cantidad de turnos: " + cantidad + ">";
    }
}
