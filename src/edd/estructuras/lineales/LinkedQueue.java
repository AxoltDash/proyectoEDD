
package edd.estructuras.lineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de Cola Ligada.
 *
 * @author mindahrelfen
 */
public class LinkedQueue<E> implements Queue<E> {

    /**
     * Referencia a la cabeza de la cola.
     */
    protected SNode<E> head;

    /**
     * Referencia al final de la cola.
     */
    protected SNode<E> tail;

    /**
     * Tamaño de la cola.
     */
    protected int size;

    /**
     * Constructor por defecto, construye una cola sin elementos.
     */
    public LinkedQueue() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E dequeue() {
        E e;
        SNode<E> aux;

        if (size == 0) {
            return null;
        } else {
            e = head.elem;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                aux = head.next;
                head.next = null;
                head.elem = null;
                head = aux;
            }
            size--;

            return e;
        }
    }

    @Override
    public void enqueue(E e) {
        SNode<E> aux;

        aux = new SNode<>();
        aux.elem = e;
        if (head == null) {
            head = aux;
            tail = aux;
        } else {
            tail.next = aux;
            tail = aux;
        }
        size++;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedQueueIterator();
    }

    @Override
    public E first() {
        if (size == 0) {
            return null;
        } else {
            return head.elem;
        }
    }

    /**
     * Clase que implementa el Iterador de la clase LinkedQueue.
     */
    protected class LinkedQueueIterator implements Iterator<E> {

        /**
         * Referencia al nodo actual.
         */
        protected SNode<E> actual;

        /**
         * Referencia al nodo que se puede borrar.
         */
        protected SNode<E> previousToDelete;

        /**
         * Bandera que dice si se puede borrar un elemento o no.
         */
        protected boolean canRemove;

        /**
         * Constructor por defecto. Inicializa el iterador a la cabeza de la
         * cola.
         */
        public LinkedQueueIterator() {
            actual = head;
            previousToDelete = null;
            canRemove = false;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public E next() {
            E e;

            if (!hasNext()) throw new NoSuchElementException();

            if (canRemove) {
                if (previousToDelete == null) {
                    previousToDelete = head;
                } else {
                    previousToDelete = previousToDelete.next;
                }
            } else {
                if (actual != head) {
                    previousToDelete = actual;
                }
            }

            e = actual.elem;
            actual = actual.next;
            canRemove = true;

            return e;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
            /***
            int i;

            if (!canRemove) throw new IllegalStateException();

            if (previousToDelete == null) {
                dequeue();
            } else {
                previousToDelete.next.elem = null;
                previousToDelete.next = actual;
                size--;
            }

            canRemove = false;
            ***/
        }
    }

    class SNode<E> {

        /**
         * Referencia al nodo siguiente.
         */
        SNode<E> next;

        /**
         * Referencia al valor contenido en el nodo.
         */
        E elem;

        @Override
        public String toString() {
            return "<" + elem + ">";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb;
        SNode<E> aux;

        if (isEmpty()) return "[]";

        sb = new StringBuilder();
        aux = head;

        sb.append("[");
        while (aux != null) {
            sb.append(aux.toString());
            aux = aux.next;
            if (aux != null) sb.append(" ");
        }
        sb.append("]");

        return sb.toString();
    }
}
