package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;


public class Contenido implements Comparable<Contenido> {
    private String tipoContenido;
    private String id;
    private String titulo;
    private String tema;
    private String descripcion;
    private Estudiante autor;
    private double calificacionPromedio;
    private File contenido;
    private Grupo grupo;
    private ListaSimplementeEnlazada<Calificacion> calificaciones;

    public Contenido(String tipoContenido, String titulo, String tema, String descripcion, Estudiante autor, File archivoAdjunto, Grupo grupo) {
        this.tipoContenido = tipoContenido;
        this.titulo = titulo;
        this.tema = tema;
        this.descripcion = descripcion;
        this.autor = autor;
        this.contenido = archivoAdjunto;
        this.grupo = grupo;
    }

    public Estudiante getAutor() {
        return autor;
    }

    public void setAutor(Estudiante autor) {
        this.autor = autor;
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
        if (this.id != null && !this.id.equals(id)) {
            System.err.println("¡ADVERTENCIA! Intentando cambiar ID de " + this.id + " a " + id);
            throw new IllegalStateException("El ID no puede ser modificado");
        }
        this.id = id;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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



    public boolean calificar(Calificacion calificacion){
        for(Calificacion c : calificaciones){
            if(c.getUsuario().getId().equals(calificacion.getUsuario().getId())){
                eliminarCalificacion(calificacion);
            }
        }
        if (!calificaciones.contains(calificacion)) {
            calificaciones.add(calificacion);
            calificacionPromedio = (calificacionPromedio * (calificaciones.size()-1) + calificacion.getValoracion()) / calificaciones.size();
            int id = UtilSQL.agregarCalificacion(calificacion);
            calificacion.setId(String.valueOf(id));
            return true;
        }
        return false;
    }


    public boolean agregarCalificacion(Calificacion calificacion) {
        for(Calificacion c : calificaciones){
            if(c.getUsuario().getId().equals(calificacion.getUsuario().getId())){
                eliminarCalificacion(calificacion);
            }
        }
        if (!calificaciones.contains(calificacion)) {
            calificaciones.add(calificacion);
            calificacionPromedio = (calificacionPromedio * (calificaciones.size()-1) + calificacion.getValoracion()) / calificaciones.size();
            return true;
        }
        return false;
    }

    public boolean eliminarCalificacion(Calificacion calificacion) {
        if (calificaciones.contains(calificacion)) {
            calificaciones.remove(calificacion);
            UtilSQL.eliminarCalificacion(calificacion.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contenido contenido = (Contenido) o;
        return id != null && id.equals(contenido.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
