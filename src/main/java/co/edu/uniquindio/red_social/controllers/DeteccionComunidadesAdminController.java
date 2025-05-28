package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.util.GrupoTabla;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeteccionComunidadesAdminController {

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Button buttonCaminoMasCorto;

    @FXML
    private Button buttonMasConexiones;

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
    private TableColumn<GrupoTabla, String> estudianteTableColumn;

    @FXML
    private TableView<GrupoTabla> estudiantesConMasConexionesTable;

    @FXML
    private Label handlevolver;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private TableColumn<GrupoTabla, String> tipoGrupoTableColumn;

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
    void irAEstudiantesMasConexiones(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/estudiantesConMasConexiones.fxml", event);

    }

    @FXML
    void irAGrafo(ActionEvent event) {
        if (grupoSeleccionado == null || grupoSeleccionado.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("No se ha seleccionado ningún grupo");
            alert.setContentText("Por favor selecciona un grupo de la tabla primero.");
            alert.showAndWait();
            return;
        }
        GrupoTabla.NOMBRE_GRUPO = grupoSeleccionado;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/crearGrupo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gráfico del Grupo: " + grupoSeleccionado);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void irAMasValorados(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/ContenidosMasValorados.fxml", event);

    }

    @FXML
    void irANivelesParticipacion(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/NivelesDeParticipacion.fxml", event);

    }

    RedSocial redSocial = RedSocial.getInstance();
    String grupoSeleccionado;
    @FXML
    void initialize() {
        ListaSimplementeEnlazada<String> grupos = redSocial.obtenerGruposAutomaticos();
        ListaSimplementeEnlazada<String> tipos = new ListaSimplementeEnlazada<>();

        for (String grupo : grupos) {
            int cantidad = redSocial.cuantosTienenPreferencia(grupo);
            tipos.add("Automático, posibles miembros: " + cantidad);
        }

        estudianteTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombreGrupo()));
        tipoGrupoTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoGrupo()));

        ListaSimplementeEnlazada<GrupoTabla> datosTabla = new ListaSimplementeEnlazada<>();
        for (int i = 0; i < grupos.size(); i++) {
            datosTabla.add(new GrupoTabla(grupos.get(i), tipos.get(i)));
        }

        ObservableList<GrupoTabla> datosObservables = FXCollections.observableArrayList();
        for (GrupoTabla dato : datosTabla) {
            datosObservables.add(dato);
        }

        estudiantesConMasConexionesTable.setItems(datosObservables);

        estudiantesConMasConexionesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        grupoSeleccionado = newValue.getNombreGrupo();
                        System.out.println("Grupo seleccionado: " + grupoSeleccionado);
                    }
                }
        );
    }
}
