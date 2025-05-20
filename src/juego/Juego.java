package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;
import java.util.Arrays;

public class Juego extends InterfaceJuego {
	public Image fondo; 
    public Entorno entorno;
    public Personaje personaje;
    public Murcielago[] Murci= new Murcielago [10];
    public Poderes[] disparo = new Poderes [100];
    Juego() {
    	int alto = 1400, ancho = 900;
        this.entorno = new Entorno(this, "Enki's dream", alto,ancho);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg").getScaledInstance(1400, 900, Image.SCALE_SMOOTH); // carga la imagen del fondo y lo escala a el tamaño de la pantalla.
        this.personaje = new Personaje(600, 400);
        this.entorno.iniciar(); 
        /////////////////////////////////////////////////
        for (int i=0;i<10;i++) {
			int numero=(int)Math.floor(Math.random()*4+1);
			//System.out.println(numero);
			if(numero==1) {
				Murci[i]= new Murcielago((int)Math.floor(Math.random()*800+1),0);
			}
			if(numero==2) {
				Murci[i]= new Murcielago(0,(int)Math.floor(Math.random()*600+1));
			}
			if(numero==3) {
				Murci[i]= new Murcielago(800,(int)Math.floor(Math.random()*600+1));
			}
				
			if(numero==4) {
				Murci[i]= new Murcielago((int)Math.floor(Math.random()*800+1),600);
			}
		
		}
        ////////////////////////////////////////////////////////////////////////
        for (int i=0;i<3;i++) {
        	  this.disparo[i]= new Poderes(personaje, false, 0);	
        }
      
        
    }

    public void tick() {
    	
        entorno.dibujarImagen(this.fondo, 700, 450, 0);  //background
        

        
        this.personaje.dibujar(entorno); // dibuja al personaje.
        
        entorno.dibujarRectangulo(1301, 400, 200, 1600, 0, Color.black);
        //MENU
        entorno.dibujarRectangulo(1300, 70, 1, 150, 11, Color.WHITE);
        entorno.dibujarRectangulo(1200, 100, 1, 1600, 0, Color.WHITE);
        //////////////////////////////////////////////////////////////////////////////////////////////
        for (int i=0;i<10;i++) {
			Murci[i].dibujarse(entorno);
			Murci[i].seguirMago(personaje);
			
		}
          
        //////////////////////////////////////////////////////////////////////////////////////////////
        for (int i=0;i<3;i++) {
        
        if (disparo[i].activo==false && entorno.sePresiono(entorno.TECLA_ESPACIO)) {
        	if(entorno.sePresiono(entorno.TECLA_ESPACIO))
        	disparo[i].activo=true;
      
        	disparo[i].moverDerecha();
        }
        if (disparo[i].activo == true) {
        	disparo[i].dibujarseDisparo(entorno);
        	disparo[i].moverDerecha();
        	
        }
        System.out.println(disparo[i].activo);
        }
        /////////////////////////////////////////////////////////////////////////////////////////////
        entorno.cambiarFont("Chiller", 30, Color.WHITE,entorno.NEGRITA);
        entorno.escribirTexto("Enki's Dream", 1255, 50);
        
        
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
            personaje.quedarseQuieto(); // si personaje no se movio, se queda quieto, es decir la tecla no esta presionada.(no hay animacion)
        }

        personaje.dibujar(entorno);
        
    }
    

    
    @SuppressWarnings("unused")

    public static void main(String[] args) {
    	
    	
        Juego juego = new Juego();
    }
}
