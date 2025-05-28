package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
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

    private Estudiante usuarioLogueado;


    @FXML
    private void initialize() {

    }

    @FXML
    private void handleIniciarSesion(ActionEvent event) {
        String correo = textFieldEmail.getText().trim();
        String contrasena = passwordField.getText().trim();

        Estudiante estudiante = redSocial.estudianteExisteCorreo(correo);
        Administrador admin = redSocial.administradorExisteCorreo(correo);


        if (estudiante != null) {
            if (estudiante.getContrasena().equals(contrasena)) {
                usuarioLogueado = estudiante;
                PerfilUsuario.setUsuarioActual(estudiante);
                irAInicio();
            } else {
                datosIncorrectosLabel.setText("Contraseña incorrecta.");
            }
            return;
        }

        if (admin != null) {
            if (contrasena.equals("admin123")) {
                PerfilUsuario.setUsuarioActual(admin);
                irAVistaAdministrador();
            } else {
                datosIncorrectosLabel.setText("Contraseña incorrecta para administrador.");
            }
            return;
        }


        datosIncorrectosLabel.setText("No hay usuarios registrados con ese email.");

    }



    private void irAVistaAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/inicioAdmin.fxml"));
            Parent root = loader.load();

            InicioAdminController inicioAdminController = loader.getController();
            inicioAdminController.inicializarUsuario((Administrador) PerfilUsuario.getUsuarioActual());

            Stage stage = (Stage) textFieldEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

            InicioController inicioController = loader.getController();
            inicioController.inicializarUsuario(usuarioLogueado);

            Stage stage = (Stage) textFieldEmail.getScene().getWindow();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
