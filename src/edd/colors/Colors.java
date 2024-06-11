package edd.colors;
//Renember on main: import edd.colors.Colors;

/**
 * Clase que se encarga de guardar <strong>las cadenas de formato</strong> para la terminal.
 * Modificiaciones Ver 1.2.5: 
 * Agregado el color ORANGE y BGD_ORANGE. 
 * Agregado hiprint() y hiprintln() para imprimir con HIGH_INTENSITY.
 * Agregado title() para imprimir titulos ajustables con centelleos.
 * Agregado equalsline() para imprimir una linea con "=" y "-".
 * Agregado equalslineString() para obtener cadena de una linea con "=" y "-".
 * Agregado toString() para obtener un String con el formato dado.
 * 
 * @author Mindahrelfen
 * @author AxoltDash
 */
public class Colors {

    /**
     * Modifica el color de la fuente a negro.
     */
    public static final String BLACK = "\033[0;30m";

    /**
     * Modifica el color de la fuente a rojo.
     */
    public static final String RED = "\033[0;31m";

    /**
     * Modifica el color de la fuente a verde.
     */
    public static final String GREEN = "\033[0;32m";

    /**
     * Modifica el color de la fuente a amarillo.
     */
    public static final String YELLOW = "\033[0;33m";

    /**
     * Modifica el color de la fuente a azul.
     */
    public static final String BLUE = "\033[0;34m";

    /**
     * Modifica el color de la fuente a magenta.
     */
    public static final String MAGENTA = "\033[0;35m";

    /**
     * Modifica el color de la fuente a cyan.
     */
    public static final String CYAN = "\033[0;36m";

    /** 
     * Modifica el color de la fuente a naranja.
    */
    public static final String ORANGE = "\u001B[38;5;214m";

    /**
     * Modifica el color de la fuente a blanco.
     */
    public static final String WHITE = "\033[0;37m";

    /**
     * Elimina todos los formatos de la fuente, incluyendo el color.
     */
    public static final String RESTORE = "\033[0m";

    /**
     * Muestra a la fuente con un colo mas intenso.
     */
    public static final String HIGH_INTENSITY = "\033[1m";

    /**
     * Muestra a la fuente con un colo menos intenso.
     */
    public static final String LOW_INTENSITY = "\033[2m";

    /**
     * Agrega el formato italics a la fuente.
     */
    public static final String ITALICS = "\033[3m";

    /**
     * Subraya a la fuente.
     */
    public static final String UNDERLINE = "\033[4m";

    /**
     * Hace parpadear a la fuente.
     */
    public static final String BLINK = "\033[5m";

    /**
     * Hace parpadear muy rapido a la fuente.
     */
    public static final String FAST_BLINK = "\033[6m";

    /**
     * Intercambia el color de la fuente con el del fondo.
     */
    public static final String REVERSE = "\033[7m";

    /**
     * Vuelve invisible a la fuente.
     */
    public static final String INVISIBLE_TEXT = "\033[8m";

    /**
     * Modifica el color de fondo a negro.
     */
    public static final String BGD_BLACK = "\033[0;40m";

    /**
     * Modifica el color de fondo a rojo.
     */
    public static final String BGD_RED = "\033[0;41m";

    /**
     * Modifica el color de fondo a verde.
     */
    public static final String BGD_GREEN = "\033[0;42m";

    /**
     * Modifica el color de fondo a amarillo.
     */
    public static final String BGD_YELLOW = "\033[0;43m";

    /**
     * Modifica el color de fondo a azul.
     */
    public static final String BGD_BLUE = "\033[0;44m";

    /**
     * Modifica el color de fondo a magenta.
     */
    public static final String BGD_MAGENTA = "\033[0;45m";

    /**
     * Modifica el color de fondo a cyan.
     */
    public static final String BGD_CYAN = "\033[0;46m";

    /**
     * Modifica el color de fondo a blanco.
     */
    public static final String BGD_WHITE = "\033[0;47m";

    /**
     * Modifica el color de fondo a naranja
     */
    public static final String BGD_ORANGE = "\u001B[48;5;214m";

