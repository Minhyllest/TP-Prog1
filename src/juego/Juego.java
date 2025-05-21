package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;

public class Juego extends InterfaceJuego {
	enum EstadoJuego { MENU, SELECCION_PERSONAJE, JUGANDO }
	EstadoJuego estado = EstadoJuego.MENU;

	Menu menu = new Menu();
	int seleccion = 0; // 0 = Enki, 1 = O'saa
	Obstaculo[] obstaculos;


	public Image fondo; 
    public Entorno entorno;
    public Personaje personaje;
    public Murcielago[] Murci= new Murcielago [10];
    public Poderes[] disparo = new Poderes [100];
    
    Juego() {
        int alto = 1280, ancho = 720;
        this.entorno = new Entorno(this, "funger arcade", alto, ancho);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg")
                                 .getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        this.entorno.iniciar(); 
        obstaculos = new Obstaculo[] {
            new Obstaculo(400, 300, 60, 60),
            new Obstaculo(50, 750, 60, 60),
            new Obstaculo(60, 40, 60, 60),
            new Obstaculo(600, 450, 60, 60),
            new Obstaculo(1000, 50, 60, 60),
            new Obstaculo(850, 150, 60, 60),
        };
    }

    
    public void tick() {
        switch (estado) {
            case MENU:
                menu.dibujarMenuPrincipal(entorno);
                if (entorno.sePresiono(entorno.TECLA_ENTER))
                    estado = EstadoJuego.SELECCION_PERSONAJE;
                break;

            case SELECCION_PERSONAJE:
                menu.dibujarSeleccionPersonaje(entorno, seleccion);
                if (entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono(entorno.TECLA_ABAJO))
                    seleccion = 1 - seleccion;

                if (entorno.sePresiono(entorno.TECLA_ENTER)) {
                    String tipo = (seleccion == 0) ? "enki" : "ossaa";
                    int alto = (seleccion == 0) ? 111 : 120;
                    int ancho = (seleccion == 0) ? 52 : 70;
                    personaje = new Personaje(600, 400, tipo, alto, ancho);

                    for (int i = 0; i < 10; i++) {
                        int lado = (int)(Math.random() * 4 + 1);
                        switch (lado) {
                            case 1: Murci[i] = new Murcielago((int)(Math.random()*800), 0); break;
                            case 2: Murci[i] = new Murcielago(0, (int)(Math.random()*600)); break;
                            case 3: Murci[i] = new Murcielago(800, (int)(Math.random()*600)); break;
                            case 4: Murci[i] = new Murcielago((int)(Math.random()*800), 600); break;
                        }
                    }

                    for (int i = 0; i < 3; i++)
                        disparo[i] = new Poderes(personaje, false, 0);

                    estado = EstadoJuego.JUGANDO;
                }
                break;
        

    	        case JUGANDO:
    	            entorno.dibujarImagen(this.fondo, 640, 360, 0);

    	        int nuevaX = personaje.getX();
    	        int nuevaY = personaje.getY();

    	        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) nuevaX -= personaje.velocidad;
    	        if (entorno.estaPresionada(entorno.TECLA_DERECHA))  nuevaX += personaje.velocidad;
    	        if (entorno.estaPresionada(entorno.TECLA_ARRIBA))    nuevaY -= personaje.velocidad;
    	        if (entorno.estaPresionada(entorno.TECLA_ABAJO))     nuevaY += personaje.velocidad;
    	        
    	        
    	        
    	        boolean hayColision = false;
    	        for (Obstaculo o : obstaculos)
    	            if (o.colisionaCon(nuevaX, nuevaY, personaje.ancho, personaje.alto))
    	                hayColision = true;

    	        if (!hayColision) {
    	            personaje.x = nuevaX;
    	            personaje.y = nuevaY;
    	        } else {
    	            personaje.quedarseQuieto();
    	        }

    	        for (Obstaculo o : obstaculos) o.dibujar(entorno);

    	        for (Murcielago m : Murci) {
    	            m.dibujarse(entorno);
    	            m.seguirMago(personaje);
    	        }

    	        if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
    	            for (Poderes p : disparo) {
    	                if (p != null && !p.activo) {
    	                    p.activo = true;
    	                    p.moverDerecha();
    	                    break;
    	                }
    	            }
    	        }

    	        for (Poderes p : disparo)
    	            if (p != null && p.activo) {
    	                p.dibujarseDisparo(entorno);
    	                p.moverDerecha();
    	            }

    	        boolean seMovio = false;
    	        if (entorno.estaPresionada(entorno.TECLA_ARRIBA))    { personaje.moverArriba(); seMovio = true; }
    	        if (entorno.estaPresionada(entorno.TECLA_ABAJO))     { personaje.moverAbajo();  seMovio = true; }
    	        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) { personaje.moverIzquierda(); seMovio = true; }
    	        if (entorno.estaPresionada(entorno.TECLA_DERECHA))   { personaje.moverDerecha();   seMovio = true; }

    	        if (!seMovio) personaje.quedarseQuieto();

    	        personaje.dibujar(entorno);
    	        break;

    	    }
    	
    }
        


    
    @SuppressWarnings("unused")

    public static void main(String[] args) {
    	
    	
        Juego juego = new Juego();
    }
}
