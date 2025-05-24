package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.util.GrupoService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.stage.Stage;

public class CrearGrupoController {

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Button buttonCrear;

    @FXML
    private Label creadoConExitoLabel;

    @FXML
    private TextField descripcionField;

    @FXML
    private Label descripcionLabel;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private TextField nombreField;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label publicarNuevoGrupoLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TextField tipoGrupoField;

    @FXML
    private Label tipoGrupoLabel;

    @FXML
    public void initialize() {

    }


    @FXML
    private void handleCrearGrupo(ActionEvent event) {
        crearGrupo();
    }


    private void crearGrupo() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        String tipo = tipoGrupoField.getText(); // Asegúrate de usar el campo correcto

        if (!nombre.isEmpty() && !descripcion.isEmpty() && !tipo.isEmpty()) {
            Grupo nuevoGrupo = new Grupo(nombre, descripcion, tipo, true);
            GrupoService.getInstance().agregarGrupo(nuevoGrupo);

            creadoConExitoLabel.setText("¡Grupo creado con éxito!");

            // Espera 3 segundos y cierra la ventana
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> {
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            });
            pause.play();
        } else {
            creadoConExitoLabel.setText("Por favor, completa todos los campos.");
        }
    }
}
