
package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static co.edu.uniquindio.red_social.util.EstudianteActual.usuarioActual;

public class InicioController {

    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelBienvendioAThinkTogether;

    @FXML
    private Label LabelCantidadIntegrantes;

    @FXML
    private Label LabelExploraContenidos;

    @FXML
    private Label LabelGrupoSugerido;

    @FXML
    private Label LabelGruposDeEstudiosSugeridos;

    @FXML
    private Label LabelGruposEncontradosParaTi;

    @FXML
    private Label LabelTipo;

    @FXML
    private Label LabelTotalPublicados;

    @FXML
    private Label LabelTusContenidos;

    @FXML
    private Label LabelTusContenidosContenido;

    @FXML
    private Label LabelUltimoContenido;

    @FXML
    private Label LabelUltimoContenidoTexto;

    @FXML
    private Label LabelValoracion;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldSaludo;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxGruposEstudio;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private VBox VistaInicio;

    @FXML
    private Button botonBuscar;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private GridPane hBoxizquierda;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private ScrollPane scrollPrincipal;
    @FXML
    private AnchorPane root;

    private Estudiante usuario;

    private RedSocial redSocial = RedSocial.getInstance();

    public void setUsuarioActual(Estudiante usuario) {
        this.usuario = usuario;
    }


    @FXML
    public void initialize() {
        InicioButton.setSelected(true);


        chambaImagen();

        actualizarSaludo();
        actualizarUltimoContenidoGeneral();
        actualizarUltimoContenidoUsuario();
        actualizarGruposSugeridos();

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

    private void actualizarUltimoContenidoGeneral() {
        Contenido ultimoContenido = redSocial.obtenerUltimoContenido();
        if (ultimoContenido != null) {
            LabelUltimoContenido.setText(ultimoContenido.getTitulo());
        } else {
            LabelUltimoContenido.setText("No hay contenidos recientes");
        }
    }

    private void actualizarUltimoContenidoUsuario() {
        try {
            // Verificar que el usuario esté inicializado
            if (usuario == null) {
                usuario = (Estudiante) PerfilUsuario.getUsuarioActual();
                if (usuario == null) {
                    System.out.println("Usuario no autenticado");
                    mostrarContenidoVacio();
                    return;
                }
            }

            // Obtener el último contenido
            Contenido ultimo = redSocial.obtenerUltimoContenidoDeUsuario(usuario.getId());

            // Actualizar la UI en el hilo de JavaFX
            Platform.runLater(() -> {
                if (ultimo != null) {
                    LabelTusContenidosContenido.setText(ultimo.getTitulo());
                    LabelTipo.setText(ultimo.getTipoContenido());

                    // Formatear la calificación a 1 decimal
                    String calificacion = String.format("★ %.1f", ultimo.getCalificacionPromedio());
                    LabelValoracion.setText(calificacion);

                    // Actualizar contador de publicaciones
                    int totalContenidos = redSocial.contarContenidosDeUsuario(usuario.getId());
                    LabelTotalPublicados.setText(totalContenidos + " Publicados");
                } else {
                    mostrarContenidoVacio();
                }
            });

        } catch (Exception e) {
            System.err.println("Error al actualizar último contenido: " + e.getMessage());
            Platform.runLater(this::mostrarContenidoVacio);
        }
    }

    private void mostrarContenidoVacio() {
        LabelTusContenidosContenido.setText("No has publicado contenidos");
        LabelTipo.setText("");
        LabelValoracion.setText("★ 0.0");
        LabelTotalPublicados.setText("0 Publicados");
    }

    private void actualizarGruposSugeridos() {
        try {
            // Verificar y obtener el usuario actual
            Usuario usuarioActual = PerfilUsuario.getUsuarioActual();
            if (!(usuarioActual instanceof Estudiante)) {
                System.out.println("El usuario actual no es un Estudiante");
                mostrarMensajeNoGrupos();
                return;
            }

            Estudiante estudiante = (Estudiante) usuarioActual;
            ListaSimplementeEnlazada<Grupo> sugeridos = redSocial.obtenerGruposSugeridos(estudiante.getId());

            if (!sugeridos.isEmpty()) {
                Grupo primero = sugeridos.get(0);
                Platform.runLater(() -> {
                    LabelGrupoSugerido.setText(primero.getNombre());
                    LabelCantidadIntegrantes.setText(primero.getMiembros().size() + " integrantes");
                    LabelGruposEncontradosParaTi.setText("Se han encontrado " + sugeridos.size() + " grupos para ti");
                });
            } else {
                mostrarMensajeNoGrupos();
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar grupos sugeridos: " + e.getMessage());
            mostrarMensajeNoGrupos();
        }
    }

    private void mostrarMensajeNoGrupos() {
        Platform.runLater(() -> {
            LabelGrupoSugerido.setText("No hay grupos sugeridos");
            LabelCantidadIntegrantes.setText("0 integrantes");
            LabelGruposEncontradosParaTi.setText("No se encontraron grupos");
        });
    }



    @FXML
    public void inicializarUsuario(Estudiante usuario) {
        this.usuario = usuario;


        // Actualizar saludo o información en la vista
        actualizarSaludo();
    }






    public void actualizarSaludo() {
        Usuario usuario = PerfilUsuario.getUsuarioActual();
        if (usuario != null) {
            TextFieldSaludo.setText("¡Hola, " + usuario.getNombre() + "!");
        } else {
            System.out.println("No hay usuario actual en PerfilUsuario");
            TextFieldSaludo.setText("¡Hola!");
        }
    }



    public void actualizarGrupoEstudio(String nombreGrupo, String integrantes) {
        LabelGrupoSugerido.setText(nombreGrupo);
        LabelCantidadIntegrantes.setText(integrantes + " integrantes");
    }

    public void actualizarContenido(String tipo, String valoracion) {
        LabelTipo.setText(tipo);
        LabelValoracion.setText("★ " + valoracion);
    }

    @FXML
    private void irAChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/mensajes.fxml"));
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
    private void irAGruposEstudio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/gruposEstudio.fxml"));
            Parent configView = loader.load();

            GruposDeEstudioController controller = loader.getController();

            // PASAR el usuario (que tienes en 'usuario' o usa PerfilUsuario.getUsuarioActual())
            if (usuario != null) {
                controller.setUsuarioActual(usuario);
                System.out.println("Usuario enviado a GruposDeEstudioController: " + usuario.getNombre());
            } else {
                System.out.println("usuario en InicioController es null");
            }

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void irAConfig(ActionEvent event) {
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

    @FXML
    private void irASolicitudesAyuda(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/solicitudes.fxml");
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
    private void irAContenidos(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/TusContenidos.fxml");
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


}
