package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Personaje {
	
    public int x;
    public int y;
    public int velocidad;
    
    public Image enkiArriba;
    public Image enkiAbajo;
    public Image enkiIzquierda;
    public Image enkiDerecha;
    
    public Image imagenActual;
    
    public Image enkiQuietoArriba;
    public Image enkiQuietoAbajo;
    public Image enkiQuietoIzquierda;
    public Image enkiQuietoDerecha;



    public Personaje(int x, int y) {
    	
        this.x = x;
        this.y = y;
        this.velocidad = 2;
        
        this.enkiArriba = Herramientas.cargarImagen("images/personaje/supgif.gif");
        this.enkiAbajo = Herramientas.cargarImagen("images/personaje/gif2.gif");
        this.enkiIzquierda = Herramientas.cargarImagen("images/personaje/izqgif.gif");
        this.enkiDerecha = Herramientas.cargarImagen("images/personaje/dergif.gif");
        
        this.enkiQuietoArriba = Herramientas.cargarImagen("images/personaje/supgifquieto.gif");
        this.enkiQuietoAbajo = Herramientas.cargarImagen("images/personaje/gif2quieto.gif");
        this.enkiQuietoDerecha = Herramientas.cargarImagen("images/personaje/dergifquieto.gif");
        this.enkiQuietoIzquierda = Herramientas.cargarImagen("images/personaje/izqgifquieto.gif");
        this.imagenActual = enkiQuietoAbajo; 
    }
    
    public void quedarseQuieto() {
        if (imagenActual == enkiIzquierda) imagenActual = enkiQuietoIzquierda;
        else if (imagenActual == enkiDerecha) imagenActual = enkiQuietoDerecha;
        else if (imagenActual == enkiArriba) imagenActual = enkiQuietoArriba;
        else if (imagenActual == enkiAbajo) imagenActual = enkiQuietoAbajo;
    }
    

    
    // Métodos para mover al personaje
    public void moverIzquierda() { this.x -= velocidad; this.imagenActual = this.enkiIzquierda;}
    public void moverDerecha() { this.x += velocidad; this.imagenActual = this.enkiDerecha;}
    public void moverArriba() {this.y -= velocidad; this.imagenActual = this.enkiArriba;}
    public void moverAbajo() { this.y += velocidad; this.imagenActual = this.enkiAbajo; }

    // Dibuja el personaje
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagenActual, x, y, 0);
    }
}


