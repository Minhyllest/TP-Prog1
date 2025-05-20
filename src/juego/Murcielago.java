package juego;

import java.awt.Color;

import entorno.Entorno;

public class Murcielago {
	private double x;
	private double y;
	private double angulo;
	
	public Murcielago(double x, double y) {
		this.x =  x;
		this.y = y;
		this.angulo = 0;
	}
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarCirculo(this.x, this.y, 20,Color.RED);
		
	}
	public void cambiarAngulo(double x2, double y2){
		this.angulo = Math.atan2(y2 - this.y, x2 - this.x);
	}

	public void seguirMago(Personaje personaje){
			if(this.x < personaje.getX()) {
				this.x=this.x+1;
			}
			if(this.x > personaje.getX()) {
				this.x=this.x-1;
			}
			
				if(this.y < personaje.getY()) {
				this.y++;
				}
		if(this.y > personaje.getY()) {
				this.y--;
				}
		
	}
	
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAngulo() {
		return angulo;
	}
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	
	
	

}
