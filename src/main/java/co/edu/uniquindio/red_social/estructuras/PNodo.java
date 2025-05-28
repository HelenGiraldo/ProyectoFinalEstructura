package co.edu.uniquindio.red_social.estructuras;

public class PNodo<T> {
    private T value;
    private PNodo<T> next;
    private PNodo<T> previous;
    private int priority;

    public PNodo(T value, int priority) {
        this.value = value;
        this.next = null;
        this.previous = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public PNodo<T> getNext() {
        return next;
    }

    public void setNext(PNodo<T> next) {
        this.next = next;
    }

    public PNodo<T> getPrevious() {
        return previous;
    }

    public void setPrevious(PNodo<T> previous) {
        this.previous = previous;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
