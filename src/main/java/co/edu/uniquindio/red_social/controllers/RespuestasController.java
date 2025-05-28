package co.edu.uniquindio.red_social.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.SolicitudAmistad;
import co.edu.uniquindio.red_social.clases.social.Solucion;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RespuestasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Label agregadoConExitoLabel;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private TableColumn<Solucion, String> contenidoColumn;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label publicarNuevoContenidoLabel;

    @FXML
    private TableColumn<Solucion, String> respuestaColumn;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TableView<Solucion> tableRespuestas;

    @FXML
    private Label volverLabel;


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

    RedSocial redSocial = RedSocial.getInstance();
    Estudiante estudiante = (Estudiante) PerfilUsuario.getUsuarioActual();
    private Solucion solucionSeleccionada;

    @FXML
    void initialize() {
        UtilSQL.obtenerSolucionesPorUsuario(estudiante);
        ListaSimplementeEnlazada<Solucion> soluciones = estudiante.getSoluciones();

        respuestaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty("Solicitud resuelta"));
        contenidoColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTexto()));

        ObservableList<Solucion> datosTabla = FXCollections.observableArrayList();
        for (Solucion solucion : soluciones) {
            datosTabla.add(solucion);
        }

        tableRespuestas.setItems(datosTabla);

        tableRespuestas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        solucionSeleccionada = newValue;
                        System.out.println("Solución seleccionada: " + solucionSeleccionada);
                    }
                }
        );
    }

    @FXML
    void onAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/NuevaSolicitud.fxml"));
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
    void onEliminar(ActionEvent event) {
        if (solucionSeleccionada != null) {
            System.out.println("Eliminando solución: " + solucionSeleccionada);
        } else {
            System.out.println("No hay solución seleccionada");
        }
    }

}
