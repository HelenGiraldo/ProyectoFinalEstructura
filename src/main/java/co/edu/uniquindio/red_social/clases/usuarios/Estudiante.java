package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.clases.contenidos.Calificacion;
import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionContenido;
import co.edu.uniquindio.red_social.clases.social.Chat;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.social.SolicitudAmistad;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.BNodo;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;

public class Estudiante extends Usuario implements AdministracionContenido {
    private ListaSimplementeEnlazada<Estudiante> contactos;
    private ListaSimplementeEnlazada<String> preferencias;
    private ListaSimplementeEnlazada<SolicitudAmistad> solicitudesRecibidas;
    private ArbolBinario<Publicacion> publicaciones;
    private ListaSimplementeEnlazada<Grupo> grupos;
    private ListaSimplementeEnlazada<Chat> chats;

    public Estudiante(String nombre,String apellido, String correo, String contrasena, File fotoPerfil) {
        super(nombre,apellido, correo, contrasena, fotoPerfil);
        this.contactos = new ListaSimplementeEnlazada<>();
        this.preferencias = new ListaSimplementeEnlazada<>();
        this.solicitudesRecibidas = new ListaSimplementeEnlazada<>();
        this.publicaciones = new ArbolBinario<>();
        this.grupos = new ListaSimplementeEnlazada<>();
        this.chats = new ListaSimplementeEnlazada<>();
    }

    public boolean anadirContacto(Estudiante contacto) {
        if (!contactos.contains(contacto)) {
            return contactos.add(contacto);
        }
        return false;
    }

    public boolean eliminarContacto(Estudiante contacto) {
        if (contactos.contains(contacto)) {
            eliminarChat(contacto);
            contacto.eliminarChat(this);
            contacto.getContactos().remove(this);
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

    public boolean enviarSolicitud(Estudiante estudianteSolicitado) {
        if(!contactos.contains(estudianteSolicitado)) {
            for(SolicitudAmistad solicitud : solicitudesRecibidas) {
                if(solicitud.getEstudianteSolicitado().equals(estudianteSolicitado)) {
                    return aceptarSolicitud(solicitud);
                }
            }
            SolicitudAmistad solicitud = new SolicitudAmistad(this, estudianteSolicitado);
            return estudianteSolicitado.anadirSolicitud(solicitud);
        }
        return false;
    }

    public boolean anadirSolicitud(SolicitudAmistad solicitud) {
        if (!solicitudesRecibidas.contains(solicitud)) {
            return solicitudesRecibidas.add(solicitud);
        }
        return false;
    }

    public boolean aceptarSolicitud(SolicitudAmistad solicitud) {
        if (solicitudesRecibidas.contains(solicitud)) {
            solicitud.aceptarSolicitud();
            return true;
        }
        return false;
    }

    public void eliminarSolicitud(SolicitudAmistad solicitud) {
        if (solicitudesRecibidas.contains(solicitud)) {
            solicitudesRecibidas.remove(solicitud);
        }
    }

    public boolean anadirGrupo(Grupo grupo) {
        if (!grupos.contains(grupo)) {
            return grupos.add(grupo);
        }
        return false;
    }

    public boolean eliminarGrupo(Grupo grupo) {
        if (grupos.contains(grupo)) {
            return grupos.remove(grupo);
        }
        return false;
    }

    public boolean anadirChat(Chat chat) {
        if (!chats.contains(chat)) {
            return chats.add(chat);
        }
        return false;
    }

    public boolean eliminarChat(Chat chat) {
        if (chats.contains(chat)) {
            return chats.remove(chat);
        }
        return false;
    }

    public boolean eliminarChat(Estudiante estudiante) {
        for (Chat chat : chats) {
            if (chat.getMiembros().contains(estudiante)) {
                return chats.remove(chat);
            }
        }
        return false;
    }


    @Override
    public boolean publicarContenido(Publicacion publicacion, Grupo grupo) {
        if(grupos.contains(grupo)){
            publicaciones.add(publicacion);
            grupo.agregarPublicacion(publicacion);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarPublicacion(Publicacion publicacion) {
        if(publicaciones.contains(publicacion)) {
            publicaciones.remove(publicacion);
            for (Grupo grupo : grupos) {
                grupo.eliminarPublicacion(publicacion);
            }
            return true;
        }
        return false;
    }

    public void eliminarPublicacionGrupo(Grupo grupo) {
        eliminarPublicacionesDeGrupo(publicaciones.getRaiz(), grupo);
    }

    private void eliminarPublicacionesDeGrupo(BNodo<Publicacion> nodo, Grupo grupo) {
        if (nodo == null) {
            return;
        }

        // Primero procesamos los hijos (postorden para evitar problemas)
        eliminarPublicacionesDeGrupo(nodo.getIzquierdo(), grupo);
        eliminarPublicacionesDeGrupo(nodo.getDerecho(), grupo);

        // Luego verificamos el nodo actual
        if (nodo.getValor().getGrupo().equals(grupo)) {
            // Eliminamos el valor del nodo actual del árbol
            publicaciones.remove(nodo.getValor());
        }
    }
    @Override
    public boolean modificarPublicacion(Publicacion publicacionAntigua, Publicacion publicacionNueva) {
        if(publicaciones.contains(publicacionAntigua)) {
            publicacionAntigua.setTitulo(publicacionNueva.getTitulo());
            publicacionAntigua.setContenido(publicacionNueva.getContenido());
            publicacionAntigua.setDescripcion(publicacionNueva.getDescripcion());
            return true;
        }
        return false;
    }

    @Override
    public void valorarContenido(Publicacion publicacion, int valoracion) {
        publicacion.calificar(new Calificacion(valoracion,this));
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


    public ArbolBinario<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(ArbolBinario<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public ListaSimplementeEnlazada<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(ListaSimplementeEnlazada<Grupo> grupos) {
        this.grupos = grupos;
    }

    public ListaSimplementeEnlazada<Chat> getChats() {
        return chats;
    }

    public void setChats(ListaSimplementeEnlazada<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                "contrasena='" + getContrasena() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", contrasena='" + getContrasena() + '\'' +
                '}';
    }




}