package co.edu.uniquindio.red_social.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContenidosMasValoradosAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Button buttonCaminoCorto;

    @FXML
    private Button buttonComunidades;

    @FXML
    private Button buttonMasConexiones;

    @FXML
    private Button buttonParticipacion;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private Label contenidosLabel;

    @FXML
    private TableView<Contenido> contenidosMasValoradosTable;

    @FXML
    private Label handlevolver;

    @FXML
    private Label masValoradosLabel;

    @FXML
    private TableColumn<Contenido, String> numeroValoracionesColumn;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private TableColumn<Contenido, String> tituloTableColumn;

    @FXML
    private TableColumn<Contenido, String> valoracionTableColumn;

    @FXML
    void handleVolver(MouseEvent event) {

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
    void irAEstudiantesMasConexiones(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/estudiantesConMasConexiones.fxml", event);

    }

    @FXML
    void irANivelesParticipacion(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/NivelesDeParticipacion.fxml", event);

    }
    RedSocial redSocial = RedSocial.getInstance();
    ObservableList<Contenido> contenidosObservable = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Configurar las columnas de la tabla
        tituloTableColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        valoracionTableColumn.setCellValueFactory(new PropertyValueFactory<>("contenido"));
        numeroValoracionesColumn.setCellValueFactory(cellData -> {
            int size = cellData.getValue().getCalificaciones().size();
            return javafx.beans.binding.Bindings.createStringBinding(() -> String.valueOf(size));
        });

        // Cargar los contenidos en la tabla
        cargarContenidos();
    }

    private void cargarContenidos() {
        ListaSimplementeEnlazada<Contenido> contenidos = new ListaSimplementeEnlazada<>();

        // Obtener todos los contenidos de todos los estudiantes
        for(Estudiante estudiante : redSocial.getEstudiantes()) {
            for(Contenido contenido : estudiante.getContenidos().recorrerInorden()) {
                contenidos.add(contenido);
            }
        }


        // Agregar a la lista observable
        for(Contenido contenido : contenidos) {
            contenidosObservable.add(contenido);
        }

        // Establecer los datos en la tabla
        contenidosMasValoradosTable.setItems(contenidosObservable);
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
}