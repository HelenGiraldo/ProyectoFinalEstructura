package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class Grupo {
    private String nombre;
    private String descripcion;
    private boolean publico;
    private String id;
    private ListaSimplementeEnlazada<Publicacion> publicaciones;
    private ListaSimplementeEnlazada<Estudiante> miembros;

    public Grupo(String descripcion, String nombre, boolean publico) {
        this.publicaciones = new ListaSimplementeEnlazada<>();
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.publico = publico;
        this.miembros = new ListaSimplementeEnlazada<>();
    }

    public boolean agregarMiembro( Estudiante miembro) {
        if (!miembros.contains(miembro)) {
            return miembros.add(miembro);
        }
        return false;
    }
    public boolean eliminarMiembro( Estudiante miembro) {
        if (miembros.contains(miembro)) {
            return miembros.remove(miembro);
        }
        return false;
    }

    public boolean agregarPublicacion(Publicacion publicacion) {
        if (!publicaciones.contains(publicacion)) {
            return publicaciones.add(publicacion);
        }
        return false;
    }

    public boolean eliminarPublicacion(Publicacion publicacion) {
        if (publicaciones.contains(publicacion)) {
            return publicaciones.remove(publicacion);
        }
        return false;
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

    public ListaSimplementeEnlazada<Estudiante> getMiembros() {
        return miembros;
    }

    public void setMiembros(ListaSimplementeEnlazada<Estudiante> miembros) {
        this.miembros = miembros;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

