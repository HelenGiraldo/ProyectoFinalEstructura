package co.edu.uniquindio.red_social.util.grafo;

import javax.swing.*;

public class GrafoSwing {
    public static void main() {
        JFrame ventana = new JFrame("Grafo Visual con Etiquetas");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelGrafo panel = new PanelGrafo();


        ventana.add(panel);
        ventana.setSize(800, 800);
        ventana.setVisible(true);
    }
}