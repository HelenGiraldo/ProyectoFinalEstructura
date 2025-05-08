package co.edu.uniquindio.red_social.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

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
    private void registrarUsuario(ActionEvent event) {
        // Leer datos
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = textFieldEmail.getText();
        String password = passwordField.getText();

        // Validar campos vacíos (puedes hacerlo opcional)
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            labelRegistroValidacion.setText("Por favor, complete todos los campos.");
            return;
        }

        // Simular guardar datos
        System.out.println("Usuario registrado: " + nombre + " " + apellido + " (" + email + ")");

        // Mensaje de éxito
        labelRegistro.setText("¡Registro exitoso!");

        // Cambiar a la vista de inicio (ajusta la ruta si es necesario)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/co/edu/uniquindio/red_social/Inicio.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            labelRegistro.setText("No se pudo cargar la vista de inicio.");
        }
    }
}
