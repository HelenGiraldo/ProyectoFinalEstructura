package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RegistroController {

    private File archivoImagenSeleccionada;  // NUEVO atributo

    @FXML
    private Label LabelNombre;

    @FXML
    private TextField apellidoField;

    @FXML
    private VBox cardTransparente;

    @FXML
    private Button elegirImagen;

    @FXML
    private Button handleRegistrarse;

    @FXML
    private Label handleVolver;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelRegistro;

    @FXML
    private Label labelRegistroValidacion;

    @FXML
    private TextField nombreField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        PerfilUsuario perfil = PerfilUsuario.getInstancia();
        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                imagenPerfil.setImage(newImg);
            }
        });

        // Para inicializar desde el comienzo
        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }
    }

    @FXML
    private void handleVolver(MouseEvent event) {
        cambiarEscenaLogin();
    }

    public void cambiarEscenaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Login.fxml"));
            Parent newRoot = loader.load();

            // Obtener la ventana actual (stage)
            Stage stage = (Stage) handleVolver.getScene().getWindow();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void registrarUsuario(ActionEvent event) {
        // Leer datos
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = textFieldEmail.getText();
        String password = passwordField.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            labelRegistroValidacion.setText("Por favor, complete todos los campos.");
            return;
        }

        if (archivoImagenSeleccionada == null) {
            labelRegistroValidacion.setText("Debe seleccionar una imagen de perfil.");
            return;
        }



        // Registrar usuario
        RedSocial redSocial = RedSocial.getInstance();
        Estudiante estudiante= redSocial.crearEstudiante(nombre, apellido, email, password, archivoImagenSeleccionada);

        PerfilUsuario.setUsuarioActual(estudiante);
        // Mostrar mensaje de éxito
        labelRegistro.setText("¡Registro exitoso!");

        PerfilUsuario.getInstancia().setImagenPerfil(null);

        cambiarEscenaLogin();


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
            archivoImagenSeleccionada = file; // GUARDAR archivo original
            String imagePath = file.toURI().toString();
            Image nuevaImagen = new Image(imagePath);
            imagenPerfil.setImage(nuevaImagen);
            PerfilUsuario.getInstancia().setImagenPerfil(nuevaImagen);
            System.out.println("Imagen seleccionada: " + imagePath);
        }

    }
}
