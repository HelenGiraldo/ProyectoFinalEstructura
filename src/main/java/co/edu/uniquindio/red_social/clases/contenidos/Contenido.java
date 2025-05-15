package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.clases.social.Grupo;
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
    private double calificacionPromedio;
    private File archivoAdjunto;
    private Grupo grupo;
    private ListaSimplementeEnlazada<Calificacion> calificaciones;

    public Contenido(String titulo, String tema, String tipo, String descripcion, double calificacionPromedio) {
        this.titulo = titulo;
        this.tema = tema;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.calificacionPromedio = 0.0;
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

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public ListaSimplementeEnlazada<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ListaSimplementeEnlazada<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
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

    public boolean calificar(Calificacion calificacion){
        if (!calificaciones.contains(calificacion)) {
            calificaciones.add(calificacion);
            calificacionPromedio = (calificacionPromedio * (calificaciones.size()-1) + calificacion.getValoracion()) / calificaciones.size();
        }
        return false;
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

