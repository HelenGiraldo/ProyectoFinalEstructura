package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;

public interface AdministracionContenido {
    public boolean publicarContenido(Contenido contenido, Grupo grupo);

    public boolean eliminarPublicacion(Contenido contenido);

    public boolean modificarPublicacion(Contenido contenidoAntiguo, Contenido contenidoNuevo);

    public void valorarContenido(Contenido contenido, int valoracion);


}