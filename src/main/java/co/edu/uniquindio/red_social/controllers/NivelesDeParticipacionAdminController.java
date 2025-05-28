package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
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

import java.io.IOException;

public class NivelesDeParticipacionAdminController {

    @FXML
    private Label IdLabel;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Label amigosLabel;

    @FXML
    private Button buttonCaminoMasCorto;

    @FXML
    private Button buttonComunidades;

    @FXML
    private Button buttonMasConexiones;

    @FXML
    private Button buttonMasValorados;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ComboBox<Estudiante> comboBoxEstudiante;

    @FXML
    private Label gruposLabel;

    @FXML
    private Label handlevolver;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label publicacionesLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private Label valoracionesLabel;

    @FXML
    void handleVolver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/CaminoMasCorto.fxml"));
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
    void irACaminoMasCorto(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/CaminoMasCorto.fxml", event);

    }

    @FXML
    void irADeteccionComunidades(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/DeteccinComunidades.fxml", event);

    }

    @FXML
    void irAEstudiantesMasConexiones(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/estudiantesConMasConexiones.fxml", event);

    }

    @FXML
    void irAMasValorados(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/ContenidosMasValorados.fxml", event);

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
    @FXML
    void irAction(ActionEvent event){
        if(comboBoxEstudiante.getValue() == null) {
            return;
        }
        Estudiante estudianteSeleccionado = comboBoxEstudiante.getValue();
        estudianteSeleccionado.getContenidos().recorrerInorden().show();
        nombreLabel.setText(estudianteSeleccionado.getNombre());
        IdLabel.setText(String.valueOf(estudianteSeleccionado.getId()));
        publicacionesLabel.setText(String.valueOf(estudianteSeleccionado.getContenidos().getPeso()));
        RedSocial redSocial = RedSocial.getInstance();
        int publicaciones = 0;
        for(Grupo grupo : redSocial.getGrupos()) {
            for(Contenido contenido : grupo.getContenidos().recorrerInorden()) {
                if(contenido.getAutor().equals(estudianteSeleccionado)) {
                    publicaciones += 1;
                }
            }
        }
        amigosLabel.setText(String.valueOf(estudianteSeleccionado.getContactos().size()+ publicaciones));
        valoracionesLabel.setText(String.valueOf(estudianteSeleccionado.valoracionesPublicacion()));
        gruposLabel.setText(String.valueOf(estudianteSeleccionado.getGrupos().size()));
    }

    RedSocial redSocial = RedSocial.getInstance();
    @FXML
    void initialize() {
        ListaSimplementeEnlazada<Estudiante> estudiantes = redSocial.getEstudiantes();

        ObservableList<Estudiante> listaObservable = FXCollections.observableArrayList();

        for (Estudiante estudiante : estudiantes) {
            listaObservable.add(estudiante);
        }
        comboBoxEstudiante.setItems(listaObservable);

    }

}
