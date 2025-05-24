package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Calificacion;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionContenido;
import co.edu.uniquindio.red_social.clases.social.Chat;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.social.SolicitudAmistad;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.BNodo;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;

/**
 * Clase que representa a un estudiante en la red social.
 * Hereda de la clase Usuario y implementa las interfaces AdministracionContenido.
 */

public class Estudiante extends Usuario implements AdministracionContenido {
    private ListaSimplementeEnlazada<Estudiante> contactos;
    private ListaSimplementeEnlazada<String> preferencias;
    private ListaSimplementeEnlazada<SolicitudAmistad> solicitudesRecibidas;
    private ArbolBinario<Contenido> contenidos;
    private ListaSimplementeEnlazada<Grupo> grupos;
    private ListaSimplementeEnlazada<Chat> chats;
    private String id;


    public Estudiante(String nombre,String apellido, String correo, String contrasena, File fotoPerfil) {
        super(nombre,apellido, correo, contrasena, fotoPerfil);
        this.contactos = new ListaSimplementeEnlazada<>();
        this.preferencias = new ListaSimplementeEnlazada<>();
        this.solicitudesRecibidas = new ListaSimplementeEnlazada<>();
        this.contenidos = new ArbolBinario<>();
        this.grupos = new ListaSimplementeEnlazada<>();
        this.chats = new ListaSimplementeEnlazada<>();
    }

    public Estudiante(String id, String nombre,String apellido, String correo, String contrasena, File fotoPerfil) {
        super(nombre,apellido, correo, contrasena, fotoPerfil);
        this.id = (id);
        this.contactos = new ListaSimplementeEnlazada<>();
        this.preferencias = new ListaSimplementeEnlazada<>();
        this.solicitudesRecibidas = new ListaSimplementeEnlazada<>();
        this.contenidos = new ArbolBinario<>();
        this.grupos = new ListaSimplementeEnlazada<>();
        this.chats = new ListaSimplementeEnlazada<>();
    }


    /**
     * Añade un contacto a la lista de contactos del estudiante.
     * @param contacto el contacto a añadir
     * @return true si se añadió correctamente, false si ya existe
     */
    public boolean anadirContacto(Estudiante contacto) {
        if (!contactos.contains(contacto)) {
            return contactos.add(contacto);
        }
        return false;
    }

    /**
     * Elimina un contacto de la lista de contactos del estudiante.
     * @param contacto el contacto a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarContacto(Estudiante contacto) {
        if (contactos.contains(contacto)) {
            eliminarChat(contacto);
            contacto.eliminarChat(this);
            contacto.getContactos().remove(this);
            return contactos.remove(contacto);
        }
        return false;
    }

    /**
     * Añade una preferencia a la lista de preferencias del estudiante.
     * @param preferencia la preferencia a añadir
     * @return true si se añadió correctamente, false si ya existe
     */
    public boolean anadirPreferencia(String preferencia) {
        if (!preferencias.contains(preferencia)) {
            return  preferencias.add(preferencia);
        }
        return false;
    }

    /**
     * Elimina una preferencia de la lista de preferencias del estudiante.
     * @param preferencia la preferencia a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarPreferencia(String preferencia) {
        if (preferencias.contains(preferencia)) {
            preferencias.remove(preferencia);
            return true;
        }
        return false;
    }

    /**
     * Envía una solicitud de amistad a otro estudiante.
     * @param estudianteSolicitado el estudiante al que se le envía la solicitud
     * @return true si se envió correctamente, false si ya es contacto o no se pudo enviar
     */
    public boolean enviarSolicitud(Estudiante estudianteSolicitado) {
        boolean bool= agregarSolicitud(estudianteSolicitado);
        if(bool){
            UtilSQL.crearRelacionAmistad(id, estudianteSolicitado.getId(), "pendiente");
        }
        return bool;
    }

