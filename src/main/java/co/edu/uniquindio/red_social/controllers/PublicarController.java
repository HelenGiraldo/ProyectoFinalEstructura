package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class PublicarController {

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Button buttonAniadir;

    @FXML
    private Button buttonEnlace;

    @FXML
    private Button buttonPublicar;

    @FXML
    private TextField descripcionField;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label enlaceLabel;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label publicadoConExitoLabel;

    @FXML
    private Label publicarNuevoContenidoLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TextField temaField;

    @FXML
    private Label temaLabel;

    @FXML
    private TextField tipoField;

    @FXML
    private Label tipoLabel;

    @FXML
    private TextField tituloField;

    @FXML
    private Label tituloLabel;

    @FXML
    private Label archivoAdjuntoLabel;

    private Stage stage;
    private File archivoAdjunto;
    private File archivoAdjuntoSeleccionado;



    public void setStage(Stage stage) {
        this.stage = stage;

    }

    private ContenidosController contenidosController;



    public void setContenidosController(ContenidosController contenidosController) {
        this.contenidosController = contenidosController;
    }



    @FXML
    private void initialize() {
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

        // Mostrar imagen actual si ya existe
        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }
    }


    @FXML
    private void handlePublicar() {


        String titulo = tituloField.getText().trim();
        String tema = temaField.getText().trim();
        String tipo = tipoField.getText().trim();
        String descripcion = descripcionField.getText().trim();

        if (titulo.isEmpty() || tema.isEmpty() || tipo.isEmpty() || descripcion.isEmpty()) {
            publicadoConExitoLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        if (titulo.length() < 4 || descripcion.length() < 10) {
            publicadoConExitoLabel.setText("El título debe tener al menos 4 caracteres y la descripción 10.");
            return;
        }

        Contenido nuevoContenido = new Contenido(tipo, titulo, tema, descripcion, (Estudiante) PerfilUsuario.getUsuarioActual(), archivoAdjunto, null);
        nuevoContenido.setContenido(archivoAdjuntoSeleccionado);

        if (contenidosController != null) {
            contenidosController.agregarContenido(nuevoContenido);
        }

        publicadoConExitoLabel.setText("Su contenido fue publicado con éxito");

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            if (stage != null) {
                stage.close();
            }
        });

        pause.play();

        System.out.println("Contenido agregado: " + nuevoContenido.getTitulo());
        System.out.println("Tamaño actual del árbol: " + ContenidosController.getArbol().recorrerInorden().size());

    }

    @FXML
    private void handleAdjuntarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo adjunto");
        File archivo = fileChooser.showOpenDialog(stage);  // Mejor pasar el stage, no null

        System.out.println("Archivo seleccionado: " + (archivo != null ? archivo.getAbsolutePath() : "ninguno"));

        if (archivo != null) {
            archivoAdjuntoSeleccionado = archivo;
            System.out.println("Archivo asignado a variable: " + archivoAdjuntoSeleccionado.getName());

            // Actualizar texto label
            enlaceLabel.setText("Archivo adjunto: " + archivo.getName());
        } else {
            System.out.println("No se seleccionó archivo");
        }
    }





}
