package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

public class Juego extends InterfaceJuego {
	enum EstadoJuego { MENU, SELECCION_PERSONAJE, JUGANDO }
	EstadoJuego estado = EstadoJuego.MENU;

	Menu menu = new Menu();
	int seleccion = 0; // 0 = Enki, 1 = O'saa
	

	
	public Image fondo; 
    public Entorno entorno;
    public Personaje personaje;
    public Murcielago[] murci= new Murcielago [10];
    public Poderes[] disparo = new Poderes [100];
    public int contadorDisparo;
    public Obstaculo[] obstaculos = new Obstaculo[3];
    public Bomba[] bombas = new Bomba[10]; 
    public int indiceBomba = 0;
    public Pocion[] pociones = new Pocion[20];
    private int contadorPociones = 0; 
    Boton botonBombaMana;
    Boton botonBombaInfinita;
    Bomba[] bombasMana = new Bomba[10];
    Bomba[] bombasInfinitas = new Bomba[10];
    Barra barraVida;
    Barra barraMana;

    int tipoBombaSeleccionada = 0; // 0 = infinita, 1 = con maná

    Juego() {
        int alto = 1280, ancho = 720;
        this.entorno = new Entorno(this, "funger arcade", alto, ancho);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg").getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        this.entorno.iniciar(); 
        for (int i = 0; i < bombas.length; i++) {
            if (i % 2 == 0) {
                // Bomba infinita: radio 60, sin maná, animación azul
                bombas[i] = new Bomba(60, 0, "images/explosiones/explAzul.gif", false);
            } else {
                // Bomba que consume maná: radio 100, cuesta 30 MP, animación roja
                bombas[i] = new Bomba(100, 30, "images/explosiones/explRoja.gif", true);
            }
        }

        botonBombaMana = new Boton(1200, 100, 100, 40, "Bomba MP");
        botonBombaInfinita = new Boton(1200, 160, 100, 40, "Bomba ∞");
        botonBombaInfinita.setSeleccionado(true); // por defecto


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
                    barraVida = new Barra(150, 40, 200, 20, Color.RED, Color.DARK_GRAY, personaje.getVidaMaxima());
                    barraMana = new Barra(150, 70, 200, 20, Color.BLUE, Color.DARK_GRAY, personaje.getManaMaxima());

                    // Inicializar obstáculos
                    obstaculos[0] = new Obstaculo(400, 300);
                    obstaculos[1] = new Obstaculo(600, 500);
                    obstaculos[2] = new Obstaculo(800, 200);
                    
                    
                    
                    // Inicializar murciélagos
                    for (int i = 0; i < murci.length; i++) {
                        int lado = (int)(Math.random() * 4 + 1);
                        switch (lado) {
                            case 1: murci[i] = new Murcielago((int)(Math.random() * 1280), 0); break;
                            case 2: murci[i] = new Murcielago(0, (int)(Math.random() * 720)); break;
                            case 3: murci[i] = new Murcielago(1280, (int)(Math.random() * 720)); break;
                            case 4: murci[i] = new Murcielago((int)(Math.random() * 1280), 720); break;
                        }
                    }
                    // Inicializar disparos

                    estado = EstadoJuego.JUGANDO;
                }

                break;

            case JUGANDO:
                // Dibujar fondo
                entorno.dibujarImagen(this.fondo, 640, 360, 0);
                
                
                
             // --- DIBUJAR BARRA DE VIDA ---
                int vidaActual = personaje.getVida();  // asumirás luego que se implementa
                int vidaMax = personaje.getVidaMax();  // lo mismo
                int largoVida = (int)((vidaActual / (double)vidaMax) * 200); // largo proporcional
                entorno.dibujarRectangulo(100, 30, 200, 20, 0, Color.GRAY); // fondo
                entorno.dibujarRectangulo(100 - (200 - largoVida) / 2, 30, largoVida, 20, 0, Color.RED); // barra

