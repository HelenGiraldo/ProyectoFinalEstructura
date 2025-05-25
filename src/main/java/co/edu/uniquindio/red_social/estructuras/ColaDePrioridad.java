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
        return new Iterator<>() {
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
        if (head == null) {
            return false;
        }

        boolean borrado = false;

        // Eliminar todos los nodos iniciales que coincidan
        while (head != null && head.getValue().equals(objeto)) {
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            } else {
                tail = null;
            }
            borrado = true;
            length--;
        }

        PNodo<T> current = head;
        while (current != null && current.getNext() != null) {
            if (current.getNext().getValue().equals(objeto)) {
                current.setNext(current.getNext().getNext());
                if (current.getNext() != null) {
                    current.getNext().setPrevious(current);
                } else {
                    tail = current;
                }
                borrado = true;
                length--;
            } else {
                current = current.getNext();
            }
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
            length++;
            return true;
        }

        PNodo<T> current = head;
        PNodo<T> previous = null;

        // Avanzar hasta encontrar la posición correcta según la prioridad (mayor prioridad primero)
        while (current != null && current.getPriority() >= priority) {
            previous = current;
            current = current.getNext();
        }

        if (previous == null) { // Insertar al inicio
            node.setNext(head);
            head.setPrevious(node);
            head = node;
        } else if (current == null) { // Insertar al final
            tail.setNext(node);
            node.setPrevious(tail);
            tail = node;
        } else { // Insertar en medio
            previous.setNext(node);
            node.setPrevious(previous);
            node.setNext(current);
            current.setPrevious(node);
        }

        length++;
        return true;
    }

    @Override
    public void removeFirst() {
        if (isEmpty()) {
            return;
        }
        head = head.getNext();
        if (head == null) {
            tail = null;
        } else {
            head.setPrevious(null);
        }
        length--;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        PNodo<T> node = head;
        head = head.getNext();
        if (head == null) {
            tail = null;
        } else {
            head.setPrevious(null);
        }
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
        PNodo<T> node = head;
        while (node != null) {
            if (node.getValue().equals(value)) {
                return true;
            }
            node = node.getNext();
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

    public void show(){
        PNodo<T> current = head;
        while(current != null){
            System.out.print(current.getValue()+" ");
            current = current.getNext();
        }
        System.out.println();
    }
}
