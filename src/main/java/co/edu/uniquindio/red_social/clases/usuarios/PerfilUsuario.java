package co.edu.uniquindio.red_social.clases.usuarios;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class PerfilUsuario {

    private static PerfilUsuario instancia = new PerfilUsuario();
    private final ObjectProperty<Image> imagenPerfil = new SimpleObjectProperty<>();
    private static Estudiante usuarioActual;

    private PerfilUsuario() {}

    public static PerfilUsuario getInstancia() {
        return instancia;
    }

    public ObjectProperty<Image> imagenPerfilProperty() {
        return imagenPerfil;
    }

    public Image getImagenPerfil() {
        return imagenPerfil.get();
    }

    public void setImagenPerfil(Image imagen) {
        this.imagenPerfil.set(imagen);
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Estudiante usuario) {
        usuarioActual = usuario;
    }
}
