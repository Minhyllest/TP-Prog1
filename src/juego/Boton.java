package juego;

import java.awt.Color;
import entorno.Entorno;

public class Boton {
    int x, y, ancho, alto;
    String texto;
    boolean seleccionado;

    public Boton(int x, int y, int ancho, int alto, String texto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.texto = texto;
        this.seleccionado = false;
    }

    public void dibujar(Entorno entorno) {
        Color color = seleccionado ? Color.YELLOW : Color.GRAY;
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, color);
        entorno.cambiarFont("Arial", 16, Color.CYAN);
        entorno.escribirTexto(texto, x - ancho / 2 + 10, y + 5);
    }

    public boolean fueClickeado(int mouseX, int mouseY) {
        return mouseX >= x - ancho / 2 && mouseX <= x + ancho / 2 &&
               mouseY >= y - alto / 2 && mouseY <= y + alto / 2;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
