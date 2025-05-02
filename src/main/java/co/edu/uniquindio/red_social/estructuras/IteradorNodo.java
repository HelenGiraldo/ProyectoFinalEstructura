package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

public class IteradorNodo<T> implements Iterator<T> {

    Nodo<T> current;

    public IteradorNodo(Nodo<T> head) {
        this.current = head;
    }

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
}
