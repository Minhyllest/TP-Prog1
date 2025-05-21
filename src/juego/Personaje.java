package juego;

import entorno.Entorno;
import java.awt.Image;

public class Personaje {
    public int x, y, velocidad;
    public Image imagenActual;

    public Image Arriba, Abajo, Izquierda, Derecha;
    public Image QuietoArriba, QuietoAbajo, QuietoIzquierda, QuietoDerecha;
	public int ancho;
	public int alto;
	

    public Personaje(int x, int y, String tipo, int alto, int ancho) {
        this.x = x;
        this.y = y;
        this.velocidad = 2;
        
        if (tipo.equalsIgnoreCase("enki")) {
        	
        	Arriba = entorno.Herramientas.cargarImagen("images/enki/arriba.gif");
            Abajo = entorno.Herramientas.cargarImagen("images/enki/abajo.gif");
            Izquierda = entorno.Herramientas.cargarImagen("images/enki/izquierda.gif");
            Derecha = entorno.Herramientas.cargarImagen("images/enki/derecha.gif");

            QuietoArriba = entorno.Herramientas.cargarImagen("images/enki/quieto_arriba.gif");
            QuietoAbajo = entorno.Herramientas.cargarImagen("images/enki/quieto_abajo.gif");
            QuietoIzquierda = entorno.Herramientas.cargarImagen("images/enki/quieto_izquierda.gif");
            QuietoDerecha = entorno.Herramientas.cargarImagen("images/enki/quieto_derecha.gif");
        } else if (tipo.equalsIgnoreCase("ossaa")) {
        
            Arriba = entorno.Herramientas.cargarImagen("images/ossa/ossa-arriba.gif");
            Abajo = entorno.Herramientas.cargarImagen("images/ossa/ossa-abajo.gif");
            Izquierda = entorno.Herramientas.cargarImagen("images/ossa/ossa-izq.gif");
            Derecha = entorno.Herramientas.cargarImagen("images/ossa/ossa-der.gif");

            QuietoArriba = entorno.Herramientas.cargarImagen("images/ossa/ossa-arriba-quieto.gif");
            QuietoAbajo = entorno.Herramientas.cargarImagen("images/ossa/ossa-abajo-quieto.gif");
            QuietoIzquierda = entorno.Herramientas.cargarImagen("images/ossa/ossa-izq-quieto.gif");
            QuietoDerecha = entorno.Herramientas.cargarImagen("images/ossa/ossa-der-quieto.gif");
        }

        this.imagenActual = QuietoAbajo;
    }
    

    public void moverIzquierda() {
        x -= velocidad;
        imagenActual = Izquierda;
    }

    public void moverDerecha() {
        x += velocidad;
        imagenActual = Derecha;
    }

    public void moverArriba() {
        y -= velocidad;
        imagenActual = Arriba;
    }

    public void moverAbajo() {
        y += velocidad;
        imagenActual = Abajo;
    }

    public void quedarseQuieto() {
        if (imagenActual == Izquierda) imagenActual = QuietoIzquierda;
        else if (imagenActual == Derecha) imagenActual = QuietoDerecha;
        else if (imagenActual == Arriba) imagenActual = QuietoArriba;
        else if (imagenActual == Abajo) imagenActual = QuietoAbajo;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagenActual, x, y, 0);
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}



