package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.controllers.TarjetaContenidoController;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class GrupoContenidoViewUtil {

    public static void mostrarContenidosGrupo(ScrollPane scrollPane, String nombreGrupo, ListaSimplementeEnlazada<Contenido> contenidos) {
        if (contenidos == null || scrollPane == null) {
            return;
        }

        VBox contenidosVBox = new VBox(10);
        contenidosVBox.setPadding(new Insets(10));

        if (contenidos.isEmpty()) {
            contenidosVBox.getChildren().add(new Label("Este grupo no tiene contenidos aún"));
        } else {
            // Agrupar por tema (usando Map para mantener el orden)
            Map<String, ListaSimplementeEnlazada<Contenido>> contenidosPorTema = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

            // Recorrer la lista enlazada y agrupar por tema
            for (Contenido contenido : contenidos) {
                String tema = contenido.getTema();
                if (!contenidosPorTema.containsKey(tema)) {
                    contenidosPorTema.put(tema, new ListaSimplementeEnlazada<>());
                }
                contenidosPorTema.get(tema).add(contenido);
            }

            // Mostrar en UI
            for (Map.Entry<String, ListaSimplementeEnlazada<Contenido>> entry : contenidosPorTema.entrySet()) {
                // Título del tema
                Label temaLabel = new Label("Tema: " + entry.getKey());
                temaLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
                contenidosVBox.getChildren().add(temaLabel);

                // Contenidos
                for (Contenido contenido : entry.getValue()) {
                    agregarTarjetaContenido(contenidosVBox, contenido);
                }
            }
        }

        scrollPane.setContent(contenidosVBox);
        scrollPane.setFitToWidth(true);
    }

    private static void agregarTarjetaContenido(VBox contenedor, Contenido contenido) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    GrupoContenidoViewUtil.class.getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
            Parent tarjeta = loader.load();
            TarjetaContenidoController controller = loader.getController();
            controller.setContenido(contenido);
            contenedor.getChildren().add(tarjeta);
        } catch (IOException e) {
            // Fallback básico
            HBox fallback = new HBox(
                    new Label(contenido.getTitulo()),
                    new Label(" | " + contenido.getTipoContenido())
            );
            contenedor.getChildren().add(fallback);
        }
    }
}