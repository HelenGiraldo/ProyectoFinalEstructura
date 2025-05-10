package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.social.Grupo;

public interface AdministracionContenido {
    public boolean publicarContenido(Publicacion publicacion, Grupo grupo);

    public boolean eliminarPublicacion(Publicacion publicacion);

    public boolean modificarPublicacion(Publicacion publicacionAntigua, Publicacion publicacionNueva);

    public void valorarContenido(Publicacion publicacion, int valoracion);


}