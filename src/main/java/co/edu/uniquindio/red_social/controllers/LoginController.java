package co.edu.uniquindio.red_social.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleIniciarSesion(ActionEvent event) {
        String usuario = textFieldEmail.getText();
        String contrasena = passwordField.getText();
        // Aquí validas las credenciales
        System.out.println("Usuario: " + usuario + ", Contraseña: " + contrasena);
    }

    @FXML
    private void handleOlvidasteContrasena(MouseEvent event) {
        // Aquí abres la nueva interfaz de recuperación
        System.out.println("Olvidó la contraseña");
    }
}
