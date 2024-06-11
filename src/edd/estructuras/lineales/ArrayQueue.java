package edd.estructuras.lineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de una cola basada en arreglos.
 */
public class ArrayQueue<E> implements Queue<E> {
    
    /**
     * Arreglo que almacena los elementos de la cola.
     */
    private E[] element;
    
    /**
     * Capacidad máxima del arreglo.
     */
    private int maximo;
    
    /**
     * Índice del frente de la cola.
     */
    private int front;
    
    /**
     * Índice del fondo de la cola.
     */
    private int rear;
    
    /**
     * Tamaño actual de la cola.
     */
    private int size;

    /**
     * Constructor que inicializa la cola con una capacidad específica.
     *
     * @param m Capacidad máxima de la cola.
     */
    public ArrayQueue(int m) {
        this.maximo = m;
        element = (E[]) new Object[maximo];
        front = 0;
        rear = maximo - 1;
        size = 0;
    }

    /**
     * Constructor por defecto que inicializa la cola con una capacidad de 16.
     */
    public ArrayQueue() {
        this(16);
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
    public E first() {
        return isEmpty() ? null : element[front];
    }

    @Override
    public void enqueue(E e) {
        if (size == maximo) {
            throw new RuntimeException("La cola está llena");
        }
        rear = (rear + 1) % maximo;
        element[rear] = e;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }

        E tmp = element[front];
        element[front] = null;
        front = (front + 1) % maximo;
        size--;
        return tmp;
    }

    @Override
    public String toString() {
        StringBuilder sb;
        if (isEmpty()) return "[]";
        sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(element[(i + front) % element.length]);
            if (i < size - 1) sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Implementación del iterador
    @Override
    public Iterator<E> iterator() {
        return new ArrayQueueIterator();
    }

    /**
     * Clase interna que implementa el iterador para la cola.
     */
    private class ArrayQueueIterator implements Iterator<E> {
        private int currentIndex = front;
        private int remaining = size;

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = element[currentIndex];
            currentIndex = (currentIndex + 1) % element.length;
            remaining--;
            return data;
        }
    }
}
