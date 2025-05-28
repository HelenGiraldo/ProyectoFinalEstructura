package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private static final ListaSimplementeEnlazada<Contenido> listaContenidos = new ListaSimplementeEnlazada<>();

    public static ArbolBinario<Contenido> getArbol() {
        return arbolContenidos;
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

        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }


        refrescarVistaSinDuplicados();
    }



    public void agregarContenido(Contenido contenido) {
        int idGenerado = UtilSQL.agregarPublicacion(contenido);
        if (idGenerado != -1) {
            contenido.setId(String.valueOf(idGenerado));

            arbolContenidos.insertar(contenido);
            listaContenidos.add(contenido);

            mostrarContenidoEnVista(contenido);
            actualizarTotalPublicados();

            RedSocial.getInstance().agregarPublicacion(contenido,
                    contenido.getGrupo() != null ? contenido.getGrupo().getId() : null);
        }
    }

    private void refrescarVistaSinDuplicados() {
        vBoxTodosContenidos.getChildren().clear();

        Estudiante usuarioActual = (Estudiante) PerfilUsuario.getUsuarioActual();
        if (usuarioActual == null) return;

        ListaSimplementeEnlazada<Contenido> contenidosUsuario = new ListaSimplementeEnlazada<>();
        ListaSimplementeEnlazada<Contenido> todosContenidos = RedSocial.getInstance()
                .getContenidos().recorrerInorden();

        for (Contenido c : todosContenidos) {
            if (c.getAutor().equals(usuarioActual)) {
                contenidosUsuario.add(c);
            }
        }

        arbolContenidos.clear();
        for (Contenido c : contenidosUsuario) {
            arbolContenidos.insertar(c);
        }

        for (Contenido contenido : contenidosUsuario) {
            mostrarContenidoEnVista(contenido);
        }

        actualizarTotalPublicados();
    }

    private void actualizarTotalPublicados() {
        int total = arbolContenidos.getPeso();
        System.out.println("Total contenidos: " + total);
        Platform.runLater(() -> {
            LabelTotalPublicados.setText(String.valueOf(total));
        });
    }



    public static ListaSimplementeEnlazada<Contenido> getListaContenidos() {
        return listaContenidos;
    }




    private void mostrarContenidoEnVista(Contenido contenido) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
            VBox tarjeta = loader.load();

            TarjetaContenidoController controller = loader.getController();
            int total = arbolContenidos.getPeso();

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
            Estudiante usuarioActual = (Estudiante) PerfilUsuario.getUsuarioActual();

            List<Grupo> gruposList = new ArrayList<>();
            for (int i = 0; i < usuarioActual.getGrupos().size(); i++) {
                gruposList.add(usuarioActual.getGrupos().get(i));
            }

            controller.setGruposUsuario(gruposList);

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

    @FXML
    private void irAGruposEstudio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/gruposEstudio.fxml"));
            Parent configView = loader.load();

            GruposDeEstudioController controller = loader.getController();

            Estudiante usuarioActual = (Estudiante) PerfilUsuario.getUsuarioActual();

            if (usuarioActual != null) {
                controller.setUsuarioActual(usuarioActual);
                System.out.println("Usuario enviado a GruposDeEstudioController: " + usuarioActual.getNombre());
            } else {
                System.out.println("Error: No hay usuario autenticado");
                mostrarAlerta("Error", "No hay usuario autenticado");
                return;
            }

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de grupos de estudio");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    @FXML
    private void irASugerencias(ActionEvent event) {
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
    private void irASolicitudesAyuda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Configuracion.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}