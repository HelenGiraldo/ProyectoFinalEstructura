package co.edu.uniquindio.red_social.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PublicarController {

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Button buttonAniadir;

    @FXML
    private Button buttonEnlace;

    @FXML
    private Button buttonPublicar;

    @FXML
    private TextField descripcionField;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label enlaceLabel;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label publicadoConExitoLabel;

    @FXML
    private Label publicarNuevoContenidoLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TextField temaField;

    @FXML
    private Label temaLabel;

    @FXML
    private TextField tipoField;

    @FXML
    private Label tipoLabel;

    @FXML
    private TextField tituloField;

    @FXML
    private Label tituloLabel;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handlePublicar() {
        String titulo = tituloField.getText().trim();
        String tema = temaField.getText().trim();
        String tipo = tipoField.getText().trim();
        String descripcion = descripcionField.getText().trim();

        // Validación simple: que no estén vacíos
        if (titulo.isEmpty() || tema.isEmpty() || tipo.isEmpty() || descripcion.isEmpty()) {
            publicadoConExitoLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        // Validación opcional: mínimo de caracteres
        if (titulo.length() < 4 || descripcion.length() < 10) {
            publicadoConExitoLabel.setText("El título debe tener al menos 4 caracteres y la descripción 10.");
            return;
        }

        // Si pasa las validaciones
        publicadoConExitoLabel.setText("Su contenido fue publicado con éxito");

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            if (stage != null) {
                stage.close();
            }
        });
        pause.play();
    }



}
