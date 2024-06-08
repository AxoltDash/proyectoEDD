
package edd.estructuras.lineales;

/**
 * TDA Queue.
 *
 * @author mindahrelfen
 */
public interface Queue<E> extends Iterable<E> {

    /**
     * Devuelve el numero de elementos contenidos en esta pila.
     *
     * @return El tamaño de esta pila como entero mayor a cero.
     */
    public int size();

    /**
     * Pregunta si esta pila esta vacia.
     *
     * @return Devuelve true si el numero de elementos dentro de esta pila es cero.
     */
    public boolean isEmpty();

    /**
     * Muestra el elemento al inicio de la cola.
     * Devuelve <code>null</code> si está vacía.
     *
     * @return Una referencia al elemento siguiente.
     */
    public E first();

    /**
     * Devuelve el elemento al inicio de la cola y lo elimina.
     * Devuelve <code>null</code> si está vacía.
     *
     * @return Una referencia al elemento siguiente.
     */
    public E dequeue();

    /**
     * Agrega un elemento al final de la cola.
     *
     * @param e Referencia al elemento a agregar.
     */
    public void enqueue(E e);
}
