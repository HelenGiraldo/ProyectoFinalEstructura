package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static co.edu.uniquindio.red_social.data_base.UtilSQL.actualizarAdministrador;


public class ConfiguracionAdminController {

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private TextField textFieldAnterior;

    @FXML
    private HBox hboxContraseñaNueva;

    @FXML
    private TextField textFieldContraseñaNueva;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Button buttonCambiarImagen;

    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private HBox hBoxDerecha;

    @FXML
    private HBox hBoxIzquierda;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label labelAccountSettings;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelContraseña;

    @FXML
    private Label labelContraseñaAnterior;

    @FXML
    private Label labelContraseñaNueva;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelIntroduccion;

    @FXML
    private Label labelNombre;

    @FXML
    private Label labelUserInformation;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private ImageView profileImage;

    @FXML
    private TextField textFieldApellido;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private Label textFieldTitle;

    @FXML
    private AnchorPane root;

    @FXML
    public void initialize() {
        configuracionPerfilButton.setSelected(true);

        Administrador estudiante = (Administrador) PerfilUsuario.getUsuarioActual();
        PerfilUsuario perfil = PerfilUsuario.getInstancia();

        if (estudiante != null) {
            textFieldNombre.setText(estudiante.getNombre());
            textFieldApellido.setText(estudiante.getApellido());
            textFieldEmail.setText(estudiante.getEmail());

            String rutaImagen = estudiante.getImagenPerfil().getPath();

            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                File archivoImagen = new File(rutaImagen);

                System.out.println("Ruta guardada en estudiante: " + rutaImagen);
                System.out.println("Archivo existe: " + archivoImagen.exists());

                if (archivoImagen.exists()) {
                    Image imagen = new Image(archivoImagen.toURI().toString());
                    PerfilUsuario.getInstancia().setImagenPerfil(imagen);
                    profileImage.setImage(imagen);
                    estudiante.setImagenPerfil(archivoImagen);
                    System.out.println("Imagen cargada desde archivo: " + archivoImagen.getAbsolutePath());
                } else {
                    System.out.println("La imagen no existe en: " + archivoImagen.getAbsolutePath());
                }
            }

            // Listener para cambios en la imagen
            perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
                if (newImg != null) {
                    profileImage.setImage(newImg);
                    System.out.println("Imagen actualizada desde listener.");
                }
            });

            // Si ya está en memoria
            if (perfil.getImagenPerfil() != null) {
                profileImage.setImage(perfil.getImagenPerfil());
                System.out.println("Imagen cargada desde memoria.");
            }
        } else {
            System.out.println("No hay estudiante actual en el perfil.");
        }
    }



    @FXML
    private void guardar(ActionEvent event) {
        String nuevoNombre = textFieldNombre.getText();
        String nuevoApellido = textFieldApellido.getText();
        String nuevoEmail = textFieldEmail.getText();
        String nuevaContraseña = textFieldContraseñaNueva.getText();
        String contraseñaAnterior = textFieldAnterior.getText();

        Administrador administrador = (Administrador) PerfilUsuario.getInstancia().getUsuarioActual();

        if (!nuevaContraseña.isBlank()) {
            if (!administrador.getContrasena().equals(contraseñaAnterior)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Contraseña incorrecta");
                alert.setContentText("La contraseña anterior no coincide. No se realizaron cambios.");
                alert.showAndWait();
                return;
            }
            administrador.setContrasena(nuevaContraseña);
        }

        administrador.setNombre(nuevoNombre);
        administrador.setApellido(nuevoApellido);
        administrador.setEmail(nuevoEmail);

        // Procesar la imagen del ImageView
        Image imagenActual = profileImage.getImage();
        if (imagenActual != null) {
            try {
                URI uri = new URI(imagenActual.getUrl());
                File archivoOriginal = new File(uri);

                // Ruta de destino en resources
                String extension = archivoOriginal.getName().substring(archivoOriginal.getName().lastIndexOf("."));
                String nombreArchivoNuevo = "imagen_perfil" + administrador.getId() + extension;

                File destino = new File("src/main/resources/co/edu/uniquindio/red_social/usuarios/imagenes_perfil", nombreArchivoNuevo);

                // Crear carpeta si no existe
                if (!destino.getParentFile().exists()) {
                    destino.getParentFile().mkdirs();
                }

                // Copiar archivo
                java.nio.file.Files.copy(
                        archivoOriginal.toPath(),
                        destino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );


                administrador.setImagenPerfil(destino);


                Image imagenNueva = new Image(destino.toURI().toString());
                profileImage.setImage(imagenNueva);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error al guardar la imagen del perfil.");
            }
        }

        actualizarAdministrador(administrador);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Perfil actualizado");
        alert.setContentText("Tus datos han sido actualizados correctamente.");

        DialogPane dialogPane = alert.getDialogPane();
        URL cssUrl = getClass().getResource("/co/edu/uniquindio/red_social/alerta.css");
        if (cssUrl != null) {
            dialogPane.getStylesheets().add(cssUrl.toExternalForm());
        }
        dialogPane.getStyleClass().add("mi-alerta-personalizada");

        alert.showAndWait();

        System.out.println("Datos guardados correctamente.");
    }







    @FXML
    private void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            try {
                File carpetaDestino = new File("imagenesPerfil");
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdirs();
                }

                Estudiante estudiante = (Estudiante) PerfilUsuario.getInstancia().getUsuarioActual();
                String extension = archivoSeleccionado.getName().substring(archivoSeleccionado.getName().lastIndexOf("."));
                String nuevoNombreArchivo = estudiante.getId() + extension;
                File archivoDestino = new File(carpetaDestino, nuevoNombreArchivo);

                java.nio.file.Files.copy(
                        archivoSeleccionado.toPath(),
                        archivoDestino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                Image nuevaImagen = new Image(archivoDestino.toURI().toString());
                profileImage.setImage(nuevaImagen); // Solo actualiza la vista

                System.out.println("Imagen cargada: " + archivoDestino.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "No se pudo guardar la imagen.").showAndWait();
            }
        }
    }







    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Login.fxml"));
            Parent loginView = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void irALogo(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Logo.fxml");
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
    private void irAInicio(ActionEvent event) {
        try {
            // Obtener la URL de la vista a cargar
            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/InicioAdmin.fxml");
            if (configUrl == null) {
                throw new IOException("Vista no encontrada");
            }

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();

            // Asegúrate de que root no es null
            if (root != null) {
                root.getChildren().clear();  // Limpiar la vista actual
                root.getChildren().add(configView);  // Agregar la nueva vista
            } else {
                System.err.println("El contenedor principal es null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void irASolicitudesAyuda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/solicitudesAyudaAdmin.fxml"));
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