package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class TarjetaContenidoController {


    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private Label autorLabel;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label temaLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Label tituloLabel;

    @FXML
    private Label totalPublicadosLabel;

    @FXML
    private Label valoracionLabel;

    @FXML
    private Label archivoAdjuntoLabel;

    @FXML
    private Label grupoLabel;





    public void setContenido(Contenido contenido) {
        tituloLabel.setText(contenido.getTitulo());
        autorLabel.setText(contenido.getAutor().getNombre() + " " + contenido.getAutor().getApellido());
        descripcionLabel.setText(contenido.getDescripcion());
        tipoLabel.setText(contenido.getTipoContenido());
        valoracionLabel.setText("★ " + contenido.getCalificacionPromedio());

        File archivo = contenido.getContenido();

        if (archivo != null && archivo.exists()) {
            archivoAdjuntoLabel.setText("Archivo: " + archivo.getName());

            archivoAdjuntoLabel.setOnMouseClicked(event -> {
                try {
                    Desktop.getDesktop().open(archivo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            archivoAdjuntoLabel.setText("No hay archivo adjunto");
            archivoAdjuntoLabel.setOnMouseClicked(null);
        }

        if (contenido.getGrupo() != null) {
            grupoLabel.setText(contenido.getGrupo().getNombre());
        } else {
            grupoLabel.setText("Sin grupo");
        }


    }

    private void abrirArchivoAdjunto(File archivo) {
        if (archivo != null && archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
            } catch (IOException e) {
                e.printStackTrace();
                // Puedes mostrar un diálogo de error aquí si quieres
            }
        } else {
            System.out.println("Archivo no existe o es nulo");
        }
    }

    public void setContenido(Contenido contenido, int totalPublicados) {
        System.out.println("DEBUG - Contenido: " + contenido.getTitulo());
        System.out.println("DEBUG - Grupo: " + (contenido.getGrupo() != null ? contenido.getGrupo().getNombre() : "NULL"));

        tituloLabel.setText(contenido.getTitulo());
        autorLabel.setText(contenido.getAutor().getNombre() + " " + contenido.getAutor().getApellido());
        descripcionLabel.setText(contenido.getDescripcion());
        tipoLabel.setText(contenido.getTipoContenido());
        valoracionLabel.setText("★ " + contenido.getCalificacionPromedio());
        totalPublicadosLabel.setText("Total Publicados: " + totalPublicados);

        if (contenido.getContenido() != null) {
            archivoAdjuntoLabel.setText(contenido.getContenido().getName());

            archivoAdjuntoLabel.setOnMouseClicked(event -> {
                abrirArchivoAdjunto(contenido.getContenido());
            });
        } else {
            archivoAdjuntoLabel.setText("Sin archivo adjunto");
            archivoAdjuntoLabel.setOnMouseClicked(null); // Quitar listener si no hay archivo
        }

        if (contenido.getGrupo() != null) {
            grupoLabel.setText(contenido.getGrupo().getNombre());
        } else {
            grupoLabel.setText(""); // Cadena vacía para que no muestre nada
        }
    }



    }

