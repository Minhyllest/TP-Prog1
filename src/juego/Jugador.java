package juego;

import java.awt.Color;
import entorno.Entorno;

public class Jugador {
    private int x;
    private int y;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, 30, Color.BLUE);
    }
}
