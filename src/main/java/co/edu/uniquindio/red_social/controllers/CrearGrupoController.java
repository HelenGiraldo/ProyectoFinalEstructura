package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.util.GrupoService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private HBox grupoLabel;

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
    private TextField tipoGrupoLabel;


    private void crearGrupo() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        String tipo = tipoGrupoLabel.getText();

        if (!nombre.isEmpty() && !descripcion.isEmpty() && !tipo.isEmpty()) {
            Grupo nuevoGrupo = new Grupo(nombre, descripcion, tipo, true);

            GrupoService.getInstance().agregarGrupo(nuevoGrupo);

            creadoConExitoLabel.setText("¡Grupo creado con éxito!");
        } else {
            creadoConExitoLabel.setText("Por favor, completa todos los campos.");
        }

    }
}
