package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bomba {
    private double x;
    private double y;
    private boolean activa;
    private int duracion;

    private Image animacion;
    private int radio;
    private boolean usaMana;
    private  int costoMana;
	private boolean colision;

    public Bomba() {
        this.animacion = Herramientas.cargarImagen("images/explosiones/explAzul.gif");
        this.radio=80;
        this.costoMana = 20;
        this.colision=false;
        this.activa=false;
        this.duracion = 0;
    }

    public void activar(Entorno entorno) {
        this.x = entorno.mouseX();
        this.y = entorno.mouseY();
        this.duracion = 60; // duración en cuadros (~1 segundo)
        this.activa = true;
    }

    public boolean estaActiva() {
        return activa;
    }

    public boolean colision(Murcielago murci) {
        double distanciaMurci = Math.hypot((this.x - murci.getX()),(this.y - murci.getY()));  // 
        if     (distanciaMurci <= (this.radio + murci.getRadio()) ) {
            this.colision=true;
            murci.setColision(true);
            return true;
        }
        return false;
    }
    

    public void dibujar(Entorno entorno) {
       if(this.activa==true) {
            entorno.dibujarImagen(animacion, this.x, this.y, 0, 1.0);
            duracion--;
           
            if (duracion <= 0) {
                activa = false;
            }
       }
    }

    public boolean usaMana() {
        return usaMana;
    }

    public int getCostoMana() {
        return costoMana;
    }

	public boolean isColision() {
		return colision;
	}

	public void setColision(boolean colision) {
		this.colision = colision;
	}

   
}

