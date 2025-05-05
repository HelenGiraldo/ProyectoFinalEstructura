package co.edu.uniquindio.red_social.clases.grupos;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class Grupo {
    private String nombre;
    private String descripcion;
    private ListaSimplementeEnlazada<Publicacion> publicaciones;
    private ListaSimplementeEnlazada<Usuario> miembros;
    private ListaSimplementeEnlazada<Usuario> moderadores;

    public Grupo(String descripcion, String nombre) {
        this.publicaciones = new ListaSimplementeEnlazada<>();
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ListaSimplementeEnlazada<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(ListaSimplementeEnlazada<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public ListaSimplementeEnlazada<Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(ListaSimplementeEnlazada<Usuario> miembros) {
        this.miembros = miembros;
    }

    public ListaSimplementeEnlazada<Usuario> getModeradores() {
        return moderadores;
    }

    public void setModeradores(ListaSimplementeEnlazada<Usuario> moderadores) {
        this.moderadores = moderadores;
    }
}
