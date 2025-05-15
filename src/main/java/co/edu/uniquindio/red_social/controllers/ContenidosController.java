package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ContenidosController {

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
    private Button botonBuscar;

    @FXML
    private Button buttonAniadir;

    @FXML
    private Button buttonEditar;

    @FXML
    private Button buttonEliminar;

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
    private ToggleButton gruposEstudioButton;

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
    private ScrollPane scrollPrincipal;

    @FXML
    private Label tusContenidosLabel;

    @FXML
    private VBox vBoxTodosContenidos;


    private static final ArbolBinario<Contenido> arbolContenidos = new ArbolBinario<>();

    public static ArbolBinario<Contenido> getArbol() {
        return arbolContenidos;
    }

    public void inicializarContenidos(List<Contenido> contenidos) {
        vBoxTodosContenidos.getChildren().clear(); // Limpia antes de agregar nuevos

        for (Contenido contenido : contenidos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
                VBox tarjeta = loader.load();
                TarjetaContenidoController controller = loader.getController();
                controller.setContenido(contenido);
                vBoxTodosContenidos.getChildren().add(tarjeta);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private  void  initialize() {
        misContenidosButton.setSelected(true);

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
        cargarContenidos();
    }
    private void cargarContenidos() {
        vBoxTodosContenidos.getChildren().clear();
        ListaSimplementeEnlazada<Contenido> lista = arbolContenidos.recorrerInorden();
        for (Contenido contenido : lista) {
            mostrarContenidoEnVista(contenido);
        }
        actualizarTotalPublicados();
    }

    public void agregarContenido(Contenido contenido) {
        System.out.println("Archivo adjunto antes de insertar: " + (contenido.getArchivoAdjunto() != null ? contenido.getArchivoAdjunto().getName() : "null"));
        arbolContenidos.insertar(contenido);
        System.out.println("Archivo adjunto después de insertar: " + (contenido.getArchivoAdjunto() != null ? contenido.getArchivoAdjunto().getName() : "null"));
        mostrarContenidoEnVista(contenido);
        actualizarTotalPublicados();
    }

    private void actualizarTotalPublicados() {
        int total = arbolContenidos.getPeso();
        System.out.println("Total contenidos: " + total);
        Platform.runLater(() -> {
            LabelTotalPublicados.setText(String.valueOf(total));
        });
    }


    private void mostrarContenidoEnVista(Contenido contenido) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
            VBox tarjeta = loader.load();

            TarjetaContenidoController controller = loader.getController();
            int total = arbolContenidos.getPeso();

            // Aquí tienes que llamar a setContenido para que la tarjeta se actualice con el contenido
            controller.setContenido(contenido, total);

            vBoxTodosContenidos.getChildren().add(tarjeta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void irAConfig(ActionEvent event) {
        try {
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/configuracion.fxml");
            System.out.println("URL Logo: " + configUrl);
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

    @FXML
    private void irAChat(ActionEvent event) {
        try {
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/mensajes.fxml");
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

    @FXML
    private void irAInicio(ActionEvent event) {
        try {
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Inicio.fxml");
            if (configUrl == null) {
                throw new IOException("Vista no encontrada");
            }

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

    @FXML
    private void irAEstadistica(ActionEvent event) {
        try {
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Estadisticas.fxml");
            if (configUrl == null) {
                throw new IOException("Vista no encontrada");
            }

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

    @FXML
    private void irAPublicar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Publicar.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            PublicarController controller = loader.getController();
            controller.setStage(popupStage);
            controller.setContenidosController(this);

            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Nuevo Contenido");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
