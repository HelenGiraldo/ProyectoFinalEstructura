package co.edu.uniquindio.red_social.controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;

public class InicioController {

    @FXML
    private Label TextFieldSaludo;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private Label LabelGrupoSugerido;

    @FXML
    private Label LabelCantidadIntegrantes;

    @FXML
    private Label LabelTipo;

    @FXML
    private Label LabelValoracion;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    public void initialize() {
        URL imageUrl = getClass().getResource("/imagenes/imagePerfil.png");

        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            imagenPerfil.setImage(image);

            // Esperar al layout para obtener el tamaño final
            Platform.runLater(() -> {
                double centerX = imagenPerfil.getFitWidth() / 2;
                double centerY = imagenPerfil.getFitHeight() / 2;
                double radius = Math.min(imagenPerfil.getFitWidth(), imagenPerfil.getFitHeight()) / 2;

                Circle clip = new Circle(centerX, centerY, radius);
                imagenPerfil.setClip(clip);

                // Si no se ve bien, puede que debas forzar el tamaño en el FXML o CSS
            });
        } else {
            System.err.println("No se pudo cargar la imagen: /imagenes/imagePerfil.png");
        }
    }

    public void actualizarSaludo(String nombreUsuario) {
        TextFieldSaludo.setText("Hola, " + nombreUsuario);
    }

    public void scrollSuaveAbajo() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(scrollPrincipal.vvalueProperty(), 1.0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void actualizarGrupoEstudio(String nombreGrupo, String integrantes) {
        LabelGrupoSugerido.setText(nombreGrupo);
        LabelCantidadIntegrantes.setText(integrantes + " integrantes");
    }

    public void actualizarContenido(String tipo, String valoracion) {
        LabelTipo.setText(tipo);
        LabelValoracion.setText("★ " + valoracion);
    }
}
