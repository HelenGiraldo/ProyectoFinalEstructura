package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;

public class SolicitudAmistad {

    private boolean estado;
    private Estudiante estudianteSolicitante;
    private Estudiante estudianteSolicitado;
    private String id;

    public SolicitudAmistad(Estudiante estudianteSolicitante, Estudiante estudianteSolicitado) {
        this.estado = false;
        this.estudianteSolicitante = estudianteSolicitante;
        this.estudianteSolicitado = estudianteSolicitado;
    }

    public void aceptarSolicitud() {
        this.estado = true;
        estudianteSolicitante.anadirContacto(estudianteSolicitado);
        estudianteSolicitado.anadirContacto(estudianteSolicitante);
        estudianteSolicitado.eliminarSolicitud(this);
        Chat chat = new Chat(estudianteSolicitado, estudianteSolicitante);
        estudianteSolicitado.anadirChat(chat);
        estudianteSolicitante.anadirChat(chat);
        UtilSQL.aceptarSolicitudAmistad(estudianteSolicitante.getId(), estudianteSolicitado.getId());

    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public Estudiante getEstudianteSolicitante() {
        return estudianteSolicitante;
    }

    public Estudiante getEstudianteSolicitado() {
        return estudianteSolicitado;
    }
}
