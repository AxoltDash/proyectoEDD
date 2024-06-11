package edd.estructuras.lineales;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edd.banco.Tramite;

/**
 * Implementación de Cola Ligada con Prioridad.
 */
public class PriorityQueue<E> implements Queue<E> {

    /**
     * Método estático que devuelve un trámite.
     * 
     * @param tramite Objeto tramite que se inserta
     * @return tramite Objeto que se devuelve
     */
    public static Tramite tramite(Tramite tramite) {
        return tramite;
    }

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
    public PriorityQueue() {
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
        // Prioridad predeterminada si no se especifica
        enqueue(e, 0);
    }

    /**
     * Método para encolar un elemento con una prioridad específica.
     * 
     * @param e Objeto que se encolará
     * @param priority Prioridad que presenta para encolar
     */
    public void enqueue(E e, int priority) {
        SNode<E> newNode = new SNode<>();
        newNode.elem = e;
        newNode.priority = priority;

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            SNode<E> current = head;
            SNode<E> previous = null;

            while (current != null && current.priority <= priority) {
                previous = current;
                current = current.next;
            }

            if (previous == null) {
                newNode.next = head;
                head = newNode;
            } else if (current == null) {
                previous.next = newNode;
                tail = newNode;
            } else {
                previous.next = newNode;
                newNode.next = current;
            }
        }
        size++;
    }

    @Override
    public Iterator<E> iterator() {
        return new PriorityQueueIterator();
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
     * Clase que implementa el Iterador de la clase PriorityQueue.
     */
    protected class PriorityQueueIterator implements Iterator<E> {

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
         * Constructor por defecto. Inicializa el iterador a la cabeza de la cola.
         */
        public PriorityQueueIterator() {
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
        }
    }

    /**
     * Clase interna que representa un nodo de la cola.
     */
    class SNode<E> {

        /**
         * Referencia al nodo siguiente.
         */
        SNode<E> next;

        /**
         * Referencia al valor contenido en el nodo.
         */
        E elem;

        /**
         * Prioridad del elemento.
         */
        int priority;

        @Override
        public String toString() {
            return "<" + elem + ", priority=" + priority + ">";
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
