package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.util.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecuperarController {

    @FXML
    private Button buttonEnviar;

    @FXML
    private TextField ingresarEmailField;

    @FXML
    private Label ingresarEmailLabel;

    @FXML
    private Label mensajeLabel;

    @FXML
    void enviarCorreo(ActionEvent event) {
        String correoDestino = ingresarEmailField.getText().trim();

        if (correoDestino.isEmpty()) {
            mensajeLabel.setText("Por favor ingrese su correo.");
        } else {
            try {
                Estudiante est = RedSocial.getInstance().estudianteExisteCorreo(correoDestino);
                Email.olvidasteContrasena(correoDestino,est.getNombre() +" " + est.getApellido(),est.getContrasena());
                mensajeLabel.setText("Correo enviado con éxito a " + correoDestino);

                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignored) {}
                    javafx.application.Platform.runLater(() -> {
                        Stage stage = (Stage) buttonEnviar.getScene().getWindow();
                        stage.close();
                    });
                }).start();

            } catch (Exception e) {
                mensajeLabel.setText("Error al enviar el correo.");
                e.printStackTrace();
            }
        }
    }
}
