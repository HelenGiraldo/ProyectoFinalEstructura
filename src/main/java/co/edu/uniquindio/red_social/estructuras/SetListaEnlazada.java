package co.edu.uniquindio.red_social.estructuras;

/*

Clase de prueba de Set con lista enlazada
Probablemente no se use en la producción pero es interesante

Jhanca, no mire esto
 */
public class SetListaEnlazada<T> extends ListaSimplementeEnlazada<T>{

    public SetListaEnlazada() {
        super();
    }

    public SetListaEnlazada(Coleccion<T> coleccion) {
        super();
        addAll(coleccion);
    }

    @Override
    public boolean add(T objeto) {
        if (!contains(objeto)) {
            Nodo<T> nuevo = new Nodo<>(objeto);
            if (isEmpty()) {
                head = nuevo;
                tail = nuevo;
            } else {
                tail.setNext(nuevo);
                tail = nuevo;
            }
            length++;
            return true;
        }
        return false;
    }



}
