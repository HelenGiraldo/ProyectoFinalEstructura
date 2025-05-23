package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.interfaces.ChatObserver;
import co.edu.uniquindio.red_social.clases.social.Chat;
import co.edu.uniquindio.red_social.clases.social.Mensaje;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable , ChatObserver {

    // Elementos FXML
    @FXML
    private ImageView AvatarCompañero;

    @FXML
    private ImageView AvatarCompañero1;

    @FXML
    private ImageView AvatarCompañero2chat;

    @FXML
    private HBox HboxAvatarNombre;

    @FXML
    private HBox HboxAvatarNombre1;

    @FXML
    private HBox HboxAvatarNombre2;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelUser2;

    @FXML
    private Label LabelUser2chat;

    @FXML
    private Label LabelUser3chat;

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
    private Button botonBuscar;

    @FXML
    private ToggleButton buttonUser2;

    @FXML
    private ToggleButton buttonUser3;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatListVBoxFondoUno;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private VBox contenedorMensajes;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private TextField messageTextField;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private ScrollPane scrollPaneChats;

    @FXML
    private ScrollPane scrollPaneChatsEnviadosRecibidos;

    @FXML
    private ScrollPane scrollPaneContenedorMensajes;

    @FXML
    private Button sendButton;

    @FXML
    private VBox userchatListVBox;

    @FXML
    private AnchorPane root;

    ToggleGroup grupoUsuarios = new ToggleGroup();

    Estudiante estudianteActual = (Estudiante) PerfilUsuario.getUsuarioActual();
    Chat chatActual;
    Estudiante remitenteChat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Selecciona el botón de Mensajes como activo
        MensajesButton.setSelected(true);

        // Aplicar clip circular a la imagen de perfil (avatar)
        Platform.runLater(() -> {
            if (imagenPerfil != null) {
                double radius = imagenPerfil.getFitWidth() / 2;
                Circle clip = new Circle(radius, radius, radius);
                imagenPerfil.setClip(clip);
            }
        });

        // Inicializar lista de usuarios para chatear (sidebar)
        inicializarUsuarios();

        // Configurar el área del chat (contenedor de mensajes, etc)
        configurarChat();

        // Configurar otros eventos como botones, envío de mensajes
        configurarEventos();

        // Ajustes del scroll pane para los mensajes
        scrollPaneContenedorMensajes.setFitToWidth(true);
        scrollPaneContenedorMensajes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Para que el VBox de mensajes ocupe todo el ancho disponible
        contenedorMensajes.setFillWidth(true);

        // Auto-scroll al final cuando se agregan mensajes
        contenedorMensajes.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPaneContenedorMensajes.layout();
            scrollPaneContenedorMensajes.setVvalue(1.0);
        });

        // Obtener la instancia singleton del perfil de usuario
        PerfilUsuario perfil = PerfilUsuario.getInstancia();

        // Listener para actualizar la imagen cuando cambie en el singleton
        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                imagenPerfil.setImage(newImg);
            }
        });

        // Inicializar imagen al cargar la vista si ya hay una imagen cargada
        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }

        estudianteActual.getChats().show();
        System.out.println(chatActual);
        cagarMensajesChat(chatActual);

    }

    private void inicializarUsuarios() {
        userchatListVBox.getChildren().clear();

        for (Estudiante usuario : estudianteActual.getContactos()) {
            if (usuario.getId().equals(estudianteActual.getId())) continue;

            // Crear ToggleButton
            ToggleButton boton = new ToggleButton();
            boton.setPrefWidth(112);
            boton.setPrefHeight(42);
            boton.setToggleGroup(grupoUsuarios);
            boton.setUserData(usuario);
            boton.getStyleClass().add("sidebar-button-chat");

            // Crear ImageView
            ImageView avatar = new ImageView(new Image(getClass().getResource("/co/edu/uniquindio/red_social/imagenes/imagePerfil.png").toExternalForm()));
            avatar.setFitWidth(20);
            avatar.setFitHeight(19);
            avatar.getStyleClass().add("avatar");

            // Crear Label con nombre del usuario
            Label nombre = new Label(usuario.getNombre());
            nombre.setPrefHeight(19);
            nombre.setPrefWidth(70); // o ajustable
            nombre.getStyleClass().add("contact-name"); // Negrilla definida en CSS

            // Agrupar imagen + texto en un HBox
            HBox hbox = new HBox(8, avatar, nombre);
            hbox.setAlignment(Pos.CENTER_LEFT);

            // Asignar el HBox como gráfico del botón
            boton.setGraphic(hbox);

            // Asignar evento
            boton.setOnAction(e -> {
                if (boton.isSelected()) {
                    seleccionarUsuario(boton);
                }
            });

            userchatListVBox.getChildren().add(boton);
        }

        // Selección inicial automática
        if (!userchatListVBox.getChildren().isEmpty()) {
            ToggleButton first = (ToggleButton) userchatListVBox.getChildren().get(0);
            first.setSelected(true);
            seleccionarUsuario(first);
        }
    }




    private void configurarBotonUsuario(ToggleButton boton, Estudiante estudiante) {
        if (boton == null || estudiante == null) return;

        boton.setText(estudiante.getNombre() + " ");
        boton.setUserData(estudiante);

        boton.setToggleGroup(grupoUsuarios);

        boton.setOnAction(e -> {
            if (boton.isSelected()) {
                seleccionarUsuario(boton);
            }
        });

    }

    private void configurarChat() {
        contenedorMensajes.setSpacing(10);
        contenedorMensajes.setPadding(new Insets(10));
        scrollPaneContenedorMensajes.setFitToWidth(true);

    }

    private void configurarEventos() {
        if (messageTextField == null || sendButton == null || botonBuscar == null) {
            System.err.println("Error: Elementos para eventos no inicializados");
            return;
        }
    }

    private void seleccionarUsuario(ToggleButton botonUsuario) {
        if (botonUsuario == null || userchatListVBox == null) return;


        // Deseleccionar los demás botones
        userchatListVBox.getChildren().forEach(node -> {
            if (node instanceof ToggleButton) {
                ToggleButton tb = (ToggleButton) node;
                if (tb != botonUsuario) {
                    tb.setSelected(false);
                }
            }
        });

        Estudiante receptor = (Estudiante) botonUsuario.getUserData();
        if (receptor == null) return;

        // Buscar o crear el chat
        chatActual = obtenerOCrearChat(receptor);

        // 2. Registrar este controller como observer del nuevo chat
        if (chatActual != null) {
            chatActual.addObserver(this);
            chatObservadoActual = chatActual;
        }
        chatActual.getMensajes().show();

        // Actualizar interfaz
        actualizarInterfazUsuario(receptor);
        cargarMensajes(receptor);
        cagarMensajesChat(chatActual);
    }

    private Chat obtenerOCrearChat(Estudiante receptor) {
        // Buscar chat existente
        for (Chat chat : estudianteActual.getChats()) {
            if (chat.contieneUsuarios(estudianteActual, receptor)) {
                return chat;
            }
        }

        // Si no existe, crear uno nuevo
        Chat nuevoChat = new Chat(estudianteActual, receptor);
        estudianteActual.getChats().add(nuevoChat);
        return nuevoChat;
    }


    private void actualizarInterfazUsuario(Estudiante receptor) {
        if (LabelUser2 == null || AvatarCompañero == null || contenedorMensajes == null) {
            System.err.println("Error: Elementos de interfaz no inicializados");
            return;
        }

        LabelUser2.setText(receptor.getNombre() + " ");

        try {
            AvatarCompañero.setImage(new Image("/images/default-avatar.png"));
        } catch (Exception e) {
            System.err.println("Error cargando imagen de avatar: " + e.getMessage());
        }

        contenedorMensajes.getChildren().clear();

        Platform.runLater(() -> scrollPaneContenedorMensajes.setVvalue(0.0));
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {
        if (messageTextField == null || contenedorMensajes == null) return;

        String texto = messageTextField.getText().trim();
        if (!texto.isEmpty()) {
            agregarMensaje(texto, true);

            Platform.runLater(() -> {
                messageTextField.clear();
                messageTextField.requestFocus();
                scrollPaneContenedorMensajes.setVvalue(1.0);
            });
        }

        chatActual.enviarMensaje(new Mensaje(texto, LocalDateTime.now(), estudianteActual, chatActual) );

    }

    private void agregarMensaje(String texto, boolean esPropio) {
        if (contenedorMensajes == null) return;

        Label mensaje = new Label(texto);
        mensaje.getStyleClass().add(esPropio ? "message-bubble-user" : "message-bubble");
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(300);
        mensaje.setPadding(new Insets(8, 12, 8, 12));

        HBox contenedor = new HBox(mensaje);
        contenedor.setPadding(new Insets(5));
        contenedor.setAlignment(esPropio ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        contenedorMensajes.getChildren().add(contenedor);

    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
        if (campoBusqueda == null || userchatListVBox == null) return;

        String busqueda = campoBusqueda.getText().trim();
        if (!busqueda.isEmpty()) {
            campoBusqueda.clear();
        }
    }




    private void cargarMensajes(Estudiante receptor) {
        contenedorMensajes.getChildren().clear();

        Estudiante emisor = (Estudiante) PerfilUsuario.getInstancia().getUsuarioActual();
        if (emisor == null || receptor == null) return;

        // Buscar el chat entre el emisor y receptor
        for (Chat chat : estudianteActual.getChats()) {
            if ( (chat.getEstudiante().equals(emisor) && chat.getEstudiante2().equals(receptor)) ||
                    (chat.getEstudiante().equals(receptor) && chat.getEstudiante2().equals(emisor)) ) {
                chatActual = chat;
                break;
            }
        }

        if (chatActual == null) return;

        ListaSimplementeEnlazada<Mensaje> mensajes = chatActual.getMensajes();

        for (Mensaje mensaje : mensajes) {
            boolean esPropio = mensaje.getUsuarioRemitente().equals(emisor.getId());
            agregarMensaje(mensaje.getMensaje(), esPropio);
        }
    }


    private void cagarMensajesChat(Chat chat){
        chat.getMensajes().show();
        for(Mensaje mensaje: chat.getMensajes()){
            agregarMensaje(mensaje.getMensaje(), mensaje.getUsuarioRemitente().equals(estudianteActual));
        }
    }



    @FXML
    private void irAConfig(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/configuracion.fxml");
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

    private Chat chatObservadoActual = null;

    @Override
    public void update() {
        Platform.runLater(() -> {
            if (chatActual != null && chatObservadoActual != null && chatActual.equals(chatObservadoActual)) {
                cargarMensajes(chatActual.getOtroUsuario(estudianteActual));
            }
        });
    }

    @FXML
    private void irAContenidos(ActionEvent event) {
        // Lógica para ir a contenidos
    }

    @FXML
    private void irAGruposEstudio(ActionEvent event) {
        // Lógica para ir a contenidos
    }

    @FXML
    private void irASugerencias(ActionEvent event) {
        // Lógica para ir a contenidos
    }

    @FXML
    private void irASolicitudesAyuda(ActionEvent event) {
        // Lógica para ir a contenidos
    }
}