    /**
     * Envía una solicitud de amistad a otro estudiante, usado para la creación en la base de datos.
     * @param estudianteSolicitado el estudiante al que se le envía la solicitud
     * @return true si se envió correctamente, false si ya es contacto o no se pudo enviar
     */
    public boolean agregarSolicitud(Estudiante estudianteSolicitado) {
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

    /**
     * Añade una solicitud de amistad a la lista de solicitudes recibidas del estudiante.
     * @param solicitud la solicitud a añadir
     * @return true si se añadió correctamente, false si ya existe
     */
    public boolean anadirSolicitud(SolicitudAmistad solicitud) {
        if (!solicitudesRecibidas.contains(solicitud)) {
            return solicitudesRecibidas.add(solicitud);
        }
        return false;
    }

    /**
     * Acepta una solicitud de amistad recibida.
     * @param solicitud la solicitud a aceptar
     * @return true si se aceptó correctamente, false si no existe
     */
    public boolean aceptarSolicitud(SolicitudAmistad solicitud) {
        if (solicitudesRecibidas.contains(solicitud)) {
            solicitud.aceptarSolicitud();
            return true;
        }
        return false;
    }

    /**
     * Rechaza una solicitud de amistad recibida.
     * @param solicitud la solicitud a rechazar
     * @return true si se rechazó correctamente, false si no existe
     */
    public void eliminarSolicitud(SolicitudAmistad solicitud) {
        int index = solicitudesRecibidas.indexOf(solicitud);
        if (index != -1) {
            solicitudesRecibidas.remove(index);
        } else {
            System.out.println("Solicitud no encontrada en la lista de solicitudes del estudiante.");
        }
    }


    /**
     * Añade un grupo a la lista de grupos del estudiante.
     * @param grupo el grupo a añadir
     * @return true si se añadió correctamente, false si ya existe
     */
    public boolean anadirGrupo(Grupo grupo) {
        if (!grupos.contains(grupo)) {
            return grupos.add(grupo);
        }
        return false;
    }

    /**
     * Elimina un grupo de la lista de grupos del estudiante.
     * @param grupo el grupo a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarGrupo(Grupo grupo) {
        if (grupos.contains(grupo)) {
            return grupos.remove(grupo);
        }
        return false;
    }

    /**
     * Añade un chat a la lista de chats del estudiante.
     * @param chat el chat a añadir
     * @return true si se añadió correctamente, false si ya existe
     */
    public boolean anadirChat(Chat chat) {
        if (!chats.contains(chat)) {
            return chats.add(chat);
        }
        return false;
    }

    /**
     * Elimina un chat de la lista de chats del estudiante.
     * @param chat el chat a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarChat(Chat chat) {
        if (chats.contains(chat)) {
            return chats.remove(chat);
        }
        return false;
    }

    /**
     * Elimina un chat de la lista de chats del estudiante.
     * @param estudiante el estudiante al que se le eliminará el chat
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarChat(Estudiante estudiante) {
        for (Chat chat : chats) {
            if (chat.getEstudiante().equals(estudiante) || chat.getEstudiante2().equals(estudiante)) {
                return chats.remove(chat);
            }
        }
        return false;
    }


    /**
     * Publica un contenido en la red social.
     * @param contenido el contenido a publicar
     * @param grupo el grupo en el que se publicará (null si es público)
     * @return true si se publicó correctamente, false si no se pudo publicar
     */
    @Override
    public boolean publicarContenido(Contenido contenido, Grupo grupo) {
        if(grupo ==null){
            contenidos.add(contenido);
            RedSocial.getInstance().agregarPublicacion(contenido);
            return true;
        }
        if(grupos.contains(grupo)){
            contenidos.add(contenido);
            grupo.agregarPublicacion(contenido);
            return true;
        }
        return false;
    }
    public boolean publicarContenido(Contenido contenido, Grupo grupo, String id) {
        if(grupo ==null){
            contenidos.add(contenido);
            return true;
        }
        if(grupos.contains(grupo)){
            contenidos.add(contenido);
            grupo.agregarPublicacion(contenido);
            return true;
        }
        return false;
    }


    /**
     * Elimina una publicación de la red social.
     * @param contenido el contenido a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    @Override
    public boolean eliminarPublicacion(Contenido contenido) {
        if(contenidos.contains(contenido)) {
            contenidos.remove(contenido);
            for (Grupo grupo : grupos) {
                grupo.eliminarPublicacion(contenido);
            }
            return true;
        }
        return false;
    }

    /**
     * Elimina todas las publicaciones de un grupo específico.
     * @param grupo el grupo del cual se eliminarán las publicaciones
     */
    public void eliminarPublicacionGrupo(Grupo grupo) {
        eliminarPublicacionesDeGrupo(contenidos.getRaiz(), grupo);
    }

    /**
     * Método recursivo para eliminar publicaciones de un grupo específico.
     * @param nodo el nodo actual del árbol
     * @param grupo el grupo del cual se eliminarán las publicaciones
     */
    private void eliminarPublicacionesDeGrupo(BNodo<Contenido> nodo, Grupo grupo) {
        if (nodo == null) {
            return;
        }
        eliminarPublicacionesDeGrupo(nodo.getIzquierdo(), grupo);
        eliminarPublicacionesDeGrupo(nodo.getDerecho(), grupo);

        if (nodo.getValor().getGrupo().equals(grupo)) {
            contenidos.remove(nodo.getValor());
            RedSocial.getInstance().eliminarPublicacion(nodo.getValor());
        }
    }

    /**
     * Modifica una publicación existente.
     * @param contenidoAntiguo el contenido antiguo a modificar
     * @param contenidoNuevo el nuevo contenido
     * @return true si se modificó correctamente, false si no existe
     */
    @Override
    public boolean modificarPublicacion(Contenido contenidoAntiguo, Contenido contenidoNuevo) {
        if(contenidos.contains(contenidoAntiguo)) {
            contenidoAntiguo.setTitulo(contenidoNuevo.getTitulo());
            contenidoAntiguo.setContenido(contenidoNuevo.getContenido());
            contenidoAntiguo.setDescripcion(contenidoNuevo.getDescripcion());
            return true;
        }
        return false;
    }

    /**
     * Valora un contenido.
     * @param contenido el contenido a valorar
     * @param valoracion la valoración del contenido
     */
    @Override
    public void valorarContenido(Contenido contenido, int valoracion) {
        contenido.calificar(new Calificacion(valoracion,this, contenido));
    }


    // Getters y Setters


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


    public ArbolBinario<Contenido> getContenidos() {
        return contenidos;
    }

    public void setPublicaciones(ArbolBinario<Contenido> contenidos) {
        this.contenidos = contenidos;
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

    public void setContenidos(ArbolBinario<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRutaImagenPerfil() {
        return imagenPerfil.getPath();
    }


    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                "contrasena='" + getContrasena() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", contrasena='" + getContrasena() + '\'' +
                "id" + id + '\'' +
                '}';
    }




}