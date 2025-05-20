package juego;

import java.awt.Color;

import entorno.Entorno;

public class Poderes {
	 	public int x;
	    public int y;
	    public int velocidad;
	    boolean especial;
	    double angulo;
	    boolean activo;
	    
	    
	    public Poderes(Personaje personaje, boolean especial, double angulo) {
	    	this.x=personaje.getX();
	    	this.y=personaje.getY();
	    	this.especial=false;
	    	this.angulo=0;
	    	this.velocidad=5;
	    	this.activo=false;
	    }
	    public void dibujarseDisparo(Entorno entorno)
		{
	    	
			entorno.dibujarCirculo(this.x, this.y, 20,Color.GREEN);
			
		}
	    public void cambiarAngulo(double x2, double y2){
			this.angulo = Math.atan2(y2 - this.y, x2 - this.x);
		}
	    
	    public void moverDerecha() {
	        if (this.x + velocidad < 1400 -216) // Ancho de la pantalla
	            this.x += velocidad;
	      //  this.imagenActual = this.enkiDerecha;
	    }
	    
}
