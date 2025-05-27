package co.edu.uniquindio.red_social.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class RecibirSolicitudesController {

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Label agregadoConExitoLabel;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private TableColumn<?, ?> columnApellido;

    @FXML
    private TableColumn<?, ?> columnNombre;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label publicarNuevoContenidoLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TableView<?> tableSolicitudes;

    @FXML
    private Label volverLabel;

    @FXML
    void onAgregar(ActionEvent event) {

    }

    @FXML
    void onEliminar(ActionEvent event) {

    }

    @FXML
    void onVolver(MouseEvent event) {

    }

}
