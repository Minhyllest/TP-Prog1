package juego;

import java.awt.Color;

import entorno.Entorno;

public class Murcielago {
	private double x;
	private double y;
	private double radio;
	private boolean colision;
	public Murcielago(double x, double y) {
		this.x =  x;
		this.y = y;
		this.radio = 10;
		this.colision=false;
	}
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarCirculo(this.x, this.y, 20,Color.RED);
		
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

	public double getRadio() {
		return radio;
	}
	public void setRadio(double radio) {
		this.radio = radio;
	}
	public boolean isColision() {
		return colision;
	}
	public void setColision(boolean colision) {
		this.colision = colision;
	}

	
	
	

}
