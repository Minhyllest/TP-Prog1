package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import java.awt.Color;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private double x = 600;  
    private double y = 400;  
    public double radio= 30;

    Juego() {
        this.entorno = new Entorno(this, "Demo entorno", 1200, 800);
        this.entorno.iniciar();
    }

    public void tick() {
        
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            y -= 15;  
        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            y += 15;  
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            x -= 15;  
        }
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            x += 15; 
        }
        
       
        if (x < radio) x = radio;
        if (x > entorno.ancho()-radio) x = entorno.ancho()-radio;
        if (y < radio) y = radio;
        if (y > entorno.alto()-radio) y = entorno.alto()-radio;

        entorno.colorFondo(Color.ORANGE);
        entorno.dibujarCirculo(x, y, radio, Color.BLUE); 
        
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}
