package co.edu.uniquindio.red_social.estructuras;

public interface Cola<T> extends Coleccion<T>{
    void push(T value);
    void removeFirst();
    T pop();
    T peek();
    void clear();

}
