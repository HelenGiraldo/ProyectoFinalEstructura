package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

public interface Cola<T> extends Coleccion<T>{
    boolean push(T value, int priority);
    void removeFirst();
    T pop();
    T peek();
    int size();
    int length();
    boolean isEmpty();
    boolean contains(T elemento);
    Iterator<T> iterator();
    boolean add(T elemento, int priority);
    boolean remove(T elemento);
    boolean update(T elemento);
    boolean containsAll(Coleccion<T> coleccion);
    boolean removeAll(Coleccion<T> coleccion);

}
