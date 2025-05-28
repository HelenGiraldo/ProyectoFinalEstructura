package co.edu.uniquindio.red_social.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.SolicitudAyuda;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.util.SolicitudActual;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SolicitudesAyudaAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox AnchorPaneContenedorSolicitudes;

    @FXML
    private Label ContenidoSolicitudLabel;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Label adminLabel;

    @FXML
    private Button botonBuscar;

    @FXML
    private Button botonBuscar1;

    @FXML
    private HBox botonResolver;

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
    private Label nombreEstudiante2Label;

    @FXML
    private Label nombreEstudianteLabel;

    @FXML
    private Button resolverButton;

    private SolicitudAyuda solicitudSeleccionada;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label solicitudesAyudaLabel;

    @FXML
    private Label tituloLabel;

    @FXML
    private Label tituloLabel2;





    @FXML
    private void initialize() {

        SolicitudesDeAyudaButton.setSelected(true);
        System.out.println(redSocial.getSolicitudesAyuda().size());
        redSocial.getSolicitudesAyuda().show();
        cargarComboBox();
        cargarSolicitudes();
        chambaImagen();
    }

    private void chambaImagen(){
        Platform.runLater(() -> {
            if (imagenPerfil != null) {
                double radius = imagenPerfil.getFitWidth() / 2;
                Circle clip = new Circle(radius, radius, radius);
                imagenPerfil.setClip(clip);
            }
        });

        PerfilUsuario perfil = PerfilUsuario.getInstancia();


        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                imagenPerfil.setImage(newImg);
            }
        });

        System.out.println("Imagen de perfil: " + perfil.getImagenPerfil());

        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }
        File file = PerfilUsuario.getUsuarioActual().getImagenPerfil();
        Image imagen = new Image(file.toURI().toString());
        imagenPerfil.setImage(imagen);
    }


    @FXML
    void onBuscar(ActionEvent event) {
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
            String id = solicitudAyuda.getId();
            agregarSolicitud(nombreEstudiante, importancia, titulo, contenido,id);   }
    }

    @FXML
    void onBuscarFiltro(ActionEvent event) {


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
            String id = solicitudAyuda.getId();
            agregarSolicitud(nombreEstudiante, importancia, titulo, contenido,id);
        }
    }

    @FXML
    void onResolver(ActionEvent event) {
        if(solicitudSeleccionada == null){
            adminLabel.setText("Ninguna solicitud seleccionada");
        }else{
            SolicitudActual.getInstance().setSolicitudAyuda(solicitudSeleccionada);
            navegar("/co/edu/uniquindio/red_social/resolverSolicitud.fxml", event);
        }

    }

    RedSocial redSocial = RedSocial.getInstance();

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





    private void cargarComboBox() {
        comboFiltro.getItems().addAll("Muy urgente", "Urgente", "Normal");
        comboFiltro.setValue("Normal");

    }

    public void cargarSolicitudes() {
        limpiarSolicitudes();
        for(SolicitudAyuda solicitudAyuda: redSocial.getSolicitudesAyuda()){
            String nombreEstudiante = solicitudAyuda.getEstudiante().getNombre();
            String importancia = solicitudAyuda.getPrioridad();
            String titulo = solicitudAyuda.getTitulo();
            String contenido = solicitudAyuda.getMensaje();
            String id = solicitudAyuda.getId();
            agregarSolicitud(nombreEstudiante, importancia, titulo, contenido, id);
        }
    }

    public void agregarSolicitud(String nombreEstudiante, String importancia, String titulo, String contenido, String id) {
        HBox hboxSolicitud = new HBox();
        hboxSolicitud.setPrefSize(173, 100);
        HBox.setMargin(hboxSolicitud, new Insets(0, 20, 20, 0));

        hboxSolicitud.setOnMouseClicked(event -> {
            System.out.println("Seleccionada " + titulo );
            for(SolicitudAyuda solicitud : redSocial.getSolicitudesAyuda()) {
                System.out.println(id);
                if(solicitud.getId().equals(id)) {
                    solicitudSeleccionada = solicitud;
                    break;
                }
            }

            resetearEstilosSeleccion();
            hboxSolicitud.getStyleClass().add("solicitud-seleccionada");

            if(hBoxDerecha != null) {
                nombreEstudianteLabel.setText(nombreEstudiante);
                importanciaLabel.setText(importancia);
                tituloLabel.setText(titulo);
                ContenidoSolicitudLabel.setText(contenido);
            }
        });
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

    private void resetearEstilosSeleccion() {
        for(Node node : AnchorPaneContenedorSolicitudes.getChildren()) {
            if(node instanceof HBox) {
                for(Node child : ((HBox)node).getChildren()) {
                    if(child instanceof HBox) {
                        child.getStyleClass().remove("solicitud-seleccionada");
                    }
                }
            }
        }
    }



    public void limpiarSolicitudes() {
        AnchorPaneContenedorSolicitudes.getChildren().clear();
    }

    @FXML
    private void irAGruposEstudio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/GruposDeEstudioAdmin.fxml"));
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
    private void irAConfig(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/configuracionAdmin.fxml"));
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
    private void irAInicio(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/InicioAdmin.fxml");
            System.out.println("URL config: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();



            if (root != null) {
                root.getChildren().clear();
                root.getChildren().add(configView);
            } else {
                System.err.println("El contenedor principal es null.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
