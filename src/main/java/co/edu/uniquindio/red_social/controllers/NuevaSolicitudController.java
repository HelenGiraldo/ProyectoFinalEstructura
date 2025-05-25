package co.edu.uniquindio.red_social.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NuevaSolicitudController {

    @FXML
    private Label EnviadoConExitoLabel;

    @FXML
    private TextField TituloField;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Button buttonEnviar;

    @FXML
    private TextField contenidoField;

    @FXML
    private HBox grupoLabel;

    @FXML
    private ChoiceBox<?> importanciaChoiceBox;

    @FXML
    private Label resolverSolicitudLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private Label volverLabel;

}
