package co.edu.uniquindio.red_social.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label datosIncorrectosLabel;


    private final String USUARIO_QUEMADO = "helen8335@gmail.com";
    private final String CONTRASENA_QUEMADA = "1234";

    @FXML
    private void handleIniciarSesion(ActionEvent event) {
        String usuario = textFieldEmail.getText().trim();
        String contrasena = passwordField.getText().trim();

        System.out.println("Usuario ingresado: [" + usuario + "]");
        System.out.println("Contraseña ingresada: [" + contrasena + "]");

        boolean usuarioCorrecto = USUARIO_QUEMADO.equals(usuario);
        boolean contrasenaCorrecta = CONTRASENA_QUEMADA.equals(contrasena);

        System.out.println("usuarioCorrecto: " + usuarioCorrecto);
        System.out.println("contrasenaCorrecta: " + contrasenaCorrecta);

        if (usuarioCorrecto && contrasenaCorrecta) {
            System.out.println("Credenciales correctas. Redirigiendo a la vista de inicio...");
            datosIncorrectosLabel.setText("");
            irAInicio(event);
        } else {
            String mensajeError = "Datos incorrectos: ";

            if (!usuarioCorrecto && !contrasenaCorrecta) {
                mensajeError += "usuario y contraseña.";
            } else if (!usuarioCorrecto) {
                mensajeError += "usuario.";
            } else {
                mensajeError += "contraseña.";
            }

            datosIncorrectosLabel.setText(mensajeError);
            System.out.println(mensajeError);
            return;
        }
    }


    @FXML
    private void handleOlvidasteContrasena(MouseEvent event) {
        String correoDestino = textFieldEmail.getText().trim(); // Tomar el correo que escribieron en el campo
        if (correoDestino.isEmpty()) {
            datosIncorrectosLabel.setText("Por favor ingrese su correo para recuperar la contraseña.");
            return;
        }

        try {
            // Enviar el correo
            Email.sendEmail(correoDestino, "Recuperación de contraseña", "Este es tu enlace para recuperar la contraseña: [aquí iría el enlace o instrucciones]");
            datosIncorrectosLabel.setText("Correo de recuperación enviado a " + correoDestino);
            System.out.println("Correo de recuperación enviado.");
        } catch (Exception e) {
            e.printStackTrace();
            datosIncorrectosLabel.setText("No se pudo enviar el correo. Intente más tarde.");
        }
    }


    @FXML
    private void irAInicio(ActionEvent event) {
        try {
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Inicio.fxml");
            System.out.println("URL config: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent inicioView = loader.load();

            // Obtener la ventana actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena completa
            Scene scene = new Scene(inicioView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
