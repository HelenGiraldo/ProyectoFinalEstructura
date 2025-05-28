package co.edu.uniquindio.red_social.util;

public class MNodo {
    String preferencia;
    int conteo;
    MNodo siguiente;

    public MNodo(String preferencia, int conteo) {
        this.preferencia = preferencia;
        this.conteo = conteo;
        this.siguiente = null;
    }

    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    public int getConteo() {
        return conteo;
    }

    public void setConteo(int conteo) {
        this.conteo = conteo;
    }

    public MNodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(MNodo siguiente) {
        this.siguiente = siguiente;
    }
}
