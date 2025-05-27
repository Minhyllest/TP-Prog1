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
	

	
	public Image fondo; 
    public Entorno entorno;
    public Personaje personaje;
    public Murcielago[] Murci= new Murcielago [10];
    public Poderes[] disparo = new Poderes [100];
    public int contador;
    public Obstaculo[] obstaculos = new Obstaculo[3];


    
    Juego() {
        int alto = 1280, ancho = 720;
        this.entorno = new Entorno(this, "funger arcade", alto, ancho);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg").getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        this.entorno.iniciar();
       
        
     
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

                if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) || entorno.sePresiono(entorno.TECLA_DERECHA)) {
                    seleccion = 1 - seleccion;
                    menu.reiniciarFade();
                }

                if (entorno.sePresiono(entorno.TECLA_ENTER)) {
                    String tipo = (seleccion == 0) ? "enki" : "ossaa";
                    int alto = (seleccion == 0) ? 111 : 120;
                    int ancho = (seleccion == 0) ? 52 : 70;
                    personaje = new Personaje(600, 400, tipo, alto, ancho);

                    // Inicializar obstáculos
                    obstaculos[0] = new Obstaculo(400, 300);
                    obstaculos[1] = new Obstaculo(600, 500);
                    obstaculos[2] = new Obstaculo(800, 200);

                    // Inicializar murciélagos
                    for (int i = 0; i < 10; i++) {
                        int lado = (int)(Math.random() * 4 + 1);
                        switch (lado) {
                            case 1: Murci[i] = new Murcielago((int)(Math.random() * 800), 0); break;
                            case 2: Murci[i] = new Murcielago(0, (int)(Math.random() * 600)); break;
                            case 3: Murci[i] = new Murcielago(800, (int)(Math.random() * 600)); break;
                            case 4: Murci[i] = new Murcielago((int)(Math.random() * 800), 600); break;
                        }
                    }

                    estado = EstadoJuego.JUGANDO;
                }

                break;

            case JUGANDO:
                // Dibujar fondo
                entorno.dibujarImagen(this.fondo, 640, 360, 0);

                // Dibujar y aplicar efecto de los obstáculos
                for (Obstaculo o : obstaculos) {
                    if (o != null) {
                        o.dibujar(entorno);
                        o.aplicarEfecto(personaje);
                    }
                }

                // Movimiento del personaje
                boolean seMovio = false;
                if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
                    personaje.moverIzquierda();
                    seMovio = true;
                }
                if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
                    personaje.moverDerecha();
                    seMovio = true;
                }
                if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
                    personaje.moverArriba();
                    seMovio = true;
                }
                if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
                    personaje.moverAbajo();
                    seMovio = true;
                }
                for (Obstaculo o : obstaculos) {
                    if (o != null && o.colisionaCon(personaje)) {
                        personaje.retroceder();
                    }
                if (!seMovio) personaje.quedarseQuieto();

                personaje.dibujar(entorno);
                break;
                }
        }
    }
    @SuppressWarnings("unused")
    // ✅ Fuera de tick()
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}