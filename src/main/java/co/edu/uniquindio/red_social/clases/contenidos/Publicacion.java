package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;
import java.time.LocalDateTime;

public class Publicacion implements Comparable<Publicacion> {
    private String titulo;
    private String tema;
    private String descripcion;
    private LocalDateTime fecha;
    private Usuario usuario;
    private Grupo grupo;
    private String id;
    private double calificacionPromedio;
    private ListaSimplementeEnlazada<Contenido> contenidos;
    private ListaSimplementeEnlazada<Calificacion> calificaciones;

    public Publicacion(String titulo,String tema, String descripcion, LocalDateTime fecha, Usuario usuario) {
        this.titulo = titulo;
        this.tema = tema;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuario = usuario;
        this.contenidos = new ListaSimplementeEnlazada<>();
        this.calificaciones = new ListaSimplementeEnlazada<>();
        this.calificacionPromedio = 0.0;
    }

    public Publicacion(String titulo, String descripcion, LocalDateTime fecha, Usuario usuario, Grupo grupo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuario = usuario;
        this.grupo = grupo;
        this.contenidos = new ListaSimplementeEnlazada<>();
        this.calificaciones = new ListaSimplementeEnlazada<>();
        this.calificacionPromedio = 0.0;
    }

    public boolean agregarContenido(String tipoContenido, File contenido){
        Contenido nuevoContenido = new Contenido(tipoContenido, contenido);
        contenidos.add(nuevoContenido);
        return true;
    }

    public boolean eliminarContenido(Contenido contenido){
        if (contenidos.contains(contenido)) {
            return contenidos.remove(contenido);
        }
        return false;
    }

    public boolean calificar(Calificacion calificacion){
        if (!calificaciones.contains(calificacion)) {
            calificaciones.add(calificacion);
            calificacionPromedio = (calificacionPromedio * (calificaciones.size()-1) + calificacion.getValoracion()) / calificaciones.size();
        }
        return false;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public int compareTo(Publicacion o) {
        return this.tema.compareTo(o.getTema());
    }
}
