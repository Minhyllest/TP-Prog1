package juego;

import entorno.Entorno;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.awt.Image;
import entorno.Herramientas;


public class Menu {
    public boolean mostrarSeleccionPersonaje = false;
    float alphaEnkiActual = 0.4f;
    float alphaOsaaActual = 0.4f;
    final float alphaBrillante = 1.0f;
    final float alphaOpaco = 0.4f;
    final float velocidadFade = 0.05f;

    private Image osaa;
    private Image enki;

   
   


    public void dibujarMenuPrincipal(Entorno entorno) {
        entorno.cambiarFont("Century Gothic", 40, Color.WHITE);
        Image menu = Herramientas.cargarImagen("images/Menu.gif");
        entorno.dibujarImagen(menu, 640, 290, 0);
        entorno.escribirTexto("ARCADE", 550, 480);
        entorno.cambiarFont("Century Gothic", 30, Color.WHITE);
        entorno.escribirTexto("Presione ENTER para comenzar", 400, 640);
    }

   
    int marcoX = 440; // posición inicial del marco (Enki)
    int destinoX = 440;
    int alpha = 255;
    int ultimaSeleccion = -1; // para detectar cambios de personaje


    public void dibujarSeleccionPersonaje(Entorno entorno, int seleccion) {
        

    	  osaa = Herramientas.cargarImagen("images/ossa/osaa_name.gif");
          enki = Herramientas.cargarImagen("images/enki/Enki_portrait_name.gif");
          entorno.dibujarImagen(enki, 440, 360, 0);
          entorno.dibujarImagen(osaa, 840, 360, 0);
        // Texto
        entorno.cambiarFont("Century Gothic", 30, Color.WHITE);
        entorno.escribirTexto("Seleccione su personaje", 450, 140);
        
        
        
        
        // Si la selección cambió, actualizar destino
        if (seleccion != ultimaSeleccion) {
            destinoX = (seleccion == 0) ? 440 : 840;
            ultimaSeleccion = seleccion;
            
        }
        

        // Movimiento suave del marco hacia el destino
        marcoX += (destinoX - marcoX) * 0.2;

        // Fade-in del marco
        if (alpha < 255) {
            alpha += 10;
            if (alpha > 255) alpha = 255;
        }

        // Dibujar marco
        Image marco = crearMarcoConAlpha(240, 450, alpha / 255f);
        entorno.dibujarImagen(marco, marcoX, 380, 0);
    }

    
    

    public Image crearMarcoConAlpha(int ancho, int alto, float alpha) {
        BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(0, 0, ancho - 1, alto - 1); // borde sin relleno
        g2d.dispose();
        return img;
        
    }

    public void reiniciarFade() {
        this.alpha = 0; // reinicia la opacidad para el nuevo fade-in
    }

    


    public void setMostrarSeleccionPersonaje(boolean mostrar) {
        this.mostrarSeleccionPersonaje = mostrar;
    }

    public boolean estaSeleccionandoPersonaje() {
        return mostrarSeleccionPersonaje;
    }
}


