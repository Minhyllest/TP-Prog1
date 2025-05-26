package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Obstaculo {
    int x, y;
    int ancho, alto;
    Image imagen;

    public Obstaculo(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = Herramientas.cargarImagen("images/obstaculos/ataud.gif").getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
 // asegurate del path y formato
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);
    }

    public boolean colisionaCon(int px, int py, int pAncho, int pAlto) {
        return !(px + pAncho / 2 < x - ancho / 2 ||
                 px - pAncho / 2 > x + ancho / 2 ||
                 py + pAlto / 2 < y - alto / 2 ||
                 py - pAlto / 2 > y + alto / 2);
    }

}

