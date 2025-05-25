package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mensaje {

    private String mensaje;
    private LocalDateTime fecha;
    private Estudiante usuarioRemitente;
    private String id;
    private Chat chat;

    public Mensaje(String mensaje, LocalDateTime fecha, Estudiante usuarioRemitente, Chat chat) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuarioRemitente = usuarioRemitente;
        this.chat = chat;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estudiante getUsuarioRemitente() {
        return usuarioRemitente;
    }

    public void setUsuarioRemitente(Estudiante usuarioRemitente) {
        this.usuarioRemitente = usuarioRemitente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "chat=" + chat.getId() +
                ", id='" + id + '\'' +
                ", usuarioRemitente=" + usuarioRemitente.getNombre() +
                ", fecha=" + fecha +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
