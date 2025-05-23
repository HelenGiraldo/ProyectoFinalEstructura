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

    public boolean modoAdminActivado = false;


    @FXML
    private void initialize() {

    }

    @FXML
    private void handleIniciarSesion(ActionEvent event) {
        String usuarioIngresado = textFieldEmail.getText().trim();
        String contrasenaIngresada = passwordField.getText().trim();

        Administrador adminRegistrado = redSocial.administradorExisteCorreo(usuarioIngresado);
        Estudiante estudianteRegistrado = null;

        Usuario usuarioRegistrado = null;

        if (adminRegistrado != null) {
            usuarioRegistrado = adminRegistrado;
        } else {
            estudianteRegistrado = redSocial.estudianteExisteCorreo(usuarioIngresado);
            if (estudianteRegistrado != null) {
                usuarioRegistrado = estudianteRegistrado;
            }
        }

        if (usuarioRegistrado == null) {
            datosIncorrectosLabel.setText("No hay usuarios registrados con ese email.");
            return;
        }

        boolean contrasenaCorrecta = usuarioRegistrado.getContrasena().equals(contrasenaIngresada);

        if (contrasenaCorrecta) {
            datosIncorrectosLabel.setText("");
            PerfilUsuario.setUsuarioActual(usuarioRegistrado);

            // Aquí revisamos si el estudiante está activo como admin
            if (usuarioRegistrado instanceof Estudiante estudiante) {
                if (redSocial.estaActivoComoAdmin(estudiante)) {
                    redSocial.convertirEstudianteAAdmin(estudiante);
                    PerfilUsuario.setUsuarioActual(redSocial.administradorExisteCorreo(estudiante.getEmail()));
                    irAVistaAdministrador();
                    return;
                } else {
                    irAInicio();
                    return;
                }
            }

            if (usuarioRegistrado instanceof Administrador) {
                irAVistaAdministrador();
            }
        } else {
            datosIncorrectosLabel.setText("Contraseña incorrecta.");
        }
    }



    private void irAVistaAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/GruposEstudio.fxml"));
            Parent root = loader.load();
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
    private void handleEresAdmin(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modo Administrador");
        dialog.setHeaderText("Ingresa la contraseña de administrador");
        dialog.setContentText("Contraseña:");

        dialog.showAndWait().ifPresent(password -> {
            if (password.equals("admin123")) {
                modoAdminActivado = true;
                datosIncorrectosLabel.setText("Modo administrador activado.");
            } else {
                datosIncorrectosLabel.setText("Contraseña de administrador incorrecta.");
            }
        });
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
