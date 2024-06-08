/**
 * Código utilizado para el curso de Estructuras de Datos.
 *
 * Se permite consultarlo para fines didácticos de forma personal, pero no está
 * permitido transferirlo tal cual a estudiantes actuales o potenciales pues se
 * afectará su realización de los ejercicios.
 */

package edd.banco;

import java.io.IOException;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

import edd.Calificador;
import edd.RandomStringGenerator;

import edd.readerwriter.ReaderWriter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * @author mindahrelfen
 */
public class BuscadorTest extends Calificador {

    @Override
    protected void setCategories() {
        defineCategories(new String[] {
            "cantidad",
            "simular"
        }, new double[] {
            0.1,
            0.9
        });
    }

    @Test
    public void empleadosSizeTest() {
        Buscador b;
        int size;

        startTest("Revisa que la simulacion genere correctamente la cantidad de empleados dada", 1.0, "cantidad");

        b = new Buscador();
        size = rdm.nextInt(450) + 50;
        b.generarEmpleados(size);

        assertTrue(size == b.cantidadEmpleados());

        addUp(1.0);
        passed();
    }

    @Test
    public void clientesSizeTest() {
        Buscador b;
        int size;

        startTest("Revisa que la simulacion genere correctamente la cantidad de clientes dada", 1.0, "cantidad");

        b = new Buscador();
        size = rdm.nextInt(450) + 50;
        b.generarClientes(size);

        assertTrue(size == b.cantidadClientes());

        addUp(1.0);
        passed();
    }

    @Test
    public void simularTiemposTest() {
        Buscador b;
        int size;
        int h, m, v, val;
        startTest("Revisa que la simulacion genere a turnos.json con tiempos encadenados con un solo empleado", 1.0, "simular");

        b = new Buscador();

        b.generarEmpleados(1);

        size = rdm.nextInt(450) + 50;
        b.generarClientes(size);

        assertTrue(b.simular() == 0);

        try {
            String inputJSON = ReaderWriter.readLines("turnos.json");

            Type listOfMyClassObject = new TypeToken<ArrayList<Turno>>() {}.getType();
            Gson gson = new Gson();
            List<Turno> list = gson.fromJson(inputJSON, listOfMyClassObject);


            // for (Turno t: list) {
            //     System.out.println(t);
            // }

            h = Cliente.HORA_INICIO;
            m = 0;
            for (Turno t: list) {
                val = t.getHoraSalida() * 60 + t.getMinutoSalida();
                v = h * 60 + m;
                assertTrue(val >= v);
                h = t.getHoraSalida();
                m = t.getMinutoSalida();
            }
        } catch (IOException e) {
            assertTrue("Lanzo IOException".equals(null));
        }

        addUp(1.0);
        passed();
    }
}
