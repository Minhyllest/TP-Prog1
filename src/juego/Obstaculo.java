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
        this.imagen = Herramientas.cargarImagen("images/obstaculos/ataud.gif"); 
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);

        
       
    }

    public boolean colisionaCon(Personaje personaje) {
        return Math.abs(this.x - personaje.getX()) < (this.ancho / 4 + personaje.getAncho() / 4+10) &&
               Math.abs(this.y - personaje.getY()) < (this.alto / 4 + personaje.getAlto() / 4+10);
    }

    public void dibujarDebug(Entorno entorno) {
        
    }

    
    public boolean estaCerca(Personaje p) {
        double distanciaX = Math.abs(p.getX() - this.x);
        double distanciaY = Math.abs(p.getY() - this.y);
        return distanciaX < (ancho / 2 + p.getAncho() / 2) &&
               distanciaY < (alto / 2 + p.getAlto() / 2);
    }

   
    

    
    public int getX() { return x; }
    public int getY() { return y; }
}
