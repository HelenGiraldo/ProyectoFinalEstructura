package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.util.GrupoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GruposDeEstudioAdminController {

    @FXML
    private HBox HboxAvatarNombre1;

    @FXML
    private HBox HboxAvatarNombre2;

    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelTipo;

    @FXML
    private Label LabelTotalPublicados;

    @FXML
    private Label LabelTusContenidos;

    @FXML
    private Label LabelTusContenidosContenido;

    @FXML
    private Label LabelUser3chat;

    @FXML
    private Label LabelValoracion;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private Label adminLabel;

    @FXML
    private Button botonBuscar;

    @FXML
    private Button buttonAniadir;

    @FXML
    private ToggleButton buttonGrupo1;

    @FXML
    private ToggleButton buttonGrupo2;

    @FXML
    private Button buttonGrupos;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private Label contenidosLabel;

    @FXML
    private Label grupo1Label;

    @FXML
    private Label grupoActualLabel;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private Label gruposEstudioLabel;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private VBox userchatListVBox;

    @FXML
    void irAChat(ActionEvent event) {

    }

    @FXML
    void irAConfig(ActionEvent event) {

    }

    @FXML
    void irAContenidos(ActionEvent event) {

    }

    @FXML
    void irAInicio(ActionEvent event) {

    }

    @FXML
    void irASolicitudesAyuda(ActionEvent event) {

    }

    @FXML
    void irASugerencias(ActionEvent event) {

    }

    @FXML
    private VBox contenedorGrupos; // VBox en tu FXML donde se listarán los grupos

    @FXML
    private void initialize() {
        cargarGrupos();
    }

    private void cargarGrupos() {
        contenedorGrupos.getChildren().clear();
        for (Grupo grupo : GrupoService.getInstance().obtenerGrupos()) {
            Label label = new Label("Nombre: " + grupo.getNombre() + ", Tipo: " + grupo.getTipoGrupo());
            contenedorGrupos.getChildren().add(label);
        }
    }


}
