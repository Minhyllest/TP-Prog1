package juego;

import java.awt.Image;
import entorno.Entorno;

public class Bomba {
    private double x;
    private double y;
    private boolean activa = false;
    private int duracion = 0;

    private final Image animacion;
    private final int radioExplosion;
    private final boolean usaMana;
    private final int costoMana;

    public Bomba(Image animacion, int radioExplosion, boolean usaMana, int costoMana) {
        this.animacion = animacion;
        this.radioExplosion = radioExplosion;
        this.usaMana = usaMana;
        this.costoMana = costoMana;
    }

    public void activar(double x, double y, Personaje personaje) {
        this.x = x;
        this.y = y;
        this.duracion = 60; // duración en cuadros (~1 segundo)
        this.activa = true;
    }

    public boolean estaActiva() {
        return activa;
    }

    public boolean enRangoExplosion(double objX, double objY) {
        if (!activa) return false;
        double dx = objX - x;
        double dy = objY - y;
        return Math.sqrt(dx * dx + dy * dy) < radioExplosion;
    }

    public void dibujar(Entorno entorno) {
        if (activa) {
            entorno.dibujarImagen(animacion, x, y, 0, 1.0);
            duracion--;
            if (duracion <= 0) {
                activa = false;
            }
        }
    }

    public boolean usaMana() {
        return usaMana;
    }

    public int getCostoMana() {
        return costoMana;
    }

    public int getRadioExplosion() {
        return radioExplosion;
    }
}

