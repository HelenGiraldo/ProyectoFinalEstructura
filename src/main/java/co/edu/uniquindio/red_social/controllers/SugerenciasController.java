package co.edu.uniquindio.red_social.controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SugerenciasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox HboxSolicitud;

    @FXML
    private HBox HboxSolicitud1;

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
    private Button botonBuscar;

    @FXML
    private Button buttonEnviarSolicitud;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private ImageView imagePerfil;

    @FXML
    private ImageView imagePerfil2;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label interesesLabel;

    @FXML
    private Label interesesLabel2;

    @FXML
    private Label labelUsuario;

    @FXML
    private Label labelUsuario2;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private Label tusContenidosLabel;

    @FXML
    private VBox vBoxTodosContenidos;

    @FXML
    void irAChat(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/mensajes.fxml", event);
    }

    @FXML
    void irMisContenidos(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/TusContenidos.fxml", event);
    }

    @FXML
    void irAConfig(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/configuracion.fxml", event);
    }

    @FXML
    void irAGruposEstudio(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/gruposEstudio.fxml", event);
    }

    @FXML
    void irAInicio(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/Inicio.fxml", event);
    }

    @FXML
    void irASolicitudesAyuda(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/solicitudes.fxml", event);
    }

    @FXML
    void irASugerencias(ActionEvent event) {
    }

    @FXML
    private void onEnviar(){
        if(usuarioSeleccionado == null){
            System.out.println("No se ha seleccionado un usuario.");
            return;
        }else{
            usuario.enviarSolicitud(usuarioSeleccionado);
        }
    }
    @FXML
    void onBuscar(ActionEvent event) {
        String busqueda = campoBusqueda.getText().trim();
        limpiarTarjetas();
        ListaSimplementeEnlazada<Estudiante> estudiantes = red.getEstudiantes();
        if(estudiantes == null){
            return;
        }
        for (Estudiante estudiante : estudiantes) {
            if (!estudiante.getNombre().contains(busqueda) || !estudiante.getApellido().contains(busqueda) || !estudiante.getId().contains(busqueda)) {
                continue;
            }
            String rutaOriginal = estudiante.getRutaImagenPerfil();
            String rutaLimpia = procesarRutaImagen(rutaOriginal);
            Image imagen = cargarImagen(rutaLimpia);
            String preferencias ="";
            if(estudiante.getPreferencias().isEmpty()){
                preferencias = "Actividades en común";
            }else{
                for(String preferencia : estudiante.getPreferencias()){
                    preferencias += preferencia + ", ";
                }
            }
            agregarUsuarioATarjetas(estudiante.getNombre(), new ImageView(imagen), preferencias, estudiante.getId() );
        }

    }

    @FXML
    void onIntereses(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/intereses.fxml", event);

    }

    RedSocial red = RedSocial.getInstance();
    Estudiante usuario = (Estudiante) PerfilUsuario.getUsuarioActual();

    @FXML
    private AnchorPane anchorPaneContenedor;
    private Estudiante usuarioSeleccionado;

    @FXML
    void initialize() {
        agregarEstudiantes();
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


    private void agregarEstudiantes(){
        limpiarTarjetas();
        ListaSimplementeEnlazada<Estudiante> estudiantes = red.crearSugerencias(usuario);
        if(estudiantes == null){
            return;
        }
        for (Estudiante estudiante : estudiantes) {
            if(estudiante.yaSolicitado(usuario)){
                continue;
            }
            String rutaOriginal = estudiante.getRutaImagenPerfil();
            String rutaLimpia = procesarRutaImagen(rutaOriginal);
            Image imagen = cargarImagen(rutaLimpia);
            String preferencias ="";
            if(estudiante.getPreferencias().isEmpty()){
                preferencias = "Actividades en común";
            }else{
                for(String preferencia : estudiante.getPreferencias()){
                    preferencias += preferencia + ", ";
                }
            }
            agregarUsuarioATarjetas(estudiante.getNombre(), new ImageView(imagen), preferencias, estudiante.getId() );
        }

    }

    private String procesarRutaImagen(String rutaOriginal) {
        if (rutaOriginal == null || rutaOriginal.isEmpty()) {
            return "/imagenes/default.png"; // Ruta por defecto
        }

        return rutaOriginal
                .replace("src\\main\\resources", "")
                .replace("src/main/resources", "")
                .replace("\\", "/")
                .replaceAll("^/+", "/"); // Asegurar que comience con un solo /
    }

    private Image cargarImagen(String ruta) {
        try {
            return new Image(getClass().getResourceAsStream(ruta));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + ruta);
            return null;
        }
    }

    public void agregarUsuarioATarjetas(String nombre, ImageView imagenPerfil, String intereses, String id) {
        try {
            // Crear la tarjeta
            HBox tarjetaUsuario = crearTarjetaUsuario(nombre, imagenPerfil, intereses, id);

            // Obtener o crear el contenedor
            FlowPane contenedor = obtenerContenedor();

            // Añadir la tarjeta al contenedor
            contenedor.getChildren().add(tarjetaUsuario);

        } catch (Exception e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    private HBox crearTarjetaUsuario(String nombre, ImageView imagenPerfil, String intereses, String id) {
        System.out.println("Creando tarjeta para el usuario: " + nombre);
        HBox tarjetaUsuario = new HBox();
        tarjetaUsuario.setPrefSize(141, 100);
        tarjetaUsuario.setPadding(new Insets(10));
        tarjetaUsuario.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 8; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        tarjetaUsuario.setOnMouseEntered(e -> tarjetaUsuario.setStyle("-fx-background-color: #e8e8e8;"));
        tarjetaUsuario.setOnMouseExited(e -> tarjetaUsuario.setStyle("-fx-background-color: #f5f5f5;"));

        // Configurar el evento de clic
        tarjetaUsuario.setOnMouseClicked(event -> {
            usuarioSeleccionado = red.obtenerEstudiantePorId(id);
            System.out.println("Usuario seleccionado: " + usuarioSeleccionado);
            tarjetaUsuario.setStyle("-fx-background-color: #d0d0d0;");
        });

        // Configurar imagen
        configurarImagenPerfil(imagenPerfil);

        // Crear contenido
        VBox contenido = new VBox(5, imagenPerfil, crearLabelNombre(nombre), crearLabelIntereses(intereses));
        contenido.setAlignment(Pos.CENTER);
        tarjetaUsuario.getChildren().add(contenido);

        return tarjetaUsuario;
    }

    private void configurarImagenPerfil(ImageView imagenPerfil) {
        imagenPerfil.setFitHeight(40);
        imagenPerfil.setFitWidth(40);
        imagenPerfil.setPreserveRatio(true);
    }

    private Label crearLabelNombre(String nombre) {
        Label label = new Label(nombre);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }

    private Label crearLabelIntereses(String intereses) {
        Label label = new Label(intereses);
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 12px;");
        label.setMaxWidth(120);
        return label;
    }

    private FlowPane obtenerContenedor() {
        if (scrollPaneContenedorContenidos.getContent() instanceof FlowPane) {
            return (FlowPane) scrollPaneContenedorContenidos.getContent();
        } else {
            FlowPane nuevoContenedor = new FlowPane();
            nuevoContenedor.setPadding(new Insets(10));
            nuevoContenedor.setHgap(15);
            nuevoContenedor.setVgap(15);
            scrollPaneContenedorContenidos.setContent(nuevoContenedor);
            return nuevoContenedor;
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
    private void limpiarTarjetas() {
        FlowPane contenedor = obtenerContenedor();
        contenedor.getChildren().clear();
    }

    public void irARecibirSolicitudes(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/RecibirSolicitudes.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


