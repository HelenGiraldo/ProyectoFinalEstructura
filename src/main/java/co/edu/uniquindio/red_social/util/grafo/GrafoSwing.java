package co.edu.uniquindio.red_social.util.grafo;

import javax.swing.*;

public class GrafoSwing {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Grafo Visual con Etiquetas");
        PanelGrafo panel = new PanelGrafo();

        // Crear los nodos
        GNodo a = new GNodo("A", 100, 100);
        GNodo b = new GNodo("B", 200, 200);
        GNodo c = new GNodo("C", 300, 100);
        GNodo d = new GNodo("D", 400, 100);

        // Agregar los mismos objetos nodo al panel
        panel.agregarNodo(a);
        panel.agregarNodo(b);
        panel.agregarNodo(c);
        panel.agregarNodo(d);

        // Agregar aristas con etiquetas
        panel.agregarArista(a, b, "AB");
        panel.agregarArista(b, c, "BC");
        panel.agregarArista(c, a, "CA");
        panel.agregarArista(a, d, "AD");
        panel.agregarArista(b, d, "BD");
        panel.agregarArista(c, d, "CD");

        ventana.add(panel);
        ventana.setSize(600, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}