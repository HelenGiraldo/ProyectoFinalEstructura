package co.edu.uniquindio.red_social.clases.usuarios;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.File;

/*
    * Esta clase es un singleton que representa el perfil del usuario actual.
    * Contiene la imagen de perfil y el usuario actual.
 */
public class PerfilUsuario {

    private static PerfilUsuario instancia = new PerfilUsuario();
    private final ObjectProperty<Image> imagenPerfil = new SimpleObjectProperty<>();
    private static Usuario usuarioActual;

    private PerfilUsuario() {}

    public static PerfilUsuario getInstancia() {
        return instancia;
    }

    public ObjectProperty<Image> imagenPerfilProperty() {
        File file = PerfilUsuario.getUsuarioActual().getImagenPerfil();
        if (file != null && file.exists()) {
            imagenPerfil.set(new Image(file.toURI().toString()));
        } else {
            imagenPerfil.set(null);
        }
        return imagenPerfil;
    }


    public Image getImagenPerfil() {
        File file = PerfilUsuario.getUsuarioActual().getImagenPerfil();
        Image imagen = new Image(file.toURI().toString());
        return imagen;
    }

    public void setImagenPerfil(Image imagen) {
        File file = new File(imagen.getUrl());
        usuarioActual.setImagenPerfil(file);
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
}
