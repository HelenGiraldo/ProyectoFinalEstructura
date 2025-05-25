package co.edu.uniquindio.red_social.estructuras;

import java.util.function.Consumer;



public class ArbolBinario<T extends Comparable<T>> {
    BNodo<T> raiz;
    private int peso;

    public ArbolBinario() {
        this.raiz = null;
        this.peso = 0;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public void insertar(T valor) {
        raiz = agregarRecursivo(raiz, valor);
        peso++;
    }
    public void mostrarInorden(BNodo<T> nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.getIzquierdo());
            System.out.print(nodo.getValor()+" -> ");
            mostrarInorden(nodo.getDerecho());
        }
    }

    private BNodo<T> agregarRecursivo(BNodo<T> nodoActual, T valor) {
        if (nodoActual == null) {
            return new BNodo<>(valor);
        }
        if (valor.compareTo(nodoActual.getValor()) < 0) {
            nodoActual.setIzquierdo(agregarRecursivo(nodoActual.getIzquierdo(), valor));
        } else  {
            nodoActual.setDerecho(agregarRecursivo(nodoActual.getDerecho(), valor));
        }
        return nodoActual;
    }

    public void eliminar(T valor) {
        raiz = eliminarRecursivo(raiz, valor);
        peso--;
    }
    private BNodo<T> eliminarRecursivo(BNodo<T> nodoActual, T valor) {
        if (nodoActual == null) {
            return null;
        }
        if (valor.compareTo(nodoActual.getValor()) < 0) {
            nodoActual.setIzquierdo(eliminarRecursivo(nodoActual.getIzquierdo(), valor));
        } else if (valor.compareTo(nodoActual.getValor()) > 0) {
            nodoActual.setDerecho(eliminarRecursivo(nodoActual.getDerecho(), valor));
        } else {
            if (nodoActual.getIzquierdo() == null) {
                return nodoActual.getDerecho();
            } else if (nodoActual.getDerecho() == null) {
                return nodoActual.getIzquierdo();
            }
            T menorValor = (T) obtenerMenor(nodoActual.getDerecho());
            nodoActual.setValor(menorValor);
            nodoActual.setDerecho(eliminarRecursivo(nodoActual.getDerecho(), menorValor));
        }
        return nodoActual;
    }

    public ListaSimplementeEnlazada<T> recorrerInorden() {
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>();
        recorrerInorden(raiz, lista);
        return lista;
    }

    public void recorrerInorden(BNodo<T> nodo,ListaSimplementeEnlazada<T> lista ) {
        if (nodo != null) {
            recorrerInorden(nodo.getIzquierdo(),lista);
            lista.add(nodo.getValor());
            recorrerInorden(nodo.getDerecho(), lista);
        }
    }


    public ListaSimplementeEnlazada<T> recorrerPreorden() {
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>();
        recorrerPreorden(raiz, lista);
        return lista;
    }

    public void recorrerPreorden(BNodo<T> nodo,ListaSimplementeEnlazada<T> lista ) {
        if (nodo != null) {
            lista.add(nodo.getValor());
            recorrerPreorden(nodo.getIzquierdo(),lista);
            recorrerPreorden(nodo.getDerecho(), lista);
        }
    }

    public ListaSimplementeEnlazada<T> recorrerPostorden() {
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>();
        recorrerPostorden(raiz, lista);
        return lista;
    }

    public void recorrerPostorden(BNodo<T> nodo,ListaSimplementeEnlazada<T> lista ) {
        if (nodo != null) {
            recorrerPostorden(nodo.getIzquierdo(),lista);
            recorrerPostorden(nodo.getDerecho(), lista);
            lista.add(nodo.getValor());
        }
    }

    public boolean contiene(T valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(BNodo<T> nodo, T valor) {

        if (nodo == null) {
            return false;
        }
        if (nodo.getValor().equals(valor)) {
            return true;
        }
        if (valor.compareTo(nodo.getValor()) < 0) {
            return buscar(nodo.getIzquierdo(), valor);
        }
        if (valor.compareTo(nodo.getValor()) > 0) {
            return buscar(nodo.getDerecho(), valor);
        }
        return false;
    }

    public int getPeso() {
        return peso;
    }

    public int obtenerAltura() {
        return obtenerAltura(raiz);
    }

    private int obtenerAltura(BNodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        int alturaIzquierda = obtenerAltura(nodo.getIzquierdo());
        int alturaDerecha = obtenerAltura(nodo.getDerecho());
        return Math.max(alturaIzquierda, alturaDerecha)+1;
    }

    private  int obtenerNivel(T valor){
        return obtenerNivel(raiz, valor, 0);
    }

    private int obtenerNivel(BNodo<T> nodo, T valor, int nivel) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.getValor().equals(valor)) {
            return nivel;
        }
        int nivelIzquierdo = obtenerNivel(nodo.getIzquierdo(), valor, nivel + 1);
        if (nivelIzquierdo != -1) {
            return nivelIzquierdo;
        }
        return obtenerNivel(nodo.getDerecho(), valor, nivel + 1);
    }

    public int contarHojas(){
        return contarHojas(raiz);
    }

    private int contarHojas(BNodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    public T obtenerMenor(){
        return obtenerMenor(raiz);
    }

    private T obtenerMenor(BNodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getIzquierdo() == null) {
            return nodo.getValor();
        }
        return (T) obtenerMenor(nodo.getIzquierdo());
    }

    public void recorridoPorAmplitud() {
        int altura = obtenerAltura(raiz);
        for (int i = 1; i <= altura; i++) {
            imprimirNivel(raiz, i);
            System.out.println();
        }
    }



    private void imprimirNivel(BNodo<T> nodo, int nivel) {
        if (nodo == null) {
            return;
        }
        if (nivel == 1) {
            System.out.print(nodo.getValor() + " ");
        } else if (nivel > 1) {
            imprimirNivel(nodo.getIzquierdo(), nivel - 1);
            imprimirNivel(nodo.getDerecho(), nivel - 1);
        }
    }

    public BNodo<T> obtenerNodoMayor() {
        return obtenerNodoMayor(raiz);
    }

    private BNodo<T> obtenerNodoMayor(BNodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getDerecho() == null) {
            return nodo;
        }
        return obtenerNodoMayor(nodo.getDerecho());
    }

    public BNodo<T> obtenerNodoMenor() {
        return obtenerNodoMenor(raiz);
    }
    private BNodo<T> obtenerNodoMenor(BNodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getIzquierdo() == null) {
            return nodo;
        }
        return obtenerNodoMenor(nodo.getIzquierdo());
    }

    public void borrarArbol() {
        raiz = null;
        peso = 0;
    }



    public void mostrarInorden() {
        recorrerInorden().show();
    }

    public BNodo<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(BNodo<T> raiz) {
        this.raiz = raiz;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    //-------------------- Metodos solo para adaptar a la logica------------

    public boolean add(T valor) {
        insertar(valor);
        return true;
    }

    public boolean remove(T valor) {
        if (contiene(valor)) {
            eliminar(valor);
            return true;
        }
        return false;
    }

    public boolean contains(T valor) {
        return contiene(valor);
    }

    public void clear() {
        borrarArbol();
    }
/*
    public void mostrarInorden(BNodo<T> nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.getIzquierdo());
            System.out.print(nodo.getValor()+" -> ");
            mostrarInorden(nodo.getDerecho());
        }
    }
    public void recorrerPreorden(BNodo<T> nodo) {
        if (nodo != null) {
            System.out.println(nodo.getValor());
            recorrerPreorden(nodo.getIzquierdo());
            recorrerPreorden(nodo.getDerecho());
        }
    }

    public void recorrerPostorden(BNodo<T> nodo) {
        if (nodo != null) {
            recorrerPostorden(nodo.getIzquierdo());
            recorrerPostorden(nodo.getDerecho());
            System.out.println(nodo.getValor());
        }
    }

*/

}