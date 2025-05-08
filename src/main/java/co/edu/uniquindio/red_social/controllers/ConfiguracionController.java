package co.edu.uniquindio.red_social.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

public class ConfiguracionController {

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Button buttonCambiarImagen;

    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private HBox hBoxDerecha;

    @FXML
    private HBox hBoxIzquierda;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label labelAccountSettings;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelContraseña;

    @FXML
    private Label labelContraseñaAnterior;

    @FXML
    private HBox labelContraseñaNueva;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelIntroduccion;

    @FXML
    private Label labelNombre;

    @FXML
    private Label labelUserInformation;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private ImageView profileImage;

    @FXML
    private TextField textFieldAnterior;

    @FXML
    private TextField textFieldApellido;

    @FXML
    private TextField textFieldContraseñaNueva;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private Label textFieldTitle;

    @FXML
    private AnchorPane root;

    @FXML
    private void irALogo(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Logo.fxml");
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

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Inicio.fxml");
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



}
