package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.interfaces.ChatObservable;
import co.edu.uniquindio.red_social.clases.interfaces.ChatObserver;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class Chat implements ChatObservable {
    private String id;
    private Estudiante estudiante;
    private Estudiante estudiante2;
    private ListaSimplementeEnlazada<Mensaje> mensajes;
    private ListaSimplementeEnlazada<ChatObserver> observers;

    public Chat(Estudiante estudiante, Estudiante estudiante2) {
        this.mensajes = new ListaSimplementeEnlazada<>();
        this.observers = new ListaSimplementeEnlazada<>();
        this.estudiante = estudiante;
        this.estudiante2 = estudiante2;
    }

    public boolean eliminarChat() {
        estudiante.getChats().remove(this);
        estudiante2.getChats().remove(this);
        mensajes.clear();
        UtilSQL.eliminarChat(id);
        return true;
    }

    public boolean enviarMensaje(Mensaje mensaje) {
        UtilSQL.enviarMensaje(mensaje);
        notifyObservers();
        return mensajes.add(mensaje);
    }

    public boolean eliminarMensaje(Mensaje mensaje) {
        if (mensajes.contains(mensaje)) {
            UtilSQL.eliminarMensaje(mensaje);
            return mensajes.remove(mensaje);
        }
        return false;
    }

    public ListaSimplementeEnlazada<Estudiante> getMiembros() {
        ListaSimplementeEnlazada<Estudiante> miembros = new ListaSimplementeEnlazada<>();
        miembros.add(estudiante);
        miembros.add(estudiante2);
        return miembros;
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

    public ListaSimplementeEnlazada<ChatObserver> getObservers() {
        return observers;
    }

    public void setObservers(ListaSimplementeEnlazada<ChatObserver> observers) {
        this.observers = observers;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", estudiante=" + estudiante +
                ", estudiante2=" + estudiante2 +
                '}';
    }

    @Override
    public void addObserver(ChatObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ChatObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (ChatObserver observer : observers) {
            observer.update();
        }
    }

    public Estudiante getOtroUsuario(Estudiante estudianteActual) {
        if(estudianteActual.equals(estudiante)){
            return estudiante;
        }else{
            return estudiante2;
        }
    }

    public boolean contieneUsuarios(Estudiante estudianteActual, Estudiante receptor) {
        if( estudianteActual.equals(estudiante)||receptor.equals(estudiante2)){
            return true;
        }else if( estudianteActual.equals(estudiante2)||receptor.equals(estudiante)){
            return true;
        }
        return false;
    }
}
