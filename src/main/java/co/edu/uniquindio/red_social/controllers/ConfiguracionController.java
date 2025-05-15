package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;

import javafx.stage.FileChooser;
import java.io.File;


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
    public void initialize() {
        PerfilUsuario perfil = PerfilUsuario.getInstancia();
        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                profileImage.setImage(newImg);
            }
        });

        // Inicializar la imagen si ya está configurada
        if (perfil.getImagenPerfil() != null) {
            profileImage.setImage(perfil.getImagenPerfil());
        }
    }


    @FXML
    private void guardar(ActionEvent event) throws IOException {

    }

    @FXML
    private void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");



        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            String imagePath = file.toURI().toString();
            Image nuevaImagen = new Image(imagePath);
            profileImage.setImage(nuevaImagen);
            PerfilUsuario.getInstancia().setImagenPerfil(nuevaImagen);
            System.out.println("Imagen seleccionada: " + imagePath);
        }
    }



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
