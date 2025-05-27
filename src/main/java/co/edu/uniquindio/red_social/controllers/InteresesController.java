package co.edu.uniquindio.red_social.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class InteresesController {

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
    private TableColumn<String, String> columIntereses;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label publicarNuevoContenidoLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TableView<String> tableIntereses;

    @FXML
    private TextField tituloField;

    @FXML
    private Label tituloLabel;

    @FXML
    private Label volverLabel;

    private Estudiante estudiante = (Estudiante) PerfilUsuario.getUsuarioActual();
    private ObservableList<String> interesesObservable;

    @FXML
    void onAgregar(ActionEvent event) {
        String nuevoInteres = tituloField.getText().trim();
        if (!nuevoInteres.isEmpty()) {
            estudiante.anadirPreferencia(nuevoInteres);
            interesesObservable.add(nuevoInteres);
            tituloField.setText("");

            mostrarMensajeTemporal("Interés agregado con éxito!");
        } else {
            mostrarMensajeTemporal("Por favor ingrese un interés");
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        String interesSeleccionado = tableIntereses.getSelectionModel().getSelectedItem();
        if (interesSeleccionado != null) {
            estudiante.eliminarPreferencia(interesSeleccionado);
            interesesObservable.remove(interesSeleccionado);

            mostrarMensajeTemporal("Interés eliminado con éxito!");
        } else {
            mostrarMensajeTemporal("Por favor seleccione un interés a eliminar");
        }
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
        columIntereses.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        cargarIntereses();
        agregadoConExitoLabel.setVisible(false);
    }

    private void cargarIntereses() {
        ListaSimplementeEnlazada<String> intereses = estudiante.getPreferencias();
        interesesObservable = FXCollections.observableArrayList();

        for (String interes : intereses) {
            interesesObservable.add(interes);
        }

        tableIntereses.setItems(interesesObservable);
    }

    private void mostrarMensajeTemporal(String mensaje) {
        agregadoConExitoLabel.setText(mensaje);
        agregadoConExitoLabel.setVisible(true);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                agregadoConExitoLabel.setVisible(false);
            }
        }, 2000);
    }
}
