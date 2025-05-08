package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;
import java.time.LocalDateTime;

public class Publicacion {
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private String id;
    private ListaSimplementeEnlazada<Contenido> contenidos;
    private ListaSimplementeEnlazada<Calificacion> calificaciones;

    public Publicacion(String titulo, String descripcion, LocalDateTime fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.contenidos = new ListaSimplementeEnlazada<>();
        this.calificaciones = new ListaSimplementeEnlazada<>();
    }

    public boolean agregarContenido(String tipoContenido, File contenido){
        Contenido nuevoContenido = new Contenido(tipoContenido, contenido);
        contenidos.add(nuevoContenido);
        return true;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public ListaSimplementeEnlazada<Contenido> getContenido() {
        return contenidos;
    }

    public void setContenido(ListaSimplementeEnlazada<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public ListaSimplementeEnlazada<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(ListaSimplementeEnlazada<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public ListaSimplementeEnlazada<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ListaSimplementeEnlazada<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
