package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;

public class Contenido implements Comparable<Contenido> {
    private String tipoContenido;
    private File contenido;
    private String id;

    public Contenido(String tipoContenido, File contenido) {
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public File getContenido() {
        return contenido;
    }

    public void setContenido(File contenido) {
        this.contenido = contenido;
    }

    @Override
    public int compareTo(Contenido o) {
        return 0;
    }
}
