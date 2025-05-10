package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class Chat {
    private String id;
    private ListaSimplementeEnlazada<Estudiante> miembros;
    private ListaSimplementeEnlazada<Mensaje> mensajes;

    public Chat(Estudiante estudiante, Estudiante estudiante2) {
        this.miembros = new ListaSimplementeEnlazada<>();
        this.mensajes = new ListaSimplementeEnlazada<>();
        this.miembros.add(estudiante);
        this.miembros.add(estudiante2);
        estudiante.anadirChat(this);
        estudiante2.anadirChat(this);
    }

    public Chat() {
        this.miembros = new ListaSimplementeEnlazada<>();
        this.mensajes = new ListaSimplementeEnlazada<>();
    }

    public boolean agregarMiembro(Estudiante miembro) {
        if (!miembros.contains(miembro)) {
            return miembros.add(miembro);
        }
        return false;
    }

    public boolean eliminarMiembro(Estudiante miembro) {
        if (miembros.contains(miembro)) {
            return miembros.remove(miembro);
        }
        return false;
    }

    public boolean eliminarChat() {
        while(miembros.isEmpty()) {
            miembros.getFirst().eliminarChat(this);
            miembros.removeFirst();
        }
        while(mensajes.isEmpty()) {
            mensajes.removeFirst();
        }
        return true;
    }

    public boolean enviarMensaje(Mensaje mensaje) {
        return mensajes.add(mensaje);
    }

    public boolean eliminarMensaje(Mensaje mensaje) {
        if (mensajes.contains(mensaje)) {
            return mensajes.remove(mensaje);
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ListaSimplementeEnlazada<Estudiante> getMiembros() {
        return miembros;
    }

    public void setMiembros(ListaSimplementeEnlazada<Estudiante> miembros) {
        this.miembros = miembros;
    }

    public ListaSimplementeEnlazada<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ListaSimplementeEnlazada<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
