package edd.estructuras.lineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E> {
    
    private E[] element;
    private int maximo; //maximo de elementos del arreglo
    private int front;  //frente
    private int rear;   //fondo
    private int size;   //tamano

    public ArrayQueue(int m) {
        this.maximo = m;
        element = (E[]) new Object[maximo];
        front = 0;
        rear = maximo -1;
        size = 0;
    }
    public ArrayQueue(){
        this(16);
    }

    @Override 
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public E first(){
        return isEmpty() ? null : element[front];
    }

    @Override //insert
    public void enqueue(E e){
        if (size == maximo){
            throw new RuntimeException("La cola llena");
        }
        rear = (rear + 1) % maximo;
        element[rear] = e;
        size++;
    }

    @Override
    public E dequeue(){
        if (isEmpty()){
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