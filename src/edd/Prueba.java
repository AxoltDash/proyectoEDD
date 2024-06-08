
package edd;

// import java.util.Random;
import java.util.Scanner;

import edd.colors.Colors;
import edd.banco.Buscador;

public class Prueba {
    /**
     * Metodo para obtener un valor entero con un minimo y un maximo de parte del usuario.
     * 
     * @param mensaje Frase para dar a conocer al usuario que debe ingresar un valor
     * @param min Valor minimo que quieres que sea el entero que ingrese el usuario.
     * @param max Valor maximo que quieres que sea el entero que ingrese el usuario.
     * @param error Frase para imprimir en caso de que se de un valor no deseado.
     * @return Un valor entero proporcionado por el usuario.
     */
    public static int getInt(String mensaje, String error, int min, int max) {
        int val;
        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println(mensaje);
            if (scn.hasNextInt()) {
                val = scn.nextInt();
                // (-infinito, min) || (max, infinito)
                if (val < min || max < val) {
                    System.out.println(error);
                } else {
                    return val;
                }
            } else {
                scn.next();
                System.out.println(error);
            }
        }
    }

    public static void main(String[] args) {

        Colors.title("¡Practica No. 5!", Colors.MAGENTA, Colors.CYAN);

        int opcion1;
        int opcion2;

        String error = Colors.toString("Opcion no valida!", Colors.ORANGE + Colors.HIGH_INTENSITY);
        String menuSb;
        String ynSb;

        StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append(Colors.equalslineString(39,Colors.BLUE));
            sb.append(Colors.toString("Por favor, seleccione una opcion:", Colors.WHITE + Colors.HIGH_INTENSITY) + "\n");
            sb.append(Colors.toString("1.\t", Colors.CYAN + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("Generar Empleados.\n", Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("2.\t", Colors.CYAN + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("Generar Clientes.\n", Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("3.\t", Colors.CYAN + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("Simular Turnos.\n", Colors.HIGH_INTENSITY));

            sb.append(Colors.equalslineString(19,Colors.BLACK));
            sb.append(Colors.toString("0.\t", Colors.BLUE + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("Salir.\n", Colors.HIGH_INTENSITY));
            sb.append("\n");
        menuSb = sb.toString();

        sb = new StringBuilder();
            sb.append("\n");
            sb.append(Colors.equalslineString(39,Colors.BLUE));
            sb.append(Colors.toString("1.\t", Colors.CYAN + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("Si.\n", Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("2.\t", Colors.CYAN + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("No.\n", Colors.HIGH_INTENSITY));

            sb.append(Colors.equalslineString(19,Colors.BLACK));
            sb.append(Colors.toString("0.\t", Colors.BLUE + Colors.HIGH_INTENSITY));
            sb.append(Colors.toString("Regresar.\n", Colors.HIGH_INTENSITY));
            sb.append("\n");
        ynSb = sb.toString();

        String cyan = Colors.CYAN + Colors.HIGH_INTENSITY;
        Buscador b = new Buscador();

        do {         
            opcion1 = getInt(menuSb, error, 0, 3);

            switch (opcion1) {
                case 1:
                    opcion2 = b.cantidadEmpleados();
                    if (opcion2 != -1) {
                        Colors.hiprintln("Ya existen "+ opcion2 +"empleados", Colors.ORANGE);
                        opcion2 = getInt(Colors.toString("¿Generar otra cantidad de empleados?\n", cyan) + ynSb, error, 0, 2);
                        if (opcion2 != 1)
                            break;
                    }
                    b.generarEmpleados (getInt(Colors.toString("Cuantos empleados desearía generar?", cyan) + Colors.toString("[1, 2147483647]", Colors.BLUE + Colors.HIGH_INTENSITY), error, 1, 2147483647));
                    break;

                case 2:
                    opcion2 = b.cantidadClientes();
                    if (opcion2 != -1) {
                        Colors.hiprintln("Ya existen " + opcion2 + " clientes diarios.", Colors.ORANGE);
                        opcion2 = getInt(Colors.toString("¿Generamos otra cantidad de clientes diarios?\n", cyan) + ynSb, error, 0, 2);
                        if (opcion2 != 1)
                            break; 
                    }                     
                    b.generarClientes(getInt(Colors.toString("Cuantos clientes desearía generar? ", cyan) + Colors.toString("[50, 2147483647]", Colors.BLUE + Colors.HIGH_INTENSITY), error, 50, 2147483647));
                    break;
                case 3:
                    opcion2 = b.simular();
                    if (opcion2 == 1) {
                        Colors.hiprintln("Falta generar Empleados.", Colors.ORANGE);
                        break;
                    } 
                    if (opcion2 == 2) {
                        Colors.hiprintln("Falta generar Clientes.", Colors.ORANGE);
                        break;
                    } 
                    if (opcion2 == 0) {
                        Colors.hiprintln("Simulacion Exitosa", Colors.CYAN);
                        break;
                    } 
                    Colors.hiprintln("Error al simular.", Colors.RED);
                    break;
                case 0:
                    break;
                default:
                    Colors.hiprintln("Opcion no valida. Por favor, selecciona una opcion valida.", Colors.ORANGE);
                    break;
            }

        } while (opcion1 != 0);
        Colors.hiprintln("Saliendo del programa... Gracias por ejecutar!", Colors.MAGENTA);
    }
}