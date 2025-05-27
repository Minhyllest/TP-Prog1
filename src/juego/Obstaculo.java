package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Obstaculo {
    private int x, y;
    private int ancho = 124;
    private int alto = 68;
    private Image imagen;

    public Obstaculo(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagen = Herramientas.cargarImagen("images/obstaculos/ataud.gif"); // asegurate que la ruta sea correcta
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);

        // 🔲 Dibuja un rectángulo representando el área del obstáculo
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, java.awt.Color.RED);
    }

    public boolean colisionaCon(Personaje personaje) {
        return this.x < personaje.getX() + personaje.getAncho() &&
               this.x + this.ancho > personaje.getX() &&
               this.y < personaje.getY() + personaje.getAlto() &&
               this.y + this.alto > personaje.getY();
    }

    
    public boolean estaCerca(Personaje p) {
        double distanciaX = Math.abs(p.getX() - this.x);
        double distanciaY = Math.abs(p.getY() - this.y);
        return distanciaX < (ancho / 2 + p.getAncho() / 2) &&
               distanciaY < (alto / 2 + p.getAlto() / 2);
    }

    public void aplicarEfecto(Personaje p) {
        if (estaCerca(p)) {
            p.setVelocidad(p.getVelocidadBase() / 2); // reduce la velocidad a la mitad
        } else {
            p.setVelocidad(p.getVelocidadBase()); // la restablece
        }
    }

    // Getters si necesitás
    public int getX() { return x; }
    public int getY() { return y; }
}
