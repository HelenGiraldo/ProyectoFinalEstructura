package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;


public class Contenido implements Comparable<Contenido> {
    private String tipoContenido;
    private File contenido;
    private String id;
    private String titulo;
    private String tema;
    private String tipo;
    private String descripcion;
    private String autor;
    private double valoracion;
    private File archivoAdjunto;

    public Contenido(String titulo, String tema, String tipo, String descripcion, double valoracion) {
        this.titulo = titulo;
        this.tema = tema;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.valoracion = 0.0;
    }

    public Contenido(String tipoContenido, File contenido) {
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public String getTema(){
        return tema;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public File getContenido() {
        return contenido;
    }

    public void setContenido(File contenido) {
        this.contenido = contenido;
    }

    public File getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(File archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    @Override
    public int compareTo(Contenido otro) {
        return this.tema.compareToIgnoreCase(otro.tema);
    }

    @Override
    public String toString() {
        return titulo + " - " + autor + " (" + tema + ")";
    }
}

