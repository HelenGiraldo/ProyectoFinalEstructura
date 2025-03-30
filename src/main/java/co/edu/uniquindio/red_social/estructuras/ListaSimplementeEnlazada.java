package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

public class ListaSimplementeEnlazada<T> implements Lista<T> {

    Nodo<T> head;
    Nodo<T> tail;
    int length;

    public ListaSimplementeEnlazada() {
        length = 0;
        head = null;
        tail = null;
    }

    public ListaSimplementeEnlazada(Coleccion<T> coleccion) {
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
    public boolean contains(T objeto) {
        Nodo<T> current = head;
        while (current != null) {
            if (current.getValue().equals(objeto)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Nodo<T> current = head;

            @Override
            public boolean hasNext() {
                return current.getNext() != null;
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
    public T get(int i) {
        if (indexIsValid(i)) {
            Nodo<T> current = head;
            for (int j = 0; j < i; j++) {
                current = current.getNext();
            }
            return current.getValue();
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
    }

    @Override
    public T getFirst() {
        return head != null ? head.getValue() : null;
    }

    @Override
    public T getLast() {
        return tail != null ? tail.getValue() : null;
    }

    @Override
    public void show() {
        Nodo<T> current = head;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

    @Override
    public boolean indexIsValid(int index) {
        return index >= 0 && index < length;
    }

    @Override
    public boolean add(T objeto) {
        Nodo<T> nodo = new Nodo<>(objeto);
        if (head == null) {
            head = nodo;
            tail = nodo;
        } else {
            tail.setNext(nodo);
            tail = nodo;
        }
        length++;
        return true;
    }

    @Override
    public boolean add(T value, int index) {
        return addBeforeElement(value, index);
    }

    @Override
    public boolean addFirst(T value) {
        Nodo<T> nodo = new Nodo<>(value);
        if (head == null) {
            head = nodo;
            tail = nodo;
        } else {
            nodo.setNext(head);
            head = nodo;
        }
        length++;
        return true;
    }

    @Override
    public boolean addLast(T value) {
        return add(value);
    }


    @Override
    public boolean addBeforeElement(T value, int position) {
        if(!indexIsValid(position+1)){
            return false;
        }
        if (position == 0) {
            return addFirst(value);
        }

        Nodo<T> nodo = new Nodo<>(value);

        Nodo<T> current = head;

        for (int i = 0; i < position - 1; i++) {
            current = current.getNext();
        }

        nodo.setNext(current.getNext());
        current.setNext(nodo);
        length++;
        return true;
    }

    @Override
    public boolean addAfterElement(T value, int position) {
        if(!indexIsValid(position)){
            return false;
        }
        if (position == length-1) {
            return addLast(value);
        }
        Nodo<T> nodo = new Nodo<>(value);
        Nodo<T> current = head;

        for (int i = 0; i < position; i++) {
            current = current.getNext();
        }

        nodo.setNext(current.getNext());
        current.setNext(nodo);
        length++;
        return true;
    }

    /**
     * Arreglar este metodo luego
     * @param objeto
     * @return
     */
    @Override
    public boolean remove(T objeto) {
        if(head == null){
            return false;
        }
        boolean borrado = false;
        Nodo<T> current = head;
        if(current.getValue().equals(objeto)){
            head = head.getNext();
            borrado = true;
            length--;
        }

        while (current.getNext() != null) {
            if(current.getNext().getValue().equals(objeto)){
                current.setNext(current.getNext().getNext());
                borrado = true;
                length--;
            }
            current = current.getNext();
        }

        return borrado;
    }

    @Override
    public boolean remove(int index) {
        return false;
    }

    @Override
    public boolean removeFirst() {
        return false;
    }

    @Override
    public boolean removeLast() {
        return false;
    }

    @Override
    public boolean update(T objeto) {
        return false;
    }

    @Override
    public boolean clear() {
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
    public boolean addAll(Coleccion<T> coleccion, int index) {
        return false;
    }

    @Override
    public boolean removeAll(Coleccion<T> coleccion) {
        return false;
    }

}
