package co.edu.uniquindio.red_social.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class InteresesController {

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Button buttonPublicar;

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
    private TableColumn<?, ?> tableRowCell;

    @FXML
    private TextField tituloField;

    @FXML
    private Label tituloLabel;

    @FXML
    private Label volverLabel;

    @FXML
    void handlePublicar(ActionEvent event) {

    }

}
