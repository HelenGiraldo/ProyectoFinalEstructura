package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

public class PriorityCola<T> implements Cola<T>{
    DNodo<T> head;
    DNodo<T> tail;
    int length;

    public PriorityCola() {
        length = 0;
        head = null;
        tail = null;
    }

    public PriorityCola(Coleccion<T> coleccion) {
        length = 0;
        head = null;
        tail = null;
        addAll(coleccion);
    }




    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public boolean add(T objeto) {
        return false;
    }

    @Override
    public boolean remove(T objeto) {
        return false;
    }

    @Override
    public boolean update(T objetoInicial, T objetoFinal) {
        return false;
    }

    @Override
    public boolean containsAll(Coleccion<T> coleccion) {
        return false;
    }

    @Override
    public boolean addAll(Coleccion<T> coleccion) {
        return false;
    }

    @Override
    public boolean removeAll(Coleccion<T> coleccion) {
        return false;
    }


        @Override
    public void push(T value) {
        DNodo<T> node = new DNodo<>(value);
        if (isEmpty()) {
            head = node;
            tail = node;
        }else {
            tail.setNext(node);
            tail = node;
        }
        length++;
    }

    @Override
    public void removeFirst(){
        if (isEmpty()) {
            return;
        }
        head = head.getNext();
        length--;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        DNodo<T> node = head;
        head = head.getNext();
        length--;
        return node.getValue();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.getValue();
    }

    @Override
    public boolean contains(T value) {
        if (isEmpty()) {
            return false;
        }
        DNodo<T> node = head;
        while (node.getNext() != null) {
            if (node.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear(){
        head = null;
        tail = null;
        length = 0;
    }
}