                // --- DIBUJAR BARRA DE MANA ---
                int manaActual = personaje.getMana();
                int manaMax = personaje.getManaMax();
                int largoMana = (int)((manaActual / (double)manaMax) * 200);
                entorno.dibujarRectangulo(100, 60, 200, 20, 0, Color.DARK_GRAY);
                entorno.dibujarRectangulo(100 - (200 - largoMana) / 2, 60, largoMana, 20, 0, Color.BLUE);

                // Opcional: etiquetas
                entorno.cambiarFont(Herramientas.Fuente("Arial", 14, 1));
                entorno.escribirTexto("Vida", 10, 35);
                entorno.escribirTexto("Mana", 10, 65);

                
                
                
                
                

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
        				
        			//	System.out.println(murci[i].isAtacando());
        					}
        				}
        			
                }
                
                
                //Dibujar disparo y funciones

                if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
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
                                break;
                            }
                        }
                        
                        // Eliminar si sale de pantalla (ejemplo)
                        if (disparo[i] != null && (disparo[i].x < 0 || disparo[i].x > 1280
                        		|| disparo[i].y < 0 || disparo[i].y > 720	)) {
                            disparo[i] = null;
                        }
                    }
                }
                
             // Dibujar botones
                botonBombaMana.dibujar(entorno);
                botonBombaInfinita.dibujar(entorno);

                // Detectar clic en los botones
                if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
                    int mx = entorno.mouseX();
                    int my = entorno.mouseY();

                    if (botonBombaMana.fueClickeado(mx, my)) {
                        tipoBombaSeleccionada = 1;
                        botonBombaMana.setSeleccionado(true);
                        botonBombaInfinita.setSeleccionado(false);
                    } else if (botonBombaInfinita.fueClickeado(mx, my)) {
                        tipoBombaSeleccionada = 0;
                        botonBombaMana.setSeleccionado(false);
                        botonBombaInfinita.setSeleccionado(true);
                    }
                }
                
                
                
                
                
///////////////if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
    int mouseX = entorno.mouseX();
    int mouseY = entorno.mouseY();

    for (int i = 0; i < bombas.length; i++) {
        int idx = (indiceBomba + i) % bombas.length;

        if (!bombas[idx].estaActiva()) {
            boolean activada = bombas[idx].activar(mouseX, mouseY, personaje); // <-- se pasa el personaje
            if (activada) {
                indiceBomba = (idx + 1) % bombas.length;
            }
            break;
        }
    }
}



                for (Bomba b : bombasInfinitas)
                    if (b.estaActiva()) b.dibujar(entorno);

                for (Bomba b : bombasMana)
                    if (b.estaActiva()) b.dibujar(entorno);

                for (Bomba bomba : bombasInfinitas) {
                    if (bomba.estaActiva()) {
                        for (int i = 0; i < murci.length; i++) {
                            if (murci[i] != null && bomba.enRangoExplosion(murci[i].getX(), murci[i].getY())) {
                                Pocion nuevaPocion = murci[i].generarPocion();
                                if (nuevaPocion != null && contadorPociones < pociones.length)
                                    pociones[contadorPociones++] = nuevaPocion;
                                murci[i] = null;
                            }
                        }
                    }
                }
               


              
              
    
////////////////////////////////////////////////////////////////////////////////////////////////////////
                //Pociones 
                for (int i = 0; i < contadorPociones; i++) {
                    if (pociones[i] != null && pociones[i].estaActiva()) {
                        pociones[i].dibujar(entorno);
                        
                        if (pociones[i].colisionaCon(personaje)) {
                            // Efecto al recolectar (ej: curar 20 puntos de vida)
                     //       personaje.curar(20);
                            pociones[i].recolectar();
                        }                    }
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
                barraVida.setValor(personaje.getVida());
                barraMana.setValor(personaje.getMana());

                barraVida.dibujar(entorno);
                barraMana.dibujar(entorno);

                personaje.dibujar(entorno);
                break;
                }

}

    
    @SuppressWarnings("unused")
    // ✅ Fuera de tick()
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}