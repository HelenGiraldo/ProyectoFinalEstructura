package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.util.grafo.CreacionGrafo;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EstudiantesConMasConexionesAdminController {

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Button buttonCaminoCorto;

    @FXML
    private Button buttonComunidades;

    @FXML
    private Button buttonMasValorados;

    @FXML
    private Button buttonParticipacion;

    @FXML
    private Button buttonVerGrafo;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private TableColumn<Estudiante, String> conexionesTableColumn;

    @FXML
    private Label contenidosLabel;

    @FXML
    private TableColumn<Estudiante, String> estudianteTableColumn;

    @FXML
    private TableView<Estudiante> estudiantesConMasConexionesTable;

    @FXML
    private Label handlevolver;

    @FXML
    private Label masValoradosLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

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
        navegar("/co/edu/uniquindio/red_social/DeteccionComunidades.fxml", event);

    }

    @FXML
    void irAGrafo(ActionEvent event) {
        CreacionGrafo  creacionGrafo = CreacionGrafo.getInstance();

        creacionGrafo.crearGrafo();
    }

    @FXML
    void irAMasValorados(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/ContenidosMasValorados.fxml", event);

    }

    @FXML
    void irANivelesParticipacion(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/resolverSolicitud.fxml", event);

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
    private void initialize() {
        // Configurar las columnas
        estudianteTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));

        conexionesTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getContactos().size())));

        // Cargar estudiantes sin ordenar
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        ListaSimplementeEnlazada<Estudiante> estudiantes = RedSocial.getInstance().getEstudiantes();

        // Limpiar y cargar la tabla
        estudiantesConMasConexionesTable.getItems().clear();
        for (Estudiante est : estudiantes) {
            estudiantesConMasConexionesTable.getItems().add(est);
        }
    }
}
