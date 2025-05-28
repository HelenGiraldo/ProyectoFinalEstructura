package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class ContadorPreferencias {
    private MNodo cabeza;

    public ContadorPreferencias() {
        cabeza = null;
    }

    public void put(String preferencia) {
        // Caso lista vacía
        if (cabeza == null) {
            cabeza = new MNodo(preferencia, 1);
            return;
        }

        // Buscar si ya existe la preferencia
        MNodo actual = cabeza;
        MNodo anterior = null;

        while (actual != null) {
            if (actual.getPreferencia().equals(preferencia)) {
                // Incrementar conteo si ya existe
                actual.setConteo(actual.getConteo() + 1);
                return;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        // Si llegamos aquí, la preferencia no existe -> agregar nuevo nodo
        anterior.setSiguiente(new MNodo(preferencia, 1));
    }

    public ListaSimplementeEnlazada<String> getPreferencias() {
        ListaSimplementeEnlazada<String> resultado = new ListaSimplementeEnlazada<>();
        MNodo actual = cabeza;

        while (actual != null) {
            resultado.add(actual.getPreferencia() + " (" + actual.getConteo() + ")");
            actual = actual.getSiguiente();
        }

        return resultado;
    }

    public ListaSimplementeEnlazada<String> getPreferenciasComunes() {
        ListaSimplementeEnlazada<String> resultado = new ListaSimplementeEnlazada<>();
        MNodo actual = cabeza;

        while (actual != null) {
            if (actual.getConteo() >= 2) {
                resultado.add(actual.getPreferencia());
            }
            actual = actual.getSiguiente();
        }

        return resultado;
    }
}