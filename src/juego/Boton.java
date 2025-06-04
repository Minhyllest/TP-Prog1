package juego;

import java.awt.Color;
import entorno.Entorno;

public class Boton {
    private int x, y, ancho, alto;
    private String texto;
    private boolean seleccionado;
    private Color colorBase;

    public Boton(int x, int y, int ancho, int alto, String texto, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.texto = texto;
        this.seleccionado = false;
        this.colorBase = color;
    }

    public void dibujar(Entorno entorno) {
        Color color = seleccionado ? Color.black: colorBase;
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, color);
        entorno.cambiarFont("Century Gothic", 16, Color.white);

        // Centrado horizontal aproximado del texto
        int textoAnchoEstimado = texto.length() * 7;
        entorno.escribirTexto(texto, x - textoAnchoEstimado / 2, y + 5);
    }

    public boolean fueClickeado(Entorno entorno) {
        if (entorno.mouseX() >= x - ancho / 2 && entorno.mouseX() <= x + ancho / 2 &&
               entorno.mouseY() >= y - alto / 2 && entorno.mouseY() <= y + alto / 2) {
        return	true;       	
        }
        return false;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean estaSeleccionado() {
        return this.seleccionado;
    }
}
