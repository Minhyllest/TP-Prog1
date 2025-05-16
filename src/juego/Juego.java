package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;

public class Juego extends InterfaceJuego {
	public Image fondo; 
    public Entorno entorno;
    public Personaje personaje;
    
    Juego() {
        this.entorno = new Entorno(this, "Demo juego", 1400, 900);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg").getScaledInstance(1400, 900, Image.SCALE_SMOOTH);
        this.personaje = new Personaje(600, 400);
        this.entorno.iniciar(); 
    }

    public void tick() {
        entorno.dibujarImagen(this.fondo, 700, 450, 0); 
        this.personaje.dibujar(entorno);
        if (this.personaje == null) {
            return;
        }

        boolean seMovio = false;

        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            personaje.moverArriba();
            seMovio = true;
        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            personaje.moverAbajo();
            seMovio = true;
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            personaje.moverIzquierda();
            seMovio = true;
        }
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            personaje.moverDerecha();
            seMovio = true;
        }

        
        if (!seMovio) {
            personaje.quedarseQuieto();
        }

        personaje.dibujar(entorno);
    }


    

    public static void main(String[] args) {
        new Juego();
    }
}
