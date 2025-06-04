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
	public int velocidadBase;
	private double radio;
	private int xAnterior, yAnterior;
	private boolean colision;
	private boolean esAtacado;
	private int mana;
	private int vida;

	
    public Personaje(int x, int y, String tipo, int alto, int ancho) {
        this.x = x;
        this.y = y;
        this.velocidad = 2;
        this.velocidadBase = 2;
        this.velocidad = velocidadBase;
        this.ancho = ancho;
        this.alto = alto;
        this.setRadio(10);
        this.colision=false;
        this.esAtacado=false;
        this.vida=1000;
        this.mana = 100; // Comienza con maná lleno

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

    public int getVida() {
        return vida;
    }
    public int setVida() {
    	
        return vida+20;
    }


    public boolean gastarMana() {
    	if (this.mana >= 20) {
    	this.mana -= 20;
    	return true;
    	}
    	return false;
    	}
    public void recuperarMana() {
    	this.mana += 20;
    	if (this.mana > 100) {
    	this.mana = 100;
    		}
    	}

    public void recibirDanio() {
        this.vida -= 1;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }
    public void recuperarVida() {
    	this.vida +=20;
}
    public boolean colisionaCon(Murcielago m) {
        double dx = this.x - m.getX();
        double dy = this.y - m.getY();
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < this.radio + m.getRadio();
    }
	

    public void moverYDetectarColision(int dx, int dy, Obstaculo[] obstaculos) {
        int xOriginal = x;
        int yOriginal = y;

        // Movimiento horizontal
        x += dx;
        for (Obstaculo o : obstaculos) {
            if (o != null && o.colisionaCon(this)) {
                x = xOriginal;
                break;
            }
        }

        // Movimiento vertical
        y += dy;
        for (Obstaculo o : obstaculos) {
            if (o != null && o.colisionaCon(this)) {
                y = yOriginal;
                break;
            }
        }

        // Actualizar imagen según dirección
        if (dx < 0) imagenActual = Izquierda;
        else if (dx > 0) imagenActual = Derecha;
        else if (dy < 0) imagenActual = Arriba;
        else if (dy > 0) imagenActual = Abajo;
    }

    

    public void guardarPosicion() {
        this.xAnterior = this.x;
        this.yAnterior = this.y;
    }

    public void retroceder() {
        this.x = this.xAnterior;
        this.y = this.yAnterior;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int v) {
        this.velocidad = v;
    }

    public int getVelocidadBase() {
        return velocidadBase;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setX(int nuevoX) {
        this.x = nuevoX;
    }

    public void setY(int nuevoY) {
        this.y = nuevoY;
    }
   
    
    public void moverIzquierda() {
    	guardarPosicion();
        x -= velocidad;
        imagenActual = Izquierda;
        
    }

    public void moverDerecha() {
    	guardarPosicion();
        x += velocidad;
        imagenActual = Derecha;
    }

    public void moverArriba() {
    	guardarPosicion();
        y -= velocidad;
        imagenActual = Arriba;
    }

    public void moverAbajo() {
    	guardarPosicion();
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
    
    public boolean isColision() {
		return colision;
	}


	public void setColision(boolean colision) {
		this.colision = colision;
	}


	public double getRadio() {
		return radio;
	}


	public void setRadio(double radio) {
		this.radio = radio;
	}


	public boolean isEsAtacado() {
		return esAtacado;
	}


	public void setEsAtacado(boolean esAtacado) {
		this.esAtacado = esAtacado;
	}

	public int getMana() {
		// TODO Auto-generated method stub
		return mana;
	}



}


    




