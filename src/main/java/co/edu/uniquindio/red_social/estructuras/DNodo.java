package co.edu.uniquindio.red_social.estructuras;

public class DNodo <T> {
    private T value;
    private DNodo<T> next;
    private DNodo<T> previous;

    public DNodo(T value) {
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

    public DNodo<T> getNext() {
        return next;
    }

    public void setNext(DNodo<T> next) {
        this.next = next;
    }

    public DNodo<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DNodo<T> previous) {
        this.previous = previous;
    }

}
