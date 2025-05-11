package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionContenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.social.SolicitudAmistad;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;

public class Estudiante extends Usuario implements AdministracionContenido {
    private ListaSimplementeEnlazada<Estudiante> contactos;
    private ListaSimplementeEnlazada<String> preferencias;
    private ListaSimplementeEnlazada<SolicitudAmistad> solicitudesRecibidas;
    private ListaSimplementeEnlazada<Publicacion> publicaciones;
    private ListaSimplementeEnlazada<Grupo> grupos;

    public Estudiante(String nombre, String apellido, String email, String contrasena, File fotoPerfil) {
        super(nombre, apellido, email, contrasena, fotoPerfil);
        this.contactos = new ListaSimplementeEnlazada<>();
        this.preferencias = new ListaSimplementeEnlazada<>();
        this.solicitudesRecibidas = new ListaSimplementeEnlazada<>();
        this.publicaciones = new ListaSimplementeEnlazada<>();
    }

    public boolean anadirContacto(Estudiante contacto) {
        if (!contactos.contains(contacto)) {
            return contactos.add(contacto);
        }
        return false;
    }

    public boolean eliminarContacto(Estudiante contacto) {
        if (contactos.contains(contacto)) {
            return contactos.remove(contacto);
        }
        return false;
    }

    public boolean anadirPreferencia(String preferencia) {
        if (!preferencias.contains(preferencia)) {
           return  preferencias.add(preferencia);
        }
        return false;
    }

    public boolean eliminarPreferencia(String preferencia) {
        if (preferencias.contains(preferencia)) {
            preferencias.remove(preferencia);
            return true;
        }
        return false;
    }



    public ListaSimplementeEnlazada<Estudiante> getContactos() {
        return contactos;
    }

    public void setContactos(ListaSimplementeEnlazada<Estudiante> contactos) {
        this.contactos = contactos;
    }

    public ListaSimplementeEnlazada<String> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(ListaSimplementeEnlazada<String> preferencias) {
        this.preferencias = preferencias;
    }

    public ListaSimplementeEnlazada<SolicitudAmistad> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public void setSolicitudesRecibidas(ListaSimplementeEnlazada<SolicitudAmistad> solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", contrasena='" + getContrasena() + '\'' +
                '}';
    }


    public void eliminarSolicitud(SolicitudAmistad solicitud) {
        if (solicitudesRecibidas.contains(solicitud)) {
            solicitudesRecibidas.remove(solicitud);
        }
    }

    @Override
    public boolean publicarContenido(Publicacion publicacion) {
        return false;
    }

    @Override
    public boolean eliminarPublicacion(Publicacion publicacion) {
        return false;
    }

    @Override
    public boolean modificarPublicacion(Publicacion publicacionAntigua, Publicacion publicacionNueva) {
        return false;
    }

    @Override
    public void valorarContenido(Publicacion publicacion, int valoracion) {

    }


}
