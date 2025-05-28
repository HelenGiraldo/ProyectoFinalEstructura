package co.edu.uniquindio.red_social.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.SolicitudAmistad;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecibirSolicitudesController {

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
    private TableColumn<SolicitudAmistad, String> columnApellido;

    @FXML
    private TableColumn<SolicitudAmistad, String> columnNombre;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label publicarNuevoContenidoLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TableView<SolicitudAmistad> tableSolicitudes;

    @FXML
    private Label volverLabel;

    private SolicitudAmistad solicitudSeleccionada;

    RedSocial redSocial = RedSocial.getInstance();
    Estudiante estudiante = (Estudiante) PerfilUsuario.getUsuarioActual();

    @FXML
    void onAgregar(ActionEvent event) {
        if(solicitudSeleccionada == null) {
            System.out.println("No hay ninguna solicitud seleccionada.");
            return;
        }
        estudiante.aceptarSolicitud(solicitudSeleccionada);
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if(solicitudSeleccionada == null) {
            System.out.println("No hay ninguna solicitud seleccionada.");
            return;
        }
        estudiante.eliminarSolicitud(solicitudSeleccionada);
    }

    @FXML
    void onVolver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/sugerencias.fxml"));
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
    void initialize() {
        ListaSimplementeEnlazada<SolicitudAmistad> solicitudesLista = estudiante.getSolicitudesRecibidas();
        ObservableList<SolicitudAmistad> solicitudesObservableList = FXCollections.observableArrayList();

        for(SolicitudAmistad solicitud : solicitudesLista) {
            solicitudesObservableList.add(solicitud);
        }

        // Configurar las columnas de la tabla
        columnNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstudianteSolicitante().getNombre()));
        columnApellido.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstudianteSolicitante().getApellido()));

        // Cargar los datos en la tabla
        tableSolicitudes.setItems(solicitudesObservableList);

        // Listener para la selección de filas en la tabla
        tableSolicitudes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            solicitudSeleccionada = newSelection;
            if (solicitudSeleccionada != null) {
                System.out.println("Solicitud seleccionada de: " + solicitudSeleccionada.getEstudianteSolicitante().getNombre() + " " + solicitudSeleccionada.getEstudianteSolicitante().getApellido());
                // Aquí puedes realizar acciones adicionales cuando se selecciona una solicitud
            }
        });
    }

}