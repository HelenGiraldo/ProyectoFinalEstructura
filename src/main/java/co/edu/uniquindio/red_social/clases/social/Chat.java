package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class Chat {
    private String id;
    private Estudiante estudiante;
    private Estudiante estudiante2;
    private ListaSimplementeEnlazada<Mensaje> mensajes;

    public Chat(Estudiante estudiante, Estudiante estudiante2) {
        this.mensajes = new ListaSimplementeEnlazada<>();
        this.estudiante = estudiante;
        this.estudiante2 = estudiante2;
    }

    public boolean eliminarChat() {
        estudiante.eliminarChat(this);
        estudiante2.eliminarChat(this);
        mensajes.clear();
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Estudiante getEstudiante2() {
        return estudiante2;
    }

    public void setEstudiante2(Estudiante estudiante2) {
        this.estudiante2 = estudiante2;
    }

    public ListaSimplementeEnlazada<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ListaSimplementeEnlazada<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
