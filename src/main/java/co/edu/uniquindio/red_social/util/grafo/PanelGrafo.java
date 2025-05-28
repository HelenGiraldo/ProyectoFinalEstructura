package co.edu.uniquindio.red_social.util.grafo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

class PanelGrafo extends JPanel implements MouseListener, MouseMotionListener {


    // Paleta de colores moderna
    private final Color COLOR_FONDO = new Color(240, 240, 245);
    private final Color[] COLORES_NODOS = {
            new Color(100, 181, 246), // Azul claro
            new Color(77, 182, 172),  // Verde agua
            new Color(255, 183, 77),  // Naranja
            new Color(229, 115, 115), // Rojo claro
            new Color(186, 104, 200)  // Morado
    };
    private final Color COLOR_BORDE_NODO = new Color(55, 71, 79);
    private final Color COLOR_TEXTO = new Color(33, 33, 33);
    private final Color COLOR_ARISTA = new Color(120, 144, 156);
    private final Color COLOR_ETIQUETA_ARISTA = new Color(69, 90, 100);
    private final Color COLOR_FONDO_ETIQUETA = new Color(255, 255, 255, 220);

    private static final int RADIO_NODO = 30;  // Aumentamos de 20 a 30 (diámetro de 60)
    private static final int DIAMETRO_NODO = RADIO_NODO * 2;
    private GNodo nodoSeleccionado = null;
    private Arista etiquetaSeleccionada = null;
    private int offsetX, offsetY;
    private final CreacionGrafo grafo = CreacionGrafo.getInstance();

    public PanelGrafo() {
        grafo.estudiantes.show();
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(800, 600));
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Configuración de calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Dibujar aristas primero (para que queden detrás de los nodos)
        dibujarAristas(g2d);

        // Dibujar nodos
        dibujarNodos(g2d);
    }

    private void dibujarAristas(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(COLOR_ARISTA);

        for (Arista a : grafo.aristas) {
            a.calcularPosicionEtiqueta();
            // Dibujar línea entre nodos
            g2d.drawLine(a.origen.x, a.origen.y, a.destino.x, a.destino.y);

            // Dibujar etiqueta si existe
            if (a.getEtiqueta() != null && !a.getEtiqueta().isEmpty()) {
                dibujarEtiquetaArista(g2d, a);
            }
        }
    }

    private void dibujarEtiquetaArista(Graphics2D g2d, Arista a) {
        FontMetrics fm = g2d.getFontMetrics();
        int textoAncho = fm.stringWidth(a.getEtiqueta());
        int textoAlto = fm.getHeight();

        // Fondo de la etiqueta
        g2d.setColor(COLOR_FONDO_ETIQUETA);
        g2d.fillRoundRect(a.etiquetaX - textoAncho/2 - 5,
                a.etiquetaY - textoAlto/2 - 3,
                textoAncho + 10,
                textoAlto + 6,
                10, 10);

        // Borde de la etiqueta
        g2d.setColor(COLOR_ARISTA);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawRoundRect(a.etiquetaX - textoAncho/2 - 5,
                a.etiquetaY - textoAlto/2 - 3,
                textoAncho + 10,
                textoAlto + 6,
                10, 10);

        // Texto de la etiqueta
        g2d.setColor(COLOR_ETIQUETA_ARISTA);
        g2d.drawString(a.getEtiqueta(), a.etiquetaX - textoAncho/2, a.etiquetaY + textoAlto/4);
    }

    private void dibujarNodos(Graphics2D g2d) {
        System.out.println("Total nodos a dibujar: " + grafo.estudiantes.size());
        FontMetrics fm = g2d.getFontMetrics();
        int colorIndex = 0;

        for (GNodo n : grafo.estudiantes) {
            // Asignar color rotatorio
            Color colorNodo = COLORES_NODOS[colorIndex % COLORES_NODOS.length];
            colorIndex++;

            System.out.println("Dibujando nodo: " + n.nombre + " en (" + n.x + "," + n.y + ")");

            // Dibujar nodo
            dibujarNodoIndividual(g2d, n, colorNodo, fm);
        }
    }

    private void dibujarNodoIndividual(Graphics2D g2d, GNodo n, Color colorNodo, FontMetrics fm) {
        // Relleno del nodo (más grande)
        g2d.setColor(colorNodo);
        g2d.fillOval(n.x - RADIO_NODO, n.y - RADIO_NODO, DIAMETRO_NODO, DIAMETRO_NODO);

        // Borde del nodo
        g2d.setColor(COLOR_BORDE_NODO);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawOval(n.x - RADIO_NODO, n.y - RADIO_NODO, DIAMETRO_NODO, DIAMETRO_NODO);

        // Texto del nodo (centrado y mejor ajustado)
        g2d.setColor(COLOR_TEXTO);
        int textoAncho = fm.stringWidth(n.nombre);
        int textoAlto = fm.getHeight();

        // Ajustamos posición vertical para mejor centrado
        g2d.drawString(n.nombre, n.x - textoAncho/2, n.y + textoAlto/4);
    }
    // ==== Eventos del Mouse ====
    @Override
    public void mousePressed(MouseEvent e) {
        // Primero verificar si se está seleccionando una etiqueta
        for (Arista a : grafo.aristas) {
            if (a.getEtiqueta() != null && !a.getEtiqueta().isEmpty()) {
                FontMetrics fm = getFontMetrics(getFont());
                int textoAncho = fm.stringWidth(a.getEtiqueta());
                int textoAlto = fm.getHeight();

                Rectangle etiquetaRect = new Rectangle(
                        a.etiquetaX - textoAncho/2 - 5,
                        a.etiquetaY - textoAlto/2 - 3,
                        textoAncho + 10,
                        textoAlto + 6
                );

                if (etiquetaRect.contains(e.getPoint())) {
                    etiquetaSeleccionada = a;
                    offsetX = e.getX() - a.etiquetaX;
                    offsetY = e.getY() - a.etiquetaY;
                    repaint();
                    return;
                }
            }
        }

        // Si no se seleccionó una etiqueta, verificar nodos
        for (GNodo n : grafo.estudiantes) {
            if (e.getX() >= n.x - 20 && e.getX() <= n.x + 20 &&
                    e.getY() >= n.y - 20 && e.getY() <= n.y + 20) {
                nodoSeleccionado = n;
                offsetX = e.getX() - n.x;
                offsetY = e.getY() - n.y;
                break;
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        nodoSeleccionado = null;
        etiquetaSeleccionada = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (etiquetaSeleccionada != null) {
            // Mover la etiqueta de la arista
            etiquetaSeleccionada.etiquetaX = e.getX() - offsetX;
            etiquetaSeleccionada.etiquetaY = e.getY() - offsetY;
            repaint();
        } else if (nodoSeleccionado != null) {
            // Mover el nodo
            nodoSeleccionado.x = e.getX() - offsetX;
            nodoSeleccionado.y = e.getY() - offsetY;

            // Recalcular posiciones de etiquetas para aristas conectadas
            for (Arista a : grafo.aristas) {
                if (a.origen == nodoSeleccionado || a.destino == nodoSeleccionado) {
                    a.calcularPosicionEtiqueta();
                }
            }
            repaint();
        }
    }

    // Métodos no usados
    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
