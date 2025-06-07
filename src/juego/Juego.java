package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

public class Juego extends InterfaceJuego {
    enum EstadoJuego { MENU, SELECCION_PERSONAJE, JUGANDO, FIN, GANASTE }
    EstadoJuego estado = EstadoJuego.MENU;

    Menu menu = new Menu();   
    int seleccion = 0;
    int contadorMurci=0;
    public Image fondo;
    public Entorno entorno;
   
    public Murcielago[] murci = new Murcielago[50];
    public Poderes[] disparo = new Poderes[100];
    public Image gifInfinita;	
    public Obstaculo[] obstaculos = new Obstaculo[3];
    public Pocion[] pociones = new Pocion[20];
    private int contadorPociones = 0;
    public Bomba[] bombas = new Bomba[20];
    Personaje personaje;
    Boton botonBomba;
    Boton botonDisparo;
    Barra barraVida;
    Barra barraMana;
    
    Juego() {
        this.entorno = new Entorno(this, "funger arcade", 1280, 720);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg").getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        botonDisparo = new Boton(1200, 100, 145, 40, "Disparo Infinita", Color.BLUE);
        botonBomba = new Boton(1200, 150, 150, 40, "Bomba Con Mana", Color.RED);

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
                    barraVida = new Barra(150, 40, 200, 20, Color.RED, Color.DARK_GRAY, personaje.getVida());
                    barraMana = new Barra(150, 70, 200, 20, Color.BLUE, Color.DARK_GRAY, personaje.getMana());

                    for (int i = 0; i < murci.length; i++) {
                        int lado = (int) (Math.random() * 4 + 1);
                        int x = (lado == 1 || lado == 4) ? (int) (Math.random() * 1280) : (lado == 2 ? 0 : 1280);
                        int y = (lado == 2 || lado == 3) ? (int) (Math.random() * 720) : (lado == 1 ? 0 : 720);
                        murci[i] = new Murcielago(x, y);
                    }

                    obstaculos[0] = new Obstaculo(400, 300);
                    obstaculos[1] = new Obstaculo(600, 500);
                    obstaculos[2] = new Obstaculo(800, 200);

                    estado = EstadoJuego.JUGANDO;
                }
                break;

            case JUGANDO:
                entorno.dibujarImagen(this.fondo, 640, 360, 0);

                if (personaje == null) break;

                personaje.dibujar(entorno);              
                barraVida.dibujar(entorno);
                barraMana.dibujar(entorno);
                botonBomba.dibujar(entorno);
                botonDisparo.dibujar(entorno);
               
