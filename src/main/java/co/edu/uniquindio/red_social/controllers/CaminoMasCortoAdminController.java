package co.edu.uniquindio.red_social.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CaminoMasCortoAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BuscarCamino;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Button buttonComunidades;

    @FXML
    private Button buttonMasConexiones;

    @FXML
    private Button buttonMasValorados;

    @FXML
    private Button buttonParticipacion;

    @FXML
    private Label caminoMasCortoLabel;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ComboBox<Estudiante> comboBoxEstudiante1;

    @FXML
    private ComboBox<Estudiante> comboBoxEstudiante2;

    @FXML
    private Label estudiante1Label;

    @FXML
    private Label estudiante2Label;

    @FXML
    private Label handlevolver;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    void handleVolver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/InicioAdmin.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irADeteccionComunidades(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/DeteccionComunidades.fxml", event);

    }

    @FXML
    void irAEstudiantesMasConexiones(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/estudiantesConMasConexiones.fxml", event);

    }

    @FXML
    void irAMasValorados(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/ContenidosMasValorados.fxml", event);


    }

    @FXML
    void irANivelesParticipacion(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/NivelesDeParticipacion.fxml", event);
    }

    private void navegar(String url, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    RedSocial redSocial  = RedSocial.getInstance();
    @FXML
    void buscarCamino(ActionEvent event) {
        Estudiante estudiante1 = comboBoxEstudiante1.getValue();
        Estudiante estudiante2 = comboBoxEstudiante2.getValue();

      caminoMasCortoLabel.setText(estudiante1.getNombre() + " - " + estudiante2.getNombre());
    }

    @FXML
    void initialize() {
        ListaSimplementeEnlazada<Estudiante> estudiantes = redSocial.getEstudiantes();

        ObservableList<Estudiante> listaObservable = FXCollections.observableArrayList();

        for (Estudiante estudiante : estudiantes) {
            listaObservable.add(estudiante);
        }

        comboBoxEstudiante1.setItems(listaObservable);
        comboBoxEstudiante2.setItems(listaObservable);



    }

}
