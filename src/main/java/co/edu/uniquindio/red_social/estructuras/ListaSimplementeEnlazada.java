package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

public class ListaSimplementeEnlazada<T> implements Lista<T>, Iterable<T> {

    protected Nodo<T> head;
    protected Nodo<T> tail;
    protected int length;

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
        return new IteradorNodo<>(head);
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

    @Override
    public boolean remove(T objeto) {
        if(head == null){
            return false;
        }
        boolean borrado = false;
        Nodo<T> current = head;
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
    public boolean remove(int index) {
        if (indexIsValid(index)) {
            if (index == 0) {
                head = head.getNext();
            } else {
                Nodo<T> current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                current.setNext(current.getNext().getNext());
            }
            length--;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFirst() {
        if (head != null) {
            head = head.getNext();
            length--;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeLast() {
        if (head == null) {
            return false;
        }
        if (head.getNext() == null) {
            head = null;
            tail = null;
        } else {
            Nodo<T> current = head;
            while (current.getNext().getNext() != null) {
                current = current.getNext();
            }
            current.setNext(null);
            tail = current;
        }
        length--;
        return true;
    }

    @Override
    public boolean update(T objetoInicial, T objetoFinal) {
        Nodo<T> current = head;
        if(head == null){
            return false;
        }
        boolean borrado = false;
        while (current != null) {
            if (current.getValue().equals(objetoInicial)) {
                current.setValue(objetoFinal);
                borrado = true;
            }
            current = current.getNext();
        }
        return borrado;
    }


    @Override
    public boolean clear() {
        head = null;
        tail = null;
        length = 0;
        return true;
    }

    @Override
    public boolean containsAll(Coleccion<T> coleccion) {
        Iterator<T> iterator = coleccion.iterator();
        while (iterator.hasNext()) {
            if (!contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Coleccion<T> coleccion) {

        Iterator<T> iterator = coleccion.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return true;
    }

    @Override
    public boolean addAll(Coleccion<T> coleccion, int index) {

        if (indexIsValid(index)) {
            Iterator<T> iterator = coleccion.iterator();
            while (iterator.hasNext()) {
                addBeforeElement(iterator.next(), index);
                index++;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Coleccion<T> coleccion) {
        Iterator<T> iterator = coleccion.iterator();
        boolean flag = true;
        while (iterator.hasNext()) {
            if(!remove(iterator.next())){
                flag = false;
            }
        }
        return flag;
    }

    public int indexOf(T solicitud) {
        Nodo<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.getValue().equals(solicitud)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }


}
