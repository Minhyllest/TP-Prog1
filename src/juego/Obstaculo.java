package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Obstaculo {
    private int x, y;
    private int ancho, alto;
    private Image imagen;

    public Obstaculo(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = Herramientas.cargarImagen("images/obstaculos/ataud.gif");
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);
    }
    
    
    public boolean colisionaCon(int px, int py, int pAncho, int pAlto) {
        return Math.abs(px - x) < (pAncho + ancho) / 2 &&
               Math.abs(py - y) < (pAlto + alto) / 2;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
