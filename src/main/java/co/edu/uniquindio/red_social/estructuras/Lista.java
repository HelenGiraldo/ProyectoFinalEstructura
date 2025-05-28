package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;

    /**
     *  Coleccion made by: Helen Giraldo y Miguel Vargas
     *  <p>
     *      Clase madre para todas las listas del proyecto,
     *      contiene los siguientes metodos:
     *      {@code size(), length(), isEmpty(), contains(T), iterator(), add(T), remove(T), update(T),
     *       containsAll(Coleccion<T>), addAll(Coleccion<T>), removeAll(Coleccion<T>),
     *       get(int), getFirst(), getLast(), add(T,int), addFirst(T), addBeforeElement(T,int),
     *       addAfterElement(T,int), addAll(SinglyLinkedList<T>), addAll(SinglyLinkedList<T>,int),
     *       remove(int), removeFirst(), removeLast(), clear(), show(), indexIsValid(int)}
     *  </p>
     *  <p>
     *  Solo JhancaGOD
     **/
    public interface Lista<T> extends Coleccion<T> {

        // -------------------------- Consultas -----------------------------

        int size();

        default int length() {
            return size();
        }

        boolean isEmpty();

        boolean contains(T objeto);

        Iterator<T> iterator();

        T get(int i);

        T getFirst();

        T getLast();

        void show();

        boolean indexIsValid(int index);

        // -------------------------- Modificaciones -----------------------------
        boolean add(T objeto);

        boolean add(T value, int index);

        boolean addFirst(T value);

        boolean addLast(T value);

        boolean addBeforeElement(T value, int position);

        boolean addAfterElement(T value, int position);


        boolean remove(T objeto);

        boolean remove(int index);

        boolean removeFirst();

        boolean removeLast();

        boolean update(T objetoInicial, T objetoFinal);

        boolean clear();

        // -------------------------- Masivas -----------------------------
        boolean containsAll(Coleccion<T> coleccion);


        boolean addAll(Coleccion<T> coleccion);

        boolean addAll(Coleccion<T> coleccion, int index);

        boolean removeAll(Coleccion<T> coleccion);

    }


