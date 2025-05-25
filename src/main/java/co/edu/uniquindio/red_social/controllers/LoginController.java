package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    RedSocial redSocial = RedSocial.getInstance();
    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label datosIncorrectosLabel;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleIniciarSesion(ActionEvent event) {
        String usuarioIngresado = textFieldEmail.getText().trim();
        String contrasenaIngresada = passwordField.getText().trim();

        Estudiante usuarioRegistrado = redSocial.estudianteExisteCorreo(usuarioIngresado);


        System.out.println("------ INTENTANDO INICIAR SESIÓN ------");
        System.out.println("Email ingresado: " + usuarioIngresado);
        System.out.println("Contraseña ingresada: " + contrasenaIngresada);

        if (usuarioRegistrado == null) {
            datosIncorrectosLabel.setText("No hay usuarios registrados con ese email.");
            return;
        }

        boolean contrasenaCorrecta = usuarioRegistrado.getContrasena().equals(contrasenaIngresada);

        if (contrasenaCorrecta) {
            System.out.println("Login exitoso.");
            datosIncorrectosLabel.setText("");
            PerfilUsuario.setUsuarioActual(usuarioRegistrado);
            irAInicio();
        } else {
            datosIncorrectosLabel.setText("Contraseña incorrecta.");
            System.out.println("Login fallido: Contraseña incorrecta.");
        }



    }

    @FXML
    private void handleOlvidasteContrasena(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Recuperar.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Recuperar Contraseña");

            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            datosIncorrectosLabel.setText("No se pudo cargar la interfaz de recuperación.");
        }
    }



    @FXML
    private void handleVolver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Logo.fxml"));
            Parent newRoot = loader.load();

            // Obtener la ventana actual (stage)
            Stage stage = (Stage) textFieldEmail.getScene().getWindow();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void irAInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Inicio.fxml"));
            Parent newRoot = loader.load();

            // Obtener la ventana actual (stage)
            Stage stage = (Stage) textFieldEmail.getScene().getWindow();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
