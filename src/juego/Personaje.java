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
	
    // carga de variables de instancia (posicion, velocoidad, animaciones e imagenes del personaje quieto).

    public Personaje(int x, int y) {
    	
        this.x = x;
        this.y = y;
        this.velocidad = 5;
        
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
    
    public void quedarseQuieto() { // funcion quedarse quieto que setea la imagen actual en base a la ultima posicion que haya tenido el personaje y pone la imagen quieta.
        if (imagenActual == enkiIzquierda) imagenActual = enkiQuietoIzquierda;
        else if (imagenActual == enkiDerecha) imagenActual = enkiQuietoDerecha;
        else if (imagenActual == enkiArriba) imagenActual = enkiQuietoArriba;
        else if (imagenActual == enkiAbajo) imagenActual = enkiQuietoAbajo;
    }
    
    
    
    // Métodos para mover al personaje
    public void moverIzquierda() {
        if (this.x - velocidad > 0 +20)
            this.x -= velocidad;
        this.imagenActual = this.enkiIzquierda;
    }

    public void moverDerecha() {
        if (this.x + velocidad < 1400 -216) // Ancho de la pantalla
            this.x += velocidad;
        this.imagenActual = this.enkiDerecha;
    }

    public void moverArriba() {
        if (this.y - velocidad > 0 +40)
            this.y -= velocidad;
        this.imagenActual = this.enkiArriba;
    }

    public void moverAbajo() {
        if (this.y + velocidad < 900 -40 ) // Alto de la pantalla
            this.y += velocidad;
        this.imagenActual = this.enkiAbajo;
    }


    
    
    // Dibuja el personaje
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagenActual, x, y, 0);
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}


