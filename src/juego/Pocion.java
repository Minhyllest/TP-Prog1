package juego;

import entorno.Entorno;

import java.awt.Image;

public class Pocion {
    private double x;
    private double y;
    private boolean activa;
    private final int radio = 20;
    private Image potion = entorno.Herramientas.cargarImagen("images/mana-potion2.gif");

    public Pocion(double x, double y) {
        this.x = x;
        this.y = y;
        this.activa = true;
    }

    public void dibujar(Entorno entorno) {
        if (activa) {

        	entorno.dibujarImagen(potion, x, y, 0); 
        }
    }

    public boolean colisionaCon(Personaje personaje) {
        if (!activa) return false;
        double distancia = Math.hypot(x - personaje.getX(), y - personaje.getY());
        return distancia < (radio + personaje.getRadio());
    }

    public void recolectar() {
        activa = false;
    }

    public boolean estaActiva() {
        return activa;
    }
}