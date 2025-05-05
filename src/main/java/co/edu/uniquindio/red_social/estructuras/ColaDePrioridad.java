package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

public class ColaDePrioridad<T> implements Cola<T>{
    PNodo<T> head;
    PNodo<T> tail;
    int length;

    public ColaDePrioridad() {
        length = 0;
        head = null;
        tail = null;
    }


    @Override
    public int size() {
        return length;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            PNodo<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.getValue();
                current = current.getNext();
                return value;
            }
        };
    }

    @Override
    public boolean add(T objeto) {
        return false;
    }

    @Override
    public boolean add(T objeto, int priority) {
        return push(objeto, priority);
    }

    @Override
    public boolean remove(T objeto) {
        if(head == null){
            return false;
        }
        boolean borrado = false;
        PNodo<T> current = head;
        while(current !=null && current.getValue().equals(objeto)){
            head = head.getNext();
            current = head;
            borrado = true;
            length--;
        }
        while (current.getNext() != null) {
            if(current.getNext().getValue().equals(objeto)){
                current.setNext(current.getNext().getNext());
                borrado = true;
                length--;
                if(current.getNext() == null){
                    tail = current;
                }
            }
            current = current.getNext();
        }

        return borrado;
    }

    @Override
    public boolean update(T elemento) {
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
    public boolean push(T value, int priority) {
        PNodo<T> node = new PNodo<>(value, priority);
        if (isEmpty()) {
            head = node;
            tail = node;
            return true;
        }
        PNodo<T> current = head;
            while (current != null && current.getPriority() <= priority) {
                current = current.getNext();
            }
            if (current == head) {
                node.setNext(head);
                head.setPrevious(node);
                head = node;
            } else if (current == null) {
                tail.setNext(node);
                node.setPrevious(tail);
                tail = node;

            } else {

                PNodo<T> anterior = current.getNext();
                anterior.setNext(node); ;
                node.setPrevious(anterior);

                node.setNext(current);
                current.setPrevious(node);
            }
            return true;
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
        PNodo<T> node = head;
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
        PNodo<T> node = head;
        while (node.getNext() != null) {
            if (node.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean clear(){
        head = null;
        tail = null;
        length = 0;
        return true;
    }
}
