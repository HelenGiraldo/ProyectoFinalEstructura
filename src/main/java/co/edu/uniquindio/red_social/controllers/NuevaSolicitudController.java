package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NuevaSolicitudController {

    @FXML
    private Label EnviadoConExitoLabel;

    @FXML
    private TextField TituloField;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Button buttonEnviar;

    @FXML
    private TextField contenidoField;

    @FXML
    private HBox grupoLabel;

    @FXML
    private ChoiceBox<String> importanciaChoiceBox;

    @FXML
    private Label resolverSolicitudLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private Label volverLabel;

    @FXML
    private void initialize(){
        cargarComboBox();
    }

    @FXML
    void enviarSolicitud(ActionEvent event) {
        String titulo = TituloField.getText();
        String contenido = contenidoField.getText();
        String importancia = importanciaChoiceBox.getValue();

        RedSocial redSocial = RedSocial.getInstance();
        redSocial.agregarSolicitudAyuda(contenido,(Estudiante) PerfilUsuario.getUsuarioActual(),titulo, importancia);

        EnviadoConExitoLabel.setText("Solicitud enviada con éxito");
        EnviadoConExitoLabel.setVisible(true);


        TituloField.clear();
        contenidoField.clear();
        importanciaChoiceBox.setValue("Normal");


    }

    private void cargarComboBox() {
        importanciaChoiceBox.getItems().addAll("Muy urgente", "Urgente", "Normal");
        importanciaChoiceBox.setValue("Normal");

    }

    @FXML
    void onVolver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/solicitudes.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
