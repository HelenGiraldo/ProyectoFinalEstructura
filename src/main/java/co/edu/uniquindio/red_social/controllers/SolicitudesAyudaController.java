package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.SolicitudAyuda;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SolicitudesAyudaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox AnchorPaneContenedorSolicitudes;

    @FXML
    private Label ContenidoSolicitudLabel;

    @FXML
    private HBox HboxSolicitud;

    @FXML
    private HBox HboxSolicitud2;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Label TituloSolicitudLabel;

    @FXML
    private Button botonAniadir;

    @FXML
    private Button botonBuscar;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private ComboBox<String> comboFiltro;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private AnchorPane contenedorSolicitudes;

    @FXML
    private Label contenidoSolicitudLabel2;

    @FXML
    private Label filtrarSolicitudesLabel;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label importancia2Label;

    @FXML
    private Label importanciaLabel;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private Label nombreEstudiante2Label;

    @FXML
    private Label nombreEstudianteLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label solicitudesAyudaLabel;

    @FXML
    private Label titulo2;

    private Estudiante usuario;

    public void setUsuarioActual(Estudiante usuario) {
        this.usuario = usuario;
    }



    @FXML
    void irAContenido(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/TusContenidos.fxml", event);
    }

    @FXML
    void irAGrupoEStudio(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/gruposEstudio.fxml", event);

    }

    @FXML
    void irAChat(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/mensajes.fxml", event);

    }

    @FXML
    void irAConfig(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/Configuracion.fxml", event);
    }

    @FXML
    void irAInicio(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/Inicio.fxml", event);

    }

    RedSocial redSocial = RedSocial.getInstance();

    private void navegar(String url, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent configView = loader.load();
            Object controller = loader.getController();

            if (controller instanceof GruposDeEstudioController) {
                ((GruposDeEstudioController) controller).setUsuarioActual(usuario);
                System.out.println("Usuario enviado a GruposDeEstudioAdminController: " + usuario.getNombre());
            } else if (controller instanceof InicioController) {
                ((InicioController) controller).setUsuarioActual(usuario);
                System.out.println("Usuario enviado a InicioController: " + usuario.getNombre());
            } else if (controller instanceof ConfiguracionController) {
                ((ConfiguracionController) controller).setUsuarioActual(usuario);
            } // Agrega otros controladores según el destino

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
        SolicitudesDeAyudaButton.setSelected(true);

        System.out.println(redSocial.getSolicitudesAyuda().size());
        redSocial.getSolicitudesAyuda().show();
        cargarComboBox();
        cargarSolicitudes();
    }

    private void cargarComboBox() {
        comboFiltro.getItems().addAll("Muy urgente", "Urgente", "Normal");
        comboFiltro.setValue("Normal");

    }


    @FXML
    private void irNotificaciones(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/Configuracion.fxml", event);

    }
    @FXML
    private void aniadirSolicitud(ActionEvent event){
        navegar("/co/edu/uniquindio/red_social/NuevaSolicitud.fxml", event);
    }

    @FXML
    private void buscarNivel(){
        if(comboFiltro.getValue() == null|| comboFiltro.getValue().isEmpty()) {
            cargarSolicitudes();
            return;
        }
        limpiarSolicitudes();
        for(SolicitudAyuda solicitudAyuda: redSocial.getSolicitudesAyuda()){
            if(!solicitudAyuda.getPrioridad().toLowerCase().trim().equals(comboFiltro.getValue().toLowerCase().trim()) )
            {
                continue;
            }
            String nombreEstudiante = solicitudAyuda.getEstudiante().getNombre();
            String importancia = solicitudAyuda.getPrioridad();
            String titulo = solicitudAyuda.getTitulo();
            String contenido = solicitudAyuda.getMensaje();
            agregarSolicitud(nombreEstudiante, importancia, titulo, contenido);
        }
    }

    @FXML
    private void botonBuscar(){
        if(comboFiltro.getValue() == null|| comboFiltro.getValue().isEmpty()) {
            cargarSolicitudes();
            return;
        }
        limpiarSolicitudes();

        for(SolicitudAyuda solicitudAyuda: redSocial.getSolicitudesAyuda()){
            if(!solicitudAyuda.getTitulo().toLowerCase().contains(campoBusqueda.getText().toLowerCase()) )
            {
                continue;
            }
            String nombreEstudiante = solicitudAyuda.getEstudiante().getNombre();
            String importancia = solicitudAyuda.getPrioridad();
            String titulo = solicitudAyuda.getTitulo();
            String contenido = solicitudAyuda.getMensaje();
            agregarSolicitud(nombreEstudiante, importancia, titulo, contenido);
        }
    }


    public void cargarSolicitudes() {
        limpiarSolicitudes();
        for(SolicitudAyuda solicitudAyuda: redSocial.getSolicitudesAyuda()){
            String nombreEstudiante = solicitudAyuda.getEstudiante().getNombre();
            String importancia = solicitudAyuda.getPrioridad();
            String titulo = solicitudAyuda.getTitulo();
            String contenido = solicitudAyuda.getMensaje();
            agregarSolicitud(nombreEstudiante, importancia, titulo, contenido);
        }
    }

    public void agregarSolicitud(String nombreEstudiante, String importancia, String titulo, String contenido) {
        HBox hboxSolicitud = new HBox();
        hboxSolicitud.setPrefSize(173, 100);
        HBox.setMargin(hboxSolicitud, new Insets(0, 20, 20, 0));

        switch (importancia.toLowerCase()) {
            case "muy urgente":
                hboxSolicitud.getStyleClass().add("card-solicitud-muy-urgente");
                break;
            case "urgente":
                hboxSolicitud.getStyleClass().add("card-solicitud-urgente");
                break;
            default:
                hboxSolicitud.getStyleClass().add("card-solicitud-normal");
                break;
        }

        VBox vboxPrincipal = new VBox(5);
        vboxPrincipal.setPrefSize(140, 17);

        Label nombreLabel = new Label(nombreEstudiante);
        nombreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        VBox vboxImportancia = new VBox();
        Label importanciaLabel = new Label("Importancia: " + importancia);
        importanciaLabel.setStyle("-fx-font-style: italic;");

        VBox vboxContenido = new VBox(3);
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-weight: bold;");
        Label contenidoLabel = new Label(contenido);
        contenidoLabel.setWrapText(true);

        vboxImportancia.getChildren().add(importanciaLabel);
        vboxContenido.getChildren().addAll(tituloLabel, contenidoLabel);
        vboxPrincipal.getChildren().addAll(nombreLabel, vboxImportancia, vboxContenido);
        hboxSolicitud.getChildren().add(vboxPrincipal);

        if (AnchorPaneContenedorSolicitudes.getChildren().isEmpty()) {
            HBox nuevaFila = new HBox();
            nuevaFila.getChildren().add(hboxSolicitud);
            AnchorPaneContenedorSolicitudes.getChildren().add(nuevaFila);
        } else {
            HBox ultimaFila = (HBox) AnchorPaneContenedorSolicitudes.getChildren().get(
                    AnchorPaneContenedorSolicitudes.getChildren().size() - 1);

            if (ultimaFila.getChildren().size() < 2) {
                ultimaFila.getChildren().add(hboxSolicitud);
            } else {
                HBox nuevaFila = new HBox();
                nuevaFila.getChildren().add(hboxSolicitud);
                AnchorPaneContenedorSolicitudes.getChildren().add(nuevaFila);
            }
        }
    }



    public void cargarSolicitudesEjemplo() {
        limpiarSolicitudes();

        // Agregar solicitudes de ejemplo
        agregarSolicitud("Juan Pérez", "normal", "Problema con matemáticas",
                "Necesito ayuda con ecuaciones diferenciales para el examen final.");
        agregarSolicitud("Juan Pérez", "muy urgente", "Problema con matemáticas",
                "Necesito ayuda con ecuaciones diferenciales para el examen final.");
        agregarSolicitud("Juan Pérez", "urgente", "Problema con matemáticas",
                "Necesito ayuda con ecuaciones diferenciales para el examen final.");

    }


    public void limpiarSolicitudes() {
        AnchorPaneContenedorSolicitudes.getChildren().clear();
    }
}