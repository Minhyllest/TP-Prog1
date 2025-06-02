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
    int seleccion = 0;

    public Image fondo;
    public Entorno entorno;
   
    public Murcielago[] murci = new Murcielago[10];
    public Poderes[] disparo = new Poderes[100];
    public int contadorDisparo;
    public Obstaculo[] obstaculos = new Obstaculo[3];
    

    public int indiceBomba = 0;
    public Pocion[] pociones = new Pocion[20];
    private int contadorPociones = 0;
    public Bomba[] bombasInfinitas = new Bomba[10];
    public Bomba[] bombasConMana = new Bomba[10];
    public int indiceBombaInf = 0;
    public int indiceBombaMana = 0;
    
    

    Bomba[] bombas;
    Boton[] botones;
    int bombaSeleccionada = 0;
    Personaje personaje;

    Boton botonBombaMana;
    Boton botonBombaInfinita;
    Barra barraVida;
    Barra barraMana;
    Bomba bombaInfinita;
    Bomba bombaConMana;
    int tipoBombaSeleccionada = 0; // 0 = infinita, 1 = con maná
  
    Juego() {
        this.entorno = new Entorno(this, "funger arcade", 1280, 720);
        this.fondo = Herramientas.cargarImagen("images/fondo.jpg").getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        this.bombas = new Bomba[20]; //
        bombas = new Bomba[20];
        bombasInfinitas = new Bomba[10];
        bombasConMana = new Bomba[10];

        // Imágenes de explosiones
        Image gifInfinita = Herramientas.cargarImagen("images/explosiones/explAzul.gif");
        Image gifMana = Herramientas.cargarImagen("images/explosiones/expl_02_.gif");
        
        // Bombas de referencia
        bombaInfinita = new Bomba(gifInfinita, 60, false, 0);
        bombaConMana = new Bomba(gifMana, 100, true, 30);

        // Botones
        botonBombaInfinita = new Boton(1200, 100, 145, 40, "Bomba Infinita", Color.BLUE);
        botonBombaMana = new Boton(1200, 150, 150, 40, "Bomba Con Mana", Color.RED);

        // Inicializar array de bombas
        for (int i = 0; i < bombas.length; i++) {
            bombas[i] = new Bomba(gifInfinita, 80, false, 0); // luego se sobreescriben al lanzar
        }
        
        
        

        this.entorno.iniciar();
    }

    public void dibujarBarraMana(Entorno entorno) {
        int x = 50;
        int y = 30;
        int ancho = 200;
        int alto = 20;
        int manaActual = personaje.getMana();
        int manaMaximo = personaje.getManaMaximo();
        double porcentaje = (double) manaActual / manaMaximo;
        int anchoActual = (int) (ancho * porcentaje);
        entorno.dibujarRectangulo(x + ancho / 2, y, ancho, alto, 0, Color.WHITE);
        entorno.dibujarRectangulo(x + anchoActual / 2, y, anchoActual, alto - 4, 0, Color.CYAN);
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
                    barraMana = new Barra(150, 70, 200, 20, Color.BLUE, Color.DARK_GRAY, personaje.getManaMaximo());

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
                barraVida.setValor(personaje.getVida());
                barraMana.setValor(personaje.getMana());
                barraVida.dibujar(entorno);
                barraMana.dibujar(entorno);

                botonBombaMana.dibujar(entorno);
                botonBombaInfinita.dibujar(entorno);
                
                
                if (tipoBombaSeleccionada == 0) { // Bomba infinita
                    
                    indiceBombaInf++;
                    if (indiceBombaInf >= bombasInfinitas.length) indiceBombaInf = 0;
                } else if (tipoBombaSeleccionada == 1 && barraMana.valorMaximo >= 20) { // Bomba con maná
                    
                    barraMana.valorMaximo -= 20; // gasto de maná
                    indiceBombaMana++;
                    if (indiceBombaMana >= bombasConMana.length) indiceBombaMana = 0;
                }

                // --- DIBUJAR BARRA DE VIDA ---
                int vidaActual = personaje.getVida();  // asumirás luego que se implementa
                int vidaMax = personaje.getVidaMaxima();  // lo mismo
                int largoVida = (int)((vidaActual / (double)vidaMax) * 200); // largo proporcional
                entorno.dibujarRectangulo(100, 30, 200, 20, 0, Color.GRAY); // fondo
                entorno.dibujarRectangulo(100 - (200 - largoVida) / 2, 30, largoVida, 20, 0, Color.RED); // barra

                // --- DIBUJAR BARRA DE MANA ---
                int manaActual = personaje.getMana();
                int manaMax = personaje.getManaMaximo();
                int largoMana = (int)((manaActual / (double)manaMax) * 200);
                entorno.dibujarRectangulo(100, 60, 200, 20, 0, Color.DARK_GRAY);
                entorno.dibujarRectangulo(100 - (200 - largoMana) / 2, 60, largoMana, 20, 0, Color.BLUE);

                // Opcional: etiquetas
                entorno.cambiarFont("Arial", 14, Color.white, 1);;
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
        				
        				System.out.println(murci[i].isAtacando());
        					}
        				}
        			for (int i1 = 0; i1 < murci.length; i1++) {
        			    if (murci[i1] != null && personaje.colisionaCon(murci[i1])) {
        			        personaje.recibirDanio(10); // o la cantidad de daño que quieras
        			        // podés hacer que el murciélago desaparezca:
        			        murci[i1] = null;
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
                
                
                
                
                
             // Obtener posición del mouse
                int mouseX = entorno.mouseX();
                int mouseY = entorno.mouseY();

                // Si se presionó clic izquierdo
                if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
                    // Verifica que no se haya hecho clic en los botones
                    if (!botonBombaMana.fueClickeado(mouseX, mouseY) && !botonBombaInfinita.fueClickeado(mouseX, mouseY)) {
                        Bomba referencia = (tipoBombaSeleccionada == 0) ? bombaInfinita : bombaConMana;

                        if (referencia.getCostoMana() == 0 || personaje.getMana() >= referencia.getCostoMana()) {
                            for (int i = 0; i < bombas.length; i++) {
                                int idx = (indiceBomba + i) % bombas.length;
                                if (!bombas[idx].estaActiva()) {
                                    String ruta = tipoBombaSeleccionada == 0
                                        ? "images/explosiones/explAzul.gif"
                                        : "images/explosiones/explRoja.gif";
                                    Image img = Herramientas.cargarImagen(ruta);

                                    bombas[idx] = new Bomba(img, referencia.getRadioExplosion(), referencia.usaMana(), referencia.getCostoMana());
                                    bombas[idx].activar(mouseX, mouseY, personaje);

                                    indiceBomba = (idx + 1) % bombas.length;

                                    if (referencia.getCostoMana() > 0) {
                                        personaje.gastarMana(referencia.getCostoMana());
                                    }
                                    break;
                                }
                            }
                        } else {
                            // Si no hay maná suficiente, cambiar a bomba infinita
                            tipoBombaSeleccionada = 0;
                            botonBombaMana.setSeleccionado(false);
                            botonBombaInfinita.setSeleccionado(true);
                        }
                    }
                }
                
                for (Bomba b : bombas)
                    if (b != null && b.estaActiva()) b.dibujar(entorno);

                // Dibujar bombas activas
                for (Bomba b : bombasInfinitas)
                    if (b != null && b.estaActiva())
                        b.dibujar(entorno);

                for (Bomba b : bombasConMana)
                    if (b != null && b.estaActiva())
                        b.dibujar(entorno);

                // Aplicar daño a murciélagos por bombas
                for (Bomba bomba : bombasInfinitas) {
                    if (bomba != null && bomba.estaActiva()) {
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

                for (Bomba bomba : bombasConMana) {
                    if (bomba != null && bomba.estaActiva()) {
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
                for (Bomba bomba : bombas) {
                    if (bomba != null && bomba.estaActiva()) {
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
                // Mostrar y recolectar pociones activas
                for (int i = 0; i < contadorPociones; i++) {
                    if (pociones[i] != null && pociones[i].estaActiva()) {
                        pociones[i].dibujar(entorno);

                        if (pociones[i].colisionaCon(personaje)) {
                            personaje.recuperarMana(20); // o la cantidad que quieras
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
               

                

                personaje.dibujar(entorno);
                barraVida.setValor(personaje.getVida());
                barraMana.setValor(personaje.getMana());
                barraVida.dibujar(entorno);
                barraMana.dibujar(entorno);
                barraVida.restar(personaje.getVida());
                barraVida.dibujar(entorno);

             // Dibujar barra de maná
                dibujarBarraMana(entorno);
              
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






















