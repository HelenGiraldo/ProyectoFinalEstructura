package co.edu.uniquindio.red_social.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class ContenidosController {

    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelTipo;

    @FXML
    private Label LabelTotalPublicados;

    @FXML
    private Label LabelTusContenidos;

    @FXML
    private Label LabelTusContenidosContenido;

    @FXML
    private Label LabelValoracion;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private Button botonBuscar;

    @FXML
    private Button buttonAniadir;

    @FXML
    private Button buttonEditar;

    @FXML
    private Button buttonEliminar;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private Label contenidosLabel;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private Label tusContenidosLabel;

    @FXML
    private VBox vBoxTodosContenidos;

    @FXML
    private void irAConfig(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/configuracion.fxml");
            System.out.println("URL Logo: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();

            if (root != null) {
                root.getChildren().clear();
                root.getChildren().add(configView);
            } else {
                System.err.println("El contenedor principal es null.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void irAChat(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/mensajes.fxml");
            System.out.println("URL config: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();

            if (root != null) {
                root.getChildren().clear();
                root.getChildren().add(configView);
            } else {
                System.err.println("El contenedor principal es null.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irAInicio(ActionEvent event) {
        try {
            // Obtener la URL de la vista a cargar
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Inicio.fxml");
            if (configUrl == null) {
                throw new IOException("Vista no encontrada");
            }

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();

            // Asegúrate de que root no es null
            if (root != null) {
                root.getChildren().clear();  // Limpiar la vista actual
                root.getChildren().add(configView);  // Agregar la nueva vista
            } else {
                System.err.println("El contenedor principal es null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
