package juego;

import entorno.Entorno;
import java.awt.Color;

public class Barra {
    private int x, y, ancho, alto;
    private Color colorFondo;
    private Color colorRelleno;
    private int valorActual;
    private int valorMaximo;

    public Barra(int x, int y, int ancho, int alto, Color colorRelleno, Color colorFondo, int valorMaximo) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.colorRelleno = colorRelleno;
        this.colorFondo = colorFondo;
        this.valorMaximo = valorMaximo;
        this.valorActual = valorMaximo;
    }

    public void setValor(int valor) {
        this.valorActual = Math.max(0, Math.min(valor, valorMaximo));
    }

    public void dibujar(Entorno entorno) {
        // Fondo
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, colorFondo);
        // Relleno proporcional
        double porcentaje = (double) valorActual / valorMaximo;
        entorno.dibujarRectangulo(
            x - (1 - porcentaje) * ancho / 2,
            y,
            ancho * porcentaje,
            alto,
            0,
            colorRelleno
        );
    }
}