    /**
     * Imprime la cadena <code>s</code> con el formato dado. Imprime con retorno de carro.
     *
     * @param s Cadena a imprimir.
     * @param format Formato a aplicar a dicha cadena.
     */
    public static final void println(Object s, String format) {
        System.out.println(format + s + RESTORE);
    }

    /**
     * Imprime la cadena <code>s</code> con el formato dado. Imprime sin retorno de carro.
     *
     * @param s Cadena a imprimir.
     * @param format Formato a aplicar a dicha cadena.
     */
    public static final void print(Object s, String format) {
        System.out.print(format + s + RESTORE);
    }
    
    /**
     * Imprime la cadena <code>s</code> con el formato dado. Imprime con retorno de carro. Imprime con HIGH_INTENSITY.
     *
     * @param s Cadena a imprimir.
     * @param format Formato a aplicar a dicha cadena.
     */
    public static final void hiprintln(Object s, String format) {
        System.out.println(format + HIGH_INTENSITY + s + RESTORE);
    }

    /**
     * Imprime la cadena <code>s</code> con el formato dado. Imprime sin retorno de carro. Imprime con HIGH_INTENSITY.
     *
     * @param s Cadena a imprimir.
     * @param format Formato a aplicar a dicha cadena.
     */
    public static final void hiprint(Object s, String format) {
        System.out.print(format + HIGH_INTENSITY + s + RESTORE);
    }

    /**
     * Imprime un titulo llamativo con HIGH_INTENSITY y con formato de preferencia.
     *
     * @param title Cadena a imprimir.
     * @param format Formato a aplicar a la decoracion de la cadena.
     * @param formatTitle Formato para aplicar al texto del titulo.
     */
    public static final void title(String title, String format, String formatTitle){
        int longTitle = title.length();
        StringBuilder slashBuilder = new StringBuilder();
        
        for (int i = 0; i < (longTitle + 6); i++) {
            slashBuilder.append("/");
        }
        String slash = slashBuilder.toString();
        title = formatTitle + HIGH_INTENSITY + title + format + HIGH_INTENSITY; 

        hiprint("  //", format + BLINK); hiprint(slash,  format); hiprint("//", format + BLINK);
        System.out.println();
        hiprint("//", format + BLINK); hiprint("//// " + title + " ////", format); hiprint("//", format + BLINK);
        System.out.println();
        hiprint("  //", format + BLINK); hiprint(slash, format); hiprint("//", format + BLINK);
        System.out.println("\n");
    }

    /**
     * Imprime una linea formada por una alternancia de simbolos de igual ("=") y guion ("-").
     * La longitud de la linea y el formato de impresion son especificados como parametros.
     *
     * @param l       Longitud de la linea, el numero de simbolos que se imprimiran.
     * @param format  Formato de impresion.
     *
     * @throws IllegalArgumentException Si la longitud especificada es menor o igual a cero.
     */
    public static final void equalsline(int l, String format){
        if (l > 0) {

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < l; i++) {
            if (i%2 == 0) {
                line.append("=");
            } else {
                line.append("-");
            }
            
        }
        println(line.toString(), format);
        } else {
            throw new IllegalArgumentException("The number of symbols to print must be greater than \"0\".");
        }
    }


    /**
     * Da una cadena una linea formada por una alternancia de simbolos de igual ("=") y guion ("-").
     * La longitud de la linea y el formato son especificados como parametros.
     *
     * @param l       Longitud de la linea, el numero de simbolos que se imprimiran.
     * @param format  Formato.
     * 
     * @return        Linea de lineas que se regresa
     *
     * @throws IllegalArgumentException Si la longitud especificada es menor o igual a cero.
     */
    public static final String equalslineString(int l, String format){
        if (l > 0) {

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < l; i++) {
            if (i%2 == 0) {
                line.append("=");
            } else {
                line.append("-");
            }
        }
        return toString(line.toString(), format) + "\n";
        
        } else {
            throw new IllegalArgumentException("The number of symbols to print must be greater than \"0\".");
        }
    }

    /**
     * Obtiene un String <code>s</code> con el formato dado
     * 
     * @param s     Mensaje que se obtiene para convertir a String
     * @param format Formato que se aplica
     * @return String ya con codigo de color
     */
    public static final String toString(Object s, String format){
        return (format + s + RESTORE);
    }
}