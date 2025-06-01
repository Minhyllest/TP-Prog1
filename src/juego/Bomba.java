package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bomba {
    private double x;
    private double y;
    private boolean activa = false;
    private int duracion = 0;
    private final Image gifExplos;
    private boolean consumeMana;
    private int costoMana;
    private Image gifExplosion;
    private boolean usaMana;
    private int radioExplosion;
  
    
  



    public Bomba(int radio, int costoMana, String rutaGif, boolean usaMana) {
        this.radioExplosion = radio;
        this.costoMana = costoMana;
        this.gifExplosion = Herramientas.cargarImagen(rutaGif);
        this.usaMana = usaMana;
    }



    public boolean activar(double x, double y, Personaje personaje) {
        if (usaMana && !personaje.tieneManaSuficiente(costoMana)) {
            return false; // no se puede usar
        }

        if (usaMana) {
            personaje.gastarMana(costoMana);
        }

        this.x = x;
        this.y = y;
        this.activa = true;
        this.duracion = 50;
        return true;
    }

    public boolean esDeMana() {
        return usaMana;
    }

    public int getRadio() {
        return radioExplosion;
    }

    public void desactivar() {
        this.activa = false;
        this.duracion = 0;
    }

    public boolean estaActiva() {
        return activa;
    }

    public void dibujar(Entorno entorno) {
        if (!activa) return;
        entorno.dibujarImagen(gifExplos, x, y, 0);
        duracion--;
        if (duracion <= 0) desactivar();
    }

    public boolean enRangoExplosion(double px, double py) {
        double dx = px - x;
        double dy = py - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia <= radioExplosion;
    }

    public boolean requiereMana() {
        return consumeMana;
    }
}
