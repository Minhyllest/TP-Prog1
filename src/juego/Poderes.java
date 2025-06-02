package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Poderes {
         public int x;
        public int y;
        public int velocidad;
        boolean especial;
        double radio;
        boolean activo;
        int direccion;
        boolean colision;
        public Image gifProy;

        public Poderes(Personaje personaje, boolean especial, double radio) {
            this.x=personaje.getX();
            this.y=personaje.getY();
            this.especial=false;
            this.radio=10;
            this.velocidad=5;
            this.activo=false;
            this.direccion=0;
            this.colision=false;
            this.gifProy = Herramientas.cargarImagen("images/explosiones/ProyectilAzul1.gif");
        }

        public void posicionActual(Personaje personaje) {
            this.x=personaje.getX();
            this.y=personaje.getY();
            if(personaje.imagenActual==personaje.QuietoDerecha||personaje.imagenActual == personaje.Derecha) this.direccion=1;
            if(personaje.imagenActual==personaje.QuietoIzquierda||personaje.imagenActual == personaje.Izquierda) this.direccion=2;
            if(personaje.imagenActual==personaje.QuietoArriba||personaje.imagenActual == personaje.Arriba) this.direccion=3;
            if(personaje.imagenActual==personaje.QuietoAbajo||personaje.imagenActual == personaje.Abajo) this.direccion=4;

        }
        


        public void moverPoder(Personaje personaje) {                                //MOVIMIENTO DEL DISPARO NORMAL
            if (this.activo==true && this.direccion==1 ) this.x += velocidad;
            if (this.activo==true && this.direccion==2 ) this.x -= velocidad;
            if (this.activo==true && this.direccion==3 ) this.y -= velocidad;
            if (this.activo==true && this.direccion==4 ) this.y += velocidad;
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
       public void dibujarseDisparo(Entorno entorno){
    	
       	if (this.direccion == 1)entorno.dibujarImagen(gifProy, x, y, 0);
       	if (this.direccion == 2)entorno.dibujarImagen(gifProy, x, y, 3);
       	if (this.direccion == 3)entorno.dibujarImagen(gifProy, x, y, 4.7);
       	if (this.direccion == 4)entorno.dibujarImagen(gifProy, x, y, 8);
       	
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