                if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)&& botonDisparo.fueClickeado(entorno)) {
                	botonDisparo.setSeleccionado(true);
                	botonBomba.setSeleccionado(false);
                }
                if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)&&botonBomba.fueClickeado(entorno)) {
                	botonDisparo.setSeleccionado(false);
                	botonBomba.setSeleccionado(true);                	
                }
                // Dibujar y aplicar efecto de los obstáculos
                 for (Obstaculo o : obstaculos) {
                    if (o != null) {
                    	if (o.colisionaCon(personaje)) {
                            personaje.retroceder(); // impide que lo atraviese
                        }
                        o.dibujar(entorno);
                        o.dibujarDebug(entorno);
                    }
                }              
                //Dibujar murcielagos y sus funciones
                for (int i=0;i<murci.length;i++) {
        			if(murci[i]!=null) {
                	murci[i].dibujarse(entorno);
        			murci[i].seguirMago(personaje);
        			if(murci[i].colision(personaje)==true ) {	
        				personaje.recibirDanio();
        				
        			   this.personaje.setEsAtacado(false);
        			   barraVida.setValor(personaje.getVida());
        				}
        			}	
                } 
  
                //Dibujar disparo y funciones
                if (entorno.sePresionoBoton(entorno.BOTON_DERECHO) && botonDisparo.estaSeleccionado()) {
                    // Buscar primer disparo disponible (null o no activo)
                    for (int i = 0; i < disparo.length; i++) {
                        if (disparo[i] == null || !disparo[i].activo) {
                            disparo[i] = new Poderes(personaje, true, 20);
                            disparo[i].posicionActual(personaje);
                            disparo[i].activo=true;
                            break; // Solo un disparo por tecla
                        }
                    }
                }
             // Actualizar y dibujar disparos
                for (int i = 0; i < disparo.length; i++) {
                    if (disparo[i] != null && disparo[i].activo) {
                        disparo[i].dibujarseDisparo(entorno);
                        disparo[i].moverPoder(personaje); 
                        // Verificar colisión con cualquier murciélago
                        for (int j = 0; j < murci.length; j++) {
                            if (murci[j] != null && disparo[i].colision(murci[j])) {
                            	Pocion nuevaPocion = murci[j].generarPocion();
                                if (nuevaPocion != null && contadorPociones < pociones.length) {
                                    pociones[contadorPociones++] = nuevaPocion;
                                }                            
                            	murci[j] = null;  // Eliminar murciélago
                                disparo[i] = null; // Eliminar disparo
                                contadorMurci++;
                                break;
                            }
                        }  
                        // Eliminar si sale de pantalla 
                        if (disparo[i] != null && (disparo[i].x < 0 || disparo[i].x > 1280
                        		|| disparo[i].y < 0 || disparo[i].y > 720	)) {
                            disparo[i] = null;}         
                    }
                }
                //Bombas y funcionamientos
                if (botonBomba.estaSeleccionado()&& entorno.sePresionoBoton(entorno.BOTON_DERECHO)&&personaje.getMana()>=20) {               	
                	for(int i=0;i<bombas.length;i++) {
                        if (bombas[i] == null || !bombas[i].estaActiva()) {
                        	bombas[i] = new Bomba();
                            bombas[i].activar(entorno);
                            personaje.gastarMana();
                            barraMana.setValor(personaje.getMana());                           
                          System.out.println("bla");
                          botonBomba.setSeleccionado(false);
                          break;
                            } 		              	
                	}
                }	
                for (Bomba b : bombas) {
                  if (b != null && b.estaActiva()) {
                	  b.dibujar(entorno);
                	 
                	  for (int j = 0; j < murci.length; j++) {
                          if (murci[j] != null && b.colision(murci[j])) {
                          	Pocion nuevaPocion = murci[j].generarPocion();
                              if (nuevaPocion != null && contadorPociones < pociones.length) {
                                  pociones[contadorPociones++] = nuevaPocion;
                              } 
                              contadorMurci++;
                          	murci[j] = null;  // Eliminar murciélago
                              b = null; // Eliminar disparo
                              break;
                          }
                	  }
                  }
                }
                // Mostrar y recolectar pociones activas
                for (int i = 0; i < pociones.length; i++) {
                    if (pociones[i] != null && pociones[i].estaActiva()) {
                        pociones[i].dibujar(entorno);

                        if (pociones[i].colisionaCon(personaje)) {
                        	personaje.recuperarMana(); // o la cantidad que quieras
                        	personaje.recuperarVida();
                        	barraVida.setValor(personaje.getVida());
                        	barraMana.setValor(personaje.getMana());
                            pociones[i].recolectar();
                        }

                    }
                }

                 // Movimiento del personaje
                int dx = 0;
                int dy = 0;

                if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) dx -= personaje.getVelocidad();
                if (entorno.estaPresionada(entorno.TECLA_DERECHA)) dx += personaje.getVelocidad();
                if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) dy -= personaje.getVelocidad();
                if (entorno.estaPresionada(entorno.TECLA_ABAJO)) dy += personaje.getVelocidad();

                if (dx != 0 || dy != 0) {
                    personaje.moverYDetectarColision(dx, dy, obstaculos);
                } else {
                    personaje.quedarseQuieto();
                }
               if(personaje.getVida()==0) {
               	estado = EstadoJuego.FIN;
               }

               	if(contadorMurci == murci.length) {
               		estado = EstadoJuego.GANASTE;
               	}
               	System.out.println(contadorMurci);
               
               
                break;
            
            case FIN:
            	
            	entorno.cambiarFont("Century Gothic", 50, Color.WHITE);
                entorno.escribirTexto("GAME OVER", 480, 350);
                break;
            case GANASTE:      	 
            	entorno.cambiarFont("Century Gothic", 50, Color.WHITE);
                entorno.escribirTexto("HAS GANADO", 480, 350);
                break;
                
        }
      
    }

    public static void main(String[] args) {
        try {
            new Juego();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}