package co.edu.uniquindio.red_social.estructuras;

public class MiCola<T>{

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    public MiCola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public void encolar(T item) {
        push(item);
    }

    public void push(T item) {
        Nodo<T> nuevoNodo = new Nodo<>(item);
        if (isEmpty()) {
            frente = nuevoNodo;
        } else {
            fin.setNext(nuevoNodo);
        }
        fin = nuevoNodo;
        tamanio++;
    }

    public T desencolar() {
        return pop();
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía");
        }
        T dato = frente.getValue();
        frente = frente.getNext();
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return dato;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía");
        }
        return frente.getValue();
    }

    public boolean estaVacia() {
        return isEmpty();
    }
    public boolean isEmpty() {
        return frente == null;
    }

    public int size() {
        return tamanio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cola: [");
        Nodo<T> actual = frente;
        while (actual != null) {
            sb.append(actual.getValue());
            if (actual.getNext() != null) {
                sb.append(", ");
            }
            actual = actual.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}