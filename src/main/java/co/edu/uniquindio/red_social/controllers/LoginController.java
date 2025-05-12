package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.clases.usuarios.UsuarioRegistrado;
import co.edu.uniquindio.red_social.util.Email;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleIniciarSesion(ActionEvent event) {
        String usuarioIngresado = textFieldEmail.getText().trim();
        String contrasenaIngresada = passwordField.getText().trim();

        Usuario usuarioRegistrado = UsuarioRegistrado.buscarUsuarioPorEmail(usuarioIngresado);

        System.out.println("------ INTENTANDO INICIAR SESIÓN ------");
        System.out.println("Email ingresado: " + usuarioIngresado);
        System.out.println("Contraseña ingresada: " + contrasenaIngresada);

        if (usuarioRegistrado == null) {
            datosIncorrectosLabel.setText("No hay usuarios registrados con ese email.");
            System.out.println("Error: No hay usuario registrado con ese email.");
            return;
        }

        System.out.println("Email registrado: " + usuarioRegistrado.getEmail());
        System.out.println("Contraseña registrada: " + usuarioRegistrado.getContrasena());

        boolean contrasenaCorrecta = usuarioRegistrado.getContrasena().equals(contrasenaIngresada);

        if (contrasenaCorrecta) {
            System.out.println("Login exitoso.");
            datosIncorrectosLabel.setText("");
            PerfilUsuario.getInstancia().setUsuarioActual(usuarioRegistrado);
            irAInicio(event);
        } else {
            datosIncorrectosLabel.setText("Contraseña incorrecta.");
            System.out.println("Login fallido: Contraseña incorrecta.");
        }



    }

    @FXML
    private void handleOlvidasteContrasena(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("co/edu/uniquindio/red_social/Recuperar.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Recuperar Contraseña");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            datosIncorrectosLabel.setText("No se pudo cargar la interfaz de recuperación.");
        }
    }


    @FXML
    private void handleVolver(MouseEvent event) {
        try {
            // Cargar la vista del logo o de inicio
            URL logoUrl = getClass().getResource("/co/edu/uniquindio/red_social/logo.fxml");
            System.out.println("URL logo: " + logoUrl);  // Asegúrate de que la ruta sea correcta
            FXMLLoader loader = new FXMLLoader(logoUrl);
            Parent logoView = loader.load();

            // Obtener el Stage y cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(logoView);
            stage.setScene(scene);
            stage.show();

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
            Parent inicioView = loader.load();

            // PASO CLAVE: obtener controlador y pasarle el usuario
            InicioController controller = loader.getController();
            controller.inicializarUsuario((Usuario) PerfilUsuario.getInstancia().getUsuarioActual());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(inicioView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
