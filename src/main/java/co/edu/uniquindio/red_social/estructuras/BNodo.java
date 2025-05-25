package co.edu.uniquindio.red_social.estructuras;

public class BNodo<T> {
    private T valor;
    private BNodo izquierdo;
    private BNodo derecho;

    public BNodo(T valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public BNodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(BNodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public BNodo getDerecho() {
        return derecho;
    }

    public void setDerecho(BNodo derecho) {
        this.derecho = derecho;
    }
}
